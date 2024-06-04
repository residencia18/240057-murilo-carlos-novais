import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { Firestore, addDoc, collection, deleteDoc, doc, getDoc, getDocs, query, updateDoc, where } from '@angular/fire/firestore';
import { getStorage, ref } from '@firebase/storage';
import { deleteObject, uploadBytesResumable } from '@angular/fire/storage';
import { sha256 } from 'js-sha256';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private usersCollection = collection(this.firestore, 'users');
  private readonly storage = getStorage();

  constructor(
    private firestore: Firestore,
    private authService: AuthService
  ) {}

  async getAll() {
    try {
      const userDocs = await getDocs(query(this.usersCollection));
      const users = userDocs.docs.map(async user => {
        const trainingsCollection = collection(this.firestore, 'users', user.id, 'trainings');
        const trainingsDocs = await getDocs(trainingsCollection);
        const trainings = trainingsDocs.docs.map(doc => doc.data());
        return { ...user.data(), id: user.id, trainings };
      });

      return Promise.all(users);
    } catch (error) {
      console.error('Error fetching users:', error);
      throw error;
    }
  }

  async getById(id: string) {
    try {
      const userDoc = await getDoc(doc(this.firestore, 'users', id));
      return userDoc.data();
    } catch (error) {
      console.error(`Error fetching user with ID ${id}:`, error);
      throw error;
    }
  }

  async getByLoginId(id: string) {
    try {
      const userDocs = await getDocs(query(this.usersCollection, where('loginId', '==', id)));
      const user = userDocs.docs.map(userDoc => ({ ...userDoc.data(), id: userDoc.id }))[0];
      return user;
    } catch (error) {
      console.error(`Error fetching user with login ID ${id}:`, error);
      throw error;
    }
  }

  async getFilesById(id: string) {
    try {
      const trainingsCollection = collection(this.firestore, 'users', id, 'trainings');
      const trainingsDocs = await getDocs(trainingsCollection);
      const trainings = trainingsDocs.docs.map(training => ({ ...training.data(), id: training.id }));
      return trainings;
    } catch (error) {
      console.error(`Error fetching trainings for user ID ${id}:`, error);
      throw error;
    }
  }

  async create(user: any) {
    try {
      const response = await this.authService.signupUser(user.mail);
      if (!response.success) {
        return response;
      }

      user.loginId = response.uid;
      await this.authService.sendRecoveryPassword(user.mail);
      return await addDoc(this.usersCollection, user);
    } catch (error) {
      console.error('Error creating user:', error);
      throw error;
    }
  }

  async update(id: string, user: any) {
    try {
      const userDoc = doc(this.usersCollection, id);
      return await updateDoc(userDoc, user);
    } catch (error) {
      console.error(`Error updating user with ID ${id}:`, error);
      throw error;
    }
  }

  async delete(id: string) {
    try {
      const userDoc = doc(this.usersCollection, id);
      return await deleteDoc(userDoc);
    } catch (error) {
      console.error(`Error deleting user with ID ${id}:`, error);
      throw error;
    }
  }

  async createTraining(userId: string, file: any) {
    try {
      const filesCollection = collection(this.firestore, 'users', userId, 'trainings');
      await addDoc(filesCollection, file);
    } catch (error) {
      console.error(`Error creating training for user ID ${userId}:`, error);
      throw error;
    }
  }

  uploadFile(id: string, file: any) {
    const hashedName = this.generateFileHash(`${file.name}_${new Date().getTime()}_${id}`);
    const filePath = `trainings/${id}/${hashedName}`;
    const storageRef = ref(this.storage, filePath);

    return uploadBytesResumable(storageRef, file);
  }

  async deleteTraining(userId: string, trainingId: string) {
    try {
      const filesCollection = collection(this.firestore, 'users', userId, 'trainings');
      const trainingDoc = doc(filesCollection, trainingId);
      return await deleteDoc(trainingDoc);
    } catch (error) {
      console.error(`Error deleting training with ID ${trainingId} for user ID ${userId}:`, error);
      throw error;
    }
  }

  async deleteFileObject(id: string, fileName: string) {
    const filePath = `trainings/${id}/${fileName}`;
    const storageRef = ref(this.storage, filePath);

    try {
      await deleteObject(storageRef);
      return { success: true, message: 'Arquivo excluido com sucesso' };
    } catch (error) {
      console.error(`Error deleting file ${fileName} for user ID ${id}:`, error);
      return { success: false, error };
    }
  }

  private generateFileHash(text: string) {
    return sha256(text);
  }
}

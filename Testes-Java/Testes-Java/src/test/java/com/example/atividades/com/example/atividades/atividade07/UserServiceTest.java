package com.example.atividades.atividade07;

import org.junit.Before;
import org.junit.Test;
// import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private Database mockDatabase;
    private UserService userService;

    @Before
    public void setUp() {
        mockDatabase = mock(Database.class);
        userService = new UserService(mockDatabase);
    }

    @Test
    public void testSaveUser_ValidUser() {
        User validUser = new User("John Doe", "john.doe@example.com");

        userService.saveUser(validUser);

        verify(mockDatabase, times(1)).saveUser(validUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveUser_UserWithNoName() {
        User invalidUser = new User("", "john.doe@example.com");

        userService.saveUser(invalidUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveUser_UserWithNoEmail() {
        User invalidUser = new User("John Doe", "");

        userService.saveUser(invalidUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveUser_NullName() {
        User invalidUser = new User(null, "john.doe@example.com");

        userService.saveUser(invalidUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveUser_NullEmail() {
        User invalidUser = new User("John Doe", null);

        userService.saveUser(invalidUser);
    }

    @Test
    public void testSaveUser_ValidUser_DoesNotThrowException() {
        User validUser = new User("John Doe", "john.doe@example.com");

        try {
            userService.saveUser(validUser);
        } catch (IllegalArgumentException e) {
            fail("Unexpected IllegalArgumentException thrown");
        }

        verify(mockDatabase, times(1)).saveUser(validUser);
    }
}

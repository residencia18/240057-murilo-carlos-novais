package com.resitic.clinica.model;

import com.resitic.clinica.controller.forms.PacienteFORM;
import com.resitic.clinica.controller.forms.UpdatePacienteFORM;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private boolean ativo;

	@Embedded
	private Endereco endereco;

	public Paciente(PacienteFORM pacienteFORM) {
		this.nome = pacienteFORM.getNome();
		this.email = pacienteFORM.getEmail();
		this.cpf = pacienteFORM.getCpf();
		this.telefone = pacienteFORM.getTelefone();
		this.endereco = new Endereco(pacienteFORM.getEndereco());
		this.ativo = true;
	}

	public void update(UpdatePacienteFORM pacienteFORM) {
		if (pacienteFORM.getNome() != null)
			this.nome = pacienteFORM.getNome();
		if (pacienteFORM.getTelefone() != null)
			this.telefone = pacienteFORM.getTelefone();
		if (pacienteFORM.getEndereco() != null)
			this.endereco.update(pacienteFORM.getEndereco());
	}

	public void delete() {
		this.ativo = false;
	}
}

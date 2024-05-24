package com.resitic.clinica.model;

import com.resitic.clinica.controller.enums.EspecialidadeENUM;
import com.resitic.clinica.controller.forms.MedicoFORM;
import com.resitic.clinica.controller.forms.UpdateMedicoFORM;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;
	private String email;
	private String crm;
	private String telefone;
	private boolean ativo;

	@Enumerated(EnumType.STRING)
	private EspecialidadeENUM especialidade;

	@Embedded
	private Endereco endereco;

	public Medico(MedicoFORM medicoFORM) {
		this.nome = medicoFORM.getNome();
		this.email = medicoFORM.getEmail();
		this.crm = medicoFORM.getCrm();
		this.especialidade = medicoFORM.getEspecialidade();
		this.endereco = new Endereco(medicoFORM.getEndereco());
		this.telefone = medicoFORM.getTelefone();
		this.ativo = true;
	}

	public void update(UpdateMedicoFORM medicoFORM) {
		if (medicoFORM.getNome() != null)
			this.nome = medicoFORM.getNome();
		if (medicoFORM.getTelefone() != null)
			this.telefone = medicoFORM.getTelefone();
		if (medicoFORM.getEndereco() != null)
			this.endereco.update(medicoFORM.getEndereco());
	}

	public void delete() {
		this.ativo = false;
	}
}

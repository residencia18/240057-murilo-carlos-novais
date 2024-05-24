package com.resitic.clinica.controller.forms;

import com.resitic.clinica.controller.enums.EspecialidadeENUM;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Classe de formulário para a entidade Médico.
 * Utilizada para validação e mapeamento de dados recebidos via API.
 */
public record MedicoFORM(
		@NotBlank(message = "{nome.obrigatorio}")
		String nome,

		@NotBlank(message = "{email.obrigatorio}")
		@Email(message = "{email.invalido}")
		String email,

		@NotBlank(message = "{telefone.obrigatorio}")
		String telefone,

		@NotBlank(message = "{crm.obrigatorio}")
		@Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
		String crm,

		@NotNull(message = "{especialidade.obrigatoria}")
		EspecialidadeENUM especialidade,

		@NotNull(message = "{endereco.obrigatorio}")
		@Valid
		EnderecoFORM endereco
) {
	/**
	 * Construtor que cria um MedicoFORM.
	 * @param nome o nome do médico
	 * @param email o email do médico
	 * @param telefone o telefone do médico
	 * @param crm o CRM do médico
	 * @param especialidade a especialidade do médico
	 * @param endereco o endereço do médico
	 */
	public MedicoFORM(String nome, String email, String telefone, String crm, EspecialidadeENUM especialidade, EnderecoFORM endereco) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.crm = crm;
		this.especialidade = especialidade;
		this.endereco = endereco;
	}
}

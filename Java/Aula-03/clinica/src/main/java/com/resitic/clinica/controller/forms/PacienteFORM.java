package com.resitic.clinica.controller.forms;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Classe de formulário para a entidade Paciente.
 * Utilizada para validação e mapeamento de dados recebidos via API.
 */
public record PacienteFORM(
		@NotBlank(message = "{nome.obrigatorio}")
		String nome,

		@NotBlank(message = "{cpf.obrigatorio}")
		@Pattern(regexp = "\\d{11}", message = "{cpf.invalido}")
		String cpf,

		@NotBlank(message = "{email.obrigatorio}")
		@Email(message = "{email.invalido}")
		String email,

		@NotBlank(message = "{telefone.obrigatorio}")
		String telefone,

		@NotNull(message = "{endereco.obrigatorio}")
		@Valid
		EnderecoFORM endereco
) {
	/**
	 * Construtor que cria um PacienteFORM.
	 * @param nome o nome do paciente
	 * @param cpf o CPF do paciente
	 * @param email o email do paciente
	 * @param telefone o telefone do paciente
	 * @param endereco o endereço do paciente
	 */
	public PacienteFORM(String nome, String cpf, String email, String telefone, EnderecoFORM endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
	}
}

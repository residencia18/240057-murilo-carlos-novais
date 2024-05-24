package com.resitic.clinica.controller.forms;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.resitic.clinica.controller.enums.EspecialidadeENUM;
import com.resitic.clinica.model.Consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

/**
 * Classe de formulário para a entidade Consulta.
 * Utilizada para validação e mapeamento de dados recebidos via API.
 */
public record ConsultaFORM(

		@JsonAlias({"idMedico", "medicoId", "medico_id"})
		@NotNull(message = "O id do médico é obrigatório")
		Long idMedico,

		@JsonAlias({"idPaciente", "pacienteId", "paciente_id"})
		@NotNull(message = "O id do paciente é obrigatório")
		Long idPaciente,

		@JsonAlias({"dt_compra", "dataCompra"})
		@NotNull(message = "A data da consulta é obrigatória")
		@Future(message = "A data da consulta deve estar no futuro")
		@JsonFormat(pattern = "dd/MM

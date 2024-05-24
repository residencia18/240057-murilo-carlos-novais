package com.resitic.clinica.controller.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.resitic.clinica.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	/**
	 * Verifica se o médico está ocupado em uma determinada data.
	 *
	 * @param medicoId o ID do médico
	 * @param data a data da consulta
	 * @return true se o médico estiver ocupado, false caso contrário
	 */
	@Query("""
			SELECT 
			   CASE WHEN COUNT(c) > 0 THEN true ELSE false END 
			   FROM Consulta c 
			   WHERE c.medico.id = :medicoId 
			   AND c.data = :data 
			   AND c.status = 'AGENDADA'
			""")
	boolean isMedicoBusy(Long medicoId, LocalDateTime data);

	/**
	 * Verifica se existe uma consulta agendada para um paciente dentro de um intervalo de datas.
	 *
	 * @param pacienteId o ID do paciente
	 * @param dataAbertura a data de abertura do intervalo
	 * @param dataFechamento a data de fechamento do intervalo
	 * @return true se existir uma consulta, false caso contrário
	 */
	boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime dataAbertura, LocalDateTime dataFechamento);
}

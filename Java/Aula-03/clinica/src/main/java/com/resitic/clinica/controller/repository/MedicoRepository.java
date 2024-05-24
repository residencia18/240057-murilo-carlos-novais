package com.resitic.clinica.controller.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.resitic.clinica.controller.enums.EspecialidadeENUM;
import com.resitic.clinica.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	/**
	 * Encontra todos os médicos ativos paginados.
	 *
	 * @param paginacao informações sobre a paginação
	 * @return uma página de médicos ativos
	 */
	Page<Medico> findAllByAtivoTrue(Pageable paginacao);

	/**
	 * Seleciona aleatoriamente um médico disponível com uma determinada especialidade em uma data específica.
	 *
	 * @param especialidade a especialidade do médico
	 * @param data a data específica
	 * @return um médico disponível aleatoriamente
	 */
	@Query("""
            SELECT m FROM Medico m
            WHERE 
            m.ativo = true
            AND m.especialidade = :especialidade
            AND m.id NOT IN (
                SELECT c.medico.id FROM Consulta c
                WHERE
                c.data = :data
                AND c.status = 'AGENDADA'
                )
            ORDER BY rand()
            LIMIT 1
            """)
	Medico selectRandomMedico(EspecialidadeENUM especialidade, LocalDateTime data);

	/**
	 * Verifica se um médico está ativo com base no ID.
	 *
	 * @param id o ID do médico
	 * @return true se o médico estiver ativo, false caso contrário
	 */
	@Query("""
            SELECT m.ativo
            FROM Medico m
            WHERE m.id = :id
            """)
	Boolean findAtivoById(Long id);
}

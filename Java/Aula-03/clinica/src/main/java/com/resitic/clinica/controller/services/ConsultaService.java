package com.resitic.clinica.controller.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resitic.clinica.controller.DTO.ConsultaDetails;
import com.resitic.clinica.controller.enums.StatusConsultaENUM;
import com.resitic.clinica.controller.forms.CancelConsultaFORM;
import com.resitic.clinica.controller.forms.ConsultaFORM;
import com.resitic.clinica.controller.repository.ConsultaRepository;
import com.resitic.clinica.controller.repository.MedicoRepository;
import com.resitic.clinica.controller.repository.PacienteRepository;
import com.resitic.clinica.controller.validations.CancelConsultaValidator;
import com.resitic.clinica.controller.validations.ConsultaValidator;
import com.resitic.clinica.model.Consulta;
import com.resitic.clinica.model.Medico;
import com.resitic.clinica.model.Paciente;

@Service
public class ConsultaService {

	private final ConsultaRepository consultaRepository;
	private final MedicoRepository medicoRepository;
	private final PacienteRepository pacienteRepository;
	private final List<ConsultaValidator> consultaValidators;
	private final List<CancelConsultaValidator> cancelValidators;

	@Autowired
	public ConsultaService(
			ConsultaRepository consultaRepository,
			MedicoRepository medicoRepository,
			PacienteRepository pacienteRepository,
			List<ConsultaValidator> consultaValidators,
			List<CancelConsultaValidator> cancelValidators
	) {
		this.consultaRepository = consultaRepository;
		this.medicoRepository = medicoRepository;
		this.pacienteRepository = pacienteRepository;
		this.consultaValidators = consultaValidators;
		this.cancelValidators = cancelValidators;
	}

	public ConsultaDetails agendar(ConsultaFORM consultaFORM) {
		assert pacienteRepository.existsById(consultaFORM.id_paciente()) : "Paciente não encontrado!";
		consultaValidators.forEach(v -> v.validate(consultaFORM));
		Medico medico = getRandomMedico(consultaFORM);
		Consulta consulta = new Consulta(
				null,
				medico,
				pacienteRepository.getOne(consultaFORM.id_paciente()),
				consultaFORM.data(),
				StatusConsultaENUM.AGENDADA,
				null
		);
		consultaRepository.save(consulta);
		return new ConsultaDetails(consulta);
	}

	public void cancelar(CancelConsultaFORM cancelamentoFORM) {
		assert consultaRepository.existsById(cancelamentoFORM.id_consulta()) : "Consulta não encontrada!";
		cancelValidators.forEach(v -> v.validate(cancelamentoFORM));
		Consulta consulta = consultaRepository.getOne(cancelamentoFORM.id_consulta());
		LocalDateTime limiteCancelamento = consulta.getData().minusHours(24);
		assert LocalDateTime.now().isBefore(limiteCancelamento) : "A consulta só pode ser cancelada com antecedência mínima de 24 horas!";
		consulta.cancelar(cancelamentoFORM.motivo());
	}

	private Medico getRandomMedico(ConsultaFORM consultaFORM) {
		if (consultaFORM.id_medico() != null) {
			return medicoRepository.getOne(consultaFORM.id_medico());
		}
		assert consultaFORM.especialidade() != null : "A especialidade é obrigatória quando o médico não for informado!";
		return medicoRepository.selectRandomMedico(consultaFORM.especialidade(), consultaFORM.data());
	}
}

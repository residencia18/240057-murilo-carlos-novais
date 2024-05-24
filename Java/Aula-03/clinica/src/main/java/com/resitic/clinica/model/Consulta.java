package com.resitic.clinica.model;

import java.time.LocalDateTime;

import com.resitic.clinica.controller.enums.MotivoENUM;
import com.resitic.clinica.controller.enums.StatusConsultaENUM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = Consulta.TABLE_NAME)
@Entity(name = "Consulta")
public class Consulta {

	public static final String TABLE_NAME = "consultas";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_medico")
	private Medico medico;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;

	private LocalDateTime data;

	@Enumerated(EnumType.STRING)
	private StatusConsultaENUM status;

	@Column(name = "motivo_cancelamento")
	@Enumerated(EnumType.STRING)
	private MotivoENUM motivoCancelamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public StatusConsultaENUM getStatus() {
		return status;
	}

	public void setStatus(StatusConsultaENUM status) {
		this.status = status;
	}

	public MotivoENUM getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(MotivoENUM motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public void realizar() {
		this.status = StatusConsultaENUM.REALIZADA;
	}

	public void cancelar(MotivoENUM motivo) {
		this.status = StatusConsultaENUM.CANCELADA;
		this.motivoCancelamento = motivo;
	}
}

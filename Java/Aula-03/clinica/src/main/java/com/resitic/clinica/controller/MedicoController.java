package com.resitic.clinica.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.resitic.clinica.controller.DTO.MedicoDTO;
import com.resitic.clinica.controller.DTO.MedicoDetails;
import com.resitic.clinica.controller.forms.MedicoFORM;
import com.resitic.clinica.controller.forms.UpdateMedicoFORM;
import com.resitic.clinica.controller.repository.MedicoRepository;
import com.resitic.clinica.model.Medico;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

	private final MedicoRepository repository;

	public MedicoController(MedicoRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<MedicoDetails> cadastrar(@RequestBody @Valid MedicoFORM medicoFORM) {
		Medico medico = new Medico(medicoFORM);
		repository.save(medico);
		MedicoDetails medicoDetails = new MedicoDetails(medico);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicoDetails);
	}

	@GetMapping
	public ResponseEntity<Page<MedicoDTO>> listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
		Page<MedicoDTO> page = repository.findAllByAtivoTrue(paginacao).map(MedicoDTO::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicoDetails> buscar(@PathVariable long id) {
		Medico medico = repository.getReferenceById(id);
		return ResponseEntity.ok(new MedicoDetails(medico));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<MedicoDetails> atualizar(@RequestBody @Valid UpdateMedicoFORM medicoFORM) {
		Medico medico = repository.getReferenceById(medicoFORM.getId());
		medico.update(medicoFORM);
		return ResponseEntity.ok(new MedicoDetails(medico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable long id) {
		Medico medico = repository.getReferenceById(id);
		medico.delete();
		return ResponseEntity.noContent().build();
	}
}

package com.resitic.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.resitic.clinica.controller.DTO.MedicoDTO;
import com.resitic.clinica.controller.DTO.MedicoDetails;
import com.resitic.clinica.controller.forms.MedicoFORM;
import com.resitic.clinica.controller.forms.UpdateMedicoFORM;
import com.resitic.clinica.controller.repository.MedicoRepository;
import com.resitic.clinica.model.Medico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoRepository repository;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid MedicoFORM medicoFORM, UriComponentsBuilder uriBuilder) {
		var medico = new Medico(medicoFORM);
		repository.save(medico);
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new MedicoDetails(medico));
	}
	
	@GetMapping
	public ResponseEntity<Page<MedicoDTO>> listar(@PageableDefault(sort = {"nome"}) Pageable paginacao){
		var page = repository.findAllByAtivoTrue(paginacao).map(MedicoDTO::new);
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<MedicoDetails> buscar(@PathVariable long id) {
		var medico = repository.getReferenceById(id);
		return ResponseEntity.ok(new MedicoDetails(medico));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> atualizar(@RequestBody @Valid UpdateMedicoFORM medicoFORM) {
		var medico = repository.getReferenceById(medicoFORM.id());
		medico.update(medicoFORM);
		
		return ResponseEntity.ok(new MedicoDetails(medico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable long id) {
		var medico = repository.getReferenceById(id);
		medico.delete();
		
		return ResponseEntity.noContent().build();
	}
}

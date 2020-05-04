package br.com.tokiomarine.seguradora.avaliacao.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.service.EstudandeService;

// TODO não esquecer de usar as anotações para criação do restcontroller
@RestController
@RequestMapping("/estudantes")
public class EstudanteRestController {

		
	@Autowired
	private EstudandeService estudandeService;

	// TODO caso você não conheça THEMELEAF faça a implementação dos métodos em forma de RESTCONTROLLER (seguindo o padrão RESTFUL)

	// TODO IMPLEMENTAR CADASTRO DE ESTUDANTES (POST)
	@PostMapping
	public ResponseEntity<Void> salvarEstudante(@RequestBody Estudante estudante) {
		estudandeService.cadastrarEstudante(estudante);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(estudante.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	// TODO IMPLEMENTAR ATUALIZACAO DE ESTUDANTES (PUT)
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarEstudante(@RequestBody Estudante estudante, 
			@PathVariable("id") Long id) {
		estudante.setId(id);
		try {
			estudandeService.atualizarEstudante(estudante);			
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.noContent().build();
	}

	// TODO IMPLEMENTAR A LISTAGEM DE ESTUDANTES (GET)
	@GetMapping
	public ResponseEntity<List<Estudante>> listarEstudante() {
		return ResponseEntity.status(HttpStatus.OK).body(estudandeService.buscarEstudantes());
	}

	// TODO IMPLEMENTAR A EXCLUSÃO DE ESTUDANTES (DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarEstudante(@PathVariable("id") Long id) {
		Estudante estudante = null;
		try {
			estudante = estudandeService.buscarEstudante(id);
			estudandeService.deletarEstudante(estudante);			
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();		
	}
}

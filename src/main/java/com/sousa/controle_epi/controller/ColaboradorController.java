package com.sousa.controle_epi.controller;

import com.sousa.controle_epi.dto.InfosColaboradorDTO;
import com.sousa.controle_epi.dto.RequisitarColaboradorDTO;
import com.sousa.controle_epi.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaboradores") // Todos os endpoints aqui começam com /colaboradores
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @PostMapping
    public ResponseEntity<InfosColaboradorDTO> criarColaborador(@RequestBody RequisitarColaboradorDTO dto) {
        InfosColaboradorDTO colaborador = colaboradorService.criarColaborador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(colaborador);
    }

    @GetMapping
    public ResponseEntity<List<InfosColaboradorDTO>> listarColaboradores() {
        List<InfosColaboradorDTO> lista = colaboradorService.listarColaboradores();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfosColaboradorDTO> buscarColaboradorPorId(@PathVariable Long id) {
        InfosColaboradorDTO colaborador = colaboradorService.buscarColaboradorPorId(id);
        return ResponseEntity.ok(colaborador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InfosColaboradorDTO> atualizarColaborador(@PathVariable Long id, @RequestBody RequisitarColaboradorDTO dto) {
        InfosColaboradorDTO colaboradorAtualizado = colaboradorService.atualizarColaborador(id, dto);
        return ResponseEntity.ok(colaboradorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarColaborador(@PathVariable Long id) {
        colaboradorService.deletarColaborador(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
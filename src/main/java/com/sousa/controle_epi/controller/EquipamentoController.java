package com.sousa.controle_epi.controller;
import com.sousa.controle_epi.dto.InfosEquipamentoDTO;
import com.sousa.controle_epi.dto.RequisitarEquipamentoDTO;
import com.sousa.controle_epi.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @PostMapping
    public ResponseEntity<InfosEquipamentoDTO> criarEquipamento(@RequestBody RequisitarEquipamentoDTO dto) {
        InfosEquipamentoDTO equipamento = equipamentoService.criarEquipamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipamento);
    }

    @GetMapping
    public ResponseEntity<List<InfosEquipamentoDTO>> listarEquipamentos() {
        List<InfosEquipamentoDTO> lista = equipamentoService.listarEquipamentos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfosEquipamentoDTO> buscarEquipamentoPorId(@PathVariable Long id) {
        InfosEquipamentoDTO equipamento = equipamentoService.buscarEquipamentoPorId(id);
        return ResponseEntity.ok(equipamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InfosEquipamentoDTO> atualizarEquipamento(@PathVariable Long id, @RequestBody RequisitarEquipamentoDTO dto) {
        InfosEquipamentoDTO equipamentoAtualizado = equipamentoService.atualizarEquipamento(id, dto);
        return ResponseEntity.ok(equipamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEquipamento(@PathVariable Long id) {
        equipamentoService.deletarEquipamento(id);
        return ResponseEntity.noContent().build();
    }
}
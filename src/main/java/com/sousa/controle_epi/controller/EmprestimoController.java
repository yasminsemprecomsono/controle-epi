package com.sousa.controle_epi.controller;
import com.sousa.controle_epi.dto.InfosEmprestimoDTO;
import com.sousa.controle_epi.dto.RequisitarEmprestimoDTO;
import com.sousa.controle_epi.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    @Autowired private EmprestimoService service;

    @PostMapping
    public ResponseEntity<InfosEmprestimoDTO> criar(@RequestBody RequisitarEmprestimoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarEmprestimo(dto));
    }
    @GetMapping
    public ResponseEntity<List<InfosEmprestimoDTO>> listar() {
        return ResponseEntity.ok(service.listarEmprestimos());
    }
    // novo
    @PutMapping("/{id}/devolver")
    public ResponseEntity<InfosEmprestimoDTO> devolver(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean quebrado) {

        return ResponseEntity.ok(service.devolverEquipamento(id, quebrado));
    }
    @GetMapping("/{id}")
    public ResponseEntity<InfosEmprestimoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarEmprestimoPorId(id));
    }
}

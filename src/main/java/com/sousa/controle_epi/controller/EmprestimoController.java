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
    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<InfosEmprestimoDTO>> listarEmprestimos() {
        List<InfosEmprestimoDTO> lista = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfosEmprestimoDTO> buscarEmprestimoPorId(@PathVariable Long id) {
        InfosEmprestimoDTO emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping
    public ResponseEntity<InfosEmprestimoDTO> criarEmprestimo(@RequestBody RequisitarEmprestimoDTO dto) {
        InfosEmprestimoDTO novoEmprestimo = emprestimoService.criarEmprestimo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    // ação de devolver
    @PutMapping("/{id}/devolver")
    public ResponseEntity<InfosEmprestimoDTO> devolverEquipamento(@PathVariable Long id) {
        InfosEmprestimoDTO emprestimoAtualizado = emprestimoService.devolverEquipamento(id);
        return ResponseEntity.ok(emprestimoAtualizado);
    }
}

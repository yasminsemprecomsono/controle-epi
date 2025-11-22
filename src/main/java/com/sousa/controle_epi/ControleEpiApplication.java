package com.sousa.controle_epi;

import com.sousa.controle_epi.dto.*;
import com.sousa.controle_epi.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class ControleEpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleEpiApplication.class, args);
	}

	@Bean
	public CommandLineRunner rodarTestes(ColaboradorService colabService,
										 EquipamentoService equipService,
										 EmprestimoService empService) {
		return args -> {
			System.out.println("\n--- Teste De RN ---\n");

			try {
				// prepara os dados
				System.out.println("Criando dados para o teste...");

				RequisitarColaboradorDTO reqColab = new RequisitarColaboradorDTO();
				reqColab.setNome("Yasmin Sousa");
				reqColab.setMatricula("TESTE-2025");
				InfosColaboradorDTO colab = colabService.criarColaborador(reqColab);

				// EPI bom vai vencer em 2030
				RequisitarEquipamentoDTO reqEpiBom = new RequisitarEquipamentoDTO();
				reqEpiBom.setNomeEquipamento("Capacete Novo");
				reqEpiBom.setNumeroCA("CA-BOM-001");
				reqEpiBom.setDataValidade(LocalDate.now().plusYears(5));
				InfosEquipamentoDTO epiBom = equipService.criarEquipamento(reqEpiBom);

				// EPI vencido (venceu ontem)
				RequisitarEquipamentoDTO reqEpiVencido = new RequisitarEquipamentoDTO();
				reqEpiVencido.setNomeEquipamento("Luva Velha");
				reqEpiVencido.setNumeroCA("CA-VELHO-002");
				reqEpiVencido.setDataValidade(LocalDate.now().minusDays(1));
				InfosEquipamentoDTO epiVencido = equipService.criarEquipamento(reqEpiVencido);

				System.out.println("Dados criados \n");


				// teste 1: tenta pega item vencido
				System.out.print("Teste 1: Pegar item vencido \n");
				try {
					RequisitarEmprestimoDTO pedidoRuim = new RequisitarEmprestimoDTO();
					pedidoRuim.setIdColaborador(colab.getId());
					pedidoRuim.setIdEquipamento(epiVencido.getId());

					empService.criarEmprestimo(pedidoRuim);
					System.out.println("Sistema deixo passar (ruim)");
				} catch (Exception e) {
					System.out.println("Sistema Bloqueou (bom), Mensagem: " + e.getMessage());
				}


				// teste 2: pegar item bom
				System.out.print("Teste 2: Pegar item bom (normal)\n ");
				RequisitarEmprestimoDTO pedidoBom = new RequisitarEmprestimoDTO();
				pedidoBom.setIdColaborador(colab.getId());
				pedidoBom.setIdEquipamento(epiBom.getId());

				InfosEmprestimoDTO emprestimoRealizado = empService.criarEmprestimo(pedidoBom);
				System.out.println("Emprestimo feito com ID " + emprestimoRealizado.getIdEmprestimo());


				// teste 3: pegar o mesmo item denovo
				System.out.print("Teste 3: Bloquear item duplicado");
				try {
					empService.criarEmprestimo(pedidoBom);
					System.out.println("Sitema duplicou (ruim)");
				} catch (Exception e) {
					System.out.println("Sistema avisou (bom)");
				}


				// teste 4: testa devolver
				System.out.print("Teste 4: Testando devolucao");
				empService.devolverEquipamento(emprestimoRealizado.getIdEmprestimo());
				System.out.println("Item devolvido.");


				// teste 5: tenta devolver duas vezes o msm item
				System.out.print("Teste 5: Devolução duplicada ");
				try {
					empService.devolverEquipamento(emprestimoRealizado.getIdEmprestimo());
					System.out.println("Devolveu duas vezes(ruim)");
				} catch (Exception e) {
					System.out.println("Sistema avisou que ja foi devolvido (bom)");
				}

			} catch (Exception e) {
				System.out.println("ERRO NO TESTE: " + e.getMessage());
				e.printStackTrace();
			}
			System.out.println("\n--- Testes Feitos :D ---");
		};
	}
}
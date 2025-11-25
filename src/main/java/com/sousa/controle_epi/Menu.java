package com.sousa.controle_epi;
import com.sousa.controle_epi.dto.*;
import com.sousa.controle_epi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    @Autowired private ColaboradorService colaboradorService;
    @Autowired private EquipamentoService equipamentoService;
    @Autowired private EmprestimoService emprestimoService;

    private Scanner leitor = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        // o sistema inicia direto no menu

        boolean continuar = true;
        //loop de menu

        while (continuar) {
            System.out.println("\n===================================");
            System.out.println("      SISTEMA DE CONTROLE DE EPI   ");
            System.out.println("===================================");
            System.out.println("1 - Cadastrar Novo Colaborador");
            System.out.println("2 - Ver Lista de Colaboradores");
            System.out.println("3 - Cadastrar Novo Equipamento");
            System.out.println("4 - Ver Lista de Equipamentos");
            System.out.println("5 - REALIZAR EMPRÉSTIMO");
            System.out.println("6 - REALIZAR DEVOLUÇÃO");
            System.out.println("7 - Ver Histórico de Empréstimos");
            System.out.println("0 - Sair");
            System.out.print("--> Digite sua opção: ");

            try {
                int opcao = leitor.nextInt();
                leitor.nextLine(); // limpar buffer

                switch (opcao) {
                    case 1: telaCadastrarColaborador(); break;
                    case 2: telaListarColaboradores(); break;
                    case 3: telaCadastrarEquipamento(); break;
                    case 4: telaListarEquipamentos(); break;
                    case 5: telaFazerEmprestimo(); break;
                    case 6: telaFazerDevolucao(); break;
                    case 7: telaListarHistorico(); break;
                    case 0:
                        System.out.println("Encerrando sistema...");
                        continuar = false;
                        break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro de entrada. Digite apenas números.");
                leitor.nextLine();
            }
        }
    }

    // ---> Metedos <---

    private void telaCadastrarColaborador() {
        System.out.println("\n--- CADASTRO DE COLABORADOR ---");
        System.out.print("Nome: ");
        String nome = leitor.nextLine();
        System.out.print("Matrícula: ");
        String matricula = leitor.nextLine();
        System.out.print("Cargo: ");
        String cargo = leitor.nextLine();

        RequisitarColaboradorDTO novo = new RequisitarColaboradorDTO();
        novo.setNome(nome);
        novo.setMatricula(matricula);
        novo.setCargo(cargo);

        try {
            colaboradorService.criarColaborador(novo);
            System.out.println("✅ Colaborador salvo.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void telaListarColaboradores() {
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
        List<InfosColaboradorDTO> lista = colaboradorService.listarColaboradores();

        if (lista.isEmpty()) {
            System.out.println("(Nenhum colaborador cadastrado)");
        } else {
            for (InfosColaboradorDTO p : lista) {
                System.out.printf("ID: %d | Nome: %-20s | Cargo: %s\n", p.getId(), p.getNome(), p.getCargo());
            }
        }
    }

    private void telaCadastrarEquipamento() {
        System.out.println("\n--- CADASTRO DE EPI ---");
        System.out.print("Nome do EPI: ");
        String nome = leitor.nextLine();
        System.out.print("Número CA: ");
        String ca = leitor.nextLine();

        RequisitarEquipamentoDTO novo = new RequisitarEquipamentoDTO();
        novo.setNomeEquipamento(nome);
        novo.setNumeroCA(ca);
        // Define validade padrão de 2 anos a partir de hoje
        novo.setDataValidade(LocalDate.now().plusYears(2));

        try {
            equipamentoService.criarEquipamento(novo);
            System.out.println("✅ Equipamento salvo");
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void telaListarEquipamentos() {
        System.out.println("\n--- LISTA DE EPIs ---");
        List<InfosEquipamentoDTO> lista = equipamentoService.listarEquipamentos();

        if (lista.isEmpty()) {
            System.out.println("(Nenhum equipamento cadastrado)");
        } else {
            for (InfosEquipamentoDTO e : lista) {
                System.out.printf("ID: %d | CA: %-10s | Nome: %s\n", e.getId(), e.getNumeroCA(), e.getNomeEquipamento());
            }
        }
    }

    private void telaFazerEmprestimo() {
        System.out.println("\n--- NOVO EMPRÉSTIMO ---");
        telaListarColaboradores();
        System.out.println("-----------------------");
        telaListarEquipamentos();

        System.out.print("\nDigite o ID do Colaborador: ");
        Long idColab = leitor.nextLong();
        System.out.print("Digite o ID do Equipamento: ");
        Long idEquip = leitor.nextLong();

        RequisitarEmprestimoDTO pedido = new RequisitarEmprestimoDTO();
        pedido.setIdColaborador(idColab);
        pedido.setIdEquipamento(idEquip);

        try {
            emprestimoService.criarEmprestimo(pedido);
            System.out.println("✅ Empréstimo registrado com sucesso");
        } catch (Exception e) {
            System.out.println("❌ ERRO: " + e.getMessage());
        }
    }

    private void telaFazerDevolucao() {
        System.out.println("\n--- DEVOLUÇÃO ---");
        System.out.print("Digite o ID do Empréstimo para devolver: ");
        Long idEmp = leitor.nextLong();

        System.out.print("O item está quebrado? (1-Sim / 2-Não): ");
        int opcao = leitor.nextInt();
        boolean estaQuebrado = (opcao == 1);

        try {
            emprestimoService.devolverEquipamento(idEmp, estaQuebrado);
            System.out.println("✅ Devolvido com sucesso!");
            if(estaQuebrado) System.out.println("⚠️ Item marcado como MANUTENÇÃO.");
        } catch (Exception e) {
            System.out.println("❌ ERRO: " + e.getMessage());
        }
    }

    private void telaListarHistorico() {
        System.out.println("\n--- HISTÓRICO ---");
        List<InfosEmprestimoDTO> lista = emprestimoService.listarEmprestimos();

        if (lista.isEmpty()) {
            System.out.println("(Nenhum empréstimo registrado)");
        } else {
            for (InfosEmprestimoDTO item : lista) {
                System.out.println("ID Emp: " + item.getIdEmprestimo() +
                        " | " + item.getNomeColaborador() +
                        " pegou " + item.getNomeEquipamento() +
                        " | Status: " + item.getStatus());
            }
        }
    }
}
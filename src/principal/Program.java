package principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Program {

    private static Formatter arqSaida;

    private static Scanner arqEnt;

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in);

        final int MAX = 1000;

        int[] codigoIdoso = new int[MAX];
        String[] nomeIdoso = new String[MAX];
        int[] idadeIdoso = new int[MAX];
        String[] telefoneIdoso = new String[MAX];
        int qtdIdosos = 0;

        int[] codigoLivro = new int[MAX];
        String[] tituloLivro = new String[MAX];
        String[] autorLivro = new String[MAX];
        String[] categoriaLivro = new String[MAX];
        int[] paginasLivro = new int[MAX];
        int qtdLivros = 0;

        int[] codigoEmprestimo = new int[MAX];
        int[] idosoEmprestimo = new int[MAX];
        int[] livroEmprestimo = new int[MAX];
        String[] dataEmprestimo = new String[MAX];
        int qtdEmprestimos = 0;

        String[] comentarioLeitura = new String[MAX];
        int[] idosoComentario = new int[MAX];
        int[] livroComentario = new int[MAX];
        int[] notaLeitura = new int[MAX];
        int qtdComentarios = 0;

        qtdIdosos = carregarIdosos(codigoIdoso, nomeIdoso, idadeIdoso, telefoneIdoso);
        qtdLivros = carregarLivros(codigoLivro, tituloLivro, autorLivro, categoriaLivro, paginasLivro);
        qtdEmprestimos = carregarEmprestimos(codigoEmprestimo, idosoEmprestimo, livroEmprestimo, dataEmprestimo);
        qtdComentarios = carregarComentarios(idosoComentario, livroComentario, notaLeitura, comentarioLeitura);

        int opcao;

        do {

            System.out.println("\n====================================================");
            System.out.println("           SISTEMA LEITURA VIVA 60+");
            System.out.println("====================================================");

            System.out.println("[1] Cadastros");
            System.out.println("[2] Empréstimos");
            System.out.println("[3] Consultas");
            System.out.println("[4] Diário do Idoso");
            System.out.println("[0] Sair");

            System.out.print("Escolha: ");
            opcao = leitor.nextInt();

            switch (opcao) {

                case 1:
                    int[] qtdsCadastro = menuCadastros(leitor,
                            codigoIdoso, nomeIdoso, idadeIdoso, telefoneIdoso, qtdIdosos,
                            codigoLivro, tituloLivro, autorLivro, categoriaLivro, paginasLivro, qtdLivros);
                    qtdIdosos = qtdsCadastro[0];
                    qtdLivros = qtdsCadastro[1];
                    salvarIdosos(codigoIdoso, nomeIdoso, idadeIdoso, telefoneIdoso, qtdIdosos);
                    salvarLivros(codigoLivro, tituloLivro, autorLivro, categoriaLivro, paginasLivro, qtdLivros);
                    break;

                case 2:
                    qtdEmprestimos = menuEmprestimos(leitor,
                            codigoEmprestimo,
                            idosoEmprestimo,
                            livroEmprestimo,
                            dataEmprestimo,
                            qtdEmprestimos,
                            codigoIdoso,
                            nomeIdoso,
                            qtdIdosos,
                            codigoLivro,
                            tituloLivro,
                            qtdLivros);
                    salvarEmprestimos(codigoEmprestimo, idosoEmprestimo, livroEmprestimo, dataEmprestimo, qtdEmprestimos);
                    break;

                case 3:
                    menuConsultas(leitor,
                            codigoIdoso, nomeIdoso, idadeIdoso, telefoneIdoso, qtdIdosos,
                            codigoLivro, tituloLivro, autorLivro, categoriaLivro, paginasLivro, qtdLivros,
                            codigoEmprestimo, idosoEmprestimo, livroEmprestimo, dataEmprestimo, qtdEmprestimos);
                    break;

                case 4:
                    qtdComentarios = menuDiarioLeitura(leitor,
                            codigoIdoso, nomeIdoso, qtdIdosos,
                            codigoLivro, tituloLivro, qtdLivros,
                            comentarioLeitura, idosoComentario, livroComentario, notaLeitura, qtdComentarios);
                    salvarComentarios(idosoComentario, livroComentario, notaLeitura, comentarioLeitura, qtdComentarios);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (opcao != 0);

        leitor.close();
    }

    public static int[] menuCadastros(
            Scanner leitor,
            int[] codigoIdoso,
            String[] nomeIdoso,
            int[] idadeIdoso,
            String[] telefoneIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            String[] autorLivro,
            String[] categoriaLivro,
            int[] paginasLivro,
            int qtdLivros) {

        System.out.println("\n=== CADASTROS ===");
        System.out.println("[1] Idosos");
        System.out.println("[2] Livros");
        System.out.println("[0] Voltar");
        
        System.out.print("Escolha: ");

        int op = leitor.nextInt();

        switch (op) {

            case 1:
                qtdIdosos = cadastrarIdoso(leitor, codigoIdoso, nomeIdoso, idadeIdoso, telefoneIdoso, qtdIdosos);
                break;

            case 2:
                qtdLivros = cadastrarLivro(leitor, codigoLivro, tituloLivro, autorLivro, categoriaLivro, paginasLivro, qtdLivros);
                break;
        }

        return new int[] { qtdIdosos, qtdLivros };
    }

    private static int cadastrarLivro(Scanner leitor, int[] codigoLivro, String[] tituloLivro, String[] autorLivro,
            String[] categoriaLivro, int[] paginasLivro, int qtdLivros) {

        if (qtdLivros >= codigoLivro.length) {
            System.out.println("Limite de livros atingido.");
            return qtdLivros;
        }

        System.out.println("\n=== CADASTRO DE LIVRO ===");

        System.out.print("Código: ");
        int codigo = leitor.nextInt();

        for (int i = 0; i < qtdLivros; i++) {
            if (codigoLivro[i] == codigo) {
                System.out.println("Código de livro já cadastrado.");
                return qtdLivros;
            }
        }

        leitor.nextLine();

        System.out.print("Título: ");
        tituloLivro[qtdLivros] = leitor.nextLine();

        System.out.print("Autor: ");
        autorLivro[qtdLivros] = leitor.nextLine();

        System.out.print("Categoria: ");
        categoriaLivro[qtdLivros] = leitor.nextLine();

        System.out.print("Páginas: ");
        paginasLivro[qtdLivros] = leitor.nextInt();

        codigoLivro[qtdLivros] = codigo;
        qtdLivros++;

        System.out.println("Livro cadastrado com sucesso.");
        return qtdLivros;
    }

    private static int cadastrarIdoso(Scanner leitor, int[] codigoIdoso, String[] nomeIdoso, int[] idadeIdoso,
            String[] telefoneIdoso, int qtdIdosos) {

        if (qtdIdosos >= codigoIdoso.length) {
            System.out.println("Limite de idosos atingido.");
            return qtdIdosos;
        }

        System.out.println("\n=== CADASTRO DE IDOSO ===");

        System.out.print("Código: ");
        int codigo = leitor.nextInt();

        for (int i = 0; i < qtdIdosos; i++) {
            if (codigoIdoso[i] == codigo) {
                System.out.println("Código de idoso já cadastrado.");
                return qtdIdosos;
            }
        }

        leitor.nextLine();

        System.out.print("Nome: ");
        nomeIdoso[qtdIdosos] = leitor.nextLine();

        System.out.print("Idade: ");
        idadeIdoso[qtdIdosos] = leitor.nextInt();

        leitor.nextLine();

        System.out.print("Telefone: ");
        telefoneIdoso[qtdIdosos] = leitor.nextLine();

        codigoIdoso[qtdIdosos] = codigo;
        qtdIdosos++;

        System.out.println("Idoso cadastrado com sucesso.");
        return qtdIdosos;
    }

    public static int menuEmprestimos(
            Scanner leitor,
            int[] codigoEmprestimo,
            int[] idosoEmprestimo,
            int[] livroEmprestimo,
            String[] dataEmprestimo,
            int qtdEmprestimos,
            int[] codigoIdoso,
            String[] nomeIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            int qtdLivros) {

        System.out.println("\n=== EMPRÉSTIMOS ===");
        System.out.println("[1] Registrar Empréstimo");
        System.out.println("[2] Devolução");
        System.out.println("[3] Listar Empréstimos");
        System.out.println("[0] Voltar");
        
        System.out.print("Escolha: ");

        int op = leitor.nextInt();

        switch (op) {

            case 1:
                qtdEmprestimos = registrarEmprestimo(leitor,
                        codigoEmprestimo,
                        codigoIdoso,
                        codigoLivro,
                        qtdIdosos,
                        qtdLivros,
                        idosoEmprestimo,
                        livroEmprestimo,
                        dataEmprestimo,
                        qtdEmprestimos);
                break;

            case 2:
                qtdEmprestimos = excluirEmprestimo(leitor,
                        codigoEmprestimo,
                        idosoEmprestimo,
                        livroEmprestimo,
                        dataEmprestimo,
                        qtdEmprestimos);
                break;

            case 3:
                listarEmprestimos(codigoEmprestimo,
                        idosoEmprestimo,
                        livroEmprestimo,
                        dataEmprestimo,
                        qtdEmprestimos);
                break;
        }

        return qtdEmprestimos;
    }

    private static void listarEmprestimos(int[] codigoEmprestimo, int[] idosoEmprestimo, int[] livroEmprestimo,
            String[] dataEmprestimo, int qtdEmprestimos) {

        System.out.println("\n=== LISTA DE EMPRÉSTIMOS ===");

        if (qtdEmprestimos == 0) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }

        for (int i = 0; i < qtdEmprestimos; i++) {
            System.out.println("\nEmpréstimo: " + (i + 1));
            System.out.println("Código: " + codigoEmprestimo[i]);
            System.out.println("Código do idoso: " + idosoEmprestimo[i]);
            System.out.println("Código do livro: " + livroEmprestimo[i]);
            System.out.println("Data: " + dataEmprestimo[i]);
        }
    }

    private static int excluirEmprestimo(Scanner leitor, int[] codigoEmprestimo, int[] idosoEmprestimo,
            int[] livroEmprestimo, String[] dataEmprestimo, int qtdEmprestimos) {

        System.out.print("Digite o código do empréstimo para excluir: ");
        int codigo = leitor.nextInt();

        int pos = -1;

        for (int i = 0; i < qtdEmprestimos; i++) {
            if (codigoEmprestimo[i] == codigo) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Empréstimo não encontrado.");
            return qtdEmprestimos;
        }

        for (int i = pos; i < qtdEmprestimos - 1; i++) {
            codigoEmprestimo[i] = codigoEmprestimo[i + 1];
            idosoEmprestimo[i] = idosoEmprestimo[i + 1];
            livroEmprestimo[i] = livroEmprestimo[i + 1];
            dataEmprestimo[i] = dataEmprestimo[i + 1];
        }

        qtdEmprestimos--;
        System.out.println("Empréstimo excluído com sucesso.");
        return qtdEmprestimos;
    }

    private static int registrarEmprestimo(Scanner leitor, int[] codigoEmprestimo, int[] codigoIdoso, int[] codigoLivro,
            int qtdIdosos, int qtdLivros, int[] idosoEmprestimo, int[] livroEmprestimo, String[] dataEmprestimo,
            int qtdEmprestimos) {

        if (qtdEmprestimos >= codigoEmprestimo.length) {
            System.out.println("Limite de empréstimos atingido.");
            return qtdEmprestimos;
        }

        System.out.print("Código do empréstimo: ");
        int codigo = leitor.nextInt();

        for (int i = 0; i < qtdEmprestimos; i++) {
            if (codigoEmprestimo[i] == codigo) {
                System.out.println("Código de empréstimo já cadastrado.");
                return qtdEmprestimos;
            }
        }

        System.out.print("Código do idoso: ");
        int codIdoso = leitor.nextInt();

        int posIdoso = -1;
        for (int i = 0; i < qtdIdosos; i++) {
            if (codigoIdoso[i] == codIdoso) {
                posIdoso = i;
                break;
            }
        }

        if (posIdoso == -1) {
            System.out.println("Idoso não encontrado.");
            return qtdEmprestimos;
        }

        System.out.print("Código do livro: ");
        int codLivro = leitor.nextInt();

        int posLivro = -1;
        for (int i = 0; i < qtdLivros; i++) {
            if (codigoLivro[i] == codLivro) {
                posLivro = i;
                break;
            }
        }

        if (posLivro == -1) {
            System.out.println("Livro não encontrado.");
            return qtdEmprestimos;
        }

        leitor.nextLine();
        System.out.print("Data do empréstimo: ");
        String data = leitor.nextLine();

        codigoEmprestimo[qtdEmprestimos] = codigo;
        idosoEmprestimo[qtdEmprestimos] = codIdoso;
        livroEmprestimo[qtdEmprestimos] = codLivro;
        dataEmprestimo[qtdEmprestimos] = data;

        qtdEmprestimos++;
        System.out.println("Empréstimo registrado com sucesso.");

        return qtdEmprestimos;
    }

    public static void menuConsultas(
            Scanner leitor,
            int[] codigoIdoso,
            String[] nomeIdoso,
            int[] idadeIdoso,
            String[] telefoneIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            String[] autorLivro,
            String[] categoriaLivro,
            int[] paginasLivro,
            int qtdLivros,
            int[] codigoEmprestimo,
            int[] idosoEmprestimo,
            int[] livroEmprestimo,
            String[] dataEmprestimo,
            int qtdEmprestimos) {

        System.out.println("\n=== CONSULTAS ===");
        System.out.println("[1] Idoso (por código)");
        System.out.println("[2] Livro (por código)");
        System.out.println("[3] Empréstimo (por código)");
        System.out.println("[4] Listar todos os idosos");
        System.out.println("[5] Listar todos os livros");
        System.out.println("[6] Listar todos os empréstimos");
        System.out.println("[0] Voltar");
        
        System.out.println("Escolha: ");

        int op = leitor.nextInt();

        switch (op) {

            case 1:
                consultarIdoso(leitor, codigoIdoso, nomeIdoso,
                        idadeIdoso, telefoneIdoso, qtdIdosos);
                break;

            case 2:
                consultarLivro(leitor, codigoLivro, tituloLivro,
                        autorLivro, categoriaLivro, paginasLivro, qtdLivros);
                break;

            case 3:
                consultarEmprestimo(leitor,
                        codigoEmprestimo,
                        idosoEmprestimo,
                        livroEmprestimo,
                        dataEmprestimo,
                        qtdEmprestimos,
                        codigoIdoso,
                        nomeIdoso,
                        qtdIdosos,
                        codigoLivro,
                        tituloLivro,
                        qtdLivros);
                break;

            case 4:
                listarIdosos(codigoIdoso, nomeIdoso, idadeIdoso, telefoneIdoso, qtdIdosos);
                break;

            case 5:
                listarLivros(codigoLivro, tituloLivro, autorLivro, categoriaLivro, paginasLivro, qtdLivros);
                break;

            case 6:
                listarTodosEmprestimos(codigoEmprestimo, idosoEmprestimo, livroEmprestimo, dataEmprestimo,
                        qtdEmprestimos, codigoIdoso, nomeIdoso, qtdIdosos, codigoLivro, tituloLivro, qtdLivros);
                break;
        }
    }

    private static void listarTodosEmprestimos(
            int[] codigoEmprestimo,
            int[] idosoEmprestimo,
            int[] livroEmprestimo,
            String[] dataEmprestimo,
            int qtdEmprestimos,
            int[] codigoIdoso,
            String[] nomeIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            int qtdLivros) {

        System.out.println("\n=== LISTA DE EMPRÉSTIMOS ===");

        if (qtdEmprestimos == 0) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }

        for (int i = 0; i < qtdEmprestimos; i++) {

            String nome = "Não encontrado";
            String livro = "Não encontrado";

            for (int j = 0; j < qtdIdosos; j++) {
                if (codigoIdoso[j] == idosoEmprestimo[i]) {
                    nome = nomeIdoso[j];
                    break;
                }
            }

            for (int j = 0; j < qtdLivros; j++) {
                if (codigoLivro[j] == livroEmprestimo[i]) {
                    livro = tituloLivro[j];
                    break;
                }
            }

            System.out.println("\nEmpréstimo " + (i + 1));
            System.out.println("Código: " + codigoEmprestimo[i]);
            System.out.println("Idoso: " + nome);
            System.out.println("Livro: " + livro);
            System.out.println("Data: " + dataEmprestimo[i]);
        }
    }

    private static void listarLivros(
            int[] codigoLivro,
            String[] tituloLivro,
            String[] autorLivro,
            String[] categoriaLivro,
            int[] paginasLivro,
            int qtdLivros) {

        System.out.println("\n=== LISTA DE LIVROS ===");

        if (qtdLivros == 0) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (int i = 0; i < qtdLivros; i++) {
            System.out.println("\nLivro " + (i + 1));
            System.out.println("Código: " + codigoLivro[i]);
            System.out.println("Título: " + tituloLivro[i]);
            System.out.println("Autor: " + autorLivro[i]);
            System.out.println("Categoria: " + categoriaLivro[i]);
            System.out.println("Páginas: " + paginasLivro[i]);
        }
    }

    private static void listarIdosos(
            int[] codigoIdoso,
            String[] nomeIdoso,
            int[] idadeIdoso,
            String[] telefoneIdoso,
            int qtdIdosos) {

        System.out.println("\n=== LISTA DE IDOSOS ===");

        if (qtdIdosos == 0) {
            System.out.println("Nenhum idoso cadastrado.");
            return;
        }

        for (int i = 0; i < qtdIdosos; i++) {
            System.out.println("\nIdoso " + (i + 1));
            System.out.println("Código: " + codigoIdoso[i]);
            System.out.println("Nome: " + nomeIdoso[i]);
            System.out.println("Idade: " + idadeIdoso[i]);
            System.out.println("Telefone: " + telefoneIdoso[i]);
        }
    }

    private static void consultarLivro(
            Scanner leitor,
            int[] codigoLivro,
            String[] tituloLivro,
            String[] autorLivro,
            String[] categoriaLivro,
            int[] paginasLivro,
            int qtdLivros) {

        System.out.print("Código do livro: ");
        int codigo = leitor.nextInt();

        int pos = -1;

        for (int i = 0; i < qtdLivros; i++) {
            if (codigoLivro[i] == codigo) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Livro não encontrado.");
            return;
        }

        System.out.println("Livro encontrado!");
        System.out.println("Código: " + codigoLivro[pos]);
        System.out.println("Título: " + tituloLivro[pos]);
        System.out.println("Autor: " + autorLivro[pos]);
        System.out.println("Categoria: " + categoriaLivro[pos]);
     
        System.out.println("Páginas: " + paginasLivro[pos]);
    }
    
    
    private static void consultarEmprestimo(Scanner leitor, int[] codigoEmprestimo, int[] idosoEmprestimo,
            int[] livroEmprestimo, String[] dataEmprestimo, int qtdEmprestimos, int[] codigoIdoso, String[] nomeIdoso,
            int qtdIdosos, int[] codigoLivro, String[] tituloLivro, int qtdLivros) {

        System.out.print("Código do empréstimo: ");
        int codigo = leitor.nextInt();

        int pos = -1;
        for (int i = 0; i < qtdEmprestimos; i++) {
            if (codigoEmprestimo[i] == codigo) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

        String nome = "Não encontrado";
        String livro = "Não encontrado";

        for (int i = 0; i < qtdIdosos; i++) {
            if (codigoIdoso[i] == idosoEmprestimo[pos]) {
                nome = nomeIdoso[i];
                break;
            }
        }

        for (int i = 0; i < qtdLivros; i++) {
            if (codigoLivro[i] == livroEmprestimo[pos]) {
                livro = tituloLivro[i];
                break;
            }
        }

        System.out.println("\nCódigo do empréstimo: " + codigoEmprestimo[pos]);
        System.out.println("Idoso: " + nome);
        System.out.println("Livro: " + livro);
        System.out.println("Data: " + dataEmprestimo[pos]);
    }

    private static void consultarIdoso(Scanner leitor, int[] codigoIdoso, String[] nomeIdoso, int[] idadeIdoso,
            String[] telefoneIdoso, int qtdIdosos) {

        System.out.print("Código do idoso: ");
        int codigo = leitor.nextInt();

        int pos = -1;
        for (int i = 0; i < qtdIdosos; i++) {
            if (codigoIdoso[i] == codigo) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Idoso não encontrado.");
            return;
        }

        System.out.println("\nNome: " + nomeIdoso[pos]);
        System.out.println("Idade: " + idadeIdoso[pos]);
        System.out.println("Telefone: " + telefoneIdoso[pos]);
    }

    public static int menuDiarioLeitura(
            Scanner leitor,
            int[] codigoIdoso,
            String[] nomeIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            int qtdLivros,
            String[] comentarioLeitura,
            int[] idosoComentario,
            int[] livroComentario,
            int[] notaLeitura,
            int qtdComentarios) {

        System.out.println("\n=== DIÁRIO DE LEITURA ===");
        System.out.println("[1] Registrar comentário");
        System.out.println("[2] Listar comentários");
        System.out.println("[0] Voltar");

        System.out.println("Escolha: ");
        int op = leitor.nextInt();

        switch (op) {

            case 1:
                return registrarComentarioLeitura(
                        leitor,
                        codigoIdoso,
                        nomeIdoso,
                        qtdIdosos,
                        codigoLivro,
                        tituloLivro,
                        qtdLivros,
                        comentarioLeitura,
                        idosoComentario,
                        livroComentario,
                        notaLeitura,
                        qtdComentarios);

            case 2:
                listarComentariosLeitura(
                        codigoIdoso,
                        nomeIdoso,
                        qtdIdosos,
                        codigoLivro,
                        tituloLivro,
                        qtdLivros,
                        comentarioLeitura,
                        idosoComentario,
                        livroComentario,
                        notaLeitura,
                        qtdComentarios);
                break;
        }

        return qtdComentarios;
    }

    public static int registrarComentarioLeitura(
            Scanner leitor,
            int[] codigoIdoso,
            String[] nomeIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            int qtdLivros,
            String[] comentarioLeitura,
            int[] idosoComentario,
            int[] livroComentario,
            int[] notaLeitura,
            int qtdComentarios) {

        System.out.println("\n=== Registro de comentários de leitura ===");

        if (qtdComentarios >= comentarioLeitura.length) {
            System.out.println("Limite de comentários atingido.");
            return qtdComentarios;
        }

        System.out.print("Código do idoso: ");
        int codigo = leitor.nextInt();

        int posIdoso = -1;
        for (int i = 0; i < qtdIdosos; i++) {
            if (codigo == codigoIdoso[i]) {
                posIdoso = i;
                break;
            }
        }

        if (posIdoso == -1) {
            System.out.println("Idoso não encontrado.");
            return qtdComentarios;
        }

        System.out.println("Idoso encontrado: " + nomeIdoso[posIdoso]);

        System.out.print("Código do livro: ");
        int codigoLiv = leitor.nextInt();

        int posLivro = -1;
        for (int i = 0; i < qtdLivros; i++) {
            if (codigoLiv == codigoLivro[i]) {
                posLivro = i;
                break;
            }
        }

        if (posLivro == -1) {
            System.out.println("Livro não encontrado.");
            return qtdComentarios;
        }

        System.out.println("Livro encontrado: " + tituloLivro[posLivro]);

        leitor.nextLine();

        System.out.print("Nota: ");
        notaLeitura[qtdComentarios] = leitor.nextInt();

        leitor.nextLine();

        System.out.print("Comentário: ");
        comentarioLeitura[qtdComentarios] = leitor.nextLine();

        idosoComentario[qtdComentarios] = codigoIdoso[posIdoso];
        livroComentario[qtdComentarios] = codigoLivro[posLivro];

        qtdComentarios++;

        System.out.println("Comentário registrado com sucesso!");

        return qtdComentarios;
    }

    public static void listarComentariosLeitura(
            int[] codigoIdoso,
            String[] nomeIdoso,
            int qtdIdosos,
            int[] codigoLivro,
            String[] tituloLivro,
            int qtdLivros,
            String[] comentarioLeitura,
            int[] idosoComentario,
            int[] livroComentario,
            int[] notaLeitura,
            int qtdComentarios) {

        System.out.println("\n====================================================");
        System.out.println("         LISTA DE COMENTÁRIOS DE LEITURA");
        System.out.println("====================================================");

        if (qtdComentarios == 0) {
            System.out.println("Nenhum comentário registrado ainda.");
            return;
        }

        for (int i = 0; i < qtdComentarios; i++) {

            String nome = "Não registrado.";
            String livro = "Não registrado.";

            for (int j = 0; j < qtdIdosos; j++) {
                if (codigoIdoso[j] == idosoComentario[i]) {
                    nome = nomeIdoso[j];
                    break;
                }
            }

            for (int j = 0; j < qtdLivros; j++) {
                if (codigoLivro[j] == livroComentario[i]) {
                    livro = tituloLivro[j];
                    break;
                }
            }

            System.out.println("\n--------------------------------------------");
            System.out.println("Idoso: " + nome);
            System.out.println("Livro: " + livro);
            System.out.println("Nota: " + notaLeitura[i]);
            System.out.println("Comentário: " + comentarioLeitura[i]);
        }

        System.out.println("\n============================================");
    }

    private static String trunc(String s, int n) {
        if (s == null) {
            return "";
        }
        return s.length() > n ? s.substring(0, n) : s;
    }

    private static String campo(String linha, int inicio, int fim) {
        if (linha == null || inicio >= linha.length()) {
            return "";
        }
        if (fim > linha.length()) {
            fim = linha.length();
        }
        return linha.substring(inicio, fim).trim();
    }

    public static void salvarIdosos(int[] codigoIdoso, String[] nomeIdoso,
            int[] idadeIdoso, String[] telefoneIdoso, int qtdIdosos) {

        try {
            arqSaida = new Formatter("idosos.txt");
        }
        catch (SecurityException securityException) {
            System.err.println("Permissao de Escrita Negado. Fechando...");
            System.exit(1);
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Erro ao abrir o arquivo. Fechando...");
            System.exit(1);
        }

        try {
            arqSaida.format("%-6s %-30s %-5s %-15s%n",
                    "CODIGO", "NOME", "IDADE", "TELEFONE");
            for (int i = 0; i < qtdIdosos; i++) {
                arqSaida.format("%-6d %-30s %-5d %-15s%n",
                        codigoIdoso[i],
                        trunc(nomeIdoso[i], 30),
                        idadeIdoso[i],
                        trunc(telefoneIdoso[i], 15));
            }
        }
        catch (FormatterClosedException formatterClosedException) {
            System.err.println("Erro de escrita no arquivo. Finalizando...");
        }

        if (arqSaida != null) {
            arqSaida.close();
        }
    }

    public static int carregarIdosos(int[] codigoIdoso, String[] nomeIdoso,
            int[] idadeIdoso, String[] telefoneIdoso) {

        try {
            arqEnt = new Scanner(new File("idosos.txt"));
        }
        catch (FileNotFoundException fileNotFoundException) {
            return 0;
        }

        int qtd = 0;
        try {
            if (arqEnt.hasNextLine()) {
                arqEnt.nextLine();
            }
            while (arqEnt.hasNextLine() && qtd < codigoIdoso.length) {
                String linha = arqEnt.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }
                codigoIdoso[qtd] = Integer.parseInt(campo(linha, 0, 6));
                nomeIdoso[qtd] = campo(linha, 7, 37);
                idadeIdoso[qtd] = Integer.parseInt(campo(linha, 38, 43));
                telefoneIdoso[qtd] = campo(linha, 44, 59);
                qtd++;
            }
        }
        catch (NoSuchElementException elementException) {
            System.err.println("Arquivo idosos.txt corrompido");
        }
        catch (IllegalStateException stateException) {
            System.err.println("Erro na leitura do arquivo idosos.txt");
        }
        catch (NumberFormatException numberFormatException) {
            System.err.println("Erro de formato no arquivo idosos.txt");
        }

        if (arqEnt != null) {
            arqEnt.close();
        }
        return qtd;
    }

    public static void salvarLivros(int[] codigoLivro, String[] tituloLivro,
            String[] autorLivro, String[] categoriaLivro, int[] paginasLivro, int qtdLivros) {

        try {
            arqSaida = new Formatter("livros.txt");
        }
        catch (SecurityException securityException) {
            System.err.println("Permissao de Escrita Negado. Fechando...");
            System.exit(1);
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Erro ao abrir o arquivo. Fechando...");
            System.exit(1);
        }
        
        try {
            arqSaida.format("%-6s %-40s %-30s %-20s %-7s%n",
                    "CODIGO", "TITULO", "AUTOR", "CATEGORIA", "PAGINAS");
            for (int i = 0; i < qtdLivros; i++) {
                arqSaida.format("%-6d %-40s %-30s %-20s %-7d%n",
                        codigoLivro[i],
                        trunc(tituloLivro[i], 40),
                        trunc(autorLivro[i], 30),
                        trunc(categoriaLivro[i], 20),
                        paginasLivro[i]);
            }
        }
        catch (FormatterClosedException formatterClosedException) {
            System.err.println("Erro de escrita no arquivo. Finalizando...");
        }

        if (arqSaida != null) {
            arqSaida.close();
        }
    }

    public static int carregarLivros(int[] codigoLivro, String[] tituloLivro,
            String[] autorLivro, String[] categoriaLivro, int[] paginasLivro) {

        try {
            arqEnt = new Scanner(new File("livros.txt"));
        }
        catch (FileNotFoundException fileNotFoundException) {
            return 0;
        }

        int qtd = 0;
        try {
            if (arqEnt.hasNextLine()) {
                arqEnt.nextLine();
            }
            while (arqEnt.hasNextLine() && qtd < codigoLivro.length) {
                String linha = arqEnt.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }
                codigoLivro[qtd] = Integer.parseInt(campo(linha, 0, 6));
                tituloLivro[qtd] = campo(linha, 7, 47);
                autorLivro[qtd] = campo(linha, 48, 78);
                categoriaLivro[qtd] = campo(linha, 79, 99);
                paginasLivro[qtd] = Integer.parseInt(campo(linha, 100, 107));
                qtd++;
            }
        }
        catch (NoSuchElementException elementException) {
            System.err.println("Arquivo livros.txt corrompido");
        }
        catch (IllegalStateException stateException) {
            System.err.println("Erro na leitura do arquivo livros.txt");
        }
        catch (NumberFormatException numberFormatException) {
            System.err.println("Erro de formato no arquivo livros.txt");
        }

        if (arqEnt != null) {
            arqEnt.close();
        }
        return qtd;
    }

    public static void salvarEmprestimos(int[] codigoEmprestimo, int[] idosoEmprestimo,
            int[] livroEmprestimo, String[] dataEmprestimo, int qtdEmprestimos) {

        try {
            arqSaida = new Formatter("emprestimos.txt");
        }
        catch (SecurityException securityException) {
            System.err.println("Permissao de Escrita Negado. Fechando...");
            System.exit(1);
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Erro ao abrir o arquivo. Fechando...");
            System.exit(1);
        }

        try {
            arqSaida.format("%-6s %-6s %-6s %-12s%n",
                    "CODIGO", "IDOSO", "LIVRO", "DATA");
            for (int i = 0; i < qtdEmprestimos; i++) {
                arqSaida.format("%-6d %-6d %-6d %-12s%n",
                        codigoEmprestimo[i],
                        idosoEmprestimo[i],
                        livroEmprestimo[i],
                        trunc(dataEmprestimo[i], 12));
            }
        }
        catch (FormatterClosedException formatterClosedException) {
            System.err.println("Erro de escrita no arquivo. Finalizando...");
        }

        if (arqSaida != null) {
            arqSaida.close();
        }
    }

    public static int carregarEmprestimos(int[] codigoEmprestimo, int[] idosoEmprestimo,
            int[] livroEmprestimo, String[] dataEmprestimo) {

        try {
            arqEnt = new Scanner(new File("emprestimos.txt"));
        }
        catch (FileNotFoundException fileNotFoundException) {
            return 0;
        }

        int qtd = 0;
        try {
            if (arqEnt.hasNextLine()) {
                arqEnt.nextLine();
            }
            while (arqEnt.hasNextLine() && qtd < codigoEmprestimo.length) {
                String linha = arqEnt.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }
                codigoEmprestimo[qtd] = Integer.parseInt(campo(linha, 0, 6));
                idosoEmprestimo[qtd] = Integer.parseInt(campo(linha, 7, 13));
                livroEmprestimo[qtd] = Integer.parseInt(campo(linha, 14, 20));
                dataEmprestimo[qtd] = campo(linha, 21, 33);
                qtd++;
            }
        }
        catch (NoSuchElementException elementException) {
            System.err.println("Arquivo emprestimos.txt corrompido");
        }
        catch (IllegalStateException stateException) {
            System.err.println("Erro na leitura do arquivo emprestimos.txt");
        }
        catch (NumberFormatException numberFormatException) {
            System.err.println("Erro de formato no arquivo emprestimos.txt");
        }

        if (arqEnt != null) {
            arqEnt.close();
        }
        return qtd;
    }

    public static void salvarComentarios(int[] idosoComentario, int[] livroComentario,
            int[] notaLeitura, String[] comentarioLeitura, int qtdComentarios) {

        try {
            arqSaida = new Formatter("comentarios.txt");
        }
        catch (SecurityException securityException) {
            System.err.println("Permissao de Escrita Negado. Fechando...");
            System.exit(1);
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Erro ao abrir o arquivo. Fechando...");
            System.exit(1);
        }

        try {
            arqSaida.format("%-6s %-6s %-5s %-60s%n",
                    "IDOSO", "LIVRO", "NOTA", "COMENTARIO");
            for (int i = 0; i < qtdComentarios; i++) {
                arqSaida.format("%-6d %-6d %-5d %-60s%n",
                        idosoComentario[i],
                        livroComentario[i],
                        notaLeitura[i],
                        trunc(comentarioLeitura[i], 60));
            }
        }
        catch (FormatterClosedException formatterClosedException) {
            System.err.println("Erro de escrita no arquivo. Finalizando...");
        }

        if (arqSaida != null) {
            arqSaida.close();
        }
    }

    public static int carregarComentarios(int[] idosoComentario, int[] livroComentario,
            int[] notaLeitura, String[] comentarioLeitura) {

        try {
            arqEnt = new Scanner(new File("comentarios.txt"));
        }
        catch (FileNotFoundException fileNotFoundException) {
            return 0;
        }

        int qtd = 0;
        try {
            if (arqEnt.hasNextLine()) {
                arqEnt.nextLine();
            }
            while (arqEnt.hasNextLine() && qtd < idosoComentario.length) {
                String linha = arqEnt.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }
                idosoComentario[qtd] = Integer.parseInt(campo(linha, 0, 6));
                livroComentario[qtd] = Integer.parseInt(campo(linha, 7, 13));
                notaLeitura[qtd] = Integer.parseInt(campo(linha, 14, 19));
                comentarioLeitura[qtd] = campo(linha, 20, 80);
                qtd++;
            }
        }
        catch (NoSuchElementException elementException) {
            System.err.println("Arquivo comentarios.txt corrompido");
        }
        catch (IllegalStateException stateException) {
            System.err.println("Erro na leitura do arquivo comentarios.txt");
        }
        catch (NumberFormatException numberFormatException) {
            System.err.println("Erro de formato no arquivo comentarios.txt");
        }

        if (arqEnt != null) {
            arqEnt.close();
        }
        return qtd;
    }
}
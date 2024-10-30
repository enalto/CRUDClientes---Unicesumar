package com.enalto;

import com.enalto.domain.Cliente;
import com.enalto.repository.InMemoryDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Main {

    static {
        System.setProperty(
                "java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }


    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String javaVersion = System.getProperty("java.version");
    private final Map<Map.Entry<Integer, String>, Callable<Boolean>> options = new HashMap<>();
    private final InMemoryDatabase database = InMemoryDatabase.getInstance();
    private Menu menu;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run();
    }


    private void run() throws Exception {

        InMemoryDatabase db = InMemoryDatabase.getInstance();
        this.menu = createMenu();
        optionsHandler();
        printLogo();


        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("Escolha uma opção.");
            menu.showMenu();
            try {
                opcao = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            Map.Entry<Integer, String> entry =
                    Map.entry(opcao, menu.getItensMenu().getOrDefault(opcao, ""));

            Object call = options.getOrDefault(entry, () -> {
                System.out.println("Opção invalida. Tente novamente.");
                return true;
            }).call();

            if (call == Boolean.FALSE) {
                logger.info("Saindo...");
                break;
            }
        } while (true);
        scanner.close();
    }

    private void optionsHandler() {
        options.put(Map.entry(1, menu.getItensMenu().get(1)), () -> {
            System.out.println("Cadastrar cliente");
            return true;
        });

        options.put(Map.entry(1, menu.getItensMenu().get(1)), () -> cadastrar());
        options.put(Map.entry(2, menu.getItensMenu().get(2)), () -> true);
        options.put(Map.entry(3, menu.getItensMenu().get(3)), () -> true);
        options.put(Map.entry(4, menu.getItensMenu().get(4)), () -> true);
        options.put(Map.entry(5, menu.getItensMenu().get(5)), () -> listar());
        options.put(Map.entry(6, menu.getItensMenu().get(6)), () -> true);
        options.put(Map.entry(7, menu.getItensMenu().get(7)), () -> false);
    }

    private Menu createMenu() {
        var builder = new Menu.Builder()
                .comOpcao(1, "Cadastrar Cliente")
                .comOpcao(2, "Alterar Clientes")
                .comOpcao(3, "Buscar pelo Id")
                .comOpcao(4, "Buscar pelo Nome")
                .comOpcao(5, "Listar Clientes")
                .comOpcao(6, "Excluir Clientes")
                .comOpcao(7, "Sair")
                .build();
        return builder;
    }

    public boolean cadastrar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        Email email = null;

        do {
            System.out.print("Email: ");
            String strEmail = scanner.nextLine();
            email = new Email(strEmail);
            if (!email.isValid()) {
                System.out.println("Endereço de e-mail inválido. Por favor, tente novamente.");
            }
        } while (!email.isValid());

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        database.create(new Cliente(nome, email, telefone));
        scanner.close();
        return true;
    }


    private boolean listar() {
        database.getAll().forEach(System.out::println);
        return true;
    }


    private void printLogo() {
        String thin = """
                                                                            \s
                .   .     o,---.                                            \s
                |   |,---..|    ,---.,---..   .,-.-.,---.,---.              \s
                |   ||   |||    |---'`---.|   || | |,---||                  \s
                `---'`   '``---'`---'`---'`---'` ' '`---^`                  \s
                                                                            \s
                                                                            \s
                ,---.                                                      ||
                |---',---.,---.,---.,---.,---.,-.-.,---.,---.,---.,---.    ||
                |    |    |   ||   ||    ,---|| | |,---||    ,---||   |    ||
                `    `    `---'`---|`    `---^` ' '`---^`---'`---^`---'    ``
                               `---'                                        \s
                """;
        logger.info(thin);
    }

}
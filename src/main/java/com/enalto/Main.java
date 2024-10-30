package com.enalto;

import com.enalto.repository.InMemoryDatabase;

import java.util.HashMap;
import java.util.Map;
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
    private final Map<Map.Entry<Integer, String>, Callable> options = new HashMap<>();
    private final Menu menu = new Menu();

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run();
    }


    private void run() throws Exception {

        InMemoryDatabase db = InMemoryDatabase.getInstance();
        createMenu();
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

            if (call == null) {
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

        options.put(Map.entry(2, menu.getItensMenu().get(2)), () -> true);
        options.put(Map.entry(3, menu.getItensMenu().get(3)), () -> true);
        options.put(Map.entry(4, menu.getItensMenu().get(4)), () -> true);
        options.put(Map.entry(5, menu.getItensMenu().get(5)), () -> true);
        options.put(Map.entry(6, menu.getItensMenu().get(6)), () -> true);
        options.put(Map.entry(7, menu.getItensMenu().get(7)), () -> null);
    }

    private Menu createMenu() {
        menu.addItem(1, "Cadastrar Cliente");
        menu.addItem(2, "Alterar Clientes");
        menu.addItem(3, "Buscar pelo Id");
        menu.addItem(4, "Buscar pelo Nome");
        menu.addItem(5, "Listar Clientes");
        menu.addItem(6, "Excluir Clientes");
        menu.addItem(7, "Sair");
        return menu;
    }

    public void cadastrar() {

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
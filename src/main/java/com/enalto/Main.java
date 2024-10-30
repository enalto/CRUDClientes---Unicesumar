package com.enalto;

import com.enalto.repository.InMemoryDatabase;
import com.enalto.service.ClienteService;
import com.enalto.util.Menu;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
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
    // testando layinitialization com Supplier
    private static final Supplier<ClienteService> clienteServiceSupplier = ClienteService::new;
    private static ClienteService clienteService;

    private Menu menu;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run();
    }


    private void run() throws Exception {

        InMemoryDatabase db = InMemoryDatabase.getInstance();
        this.menu = createMenu();
        optionsHandler();
        if (Integer.parseInt(javaVersion) >= 15) {
            printLogo();
        }

        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            clearConsole();

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

    public ClienteService getClienteService() {
        if (clienteService == null) {
            clienteService = clienteServiceSupplier.get();
        }
        return clienteService;
    }

    private void optionsHandler() {
        options.put(Map.entry(1, menu.getItensMenu().get(1)), () -> add());
        options.put(Map.entry(2, menu.getItensMenu().get(2)), () -> edit());
        options.put(Map.entry(3, menu.getItensMenu().get(3)), () -> findById());
        options.put(Map.entry(4, menu.getItensMenu().get(4)), () -> findByName());
        options.put(Map.entry(5, menu.getItensMenu().get(5)), () -> list());
        options.put(Map.entry(6, menu.getItensMenu().get(6)), () -> remove());
        options.put(Map.entry(7, menu.getItensMenu().get(7)), () -> false);
    }

    private Menu createMenu() {
        var builder = new Menu.Builder()
                .comOpcao(1, "Cadastrar Cliente")
                .comOpcao(2, "Alterar Clientes")
                .comOpcao(3, "Buscar pelo Id")
                .comOpcao(4, "Buscar pelo Nome")
                .comOpcao(5, "Listar Clientes ")
                .comOpcao(6, "Excluir Clientes")
                .comOpcao(7, "Sair")
                .build();
        return builder;
    }


    private boolean add() {
        clearConsole();
        return this.getClienteService().cadastrar();
    }

    private boolean edit() {
        clearConsole();

        try {
            return this.getClienteService().alterar();
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
        return true;
    }

    private boolean list() {
        clearConsole();

        try {
            return this.getClienteService().listar();
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
        return true;
    }

    private boolean findById() {
        clearConsole();

        try {
            return this.getClienteService().findById();
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
        return true;
    }

    private boolean findByName() {
        clearConsole();

        try {
            return this.getClienteService().findByName();
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
        return true;
    }

    private boolean remove() {
        clearConsole();

        try {
            return this.getClienteService().delete();
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
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

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

}
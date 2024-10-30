package com.enalto.service;

import com.enalto.Main;
import com.enalto.domain.Cliente;
import com.enalto.repository.InMemoryDatabase;
import com.enalto.util.ValidarInput;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClienteService {

    private final InMemoryDatabase database = InMemoryDatabase.getInstance();
    private static final Logger logger = Logger.getLogger(Main.class.getName());


    public ClienteService() {
        InMemoryDatabase db = InMemoryDatabase.getInstance();
    }

    public boolean cadastrar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: " + (database.getNextId() + 1));

        String nome = ValidarInput.validateInput(scanner, "Entre com o nome: ",
                (String s) -> !s.isEmpty());

        String email = ValidarInput.validateInput(scanner, "Entre com seu email: ",
                (String s) -> s.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));

        String telefone = ValidarInput.validateInput(scanner, "Entre com o telefone: ",
                (String s) -> s.matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}\\-?\\d{4}$"));


        String confirm = ValidarInput.validateInput(scanner, "Confirma a inclusão ? (s/n)",
                (String s) -> (s.equalsIgnoreCase("s") | s.equalsIgnoreCase("n")));
        if (confirm.equalsIgnoreCase("s")) {
            Optional<Cliente> cliente = database.create(new Cliente(nome, email, telefone));
            logger.info("Cliente cadastrado com sucesso!, Id=" + cliente.get().getId());
        }
        scanner.close();
        return true;
    }

    public boolean alterar() {
        if (database.getAll().isEmpty())
            throw new RuntimeException("Lista de clientes está vazia.");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String readString = ValidarInput.validateInput(scanner, "Entre com o ID ou (0) para sair: ",
                    (String input) -> input.matches("^\\d+$"));

            int id = Integer.parseInt(readString);
            if (id == 0) break;

            Optional<Cliente> cliente = database.getById(id);
            if (cliente.isEmpty()) {
                System.out.println("Cliente com Id= " + id + " não encontrado. Tente novamente.");
            } else {
                printCliente(cliente.get());

                String novoNome = ValidarInput.validateInput(scanner, "Entre com o nome: ",
                        (String s) -> !s.isEmpty());
                String novoEmail = ValidarInput.validateInput(scanner, "Entre com seu email: ",
                        (String s) -> s.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));
                String novoTelefone = ValidarInput.validateInput(scanner, "Entre com o telefone: ",
                        (String s) -> s.trim().matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}\\-?\\d{4}$"));
                Cliente clienteAlterado = new Cliente(novoNome, novoEmail, novoTelefone);
                Optional<Cliente> result = database.updateById(id, clienteAlterado);
                if (result.isPresent()) {
                    System.out.println("Cliente alterado com sucesso.");
                } else {
                    System.out.println("Erro ao atualizar cliente.");
                }
            }
        }
        scanner.close();
        return true;
    }

    public boolean findById() {
        if (database.getAll().isEmpty())
            throw new RuntimeException("Lista de clientes está vazia.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String readString = ValidarInput.validateInput(scanner, "Entre com o ID ou (0) para sair: ",
                    (String input) -> input.matches("^\\d+$"));

            int id = Integer.parseInt(readString);
            if (id == 0) break;
            Optional<Cliente> cliente = database.getById(id);
            if (cliente.isEmpty()) {
                System.out.println("Cliente com Id= " + id + " não encontrado. Tente novamente.");
            } else {
                printCliente(cliente.get());
            }
        }
        scanner.close();
        return true;
    }

    public boolean findByName() {
        if (database.getAll().isEmpty())
            throw new RuntimeException("Lista de clientes está vazia.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String nome = ValidarInput.validateInput(scanner, "Entre com o nome ou (enter) para sair: ",
                    (String s) -> (s.length() >= 2 | s.isEmpty()));
            if (nome.isEmpty())
                break;
            List<Cliente> clienteList = database.getByName(nome);
            if (clienteList.isEmpty()) {
                System.out.println("Cliente com nome= " + nome + " não encontrado. Tente novamente.");
            } else {
                clienteList.forEach(ClienteService::printCliente);
            }
        }
        scanner.close();
        return true;
    }

    public boolean delete() {
        if (database.getAll().isEmpty())
            throw new RuntimeException("Lista de clientes está vazia.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String readString = ValidarInput.validateInput(scanner, "Entre com o ID ou (0) para sair: ",
                    (String input) -> input.matches("^\\d+$"));

            int id = Integer.parseInt(readString);
            if (id == 0) break;

            Optional<Cliente> cliente = database.getById(id);
            if (cliente.isEmpty()) {
                System.out.println("Cliente com Id= " + id + " não encontrado. Tente novamente.");
            } else {
                printCliente(cliente.get());
                String confirm = ValidarInput.validateInput(scanner, "Confirma a exclusão ? (s/n)",
                        (String s) -> (s.equalsIgnoreCase("s") | s.equalsIgnoreCase("n")));
                if (confirm.equalsIgnoreCase("s")) {
                    database.delete(id);
                    logger.info("Cliente com Id= " + id + " deletado com sucesso.");
                    break;
                }
            }
        }
        return true;
    }

    public boolean listar() {
        if (database.getAll().isEmpty())
            throw new RuntimeException("Lista de clientes está vazia.");

        System.out.println("-".repeat(50));
        database.getAll().forEach(System.out::println);
        System.out.println("-".repeat(50));
        return true;
    }

    private static void printCliente(Cliente cliente) {
        System.out.println("Id: " + cliente.getId());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("-".repeat(50));
    }
}

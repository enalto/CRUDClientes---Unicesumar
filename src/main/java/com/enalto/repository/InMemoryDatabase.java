package com.enalto.repository;

import com.enalto.domain.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryDatabase {

    private static InMemoryDatabase instance;
    private static List<Cliente> clientes;
    private static int nextId;

    private InMemoryDatabase() {
        clientes = new ArrayList<>();
        nextId = 0;
    }

    public static InMemoryDatabase getInstance() {
        if (instance == null) {
            instance = new InMemoryDatabase();
        }
        return instance;
    }

    public List<Cliente> getAll() {
        return clientes;
    }

    public Optional<Cliente> getById(long id) {
        Optional<Cliente> findResult = clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        return findResult;
    }

    public Optional<Cliente> create(Cliente cliente) {
        cliente.setId(++nextId);
        clientes.add(cliente);
        return Optional.of(cliente);
    }

    public Optional<Cliente> update(Cliente cliente) {
        Optional<Cliente> find = getById(cliente.getId());

        find.ifPresent(c -> {
            c.setNome(cliente.getNome());
            c.setEmail(cliente.getEmail());
            c.setTelefone(cliente.getTelefone());
        });
        return Optional.of(find.orElse(null));
    }

    public boolean delete(long id) {
        //clientes.removeIf(c -> c.getId() == id);

        Optional<Cliente> find = getById(id);

        if (find.isPresent()) {
            clientes.remove(find.get());
            return true;
        }
        return false;
    }

    public Optional<Cliente> getByEmail(String email) {
        Optional<Cliente> firstResult = clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();

        return firstResult;
    }

    public Optional<Cliente> getByName(String name) {
        Optional<Cliente> firstResult = clientes.stream()
                .filter(c -> c.getNome().equals(name))
                .findFirst();

        return firstResult;
    }

    public Optional<Cliente> updateById(long id, Cliente cliente) {
        Optional<Cliente> find = getById(id);
        find.ifPresent(c -> {
            c.setNome(cliente.getNome());
            c.setEmail(cliente.getEmail());
            c.setTelefone(cliente.getTelefone());
        });
        return Optional.of(find.orElse(null));
    }

}

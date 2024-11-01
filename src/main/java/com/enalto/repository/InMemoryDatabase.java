package com.enalto.repository;

import com.enalto.domain.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class InMemoryDatabase {

    private static InMemoryDatabase instance;
    private static List<Cliente> clientes;
    private static final Supplier<List<Cliente>> supplierArrayList = ArrayList::new;
    private static int nextId;

    private InMemoryDatabase() {
        nextId = 0;
    }

    public static InMemoryDatabase getInstance() {
        if (instance == null) {
            clientes = supplierArrayList.get();
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

    public boolean delete(long id) {
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

    public List<Cliente> getByName(String name) {
        List<Cliente> list = clientes.stream()
                .filter(c -> c.getNome().startsWith(name))
                .toList();
        return list;
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

    public int getNextId() {
        return nextId;
    }
}

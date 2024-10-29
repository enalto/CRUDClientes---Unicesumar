package com.enalto.repository;

import com.enalto.domain.Cliente;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClienteRepositoryImpl implements ClienteRepository {

    private InMemoryDatabase database;

    public ClienteRepositoryImpl(InMemoryDatabase database) {
        Objects.requireNonNull(database);
        this.database = database;
    }

    private ClienteRepositoryImpl() {
    }

    @Override
    public Optional<Cliente> findById(long id) {
        return database.getById(id);
    }

    @Override
    public List<Cliente> findAll() {
        return database.getAll();
    }

    @Override
    public Optional<Cliente> create(Cliente cliente) {
        Optional<Cliente> result = database.create(cliente);
        return result;
    }

    @Override
    public boolean delete(long id) {
        return database.delete(id);
    }

    @Override
    public Optional<Cliente> findByNome(String nome) {
        return database.getByName(nome);
    }

    @Override
    public Optional<Cliente> findByEmail(String email) {
        return database.getByEmail(email);
    }

    @Override
    public Optional<Cliente> updateById(long ig, Cliente cliente) {
        return database.updateById(ig, cliente);
    }
}

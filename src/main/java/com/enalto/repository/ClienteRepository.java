package com.enalto.repository;

import com.enalto.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    public Optional<Cliente> findById(long id);

    public List<Cliente> findAll();

    public Optional<Cliente> create(Cliente cliente);

    public boolean delete(long id);

    public Optional<Cliente> findByNome(String nome);

    public Optional<Cliente> findByEmail(String email);

    public Optional<Cliente> updateById(long ig, Cliente cliente);
}

package com.enalto.repository;

import com.enalto.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    public Optional<Cliente> findById(long id);

    public List<Cliente> findAll();

    public Optional<Cliente> create(Cliente cliente);

    public Optional<Cliente> delete(Cliente cliente);

    public List<Cliente> findByNome(String nome);

    public List<Cliente> findByEmail(String email);

    public Cliente updateById(long ig);
}

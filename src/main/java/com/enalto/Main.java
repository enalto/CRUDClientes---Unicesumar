package com.enalto;

import com.enalto.domain.Cliente;
import com.enalto.repository.InMemoryDatabase;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        InMemoryDatabase db = InMemoryDatabase.getInstance();

        Optional<Cliente> cliente = db.create(new Cliente("jose", "teste@email", "123456"));

        System.out.println(cliente.isPresent());

        db.getAll().forEach(System.out::println);

    }
}
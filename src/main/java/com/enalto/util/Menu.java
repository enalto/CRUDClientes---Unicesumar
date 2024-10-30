package com.enalto.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Menu {

    private final Map<Integer, String> itensMenu;

    public Menu(Builder builder) {
        this.itensMenu = new HashMap<>(builder.itensMenu);
    }

    public void showMenu() {
        this.itensMenu.forEach((key, value) -> {
            System.out.println(key + "- " + value);
        });
    }

    public Map<Integer, String> getItensMenu() {
        return Collections.unmodifiableMap(itensMenu);
    }

    public static class Builder {

        private Map<Integer, String> itensMenu = new HashMap<>();

        public Builder comOpcao(Integer key, String value) {
            itensMenu.put(key, value);
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}

package com.enalto;

import java.util.HashMap;
import java.util.Map;

public class Menu {

    private final Map<Integer, String> itensMenu = new HashMap<Integer, String>();

    public Menu() {
    }

    public Menu addItem(Integer key, String value) {
        itensMenu.put(key, value);
        return this;
    }

    public void showMenu() {
        this.itensMenu.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
    }


}

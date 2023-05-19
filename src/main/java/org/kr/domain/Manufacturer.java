package org.kr.domain;

import lombok.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Manufacturer implements Serializable {
    private int id;
    private String name;
    private String country;
    private Map<String, Souvenir> souvenirs;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
        this.souvenirs = new HashMap<>();
    }

    public void addSouvenir(Souvenir souvenir) {
        souvenirs.put(souvenir.getName(), souvenir);
    }

    @Override
    public String toString() {
        return "Manufacturer: " + name + ", country: " + country;
    }
}

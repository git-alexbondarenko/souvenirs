package org.kr.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer implements Serializable {
    private int id;
    private String name;
    private String country;
    @JsonManagedReference
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

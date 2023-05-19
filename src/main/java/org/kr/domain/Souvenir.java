package org.kr.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Souvenir implements Serializable {
    private int id;
    private String name;
    private LocalDate dateOfProduction;
    private double price;
    private Manufacturer manufacturer;

    public Souvenir(String name, LocalDate dateOfProduction, double price, Manufacturer manufacturer) {
        this.name = name;
        this.dateOfProduction = dateOfProduction;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Souvenir: " + name
                + ", production date: " + dateOfProduction + ", price: " + price
                + ", manufacturer: " + manufacturer;
    }
}

package org.kr.service;

import org.kr.dao.Dao;
import org.kr.domain.Manufacturer;

import java.util.List;

/**
        * • Додавати, редагувати, переглядати всіх виробників та всі сувеніри.
        * • Вивести інформацію про сувеніри заданого виробника.
        * • Вивести інформацію про сувеніри, виготовлені в заданій країні.
        * • Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
        * • Вивести інформацію по всіх виробниках та, для кожного виробника вивести інформацію про всі сувеніри, які він виробляє.
        * • Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року.
        * • Для кожного року вивести список сувенірів, зроблених цього року.
        * • Видалити заданого виробника та його сувеніри.
**/

public class ManufacturerService {
    private final Dao dao;

    public ManufacturerService(Dao dao) {
        this.dao = dao;
    }


    public List<Manufacturer> getManufacturers() {
        return  dao.index().values().stream().toList();
    }

    public Manufacturer getManufacturerByName(String manufacturerName) {
        return dao.get(manufacturerName);
    }

    public void addManufacturer(String manufacturerName, String country) {
        Manufacturer newManufacturer = new Manufacturer(manufacturerName, country);
        dao.add(newManufacturer);
        dao.save();
    }

    public void removeManufacturer(String manufacturerName) {
        dao.remove(manufacturerName);
        dao.save();
    }

    public void editManufacturer(String manufacturerName, String newName, String newCountry) {
        Manufacturer manufacturerToEdit = dao.get(manufacturerName);
        dao.remove(manufacturerToEdit.getName());
        manufacturerToEdit.setName(newName);
        manufacturerToEdit.setCountry(newCountry);
        dao.add(manufacturerToEdit);
        dao.save();
    }

    public List<Manufacturer> getManufacturersBySouvenirPriceUnder(double price) {
        return dao
                .index()
                .values()
                .stream()
                .filter(manufacturer -> manufacturer.getSouvenirs()
                        .values()
                        .stream()
                        .anyMatch(souvenir -> souvenir.getPrice() < price))
                .toList();
    }

    /*public Map<Manufacturer, List<Souvenir>> getAllManufacturersAndTheirSouvenirs() {
        return dao
                .index()
                .values()
                .stream()
                .collect(Collectors.toMap(
                        manufacturer -> manufacturer,
                        manufacturer -> manufacturer.getSouvenirs()
                                .values()
                                .stream()
                                .toList()));
    }*/

    public void listAllManufacturersAndTheirSouvenirs() {
        dao.index().values().forEach(manufacturer -> {System.out.println(manufacturer.getName());
                        manufacturer.getSouvenirs().values().forEach(System.out::println);
                });

    }

    public List<Manufacturer> getManufacturersBySouvenirNameAndProductionYear(String souvenirName, int year) {
        return dao
                .index()
                .values()
                .stream()
                .filter(manufacturer -> manufacturer.getSouvenirs()
                        .values()
                        .stream()
                .anyMatch(souvenir -> souvenir.getDateOfProduction().getYear() == year
                        && souvenir.getName().equals(souvenirName)))
                .toList();
    }


}

package org.kr.service;

import org.kr.domain.Souvenir;
import org.kr.dao.Dao;
import org.kr.domain.Manufacturer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SouvenirService {

    private final Dao dao;

    public SouvenirService(Dao dao) {
        this.dao = dao;
    }

    public List<Souvenir> getSouvenirs() {
        return dao
                .index()
                .values()
                .stream()
                .flatMap(manufacturer -> manufacturer.getSouvenirs()
                        .values()
                        .stream())
                .toList();
    }

    public void addSouvenir(String souvenirName, LocalDate dateOfProduction, double price, String manufacturerName) {
        Manufacturer manufacturer = dao.get(manufacturerName);
        Souvenir newSouvenir = new Souvenir(souvenirName, dateOfProduction, price, manufacturer);
        manufacturer.addSouvenir(newSouvenir);
        dao.save();
    }

    public void editSouvenir(String souvenirName, String manufacturerName, String newName, LocalDate newDate, double newPrice) {
        Manufacturer manufacturer = dao.get(manufacturerName);
        Map<String, Souvenir> manufacturerSouvenirs = manufacturer.getSouvenirs();
        Souvenir souvenirToEdit = manufacturerSouvenirs.get(souvenirName);
        manufacturerSouvenirs.remove(souvenirToEdit.getName());
        souvenirToEdit.setName(newName);
        souvenirToEdit.setDateOfProduction(newDate);
        souvenirToEdit.setPrice(newPrice);
        manufacturerSouvenirs.put(souvenirToEdit.getName(), souvenirToEdit);
        dao.save();
    }

    public void removeSouvenir(String manufacturerName, String souvenirName) {
        dao.get(manufacturerName).getSouvenirs().remove(souvenirName);
        dao.save();
    }

    public List<Souvenir> getSouvenirsByProductionCountry(String countryName) {
        return dao
                .index()
                .values()
                .stream()
                .filter(manufacturer -> manufacturer.getCountry().equals(countryName))
                .flatMap(manufacturer -> manufacturer.getSouvenirs().values().stream())
                .toList();
    }

    public List<Souvenir> getSouvenirsByProductionYear(int year) {
        return dao
                .index()
                .values()
                .stream()
                .flatMap(manufacturer -> manufacturer.getSouvenirs().values().stream())
                .filter(souvenir -> souvenir.getDateOfProduction().getYear() == year)
                .toList();
    }
}

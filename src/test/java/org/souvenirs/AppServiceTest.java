package org.souvenirs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kr.dao.Dao;
import org.kr.domain.Manufacturer;
import org.kr.domain.Souvenir;
import org.kr.service.ManufacturerService;
import org.kr.service.SouvenirService;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppServiceTest
{
    @Test
    public void testGetManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        assertTrue(manufacturers.containsAll(manufacturersList));
    }

    @Test
    public void testGetManufacturerByName() {
        assertEquals(manufacturersList.get(0), manufacturerService.getManufacturerByName("manufacturer1"));
        assertNull(manufacturerService.getManufacturerByName("falseName"));
    }

    @Test
    public void testAddManufacturer() {
        manufacturerService.addManufacturer("some name", "some country");
        assertTrue(manufacturerService.getManufacturers()
                .stream()
                .anyMatch(manufacturer -> manufacturer.getName().equals("some name")));
    }

    @Test
    public void testRemoveManufacturer() {
        manufacturerService.removeManufacturer("manufacturer1");
        assertEquals(2, manufacturerService.getManufacturers().size());
    }

    @Test
    public void testAddSouvenir() {
        souvenirService.addSouvenir("some name", LocalDate.now(), 1.23, "manufacturer1");
        assertTrue(manufacturerService
                .getManufacturerByName("manufacturer1")
                .getSouvenirs().containsKey("some name"));
    }

    @Test
    public void testGetSouvenirs() {
        assertTrue(souvenirService.getSouvenirs().containsAll(souvenirsList));
    }

    @Test
    public void testDeleteSouvenir() {
        souvenirService.removeSouvenir("manufacturer1", "toy");
        assertEquals(1, manufacturerService.getManufacturers()
                .stream()
                .filter(manufacturer -> manufacturer.getName().equals("manufacturer1"))
                .findFirst()
                .get()
                .getSouvenirs()
                .values()
                .size());
    }

    @Test
    public void testGetSouvenirsByProductionCountry() {
        assertTrue(souvenirService
                .getSouvenirsByProductionCountry("ua")
                .containsAll(List.of(souvenir1, souvenir3, souvenir4)));
    }

    @Test
    public void getManufacturersBySouvenirPriceUnder() {
        manufacturersList.remove(2);
        assertEquals(manufacturersList, manufacturerService.getManufacturersBySouvenirPriceUnder(20));
    }

    @Test
    public void getManufacturersBySouvenirNameAndProductionYear() {
        assertEquals(manufacturersList.subList(0, 2),
                manufacturerService.getManufacturersBySouvenirNameAndProductionYear("statuette", 2022));
    }

    @Test
    public void getSouvenirsByProductionYear() {
        assertEquals(List.of(souvenir1,souvenir5, souvenir2), souvenirService.getSouvenirsByProductionYear(2022));
    }


    List<Souvenir> souvenirsList;
    List<Manufacturer> manufacturersList;

    Map<String, Manufacturer> manufacturersMap;
    ManufacturerService manufacturerService;
    SouvenirService souvenirService;
    Dao dao;
    Souvenir souvenir1;
    Souvenir souvenir2;
    Souvenir souvenir3;
    Souvenir souvenir4;
    Souvenir souvenir5;


    @BeforeEach
    void setUp() {
        Manufacturer manufacturer1 = new Manufacturer("manufacturer1", "ua");

        Manufacturer manufacturer2 = new Manufacturer("manufacturer2", "us");

        Manufacturer manufacturer3 = new Manufacturer("manufacturer3", "ua");

        souvenir1 = new Souvenir("statuette", LocalDate.of(2022,1,1), 12.00, manufacturer1);
        souvenir2 = new Souvenir("sticker", LocalDate.of(2022,3,7), 1.50, manufacturer2);
        souvenir3 = new Souvenir("toy", LocalDate.of(2012,3,4), 9.99, manufacturer1);
        souvenir4 = new Souvenir("test", LocalDate.now(), 33.3, manufacturer3);
        souvenir5 = new Souvenir("statuette", LocalDate.of(2022,1,1), 12.00, manufacturer2);

        manufacturer1.addSouvenir(souvenir1);
        manufacturer1.addSouvenir(souvenir3);
        manufacturer2.addSouvenir(souvenir2);
        manufacturer2.addSouvenir(souvenir5);
        manufacturer3.addSouvenir(souvenir4);

        souvenirsList = new ArrayList<>(List.of(souvenir1, souvenir2, souvenir3, souvenir4, souvenir5));
        manufacturersList = new ArrayList<>(List.of(manufacturer1, manufacturer2, manufacturer3));


        manufacturersMap = new HashMap<>(Map.of(manufacturer1.getName(), manufacturer1, manufacturer2.getName(),
                manufacturer2, manufacturer3.getName(), manufacturer3));

        dao = new Dao(manufacturersMap);
        manufacturerService = new ManufacturerService(dao);
        souvenirService = new SouvenirService(dao);
    }
}

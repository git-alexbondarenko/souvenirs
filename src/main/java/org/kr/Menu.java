package org.kr;

import org.kr.service.ManufacturerService;
import org.kr.service.SouvenirService;
import org.kr.util.ManufacturerValidator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;


public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final ManufacturerValidator manufacturerValidator;
    private final ManufacturerService manufacturerService;
    private final SouvenirService souvenirService;

    public Menu(ManufacturerValidator manufacturerValidator, ManufacturerService manufacturerService,
                SouvenirService souvenirService) {
        this.manufacturerValidator = manufacturerValidator;
        this.manufacturerService = manufacturerService;
        this.souvenirService = souvenirService;
    }

    public void run() {
        String legend = """
                Select operation:
                1.  list all souvenirs
                2.  list all manufacturers
                3.  add souvenir
                4.  add manufacturer
                5.  delete souvenir
                6.  delete manufacturer
                7.  edit souvenir
                8.  edit manufacturer
                9.  list souvenirs of selected manufacturer
                10. list souvenirs manufactured in specified country
                11. list souvenirs by production year
                12. list all manufacturers and their souvenirs
                13. list manufacturers of specified souvenir, manufactured in specified year
                14. list manufacturers, which have souvenirs with price bellow specified
                """;
        System.out.println(legend);
        while (true) {
            scanner.reset();
            System.out.print("Choose action (0 for legend): ");
            String action = scanner.nextLine();
            switch (action) {
                case "0" -> System.out.println(legend);

                case "1" -> souvenirService.getSouvenirs().forEach(System.out::println);

                case "2" -> manufacturerService.getManufacturers().forEach(System.out::println);

                case "3" -> addSouvenir(getManufacturerName());

                case "4" -> addManufacturer(getManufacturerName());

                case "5" -> souvenirService.removeSouvenir(getManufacturerName(), getSouvenirName());

                case "6" -> manufacturerService.removeManufacturer(getManufacturerName());

                case "7" -> editSouvenir(getManufacturerName(), getSouvenirName());

                case "8" -> editManufacturer(getManufacturerName());

                case "9" -> listSouvenirsOfSelectedManufacturer(getManufacturerName());

                case "10" -> souvenirService.getSouvenirsByProductionCountry(getCountry()).forEach(System.out::println);

                case "11" -> souvenirService.getSouvenirsByProductionYear(getProductionYear()).forEach(System.out::println);

                case "12" -> manufacturerService.listAllManufacturersAndTheirSouvenirs();

                case "13" -> manufacturerService.getManufacturersBySouvenirNameAndProductionYear(
                        getSouvenirName(), getProductionYear()).forEach(System.out::println);

                case "14" -> manufacturerService.getManufacturersBySouvenirPriceUnder(getPrice()).forEach(System.out::println);
            }
        }
    }

    private void addSouvenir(String manufacturerName) {
        if (!manufacturerValidator.validate(manufacturerName)) {
            System.err.println("no such manufacturer");
            return;
        }
        String souvenirName = getSouvenirName();
        if (manufacturerService.getManufacturerByName(manufacturerName).getSouvenirs().containsKey(souvenirName)) {
            System.err.println("souvenir with this name already exists");
        } else {
            souvenirService.addSouvenir(souvenirName, getDate(), getPrice(), manufacturerName);
        }
    }

    private void addManufacturer(String manufacturerName) {
        if (!manufacturerValidator.validate(manufacturerName)) {
            manufacturerService.addManufacturer(manufacturerName, getCountry());
        } else System.err.println("Manufacturer with this name already exists");
    }

    private void editSouvenir(String manufacturerName, String souvenirName) {
        if (manufacturerValidator.validate(manufacturerName)) {
            if (manufacturerService.getManufacturerByName(manufacturerName).getSouvenirs().containsKey(souvenirName)) {
                System.out.println("Enter new values for souvenir: ");
                souvenirService.editSouvenir(souvenirName, manufacturerName, getSouvenirName(),
                        getDate(), getPrice());
            } else System.err.println("No such souvenir");
        } else System.err.println("No such manufacturer");
    }

    private void editManufacturer(String manufacturerName) {
        if (!manufacturerValidator.validate(manufacturerName)) {
            System.err.println("no such manufacturer");
        } else {
            System.out.println("Enter new values for manufacturer: ");
            manufacturerService.editManufacturer(manufacturerName, getManufacturerName(), getCountry());
        }
    }

    private void listSouvenirsOfSelectedManufacturer(String manufacturerName) {
        if (manufacturerValidator.validate(manufacturerName)) {
            manufacturerService.getManufacturerByName(manufacturerName).getSouvenirs().values().forEach(System.out::println);
        } else System.err.println("No such manufacturer");
    }



    private String getCountry() {
        System.out.print("Enter country: ");
        return scanner.nextLine();
    }

    private LocalDate getDate() {
        System.out.println("Enter production date in format yyyy mm dd");
        int[] d = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        return LocalDate.of(d[0], d[1], d[2]);
    }

    private int getProductionYear() {
        System.out.print("Enter production year: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private String getSouvenirName() {
        System.out.print("Enter souvenir name: ");
        return scanner.nextLine();
    }

    private String getManufacturerName() {
        System.out.print("Enter manufacturer name: ");
        return scanner.nextLine();
    }

    private double getPrice() {
        System.out.print("Enter price: ");
        return Double.parseDouble(scanner.nextLine());
    }
}

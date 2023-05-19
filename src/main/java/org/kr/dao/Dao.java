package org.kr.dao;

import org.kr.domain.Manufacturer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Dao {

    private final Map<String, Manufacturer> manufacturers;

    public Dao() {
        try {
            manufacturers = new HashMap<>();

            File file = new File("manufacturers.bin");
            if (!file.exists()) {
                save();
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            manufacturers.putAll(new HashMap<>((Map<String, Manufacturer>) ois.readObject()));
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao(Map<String, Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Map<String, Manufacturer> index() {
            return manufacturers;
    }

    public Manufacturer get(String manufacturerName) {
        return manufacturers.get(manufacturerName);
    }

    public void add(Manufacturer manufacturer) {
        manufacturers.put(manufacturer.getName(), manufacturer);
        save();
    }

    public void remove(String manufacturerName) {
        manufacturers.remove(manufacturerName);
        save();
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("manufacturers.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(manufacturers);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.kr.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.kr.domain.Manufacturer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dao {

    private final Map<String, Manufacturer> manufacturers;
    private final ObjectMapper objectMapper;
    private final File file;

    public Dao() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        file = new File("manufacturers.json");

        if (!file.exists()) {
            manufacturers = new HashMap<>();
            save();
        } else {
            try {
                manufacturers = objectMapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error reading manufacturers from JSON file", e);
            }
        }
    }

    public Dao(Map<String, Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
        objectMapper = new ObjectMapper();
        file = new File("manufacturers.json");
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
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
            objectMapper.writeValue(file, manufacturers);
        } catch (IOException e) {
            throw new RuntimeException("Error writing manufacturers to JSON file", e);
        }
    }
}


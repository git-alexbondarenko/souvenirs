package org.kr.util;

import org.kr.dao.Dao;

public class ManufacturerValidator {
    private final Dao dao;

    public ManufacturerValidator(Dao dao) {
        this.dao = dao;
    }

    public boolean validate(String manufacturerName) {
        return dao.index().containsKey(manufacturerName);
    }
}

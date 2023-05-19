package org.kr;

import org.kr.dao.Dao;
import org.kr.service.ManufacturerService;
import org.kr.service.SouvenirService;
import org.kr.util.ManufacturerValidator;

public class App 
{

    public static void main( String[] args ) {
        Dao dao = new Dao();
        Menu menu = new Menu(new ManufacturerValidator(dao), new ManufacturerService(dao), new SouvenirService(dao));
        menu.run();
    }
}

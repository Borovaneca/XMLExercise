package com.example.productShop.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public enum Utils {

    ;

    public static  <T> JAXBContext createContext(Class<T> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz);
    }
}
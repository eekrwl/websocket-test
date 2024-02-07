package com.example.websocket.repository;

import com.example.websocket.entity.Driver;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class DriverRepository {

    private static DriverRepository driverRepository = new DriverRepository();
    private static Map<String, Driver> drivers = new ConcurrentHashMap<>();

    private DriverRepository() {
    }

    public static DriverRepository getInstance() {
        return driverRepository;
    }

    public void create(Driver driver) {
        driver.setDtype("driver");
        drivers.put(driver.getId(), driver);
    }
}

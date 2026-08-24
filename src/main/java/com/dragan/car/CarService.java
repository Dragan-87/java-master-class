package com.dragan.car;

import java.util.UUID;

public class CarService {

    CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public Car[] getCars() {
        return carDao.getCars();
    }

    public Car getCarById(UUID uuid) {
        return carDao.getCarById(uuid);
    }

}

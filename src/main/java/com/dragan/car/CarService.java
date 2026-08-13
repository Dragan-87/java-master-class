package com.dragan.car;

import com.dragan.exceptions.ResourceNotFoundException;

import java.util.Scanner;
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
        while (true) {
            try {
                return carDao.getCarById(uuid);
            } catch (ResourceNotFoundException e) {
                System.out.println("Car UUID not found, try again:");
                Scanner scanner = new Scanner(System.in);
                getCarById(UUID.fromString(scanner.nextLine()));
            }
        }
    }

}

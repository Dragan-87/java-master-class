package com.dragan.car;

import com.dragan.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CarDao {
    private Car[] cars = {
            new Car(UUID.randomUUID(), "MA MA 223".toUpperCase(), new BigDecimal("230.00"), Brand.AUDI, false),
            new Car(UUID.randomUUID(), "MA MA 444".toUpperCase(), new BigDecimal("400.00"), Brand.MERCEDES, false),
            new Car(UUID.randomUUID(), "MA MA 933".toUpperCase(), new BigDecimal("130.00"), Brand.TESLA, true),
            new Car(UUID.randomUUID(), "MA MA 111".toUpperCase(), new BigDecimal("100.00"), Brand.TOYOTA, false)
    };

    public Car[] getCars() {
        return Arrays.copyOf(cars, cars.length);
    }

    public Car getCarById(UUID uuid) throws ResourceNotFoundException {
        for (Car car : cars) {
            if (car != null && Objects.equals(uuid, car.getUuid())) {
                return car;
            }
        }
        throw new ResourceNotFoundException("Car with UUID: " + uuid + " not found");
    }

}

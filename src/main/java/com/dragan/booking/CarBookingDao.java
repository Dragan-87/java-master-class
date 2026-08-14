package com.dragan.booking;

import com.dragan.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CarBookingDao {

    CarBooking[] carBookings = {};

    public CarBooking[] getCarBookings() {
        return Arrays.copyOf(carBookings, carBookings.length);
    }

    public CarBooking save(CarBooking carBooking) {
        var newBookings = Arrays.copyOf(carBookings, carBookings.length + 1);
        newBookings[newBookings.length - 1] = carBooking;
        carBookings = newBookings;
        return carBooking;
    }

    public CarBooking[] deleteById(UUID uuid) throws ResourceNotFoundException {
        if (carBookings == null || uuid == null) {
            return carBookings;
        }

        int indexToRemove = -1;
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] != null && Objects.equals(uuid, carBookings[i].getId())) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            System.out.println("Booking whit UUID: " + uuid + " not found.");
            return carBookings;
        }

        CarBooking[] newArray = new CarBooking[carBookings.length - 1];

        int newIndex = 0;
        for (int i = 0; i < carBookings.length; i++) {
            if (i == indexToRemove) {
                continue;
            }
            newArray[newIndex] = carBookings[i];
            newIndex++;
        }

        carBookings = newArray;
        return newArray;
    }
}

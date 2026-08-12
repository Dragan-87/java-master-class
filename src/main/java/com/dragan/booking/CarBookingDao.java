package com.dragan.booking;

import java.util.Arrays;

public class CarBookingDao {

    CarBooking[] carBookings = {};

    public CarBooking[] getCarBookings() {
        return carBookings;
    }

    public CarBooking save(CarBooking carBooking) {
        CarBooking[] newBookings = Arrays.copyOf(carBookings, carBookings.length + 1);
        newBookings[newBookings.length - 1] = carBooking;
        carBookings = newBookings;
        return carBooking;
    }

}

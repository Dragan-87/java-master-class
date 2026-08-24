package com.dragan.booking;

import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.exceptions.ResourceNotFoundException;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;


public class CarBookingService {
    private final UserService userService;
    private final CarService carService;
    private final CarBookingDao carBookingDao;
    private final LocalDate today = LocalDate.now();


    public CarBookingService(UserService userService, CarService carService, CarBookingDao carBookingDao) {
        this.userService = userService;
        this.carService = carService;
        this.carBookingDao = carBookingDao;
    }

    public CarBooking bookCar(User user, Car car, LocalDate start, LocalDate end) {
        var carBookings = carBookingDao.getCarBookings();
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && Objects.equals(car.getUuid(), carBooking.getCar().getUuid())) {
                if (!isAvailable(start, end, carBooking.getStartDate(), carBooking.getEndDate())) {
                    throw new DateTimeException("Car is already booked from: "
                            + carBooking.getStartDate() + ", to: " + carBooking.getEndDate());
                }
            }
        }

        long daysBetween = ChronoUnit.DAYS.between(start, end);
        BigDecimal price = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(daysBetween));

        CarBooking newBooking = new CarBooking(
                UUID.randomUUID(), user, car, start, end, price,
                BookingStatus.ACTIVE, LocalDateTime.now()
        );
        return carBookingDao.save(newBooking);
    }

    public boolean isAvailable(LocalDate newStart, LocalDate newEnd,
                               LocalDate existingStart, LocalDate existingEnd) {
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }

    public CarBooking cancelBookingById(UUID uuid) throws ResourceNotFoundException {
        CarBooking booking = carBookingDao.deleteById(uuid);
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found: " + uuid);
        }
        return booking;
    }

    public CarBooking[] getAllBookings() {
        return carBookingDao.getCarBookings();
    }

    public CarBooking[] getUserBookingsById(UUID uuid) {
        CarBooking[] userBookings = {};
        CarBooking[] allBookings = getAllBookings();
        for (int i = 0; i < allBookings.length; i++) {
            if (allBookings[i] != null && Objects.equals(uuid, allBookings[i].getUser().getUuid())) {
                userBookings = Arrays.copyOf(userBookings, userBookings.length + 1);
                userBookings[userBookings.length - 1] = allBookings[i];
            }
        }
        return userBookings;
    }



}

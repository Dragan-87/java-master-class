package com.dragan.booking;

import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;


public class CarBookingService {
    UserService userService;
    CarService carService;
    CarBookingDao carBookingDao = new CarBookingDao();
    Scanner scanner = new Scanner(System.in);
    LocalDate today = LocalDate.now();

    CarBooking[] carBookings = carBookingDao.getCarBookings();

    public CarBookingService(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    public CarBooking bookCar(User user, Car car, LocalDate start, LocalDate end) {
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

    public LocalDate validateStartingDate(String message) {
        LocalDate start;
        while (true) {
            System.out.println(message);
            try {
                start = LocalDate.parse(scanner.nextLine());
                if (start.isBefore(today)) {
                    throw new IllegalArgumentException("Starting date is in the past!");
                }
                return start;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format, please try again!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalDate validateEndingDate(String message, LocalDate start) {
        LocalDate end;
        while (true) {
            System.out.println(message);
            try {
                end = LocalDate.parse(scanner.nextLine());
                if (start.isAfter(end)) {
                    throw new IllegalArgumentException("Start date is after end date");
                }
                return end;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format, please try again!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public UUID validateUUID(String message) {
        while (true) {
            System.out.println(message);
            try {
                return UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("UUID is invalid, please try again!");
            }
        }
    }

    public void startBookingProcess() {
        User user;
        Car car;
        LocalDate start;
        LocalDate end;

        user = userService.getUserById(validateUUID("Enter user id"));
        car = carService.getCarById(validateUUID("Enter car id"));
        start = validateStartingDate("Staring Date, format: jjjj-mm-dd");
        end = validateEndingDate("End Date, format: jjjj-mm-dd", start);

        System.out.println(bookCar(user, car, start, end));
    }

    public void cancelBookingById() {
        while (true) {
            try {
                String stringUUID = scanner.nextLine();
                UUID uuid = UUID.fromString(stringUUID);
                
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID, pleas try again!");
                cancelBookingById();
            }
        }
    }

    /*delete after testing*/
    static void main() {
        UserService userService1 = new UserService();
        CarService carService1 = new CarService();
        CarBookingService carBookingService = new CarBookingService(userService1, carService1);
        Scanner scanner1 = new Scanner(System.in);
        carBookingService.startBookingProcess();
    }

}

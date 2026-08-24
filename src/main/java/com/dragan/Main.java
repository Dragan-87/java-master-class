package com.dragan;

import com.dragan.booking.CarBooking;
import com.dragan.booking.CarBookingDao;
import com.dragan.booking.CarBookingService;
import com.dragan.car.Car;
import com.dragan.car.CarDao;
import com.dragan.car.CarService;
import com.dragan.exceptions.ResourceNotFoundException;
import com.dragan.user.User;
import com.dragan.user.UserDao;
import com.dragan.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final CarDao carDao = new CarDao();
        final CarService carService = new CarService(carDao);

        final UserDao userDao = new UserDao();
        final UserService userService = new UserService(userDao);

        final CarBookingDao carBookingDao = new CarBookingDao();
        final CarBookingService carBookingService = new CarBookingService(userService, carService, carBookingDao);

        int userChoiceInput = 0;
        Scanner scanner = new Scanner(System.in);

        Main mainObj = new Main();

        System.out.println("Welcome to Car Booking System");
        printMenu();

        while (userChoiceInput != 8) {
            String input = scanner.nextLine();
            try {
                userChoiceInput = Integer.parseInt(input);
                switch (userChoiceInput) {
                    case 1:
                        mainObj.handleCarStartBookingProcess(carBookingService, userService, carService);
                        break;
                    case 2:
                        mainObj.handleCancelBookingById(carBookingService);
                        break;
                    case 3:
                        mainObj.handleViewAllUserBookings(carBookingService);
                        break;
                    case 4:
                        mainObj.handleAllBookingsView(carBookingService);
                        break;
                    case 5:
                        mainObj.handleViewAvailableCars(carService);
                        break;
                    case 6:
                        mainObj.handleViewAvailableElectricCars(carService);
                        break;
                    case 7:
                        mainObj.handleViewAllUsers(userService);
                        break;
                    case 8:
                        System.out.println("Exit process.");
                        break;
                    default:
                        System.out.println("Enter a number from 1 to 8");

                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a number! Please try again.\n");
                printMenu();
            }
        }
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("Pleas enter a Number to chose a option:");
        System.out.println("1 - Book Car");
        System.out.println("2 - Delete Booking");
        System.out.println("3 - View all bookings by specific user");
        System.out.println("4 - View All Bookings");
        System.out.println("5 - View Available Cars");
        System.out.println("6 - View Available Electric Cars");
        System.out.println("7 - View All Users");
        System.out.println("8 - Exit");
    }

    public void handleViewAllUsers(UserService userService) {
        var users = userService.getUsers();
        for (User user : users) {
            if (user != null) {
                System.out.println(user);
            }
        }
    }

    public void handleViewAvailableCars(CarService carService) {
        var cars = carService.getCars();
        for (Car car : cars) {
            if (car != null) {
                System.out.println(car);
            }
        }
    }

    public void handleViewAvailableElectricCars(CarService carService) {
        var cars = carService.getCars();
        for (Car car : cars) {
            if (car != null && car.isElectric()) {
                System.out.println(car);
            }
        }
    }

    public void handleCarStartBookingProcess(CarBookingService carBookingService, UserService userService, CarService carService) {
        while (true) {
            try {
                User user = handleGetUserByIdRequest(userService);
                Car car = handleGetCarByIdRequest(carService);
                LocalDate start = handleUserDateInput("Enter starting date, format yyyy-mm-dd");
                LocalDate end = handleUserDateInput("Enter ending date, format yyyy-mm-dd");
                System.out.println(carBookingService.bookCar(user, car, start, end));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid user UUID, pleas try again!");
            } catch (ResourceNotFoundException e) {
                System.out.println("UUID not found, pleas try again!");
            }
        }
    }

    public void handleCancelBookingById(CarBookingService carBookingService) {
        UUID uuid = validateUUID("Enter booking UUID");
        carBookingService.cancelBookingById(uuid);
    }

    public void handleViewAllUserBookings(CarBookingService carBookingService) {
        UUID userUUID = validateUUID("Enter user UUID");
        CarBooking[] carBookingsByUser = carBookingService.getUserBookingsById(userUUID);
        if (carBookingsByUser.length == 0) {
            System.out.println("No bookings yet");
            return;
        }

        for (CarBooking booking : carBookingsByUser) {
            if ((booking != null)) {
                System.out.println(booking);
            }
        }
    }

    public void handleAllBookingsView(CarBookingService carBookingService) {
        CarBooking[] allBookings = carBookingService.getAllBookings();
        if (allBookings.length == 0) {
            System.out.println("No bookings yet");
            return;
        }

        for (CarBooking booking : allBookings) {
            if (booking != null) {
                System.out.println(booking);
            }
        }
    }

    public User handleGetUserByIdRequest(UserService userService) {
        UUID userUUID = validateUUID("Enter user UUID:");
        try {
            return userService.getUserById(userUUID);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage() + ", pleas try again!");
            return handleGetUserByIdRequest(userService);
        }

    }

    public Car handleGetCarByIdRequest(CarService carService) {
        UUID userUUID = validateUUID("Enter car UUID:");
        try {
            return carService.getCarById(userUUID);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage() + ", pleas try again!");
            return handleGetCarByIdRequest(carService);
        }

    }

    public  UUID validateUUID(String message) throws IllegalArgumentException {
        while (true) {
            System.out.println(message);
            try {
                return UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("UUID is invalid, please try again!");
            }
        }
    }

    public LocalDate handleUserDateInput(String message) {
        while (true) {
            try {
                System.out.println(message);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date, pleas try again! Date format yyyy-mm-dd");
            }
        }
    }

}

package com.dragan;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import com.dragan.booking.CarBooking;
import com.dragan.booking.CarBookingDao;
import com.dragan.booking.CarBookingService;
import com.dragan.car.Car;
import com.dragan.car.CarDao;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserDao;
import com.dragan.user.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final CarDao carDao = new CarDao();
        final CarService carService = new CarService(carDao);

        final UserDao userDao = new UserDao();
        final UserService userService = new UserService(userDao);

        final CarBookingDao carBookingDao = new CarBookingDao();
        final CarBookingService carBookingService = new CarBookingService(userService, carService, carBookingDao);

        int userChoiceInput = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Car Booking System");
        printMenu();

        while (userChoiceInput != 8) {
            String input = scanner.nextLine();
            try {
                userChoiceInput = Integer.parseInt(input);
                switch (userChoiceInput) {
                    case 1:
                        handleCarStartBookingProcess(carBookingService);
                        break;
                    case 2:
                        handleCancelBookingById(carBookingService);
                        break;
                    case 3:
                        handleViewAllUserBookings(carBookingService);
                        break;
                    case 4:
                        handleAllBookingsView(carBookingService);
                        break;
                    case 5:
                        handleViewAvailableCars(carService);
                        break;
                    case 6:
                        handleViewAvailableElectricCars(carService);
                        break;
                    case 7:
                        handleViewAllUsers(userService);
                        break;
                    case 8:
                        System.out.println("Exit process.");
                        break;
                    default:
                        System.out.println("Number is bigger then 8");

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

    public static void handleViewAllUsers(UserService userService) {
        var users = userService.getUsers();
        for (User user : users) {
            if (user != null) {
                System.out.println(user);
            }
        }
    }

    public static void handleViewAvailableCars(CarService carService) {
        var cars = carService.getCars();
        for (Car car : cars) {
            if (car != null) {
                System.out.println(car);
            }
        }
    }

    public static void handleViewAvailableElectricCars(CarService carService) {
        var cars = carService.getCars();
        for (Car car : cars) {
            if (car != null && car.isElectric()) {
                System.out.println(car);
            }
        }
    }

    public static void handleCarStartBookingProcess(CarBookingService carBookingService) {
        carBookingService.startBookingProcess();
    }

    public static void handleCancelBookingById(CarBookingService carBookingService) {
        carBookingService.cancelBookingById();
    }

    public static void handleViewAllUserBookings(CarBookingService carBookingService) {
        CarBooking[] carBookingsByUser = carBookingService.getUserBookingsById();
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

    public static void handleAllBookingsView(CarBookingService carBookingService) {
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
}

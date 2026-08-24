package com.dragan.car;

import java.math.BigDecimal;
import java.util.UUID;

public class Car {
    private UUID uuid;
    private String regNumber;
    private BigDecimal rentalPricePerDay;
    private Brand brand;
    private boolean isElectric;

    public Car() {
    }

    public Car(UUID uuid, String regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric) {
        this.uuid = uuid;
        this.regNumber = regNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.brand = brand;
        this.isElectric = isElectric;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public boolean isElectric() {
        return isElectric;
    }


    @Override
    public String toString() {
        return "Car{" +
                "uuid=" + uuid +
                ", regNumber='" + regNumber + '\'' +
                ", rentalPricePerDay=" + rentalPricePerDay +
                ", brand=" + brand +
                ", isElectric=" + isElectric +
                '}';
    }
}

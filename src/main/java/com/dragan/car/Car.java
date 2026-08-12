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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public void setElectric(boolean electric) {
        isElectric = electric;
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

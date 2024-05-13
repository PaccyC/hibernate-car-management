package com.paccy.hibernate;

import com.paccy.hibernate.models.Address;
import com.paccy.hibernate.models.Car;
import com.paccy.hibernate.models.Driver;
import com.paccy.hibernate.utils.HibernateUtil;

public class App {

    public static void main(String[] args){


//        Create a new Car

        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("Samsung");
        car.setColor("Black");
        car.setPrice(1000000);
        HibernateUtil.saveCar(car);


        Address address = new Address();
        address.setStreet("Street");
        address.setCity("City");
        address.setZipcode("0000");

        Driver driver = new Driver();
        driver.setFirstName("Smith");
        driver.setLastName("John");
        driver.setDob("2000-01-01");
        driver.setPhone("1234567890");
        driver.setAddress(address);

//        Save the driver
        HibernateUtil.saveOrUpdateDriver(driver);


//        Retrieve the driver by id
        Driver retrievedDriver=HibernateUtil.getDriverById(driver.getId());
        System.out.println("Retrieved driver" +retrievedDriver.getFirstName());

        retrievedDriver.setCar(car);
        HibernateUtil.saveOrUpdateDriver(retrievedDriver);

    }
}

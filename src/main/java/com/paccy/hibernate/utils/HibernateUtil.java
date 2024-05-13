package com.paccy.hibernate.utils;

import com.paccy.hibernate.models.Car;
import com.paccy.hibernate.models.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Properties;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static  SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try{
                Configuration configuration = new Configuration();

                Properties settings= new Properties();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/car_management?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                settings.put(Environment.SHOW_SQL,"true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
                settings.put(Environment.HBM2DDL_AUTO,"update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Driver.class);
                configuration.addAnnotatedClass(Car.class);


                ServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                System.out.println("Hibernate Java config  ServiceRegistry created");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return  sessionFactory;

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return  sessionFactory;
    }

    public static void saveOrUpdateDriver(Driver driver){
        Transaction transaction = null;
        try(Session session =getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(driver);
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public static void deleteDriver(int id){
        Transaction transaction = null;
        try(Session session =getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Driver driver = session.get(Driver.class,id);
            if(driver != null){
                session.delete(driver);
            }
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public static Driver getDriverById(int id){
        try(Session session =getSessionFactory().openSession()){
            return session.get(Driver.class,id);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    Cars

//    Saving a new car

    public static void  saveCar(Car car){
        Transaction transaction = null;
        try(Session session =getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();

        }
        catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

//    Getting all cars
    public static Car getAllCars(){
        try(Session session =getSessionFactory().openSession()){
            List<Car> cars = session.createQuery("from Car").list();
            return (Car) cars;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


//    Getting a car by id
    public static Car getCarById(int id){
        try(Session session =getSessionFactory().openSession()){
            return session.get(Car.class,id);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }

//    Deleting a car
    public static void deleteCar(Car car){
        Transaction transaction = null;
        try(Session session =getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.delete(car);
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;

import dat.config.Populator;
import dat.utils.ApiProperties;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Populator populator = new Populator(emf);


        ApplicationConfig.startServer(ApiProperties.PORT);
        populator.populate();

    }
}
package dat.routes;

import dat.config.HibernateConfig;
import dat.controller.DoctorControllerDB;
import dat.controller.DoctorMockController;
import dat.dao.DoctorDAO;
import dat.dao.DoctorMockDAO;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DoctorRoute {

    DoctorMockDAO daoMock  = new DoctorMockDAO();
    DoctorMockController doctorMockController = new DoctorMockController(daoMock);

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    DoctorDAO dao = new DoctorDAO(emf);
    DoctorControllerDB controller = new DoctorControllerDB(dao);

    public EndpointGroup getDoctorRoutes() {
        return () -> {

            get("/", controller::getAll, Role.ANYONE);
            get("/{id}", controller::getById, Role.ANYONE);
            get("/speciality/{speciality}", controller::doctorBySpeciality, Role.ANYONE);
            get("/birthdate/range", controller::doctorByBirthdateRange, Role.ANYONE);
            post("/", controller::createDoctor, Role.ANYONE);
            put("/{id}", controller::update, Role.ANYONE);


            // ------------------------   Appointment ------------------------------------

            get("/appointment/appointment", controller::getAllAppointment, Role.ANYONE);



        };
    }
}

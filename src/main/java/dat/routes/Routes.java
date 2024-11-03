package dat.routes;

import dat.controller.DoctorMockController;
import dat.dao.DoctorMockDAO;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    DoctorRoute route = new DoctorRoute();

    public EndpointGroup getApiRoutes() {       // husk at sætte den til i applicationConig

        return () -> {
            path("/doctor", route.getDoctorRoutes());

        };


    }




}

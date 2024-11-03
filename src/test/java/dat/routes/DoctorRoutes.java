package dat.routes;

import dat.PopulatorTester;
import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.DoctorDAO;
import dat.dto.DoctorDTO;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.exceptions.ValidationException;
import dat.utils.ApiProperties;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static dat.entities.Speciality.PSYCHIATRY;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorRoutes {

    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api/doctor";
    private static DoctorDAO dao = new DoctorDAO(emf);
    private PopulatorTester populatorTester = new PopulatorTester(emf);
    private final static SecurityController securityController = SecurityController.getInstance();
    private final static SecurityDAO securityDAO = new SecurityDAO(emf);

    private static UserDTO userDTO, adminDTO;
    private static String userToken, adminToken;

    DoctorDTO d1, d2, d3;
    List<DoctorDTO> dtoList;

    @BeforeAll
    static void init() {
        app = ApplicationConfig.startServer(ApiProperties.PORT);
    }

    @AfterEach
    void tearDown() {
        populatorTester.cleanUp();
    }

    @AfterAll
    static void afterAll() {
        ApplicationConfig.stopServer(app);
    }


    @BeforeEach
    void setUp() {
        dtoList = populatorTester.populateDoctor();
        d1 = dtoList.get(0);
        d2 = dtoList.get(1);
        d3 = dtoList.get(2);

        UserDTO[] users = PopulatorTester.createTestUser(emf);
        userDTO = users[0];
        adminDTO = users[1];

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO.getUsername(), adminDTO.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        }
        catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Get all doctors ")
    void getAllDoctors() {      //only users can get into this endpoint. Remember til change it in routes if not

        System.out.println("usertoken: " + userToken);
        System.out.println("admintoken: " + adminToken);
        // Verify setup
        assertEquals(3, dtoList.size(), "Expected 3 doctors .");

        // Call the API to fetch the ingredients
        DoctorDTO[] array =
                given()
                        .when()
                        .header("Authorization", userToken) // put user token på 
                        .get(BASE_URL)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(DoctorDTO[].class);

        for (DoctorDTO d : array) {
            System.out.println(d);
        }

        assertThat(array, arrayContainingInAnyOrder(d1,d2,d3));
    }

    @Test
    @DisplayName("Test get single doctor")
    void getOneDoctorById() {
        DoctorDTO doctorDTO =
                given()
                        .when()
                        .get(BASE_URL + "/" + d1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(DoctorDTO.class);
        assertThat(d1, samePropertyValuesAs(doctorDTO));
    }

    @Test
    @DisplayName("Creating a new doctor")
    void testCreateDoctor() {
        System.out.println("usertoken: " + userToken);
        System.out.println("admintoken: " + adminToken);

        DoctorDTO doctorDTO = new DoctorDTO(null, "Dr. David Park", LocalDate.of(1978, 11, 15), 2003, "Hillside Medical Practice", PSYCHIATRY);

        DoctorDTO created =
                given()
                        .contentType("application/json")
                        .body(doctorDTO)
                        .when()
                        .header("Authorization", adminToken)    // put adminToken på
                        .post(BASE_URL)
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(DoctorDTO.class);

        assertThat(created.getId(), notNullValue());
        assertThat(created.getName(), equalTo("Dr. David Park"));
    }

    @Test
    @DisplayName("Updating a doctor  ")
    void testUpdateIngredient() {
        d1.setNameOfClinic("Green Valley Hospital");

        DoctorDTO updatedHotel =
                given()
                        .contentType("application/json")
                        .body(d1)
                        .when()
                        .put(BASE_URL + "/" + d1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(DoctorDTO.class);

        assertThat(updatedHotel.getNameOfClinic(), equalTo("Green Valley Hospital"));
    }


    @Test
    @DisplayName("Get doctors by specialty ") // this dont work
    void testSpecialty(){

        // Call the API to fetch the ingredients
        DoctorDTO[] array =
                given()
                        .when()
                        .get(BASE_URL + "/specialty/SURGERY")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(DoctorDTO[].class);

        for (DoctorDTO d : array) {
            System.out.println(d);
        }

        assertThat("Number of doctors returned", array.length, equalTo(2));
    }



}








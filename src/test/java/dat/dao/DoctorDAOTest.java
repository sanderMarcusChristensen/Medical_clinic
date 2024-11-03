package dat.dao;

import dat.PopulatorTester;
import dat.config.HibernateConfig;
import dat.dto.DoctorDTO;
import dat.entities.Doctor;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static dat.entities.Speciality.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DoctorDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static DoctorDAO dao = new DoctorDAO(emf);
    private static PopulatorTester populator = new PopulatorTester(emf);

    DoctorDTO d1, d2, d3;
    List<DoctorDTO> dtoList;

    LocalDate date1 = LocalDate.of(1979,1,1);
    LocalDate date2 = LocalDate.of(1984,1,1);


    @BeforeEach
    void setUp() {

        dtoList = populator.populateDoctor();
        d1 = dtoList.get(0);
        d2 = dtoList.get(1);
        d3 = dtoList.get(2);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUp();
    }

    @Test
    @DisplayName("Get a doctor by Id")
    void getById() {

        Doctor entityD = new Doctor(d1);
        DoctorDTO dto = dao.getById(entityD.getId());
        assertThat(entityD.getName(), is(dto.getName()));
    }

    @Test
    @DisplayName("Get all doctors ")
    void getAll() {

        assertThat(dtoList, hasSize(3));
        List<DoctorDTO> newList = dao.getAll();
        assertThat(newList, hasSize(3));
        assertThat(newList, containsInAnyOrder(d1, d2, d3));
    }

    @Test
    @DisplayName("Creating a doctor ")
    void create() {

        DoctorDTO d4 = new DoctorDTO(null, "Dr. David Park", LocalDate.of(1978, 11, 15), 2003, "Hillside Medical Practice", PSYCHIATRY);

        assertThat(dao.getAll(), hasSize(3));
        dao.create(d4);
        assertThat(dao.getAll(), hasSize(4));

    }

    @Test
    @DisplayName("Updating doctor ")
    void update() {
        assertThat(d1.getNameOfClinic(), is("City Health Clinic"));
        d1.setNameOfClinic("Downtown Medical Center");
        Long id = d1.getId();

        DoctorDTO update = dao.update(id, d1);

        assertThat(update.getNameOfClinic(), is("Downtown Medical Center"));
    }

    @Test
    @DisplayName("Getting doctors by specialty ")
    void doctorBySpeciality() {

        List<DoctorDTO> list = dao.doctorBySpeciality(SURGERY);
        assertThat(list, hasSize(2));

        List<DoctorDTO> list2 = dao.doctorBySpeciality(FAMILY_MEDICINE);
        assertThat(list2, hasSize(1));

    }

    @Test
    void doctorByBirthdateRange() {

        List<DoctorDTO> list = dao.doctorByBirthdateRange(date1,date2);
        assertThat(list,hasSize(2));

    }
}
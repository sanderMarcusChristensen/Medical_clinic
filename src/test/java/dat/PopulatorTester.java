package dat;

import dat.config.HibernateConfig;
import dat.dao.DoctorDAO;
import dat.dto.DoctorDTO;
import dat.entities.Appointment;
import dat.entities.Doctor;
import dat.entities.Speciality;
import dat.security.entities.Role;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static dat.entities.Speciality.*;


public class PopulatorTester {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    DoctorDAO dao = new DoctorDAO(emf);


    public PopulatorTester(EntityManagerFactory emf){
        this.emf = emf;

    }
    public static UserDTO[] createTestUser(EntityManagerFactory emf) {
        User user, admin;
        Role userRole, adminRole;

        user = new User("UserSander", "UserSander123");
        admin = new User("AdminSander", "AdminSander123");
        userRole = new Role("USER");
        adminRole = new Role("ADMIN");
        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }
        UserDTO userDTO = new UserDTO(user.getUsername(), "UserSander123");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "AdminSander123");
        return new UserDTO[]{userDTO, adminDTO};
    }

    public List<DoctorDTO> populateDoctor(){

        List<DoctorDTO> list = new ArrayList<>();

        DoctorDTO d1 = new DoctorDTO(null, "Dr. Alice Smith", LocalDate.of(1975, 4, 12), 2000, "City Health Clinic", FAMILY_MEDICINE);
        DoctorDTO d2 = new DoctorDTO(null, "Dr. Bob Johnson", LocalDate.of(1980, 8, 5), 2005, "Downtown Medical Center", SURGERY);
        DoctorDTO d3 = new DoctorDTO(null, "Dr. Clara Lee", LocalDate.of(1983, 7, 22), 2008, "Green Valley Hospital", SURGERY);

        DoctorDTO saved1 = dao.create(d1);
        DoctorDTO saved2 = dao.create(d2);
        DoctorDTO saved3 = dao.create(d3);

        list.add(saved1);
        list.add(saved2);
        list.add(saved3);

        return list;

    }

    public void cleanUp(){

        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM Doctor ").executeUpdate();
            em.createQuery("DELETE FROM Appointment ").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

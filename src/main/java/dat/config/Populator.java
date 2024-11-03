package dat.config;

import dat.entities.Appointment;
import dat.entities.Doctor;
import dat.entities.Speciality;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;

public  class Populator {

    private final EntityManagerFactory emf;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void populate() {
        // Create doctors
        Doctor doctor1 = new Doctor();
        doctor1.setName("Dr. Alice Smith");
        doctor1.setDateOfBirth(LocalDate.of(1975, 4, 12));
        doctor1.setYearOfGraduation(2000);
        doctor1.setNameOfClinic("City Health Clinic");
        doctor1.setSpeciality(Speciality.FAMILY_MEDICINE);

        Doctor doctor2 = new Doctor();
        doctor2.setName("Dr. Bob Johnson");
        doctor2.setDateOfBirth(LocalDate.of(1980, 8, 5));
        doctor2.setYearOfGraduation(2005);
        doctor2.setNameOfClinic("Downtown Medical Center");
        doctor2.setSpeciality(Speciality.SURGERY);

        // Create appointments for doctor1
        Appointment appointment1_1 = new Appointment("John Doe", LocalDate.of(2023, 11, 24), LocalTime.of(9, 45), "First visit", doctor1);
        Appointment appointment1_2 = new Appointment("Jane Doe", LocalDate.of(2023, 11, 30), LocalTime.of(11, 30), "Routine checkup", doctor1);
        doctor1.addAppointment(appointment1_1);
        doctor1.addAppointment(appointment1_2);

        // Create appointments for doctor2
        Appointment appointment2_1 = new Appointment("Alice Johnson", LocalDate.of(2023, 11, 27), LocalTime.of(10, 30), "Follow up", doctor2);
        Appointment appointment2_2 = new Appointment("Bob Anderson", LocalDate.of(2023, 12, 12), LocalTime.of(14, 0), "General check", doctor2);
        doctor2.addAppointment(appointment2_1);
        doctor2.addAppointment(appointment2_2);

        // Persist the doctors (and their appointments)
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(doctor1);
            em.persist(doctor2);
            em.persist(appointment1_1);
            em.persist(appointment1_2);
            em.persist(appointment2_1);
            em.persist(appointment2_2);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            em.close();
        }
    }
}

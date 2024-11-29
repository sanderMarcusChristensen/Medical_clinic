package dat.config;

import dat.entities.Appointment;
import dat.entities.Doctor;
import dat.entities.Speciality;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;

public class Populator {

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

        Doctor doctor3 = new Doctor();
        doctor3.setName("Dr. Carol Adams");
        doctor3.setDateOfBirth(LocalDate.of(1985, 3, 15));
        doctor3.setYearOfGraduation(2010);
        doctor3.setNameOfClinic("Westside Health Center");
        doctor3.setSpeciality(Speciality.PEDIATRICS);

        Doctor doctor4 = new Doctor();
        doctor4.setName("Dr. David Brown");
        doctor4.setDateOfBirth(LocalDate.of(1978, 10, 22));
        doctor4.setYearOfGraduation(2003);
        doctor4.setNameOfClinic("Eastside Clinic");
        doctor4.setSpeciality(Speciality.PSYCHIATRY);

        Doctor doctor5 = new Doctor();
        doctor5.setName("Dr. Emily White");
        doctor5.setDateOfBirth(LocalDate.of(1990, 6, 7));
        doctor5.setYearOfGraduation(2015);
        doctor5.setNameOfClinic("Northside Medical Center");
        doctor5.setSpeciality(Speciality.GERIATRICS);

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

        // Create appointments for doctor3
        Appointment appointment3_1 = new Appointment("Chris Martin", LocalDate.of(2023, 12, 1), LocalTime.of(9, 0), "Child checkup", doctor3);
        Appointment appointment3_2 = new Appointment("Laura Davis", LocalDate.of(2023, 12, 3), LocalTime.of(10, 15), "Vaccination", doctor3);
        doctor3.addAppointment(appointment3_1);
        doctor3.addAppointment(appointment3_2);

        // Create appointments for doctor4
        Appointment appointment4_1 = new Appointment("Henry Clark", LocalDate.of(2023, 12, 5), LocalTime.of(11, 0), "Skin rash", doctor4);
        Appointment appointment4_2 = new Appointment("Sophia Moore", LocalDate.of(2023, 12, 7), LocalTime.of(14, 30), "Allergy test", doctor4);
        doctor4.addAppointment(appointment4_1);
        doctor4.addAppointment(appointment4_2);

        // Create appointments for doctor5
        Appointment appointment5_1 = new Appointment("Oliver Johnson", LocalDate.of(2023, 12, 10), LocalTime.of(13, 0), "Heart checkup", doctor5);
        Appointment appointment5_2 = new Appointment("Isabella Brown", LocalDate.of(2023, 12, 14), LocalTime.of(15, 0), "Routine ECG", doctor5);
        doctor5.addAppointment(appointment5_1);
        doctor5.addAppointment(appointment5_2);

        // Persist the doctors (and their appointments)
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(doctor1);
            em.persist(doctor2);
            em.persist(doctor3);
            em.persist(doctor4);
            em.persist(doctor5);
            em.persist(appointment1_1);
            em.persist(appointment1_2);
            em.persist(appointment2_1);
            em.persist(appointment2_2);
            em.persist(appointment3_1);
            em.persist(appointment3_2);
            em.persist(appointment4_1);
            em.persist(appointment4_2);
            em.persist(appointment5_1);
            em.persist(appointment5_2);
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

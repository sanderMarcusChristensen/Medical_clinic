package dat.dao;

import dat.dto.AppointmentDTO;
import dat.dto.DoctorDTO;
import dat.entities.Appointment;
import dat.entities.Doctor;
import dat.entities.Speciality;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO implements IDAO<DoctorDTO, Long> {

    private EntityManagerFactory emf;


    public DoctorDAO (EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public DoctorDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Doctor doctor = em.find(Doctor.class, id);
            return doctor != null ? new DoctorDTO(doctor) : null;
        }
    }

    @Override
    public List<DoctorDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);
            List<Doctor> doctorList = query.getResultList();
            List<DoctorDTO> doctorDTOS = new ArrayList<>();
            for (Doctor doctor : doctorList) {
                doctorDTOS.add(new DoctorDTO(doctor));
            }
            return doctorDTOS;
        }
    }

    @Override
    public DoctorDTO create(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor(doctorDTO); // Convert DTO to entity
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Check if a doctor with the same name already exists
            Doctor existingDoctor = em.createQuery("SELECT d FROM Doctor d WHERE d.name = :name", Doctor.class)
                    .setParameter("name", doctor.getName())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingDoctor != null) {
                throw new ApiException(400, "Doctor " + doctor.getName() + " already exists.");
            }

            // Persist the new doctor
            em.persist(doctor);
            em.getTransaction().commit();

            // Return the created DoctorDTO
            return new DoctorDTO(doctor); // Convert entity back to DTO
        } catch (Exception e) {

            throw new ApiException(500, "something went wrong while creating the doctor.");
        }
    }

    @Override
    public DoctorDTO update(Long id, DoctorDTO doctorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Find the existing doctor by ID
            Doctor existingDoctor = em.find(Doctor.class, id);

            // Check if the doctor exists
            if (existingDoctor == null) {
                em.getTransaction().rollback();
                return null; // You could also throw an exception here
            }

            // Update the fields as specified
            if (doctorDTO.getDateOfBirth() != null) {
                existingDoctor.setDateOfBirth(doctorDTO.getDateOfBirth());
            }

            if (doctorDTO.getYearOfGraduation() > 0) { // Assuming year should be a positive integer
                existingDoctor.setYearOfGraduation(doctorDTO.getYearOfGraduation());
            }

            if (doctorDTO.getNameOfClinic() != null && !doctorDTO.getNameOfClinic().isEmpty()) {
                existingDoctor.setNameOfClinic(doctorDTO.getNameOfClinic());
            }

            if (doctorDTO.getSpeciality() != null) {
                existingDoctor.setSpeciality(doctorDTO.getSpeciality());
            }

            // Commit the transaction
            em.getTransaction().commit();

            // Return the updated DoctorDTO
            return new DoctorDTO(existingDoctor);
        } catch (Exception e) {
            e.printStackTrace(); // For debugging, consider using a logger
            throw new RuntimeException("Failed to update doctor: " + e.getMessage());
        }
    }


    public List<DoctorDTO> doctorBySpeciality(Speciality speciality) {
        try (EntityManager em = emf.createEntityManager()) {
            // Start the transaction
            em.getTransaction().begin();

            // Create a query to find doctors by speciality
            List<Doctor> doctors = em.createQuery("SELECT d FROM Doctor d WHERE d.speciality = :speciality", Doctor.class)
                    .setParameter("speciality", speciality)
                    .getResultList();

            List<DoctorDTO> dtoList = new ArrayList<>();

            for(Doctor d : doctors){
                DoctorDTO dto = new DoctorDTO(d);
                dtoList.add(dto);

            }

            // Commit the transaction
            em.getTransaction().commit();



            return dtoList; // Return the list of doctors found
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            throw new RuntimeException("Failed to retrieve doctors by speciality: " + e.getMessage());
        }
    }


    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to) {
        try (EntityManager em = emf.createEntityManager()) {
            // Start the transaction
            em.getTransaction().begin();

            // Create a query to find doctors by birthdate range
            List<Doctor> doctors = em.createQuery("SELECT d FROM Doctor d WHERE d.dateOfBirth BETWEEN :from AND :to", Doctor.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();

            List<DoctorDTO> dtoList = new ArrayList<>();

            for(Doctor d : doctors){
                DoctorDTO dto = new DoctorDTO(d);
                dtoList.add(dto);
            }

            // Commit the transaction
            em.getTransaction().commit();

            return dtoList; // Return the list of doctors found
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            throw new RuntimeException("Failed to retrieve doctors by birthdate range: " + e.getMessage());
        }
    }



// ------------------------   Appointment ------------------------------------



    public List<AppointmentDTO> getAllAppointment() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Appointment> query = em.createQuery("SELECT d FROM Appointment d", Appointment.class);
            List<Appointment> list = query.getResultList();

            List<AppointmentDTO> dto = new ArrayList<>();

            for (Appointment a : list) {
                dto.add(new AppointmentDTO(a));
            }
            return dto;
        }
    }


}

package dat.controller;

import dat.config.HibernateConfig;
import dat.dao.DoctorDAO;
import dat.dao.DoctorMockDAO;
import dat.dto.DoctorDTO;
import dat.entities.Speciality;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class DoctorControllerDB {

    public DoctorDAO dao;

    public DoctorControllerDB(DoctorDAO dao) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = new DoctorDAO(emf);

    }

    public void getAll(Context ctx) {

        List<DoctorDTO> doctorDTOList = dao.getAll();

        if (doctorDTOList.isEmpty()) {
            throw new ApiException(404, "Doctors not found in list");
        }
        ctx.json(doctorDTOList);
        ctx.status(200);
    }

    public void getById(Context ctx) {

        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            DoctorDTO dto = dao.getById(id);
            if (dto != null) {
                ctx.json(dto);
                ctx.status(200);
            } else {
                throw new ApiException(400, "Failed to get plant");
            }

        } catch (Exception e) {
            throw new ApiException(404, "Plant with that id not found ");
        }

    }


    public void doctorBySpeciality(Context ctx) {
        try {
            String specialityParam = ctx.pathParam("speciality").toUpperCase();
            Speciality speciality = Speciality.valueOf(specialityParam); // Convert string to Speciality enum
            List<DoctorDTO> doctors = dao.doctorBySpeciality(speciality);

            if (doctors.isEmpty()) {
                throw new ApiException(404, "No doctors found for speciality: " + specialityParam);
            }

            ctx.json(doctors);
            ctx.status(200);
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid speciality provided.");
        } catch (Exception e) {
            throw new ApiException(500, "Server error while retrieving doctors by speciality.");
        }

    }

    public void doctorByBirthdateRange(Context ctx) {

        try {
            LocalDate fromDate = LocalDate.parse(ctx.queryParam("from")); // 'from' date as query param
            LocalDate toDate = LocalDate.parse(ctx.queryParam("to")); // 'to' date as query param

            if (fromDate.isAfter(toDate)) {
                throw new ApiException(400, "Invalid date range: 'from' date must be before 'to' date.");
            }

            List<DoctorDTO> doctors = dao.doctorByBirthdateRange(fromDate, toDate);

            if (doctors.isEmpty()) {
                throw new ApiException(404, "No doctors found in the specified date range.");
            }

            ctx.json(doctors);
            ctx.status(200);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ApiException(400, "Invalid date format or missing 'from'/'to' parameters.");
        } catch (Exception e) {
            throw new ApiException(500, "Server error while retrieving doctors by birthdate range.");
        }

    }

    public void createDoctor(Context ctx) {

        DoctorDTO doctorDTO = ctx.bodyAsClass(DoctorDTO.class);
        DoctorDTO newDoctorDTO = dao.create(doctorDTO);

        if(newDoctorDTO.getName().isEmpty()){
            throw new ApiException(400, "Invalid name provided, doctor needs a name.");
        }

        if(newDoctorDTO.getSpeciality() == null){
            throw new ApiException(400, "doctor needs a specialty" );
        }

        ctx.res().setStatus(201);
        ctx.json(newDoctorDTO, DoctorDTO.class);

    }

    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));  // Get the doctor ID from the path parameter
            DoctorDTO updatedDoctorData = ctx.bodyAsClass(DoctorDTO.class);  // Parse the updated data from the request body

            // Call the DAO's update method with the ID and updated doctor data
            DoctorDTO updatedDoctor = dao.update(id, updatedDoctorData);


            if(updatedDoctor.getName().isEmpty()){
                throw new ApiException(400, "Invalid name provided, doctor needs a name.");
            }

            if(updatedDoctor.getSpeciality() == null){
                throw new ApiException(400, "doctor needs a specialty" );
            }


            if (updatedDoctor != null) {
                ctx.json(updatedDoctor);  // Return the updated doctor data as JSON
                ctx.status(200);           // Set status to OK
            } else {
                throw new ApiException(404, "Doctor with ID " + id + " not found.");  // If doctor not found
            }

        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format.");  // If ID is not an integer
        } catch (Exception e) {
            throw new ApiException(500, "Server error while updating doctor data.");  // Catch any other errors
        }
    }


}

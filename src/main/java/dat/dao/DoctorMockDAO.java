package dat.dao;

import dat.dto.DoctorDTO;
import dat.entities.Speciality;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dat.entities.Speciality.*;

public class DoctorMockDAO implements IDAO<DoctorDTO,Long> {

    private static List<DoctorDTO> doctorDTOList = new ArrayList<>();
    private static Long id;

    private static EntityManagerFactory emf;
    private static DoctorMockDAO instance;

    public DoctorMockDAO getInstance(EntityManagerFactory _emf) {

        if (instance == null) {
            emf = _emf;
            instance = new DoctorMockDAO();
        }

        id = 1L;

        DoctorDTO d1 = new DoctorDTO(1L, "Dr. Alice Smith", LocalDate.of(1975, 4, 12), 2000, "City Health Clinic", FAMILY_MEDICINE);
        DoctorDTO d2 = new DoctorDTO(2L, "Dr. Bob Johnson", LocalDate.of(1980, 8, 5), 2005, "Downtown Medical Center", SURGERY);
        DoctorDTO d3 = new DoctorDTO(3L, "Dr. Clara Lee", LocalDate.of(1983, 7, 22), 2008, "Green Valley Hospital", PEDIATRICS);
        DoctorDTO d4 = new DoctorDTO(4L, "Dr. David Park", LocalDate.of(1978, 11, 15), 2003, "Hillside Medical Practice", PSYCHIATRY);
        DoctorDTO d5 = new DoctorDTO(5L, "Dr. Emily White", LocalDate.of(1982, 9, 30), 2007, "Metro Health Center", GERIATRICS);
        DoctorDTO d6 = new DoctorDTO(6L, "Dr. Fiona Martinez", LocalDate.of(1985, 2, 17), 2010, "Riverside Wellness Clinic", SURGERY);
        DoctorDTO d7 = new DoctorDTO(7L, "Dr. George Kim", LocalDate.of(1979, 5, 29), 2004, "Summit Health Institute", FAMILY_MEDICINE);

        doctorDTOList.add(d1);
        doctorDTOList.add(d2);
        doctorDTOList.add(d3);
        doctorDTOList.add(d4);
        doctorDTOList.add(d5);
        doctorDTOList.add(d6);
        doctorDTOList.add(d7);
        return instance;

    }

    @Override
    public DoctorDTO getById(Long id) {
        return doctorDTOList.stream()
                .filter(plant -> plant.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<DoctorDTO> getAll(){

        return doctorDTOList;
    }


    public List<DoctorDTO> doctorBySpeciality(Speciality speciality) {
        return doctorDTOList.stream()
                .filter(doctorDTO -> doctorDTO.getSpeciality() == speciality) // Using == for enum comparison
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to) {
        return doctorDTOList.stream()
                .filter(doctorDTO -> (doctorDTO.getDateOfBirth().isAfter(from) || doctorDTO.getDateOfBirth().isEqual(from)) &&
                        (doctorDTO.getDateOfBirth().isBefore(to) || doctorDTO.getDateOfBirth().isEqual(to)))
                .collect(Collectors.toList());
    }


    public DoctorDTO create(DoctorDTO doctor){
        doctor.setId(id++);
        doctorDTOList.add(doctor);
        return doctor;
    }



    @Override
    public DoctorDTO update(Long id, DoctorDTO doctor) {
        DoctorDTO existingDoctor = getById(id);
        if (existingDoctor != null) {
            existingDoctor.setName(doctor.getName());
            existingDoctor.setDateOfBirth(doctor.getDateOfBirth());
            existingDoctor.setYearOfGraduation(doctor.getYearOfGraduation());
            existingDoctor.setNameOfClinic(doctor.getNameOfClinic());
            existingDoctor.setSpeciality(doctor.getSpeciality());
            return existingDoctor;
        } else {
            return null; // Return null if doctor with given ID is not found
        }
    }


}

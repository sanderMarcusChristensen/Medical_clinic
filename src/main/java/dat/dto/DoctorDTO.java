package dat.dto;

import dat.entities.Doctor;
import dat.entities.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor


public class DoctorDTO {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private int yearOfGraduation;
    private String nameOfClinic;
    private Speciality speciality;


    public DoctorDTO(Long id, String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic,Speciality speciality) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;

    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.dateOfBirth = doctor.getDateOfBirth();
        this.yearOfGraduation = doctor.getYearOfGraduation();
        this.nameOfClinic = doctor.getNameOfClinic();
        this.speciality = doctor.getSpeciality();
    }

}

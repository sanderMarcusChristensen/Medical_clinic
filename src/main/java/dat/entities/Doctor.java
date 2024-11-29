package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dto.DoctorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor


public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate dateOfBirth;
    private int yearOfGraduation;
    private String nameOfClinic;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    @Builder
    public Doctor(Long id, String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Speciality speciality, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Doctor(Long id, String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Speciality speciality, LocalDateTime createdAt, LocalDateTime updatedAt, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.appointments = appointments;
    }

    public Doctor (DoctorDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.dateOfBirth = dto.getDateOfBirth();
        this.yearOfGraduation = dto.getYearOfGraduation();
        this.nameOfClinic = dto.getNameOfClinic();
        this.speciality = dto.getSpeciality();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setDoctor(this); // Set the doctor for the appointment
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setDoctor(null); // Clear the doctor reference in the appointment
    }


    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now; // Set both createdAt and updatedAt to current time
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // Update only the updatedAt field
    }


}

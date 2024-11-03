package dat.entities;


import dat.dto.AppointmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "appointments")

@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    private LocalDate date;

    private LocalTime time;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false) // Foreign key column in the appointment table
    private Doctor doctor; // Reference to the Doctor entity

    public Appointment(String clientName, LocalDate date, LocalTime time, String comment, Doctor doctor) {
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.doctor = doctor;

    }

    public Appointment(AppointmentDTO dto ) {
        this.clientName = dto.getClientName();
        this.date = dto.getDate();
        this.time = dto.getTime();
        this.comment = dto.getComment();
        this.doctor = dto.getDoctor();

    }




}


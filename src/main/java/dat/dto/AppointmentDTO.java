package dat.dto;


import dat.entities.Appointment;
import dat.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor


public class AppointmentDTO {

    private Long id;
    private String clientName;
    private LocalDate date;
    private LocalTime time;
    private String comment;
    private Doctor doctor;

    public AppointmentDTO(Long id, String clientName, LocalDate date, LocalTime time, String comment, Doctor doctor) {
        this.id = id;
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.doctor = doctor;
    }

    public AppointmentDTO(Appointment entity){
        this.id = entity.getId();
        this.clientName = entity.getClientName();
        this.date = entity.getDate();
        this.time = entity.getTime();
        this.comment = entity.getComment();
        this.doctor = entity.getDoctor();
    }


}


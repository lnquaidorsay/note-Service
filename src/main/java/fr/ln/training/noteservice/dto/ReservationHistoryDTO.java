package fr.ln.training.noteservice.dto;

import lombok.Data;

@Data
public class ReservationHistoryDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

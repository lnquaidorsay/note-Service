package fr.ln.training.noteservice.entities;

import fr.ln.training.noteservice.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    private RoomType roomType;
    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;
}

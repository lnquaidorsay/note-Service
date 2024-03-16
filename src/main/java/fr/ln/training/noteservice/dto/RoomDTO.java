package fr.ln.training.noteservice.dto;

import fr.ln.training.noteservice.enums.RoomType;
import lombok.Data;

import java.util.Objects;

@Data
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomDTO)) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return Objects.equals(id, roomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

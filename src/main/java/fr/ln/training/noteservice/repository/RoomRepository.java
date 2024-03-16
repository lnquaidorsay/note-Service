package fr.ln.training.noteservice.repository;

import fr.ln.training.noteservice.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    // Custom query to check if a room with a given room number exists
    @Query("SELECT COUNT(r) > 0 FROM Room r WHERE r.roomNumber = ?1")
    boolean existsByRoomNumber(String roomNumber);

    Optional<Room> findByRoomNumber(String roomNumber);
}

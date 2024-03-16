package fr.ln.training.noteservice.repository;

import fr.ln.training.noteservice.entities.Client;
import fr.ln.training.noteservice.entities.Reservation;
import fr.ln.training.noteservice.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClient(Client client);
    List<Reservation> findByRoom(Room room);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND ((r.checkIN >= :checkInDate AND r.checkIN < :checkOutDate) " +
            "     OR (r.checkOUT > :checkInDate AND r.checkOUT <= :checkOutDate) " +
            "     OR (r.checkIN <= :checkInDate AND r.checkOUT >= :checkOutDate))")
    List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId, @Param("checkInDate") LocalDate checkIN, @Param("checkOutDate") LocalDate checkOUT);
}

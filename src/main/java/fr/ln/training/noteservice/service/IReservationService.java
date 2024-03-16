package fr.ln.training.noteservice.service;

import fr.ln.training.noteservice.dto.ClientDTO;
import fr.ln.training.noteservice.dto.ReservationDTO;
import fr.ln.training.noteservice.dto.RoomDTO;
import fr.ln.training.noteservice.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {
    boolean isRoomAvailable(RoomDTO roomDTO, LocalDate checkINDate, LocalDate checkOUTDate);

    ReservationDTO makeReservation(ClientDTO client, RoomDTO roomDTO, LocalDate CheckINDate, LocalDate CheckOUTDate) throws RoomNotFoundException, RoomNotAvailableException;

    void cancelReservation(Long reservationId) throws ReservationNotFoundException;

    List<ReservationDTO> getReservationsByClient(ClientDTO client) throws ClientNotFoundException, ClientSaveException;

    List<ReservationDTO> getReservationsByRoom(RoomDTO room);
}

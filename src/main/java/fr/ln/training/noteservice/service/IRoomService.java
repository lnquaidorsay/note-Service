package fr.ln.training.noteservice.service;

import fr.ln.training.noteservice.dto.RoomDTO;
import fr.ln.training.noteservice.exceptions.DuplicateRoomException;
import fr.ln.training.noteservice.exceptions.RoomNotFoundException;

import java.util.List;

public interface IRoomService {
    RoomDTO addRoom(RoomDTO room) throws DuplicateRoomException;

    RoomDTO updateRoom(RoomDTO room) throws RoomNotFoundException;

    List<RoomDTO> getAllRooms();

    RoomDTO getRoomByNumber(String roomNumber) throws RoomNotFoundException;

    void deleteRoomById(Long id) throws RoomNotFoundException;

    RoomDTO findById(Long id) throws RoomNotFoundException;
}

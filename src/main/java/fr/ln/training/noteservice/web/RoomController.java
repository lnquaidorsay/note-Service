package fr.ln.training.noteservice.web;

import fr.ln.training.noteservice.dto.RoomDTO;
import fr.ln.training.noteservice.exceptions.DuplicateRoomException;
import fr.ln.training.noteservice.exceptions.RoomNotFoundException;
import fr.ln.training.noteservice.service.RoomServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin("*")
public class RoomController {
    private RoomServiceImpl roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public RoomDTO addRoom(@RequestBody RoomDTO roomDTO) throws DuplicateRoomException {
        return roomService.addRoom(roomDTO);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public RoomDTO updateRoom(@RequestBody RoomDTO roomDTO) throws RoomNotFoundException {
        return roomService.updateRoom(roomDTO);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomNumber}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public RoomDTO getRoomByNumber(@PathVariable String roomNumber) throws RoomNotFoundException {
        return roomService.getRoomByNumber(roomNumber);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteClientById(@PathVariable Long id) throws RoomNotFoundException {
        roomService.deleteRoomById(id);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public RoomDTO getById(@PathVariable Long id) throws RoomNotFoundException {
        return roomService.findById(id);
    }
}


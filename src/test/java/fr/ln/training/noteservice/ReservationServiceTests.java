//package fr.ln.training.noteservice;

//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import fr.ln.training.noteservice.dto.ClientDTO;
//import fr.ln.training.noteservice.dto.ReservationDTO;
//import fr.ln.training.noteservice.dto.RoomDTO;
//import fr.ln.training.noteservice.entities.Client;
//import fr.ln.training.noteservice.entities.Reservation;
//import fr.ln.training.noteservice.entities.Room;
//import fr.ln.training.noteservice.exceptions.ClientNotFoundException;
//import fr.ln.training.noteservice.exceptions.ClientSaveException;
//import fr.ln.training.noteservice.exceptions.ReservationNotFoundException;
//import fr.ln.training.noteservice.exceptions.RoomNotAvailableException;
//import fr.ln.training.noteservice.exceptions.RoomNotFoundException;
//import fr.ln.training.noteservice.mappers.ClientMapper;
//import fr.ln.training.noteservice.mappers.ReservationMapper;
//import fr.ln.training.noteservice.mappers.RoomMapper;
//import fr.ln.training.noteservice.repositories.ClientRepository;
//import fr.ln.training.noteservice.repositories.ReservationRepository;
//import fr.ln.training.noteservice.repositories.RoomRepository;
//import fr.ln.training.noteservice.service.IReservationService;
//import fr.ln.training.noteservice.service.ReservationServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;

// public class ReservationServiceTests {
//    @Mock
//    private RoomRepository roomRepository;
//
//    @Mock
//    private ReservationRepository reservationRepository;
//
//    @Mock
//    private RoomMapper roomMapper;
//
//    @Mock
//    private ReservationMapper reservationMapper;
//
//    @Mock
//    private ClientMapper clientMapper;
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    @InjectMocks
//    private ReservationServiceImpl reservationService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testIsRoomAvailable_ReturnsTrue_WhenRoomIsAvailable() {
//        // Mocking the RoomDTO and Room entity
//        RoomDTO roomDTO = new RoomDTO();
//        roomDTO.setId(1L);
//
//        // Create a mock Room with a known ID for testing
//        Room room = new Room();
//        room.setId(1L);
//
//        when(roomMapper.fromRoomDTO(roomDTO)).thenReturn(room);
//        when(reservationRepository.findOverlappingReservations(eq(room.getId()), any(LocalDate.class), any(LocalDate.class)))
//                .thenReturn(new ArrayList<>());
//
//        // Test Data
//        LocalDate checkin = LocalDate.of(2023, 8, 1);
//        LocalDate checkout = LocalDate.of(2023, 8, 5);
//
//        // Perform the test
//        boolean isAvailable = reservationService.isRoomAvailable(roomDTO, checkin, checkout);
//
//        // Assertion
//        Assertions.assertTrue(isAvailable, "The room should be available.");
//    }
//
//    @Test
//    public void testIsRoomAvailable_ReturnsFalse_WhenRoomIsNotAvailable() {
//        // Arrange
//        RoomDTO roomDTO = new RoomDTO();
//        roomDTO.setId(1L);
//
//        LocalDate checkin = LocalDate.of(2023, 8, 1);
//        LocalDate checkout = LocalDate.of(2023, 8, 5);
//
//        Room room = new Room();
//        room.setId(1L);
//
//        List<Reservation> overlappingReservations = new ArrayList<>();
//        overlappingReservations.add(new Reservation());
//
//        when(roomMapper.fromRoomDTO(roomDTO)).thenReturn(room);
//        when(reservationRepository.findOverlappingReservations(1L, checkin, checkout)).thenReturn(overlappingReservations);
//
//        // Act
//        boolean isAvailable = reservationService.isRoomAvailable(roomDTO, checkin, checkout);
//
//        // Assert
//        assertFalse(isAvailable);
//    }
//
//    @Test
//    public void testMakeReservation_ThrowsRoomNotAvailableException_WhenRoomIsNotAvailable() {
//        // Create a RoomDTO for testing
//        RoomDTO roomDTO = new RoomDTO();
//        roomDTO.setId(1L);
//        roomDTO.setRoomNumber("101");
//
//        // Create an instance of the actual RoomMapper (not a mock)
//        RoomMapper roomMapper = new RoomMapper();
//
//        // Call the method to map RoomDTO to Room
//        Room room = roomMapper.fromRoomDTO(roomDTO);
//
//        // Set up the ReservationRepository mock
//        when(reservationRepository.findOverlappingReservations(any(Long.class), any(LocalDate.class), any(LocalDate.class)))
//                .thenReturn(new ArrayList<>()); // Assuming an empty list means no overlapping reservations
//
//        // Perform the test
//        LocalDate checkINDate = LocalDate.now().plusDays(1);
//        LocalDate checkOUTDate = LocalDate.now().plusDays(3);
//
//        assertThrows(RoomNotAvailableException.class, () -> {
//            reservationService.makeReservation(new ClientDTO(),roomDTO, checkINDate, checkOUTDate);
//        });
//    }
//
//        @Test
//    public void testMakeReservation_ThrowsRoomNotFoundException_WhenRoomNotFound() {
//        // Arrange
//        ClientDTO clientDTO = new ClientDTO();
//        clientDTO.setId(1L);
//
//        RoomDTO roomDTO = new RoomDTO();
//        roomDTO.setId(1L);
//
//        LocalDate checkin = LocalDate.of(2023, 8, 1);
//        LocalDate checkout = LocalDate.of(2023, 8, 5);
//
//        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RoomNotFoundException.class, () -> {
//            reservationService.makeReservation(clientDTO, roomDTO, checkin, checkout);
//        });
//    }
//
//    // Add more test cases for other methods in the ReservationServiceImpl class as needed.
//}

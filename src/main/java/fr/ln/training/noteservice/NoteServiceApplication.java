package fr.ln.training.noteservice;

import fr.ln.training.noteservice.dto.ClientDTO;
import fr.ln.training.noteservice.dto.ReservationDTO;
import fr.ln.training.noteservice.dto.RoomDTO;
import fr.ln.training.noteservice.enums.RoomType;
import fr.ln.training.noteservice.exceptions.DuplicateRoomException;
import fr.ln.training.noteservice.exceptions.EmailAlreadyExistsException;
import fr.ln.training.noteservice.exceptions.RoomNotFoundException;
import fr.ln.training.noteservice.service.IClientService;
import fr.ln.training.noteservice.service.IReservationService;
import fr.ln.training.noteservice.service.IRoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class NoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(IClientService clientService,
                                        IRoomService roomService,
                                        IReservationService reservationService) {
        return args -> {
            // Test cases for ClientService
            ClientDTO client1 = new ClientDTO();
            client1.setId(1L);
            client1.setFirstName("DUPOND");
            client1.setLastName("Benoit");
            client1.setEmail("dupont@benoit.com");
            client1.setPassword("password1");
            try {
                clientService.registerClient(client1);
            } catch (EmailAlreadyExistsException ex) {
                System.out.println("Error while registering client 1: " + ex.getMessage());
            }

            ClientDTO client2 = new ClientDTO();
            client2.setId(2L);
            client2.setFirstName("LEVALLOIS");
            client2.setLastName("Lionel");
            client2.setEmail("levallois@lionel.com");
            client2.setPassword("password2");
            try {
                clientService.registerClient(client2);
            } catch (EmailAlreadyExistsException ex) {
                System.out.println("Error while registering client 1: " + ex.getMessage());
            }

            ClientDTO authenticatedClient = clientService.authenticateClient("oussama@tahri.com", "password1");
            System.out.println("Authenticated Client: " + authenticatedClient);

            // Test cases for RoomService
            RoomDTO room1 = new RoomDTO();
            room1.setId(1L);
            room1.setRoomNumber("777");
            room1.setRoomType(RoomType.SUITE);
            try {
                roomService.addRoom(room1);
            } catch (DuplicateRoomException ex) {
                System.out.println("Error while adding room 1: " + ex.getMessage());
            }

            RoomDTO room2 = new RoomDTO();
            room2.setId(2L);
            room2.setRoomNumber("2222");
            room2.setRoomType(RoomType.SINGLE);
            try {
                roomService.addRoom(room2);
            } catch (DuplicateRoomException ex) {
                System.out.println("Error while adding room 1: " + ex.getMessage());
            }

            List<RoomDTO> allRooms = roomService.getAllRooms();
            System.out.println("All Rooms: " + allRooms);

            RoomDTO roomByNumber = roomService.getRoomByNumber("777");
            System.out.println("Room by Number: " + roomByNumber);

            // Test cases for ReservationService
            // Generate a random number between 1 and 30 (for check-in duration)
            Random random = new Random();
            int randomCheckinDuration1 = random.nextInt(30) + 1;
            int randomCheckinDuration2 = random.nextInt(30) + 1;

            // Generate a random number between 1 and 60 (for stay duration)
            int randomStayDuration1 = random.nextInt(60) + 1;
            int randomStayDuration2 = random.nextInt(60) + 1;

            // Calculate the check-in and check-out dates using the random durations
            LocalDate checkinDate1 = LocalDate.now().plusDays(randomCheckinDuration1);
            LocalDate checkoutDate1 = checkinDate1.plusDays(randomStayDuration1);

            LocalDate checkinDate2 = LocalDate.now().plusDays(randomCheckinDuration2);
            LocalDate checkoutDate2 = checkinDate2.plusDays(randomStayDuration2);

            try {
                ReservationDTO reservation1 = reservationService.makeReservation(
                        client1, room1, checkinDate1, checkoutDate1);
                System.out.println("Reservation 1: " + reservation1);
            } catch (RoomNotFoundException ex) {
                System.out.println("Error while making Reservation 1: " + ex.getMessage());
            }

            try {
                ReservationDTO reservation2 = reservationService.makeReservation(
                        client2, room2, checkinDate2, checkoutDate2);
                System.out.println("Reservation 2: " + reservation2);
            } catch (RoomNotFoundException ex) {
                System.out.println("Error while making Reservation 2: " + ex.getMessage());
            }

            boolean isRoomAvailable = reservationService.isRoomAvailable(room1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));
            System.out.println("Is Room 1 Available: " + isRoomAvailable);

            List<ReservationDTO> reservationsByClient = reservationService.getReservationsByClient(client1);
            System.out.println("Reservations by Client 1: " + reservationsByClient);

            List<ReservationDTO> reservationsByRoom = reservationService.getReservationsByRoom(room2);
            System.out.println("Reservations by room 2: " + reservationsByRoom);
        };
    }

}

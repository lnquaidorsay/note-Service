package fr.ln.training.noteservice;

import fr.ln.training.noteservice.dto.ClientDTO;
import fr.ln.training.noteservice.entities.Client;
import fr.ln.training.noteservice.exceptions.ClientNotFoundException;
import fr.ln.training.noteservice.exceptions.EmailAlreadyExistsException;
import fr.ln.training.noteservice.mappers.ClientMapper;
import fr.ln.training.noteservice.repository.ClientRepository;
import fr.ln.training.noteservice.service.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTests {
    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Before
    public void setUp() {
        // Set up mock behavior for clientMapper
        when(clientMapper.fromClientDTO(any(ClientDTO.class))).thenReturn(new Client());
        when(clientMapper.fromClient(any(Client.class))).thenReturn(new ClientDTO());

        // Set up mock behavior for clientRepository
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());
        when(clientRepository.findByEmail("dupont@benoit.com")).thenReturn(Optional.of(new Client()));
    }

    @Test
    public void testRegisterClient() throws EmailAlreadyExistsException {
        // Arrange
        ClientDTO clientDTO = new ClientDTO();

        // Act
        ClientDTO savedClientDTO = clientService.registerClient(clientDTO);

        // Assert
        assertNotNull(savedClientDTO); // Check if the result is not null
        verify(clientMapper, times(1)).fromClientDTO(clientDTO); // Verify that clientMapper.fromClientDTO is called once
        verify(clientRepository, times(1)).save(any(Client.class)); // Verify that clientRepository.save is called once with any Client argument
    }

    @Test
    public void testAuthenticateClient_ValidCredentials() throws ClientNotFoundException {
        // Arrange
        String email = "dupont@benoit.com";
        String password = "correctpassword";

        // Create a mock Client object with the provided email and password
        Client client = new Client();
        client.setEmail(email);
        client.setPassword(password);

        // Mock the behavior of the clientRepository to return the mock Client object
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));

        // Act
        ClientDTO authenticatedClientDTO = clientService.authenticateClient(email, password);

        // Assert
        assertNotNull(authenticatedClientDTO);
        assertEquals(email, authenticatedClientDTO.getEmail());
        assertEquals(password, authenticatedClientDTO.getPassword());
    }


    @Test(expected = ClientNotFoundException.class)
    public void testAuthenticateClient_InvalidCredentials() throws ClientNotFoundException {
        // Arrange
        String email = "invalid@dupond.com";
        String password = "invalidPassword";
        when(clientRepository.findByEmail(email)).thenReturn(null);

        // Act
        clientService.authenticateClient(email, password);

        // Assert (Exception is expected)
    }
}

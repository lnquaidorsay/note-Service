package fr.ln.training.noteservice.service;

import fr.ln.training.noteservice.dto.ClientDTO;
import fr.ln.training.noteservice.entities.Client;
import fr.ln.training.noteservice.exceptions.ClientNotFoundException;
import fr.ln.training.noteservice.exceptions.EmailAlreadyExistsException;
import fr.ln.training.noteservice.mappers.ClientMapper;
import fr.ln.training.noteservice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements IClientService {

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    @Override
    public ClientDTO registerClient(ClientDTO clientDTO) throws EmailAlreadyExistsException {
        log.info("Saving new Client");
        // Validate email uniqueness before registering the client
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + clientDTO.getEmail());
        }
        Client client = clientMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO authenticateClient(String email, String password) throws ClientNotFoundException {
        // Retrieve the client by email
        Optional<Client> client = clientRepository.findByEmail(email);
        Client clientToSave = client.orElse(null);

        if (clientToSave != null && clientToSave.getPassword() != null && clientToSave.getPassword().equals(password)) {
            // Convert Client entity to ClientDTO using the ClientMapper
            return clientMapper.fromClient(clientToSave);
        } else {
            throw new ClientNotFoundException("Client with email " + email + " not found or invalid credentials.");
        }
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDto) {
        log.info("Updating Client");
        Client client = clientMapper.fromClientDTO(clientDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO getClientByMail(String email) throws ClientNotFoundException {
        // Retrieve the Client entity from the database by client email
        Optional<Client> client = clientRepository.findByEmail(email);
        Client clientFromDB = client.orElse(null);
        if (clientFromDB == null) {
            throw new ClientNotFoundException("Client with email " + email + " not found.");
        }

        // Now, we need to check if the retrieved client actually has the same email as requested
        if (!clientFromDB.getEmail().equals(email)) {
            throw new ClientNotFoundException("Client with email " + email + " not found.");
        }

        // Convert Client entity to ClientDTO using the ClientMapper
        return clientMapper.fromClient(clientFromDB);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClientById(Long id) throws ClientNotFoundException {
        // Check if the client with the given ID exists in the database
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Client with ID: " + id + " Not Found!");
        }

        // Retrieve the client from the database by reservation ID
        Optional<Client> OptionalClient = clientRepository.findById(id);

        // Check if the reservation exists
        if (OptionalClient.isEmpty()) {
            throw new ClientNotFoundException("Room with ID: " + id + " Not Found!");
        }

        // Convert the optional client to Client entity
        Client client = OptionalClient.get();

        // Convert the Client entity to ClientDTO using the clientMapper
        ClientDTO clientDTO = clientMapper.fromClient(client);

        clientRepository.delete(client);

    }
}

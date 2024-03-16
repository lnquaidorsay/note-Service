package fr.ln.training.noteservice.service;

import fr.ln.training.noteservice.dto.ClientDTO;
import fr.ln.training.noteservice.exceptions.ClientNotFoundException;
import fr.ln.training.noteservice.exceptions.EmailAlreadyExistsException;

import java.util.List;

public interface IClientService {
    ClientDTO registerClient(ClientDTO clientDTO) throws EmailAlreadyExistsException;

    ClientDTO authenticateClient(String email, String password) throws ClientNotFoundException;

    ClientDTO updateClient(ClientDTO client);

    ClientDTO getClientByMail(String email) throws ClientNotFoundException;

    List<ClientDTO> getAllClients();

    void deleteClientById(Long id) throws ClientNotFoundException;
}

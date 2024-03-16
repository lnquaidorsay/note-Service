package fr.ln.training.noteservice.web;

import fr.ln.training.noteservice.dto.ClientDTO;
import fr.ln.training.noteservice.exceptions.ClientNotFoundException;
import fr.ln.training.noteservice.exceptions.EmailAlreadyExistsException;
import fr.ln.training.noteservice.service.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@CrossOrigin("*")
public class ClientController {
    private ClientServiceImpl clientService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO registerClient(@RequestBody ClientDTO clientDTO) throws EmailAlreadyExistsException {
        return clientService.registerClient(clientDTO);
    }

    @PostMapping("/authenticate")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO authenticateClient(@RequestParam String email, @RequestParam String password) throws ClientNotFoundException {
        return clientService.authenticateClient(email, password);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(clientDTO);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ClientDTO getClientByMail(@PathVariable String email) throws ClientNotFoundException {
        return clientService.getClientByMail(email);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteClientById(@PathVariable Long id) throws ClientNotFoundException {
        clientService.deleteClientById(id);
    }
}

package fr.ln.training.noteservice.repository;

import fr.ln.training.noteservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {


    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);
}

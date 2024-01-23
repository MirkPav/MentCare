package it.univr;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends CrudRepository<Worker,Long> {

    Worker findById(long id);

    Optional<Worker> findByUsernameIgnoreCase(String username);

}

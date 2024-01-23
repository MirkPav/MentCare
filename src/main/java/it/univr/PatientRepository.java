package it.univr;

import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient,Long> {

    Patient findById(long id);


    Patient findByFirstName(String firstName);

    Iterable<Patient> findAllByFirstNameIgnoreCase(String firstName);

    Iterable<Patient> findAllByLastNameIgnoreCase(String lastName);
}

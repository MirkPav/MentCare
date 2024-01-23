package it.univr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mock {

    private Drugs drugs;

    public Mock(WorkerRepository workerRepository, PatientRepository patientRepository){
        workerRepository.deleteAll();
            workerRepository.save(
                    new Worker("Mirko",
                            "Pavan",
                            "reception",
                            "1234",
                            WorkerRole.RECEPTIONIST)); //TEST

            workerRepository.save(
                    new Worker("Nurse",
                            "Tom",
                            "nurse",
                            "1",
                            WorkerRole.NURSE)); //TEST

            workerRepository.save(
                    new Worker("doctor",
                            "x",
                            "doc",
                            "12",
                            WorkerRole.DOCTOR)); //TEST

        workerRepository.save(
                new Worker("Mariano",
                        "Ceccato",
                        "manager",
                        "123",
                        WorkerRole.MANAGER)); //TEST

         //   patientRepository.save(new Patient("mirko", "pavan", "mirko.pavan@gmail.com", DangerousLevel.LOW));

        Map<String,Float> drugList = new HashMap<>();
        drugList.put("Xanax",1.2f);
        drugList.put("Lexapro",1f);
        drugList.put("Mirtazapine", 5f);
        drugs = new Drugs(drugList);

    }
    public Drugs getDrugs(){
        return drugs;
    }
}

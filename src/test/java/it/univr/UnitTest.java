package it.univr;


import org.junit.Test;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void testNewPatient(){
        Patient patient = new Patient("Mirko","Pavan","mirko.pavan@me.it",DangerousLevel.HIGH);

        assertEquals("Mirko", patient.getFirstName());
        assertEquals("Pavan", patient.getLastName());
        assertEquals("mirko.pavan@me.it", patient.getMailAddress());
        assertEquals(DangerousLevel.HIGH, patient.getDangerousLevel());
        Medication medication1 = new Medication(new Date(2024, Calendar.JANUARY,21),"Xanax",1.0f,1,"");
        Medication medication2 = new Medication(new Date(2024,Calendar.JANUARY,11),"Lexapro",0.8f,2,"none");
        patient.setMedication(medication1.getDate(),medication1.getDrug(), medication1.getDose(), medication1.getDays(), medication1.getNote());
        patient.setMedication(medication2.getDate(),medication2.getDrug(), medication2.getDose(), medication2.getDays(), medication2.getNote());
        List<Medication> medicationList1 = new LinkedList<>();
        medicationList1.add(medication1);
        medicationList1.add(medication2);
        assertEquals("Medication list size", patient.getMedicationList().size(), 2);
        assertEquals("Medication list single element", patient.getMedicationList().get(0), medication1);
        assertEquals("Medication list ", patient.getMedicationList(), medicationList1);
        medicationList1.remove(medication2);
        assertNotEquals("test medication not equals", medication1, medication2);
        assertFalse("no appointment", !patient.getAppointments().isEmpty());
        LocalDateTime now = LocalDateTime.now();
        patient.setAppointments(now);
        assertFalse("there is an appointment", patient.getAppointments().isEmpty());
        assertEquals("appointment", patient.getAppointments().size(),1);
        assertEquals("nextAppointments", patient.getNextAppointments().size(), 1);
        patient.setAppointments(now.minus(2, ChronoUnit.DAYS));
        assertEquals("nextAppointments2", patient.getNextAppointments().size(), 1);



    }

    @Test
    public void testDrugs(){
        Map<String,Float> drugList = new HashMap<>();
        drugList.put("Xanax",1.2f);
        drugList.put("Lexapro",1f);
        drugList.put("Mirtazapine", 5f);
        Drugs drugs = new Drugs(drugList);
        assertEquals("Drug List", drugs.getDrugs(), drugList.keySet());
        assertEquals("Max dose", drugs.getMaxDose("Xanax"), drugList.get("Xanax"));
    }
}

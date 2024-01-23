package it.univr;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private DangerousLevel dangerousLevel;
    private String mailAddress;
    @ElementCollection
    private List<Medication> medicationList;

    @ElementCollection
    private List<LocalDateTime> appointments;

    protected Patient(){}

    public Patient(Patient patient){
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.dangerousLevel = patient.getDangerousLevel();
        this.mailAddress = patient.getMailAddress();
        this.medicationList = new ArrayList<>();
        this.medicationList.addAll(patient.getMedicationList());
        this.appointments = new ArrayList<>();
        this.appointments.addAll(patient.getAppointments());
    }

    public Patient(String firstName, String lastName, String mailAddress, DangerousLevel dangerousLevel){
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.dangerousLevel = dangerousLevel;
        this.medicationList = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setDangerousLevel(DangerousLevel dangerousLevel){
        this.dangerousLevel = dangerousLevel;
    }

    public void setMailAddress(String mailAddress){
        this.mailAddress = mailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMailAddress() { return mailAddress; }

    public DangerousLevel getDangerousLevel() { return dangerousLevel; }


    public void setMedication(Date date, String drug, float dose, int days, String note) {
        medicationList.add(new Medication(date,drug, dose, days, note));

    }


    public List<Medication> getMedicationList() {

        return this.medicationList;
    }

    public List<LocalDateTime> getAppointments(){
        return this.appointments;
    }

    public void setAppointments(LocalDateTime appointment){
        this.appointments.add(appointment);
    }

    public List<LocalDateTime> getNextAppointments(){
        List<LocalDateTime> nextAppointments = new LinkedList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        for(LocalDateTime appointment: getAppointments()){
            if (appointment.isAfter(now.truncatedTo(ChronoUnit.HOURS))){
                nextAppointments.add(appointment);
            }

        }
        return nextAppointments;
    }

}

package it.univr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@SessionAttributes("ROLE")
public class AppController {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    public WorkerRepository workerRepository;

    Mock mock;

    @RequestMapping("/")
    public String init(){
        mock = new Mock(workerRepository,patientRepository); //todo
        return "redirect:/login";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/checkLogin")
    public String checkLogin(
            @RequestParam(name="username", required = true) String username,
            @RequestParam(name="password", required = true) String password,
            Model model){
            Optional<Worker> worker = workerRepository.findByUsernameIgnoreCase(username);
            if(worker.isPresent()) {
                if (worker.get().getPassword().equals(password)) {

                    model.addAttribute("ROLE", worker.get().getRole().name());
                    return "redirect:/home";
                }
            }
            model.addAttribute("loginError",true);
            return "login";

    }

    @RequestMapping("/home")
    public String homepage(){
            return "homepage";
    }

    @RequestMapping("/input")
    public String input() {
        return "input";
    }

    @RequestMapping("/create")
    public String create(@RequestParam(name="firstname") String firstName,
                         @RequestParam(name="lastname") String lastName,
                         @RequestParam(name="email") String email,
                         @RequestParam(name="dangerouslevel") DangerousLevel dangerousLevel
                         ){

        patientRepository.save(new Patient(firstName,lastName,email,dangerousLevel));
        return "homepage";
    }

    @RequestMapping("/list")
    public String list(Model model){
        List<Patient> data = new LinkedList<>();
        for (Patient p: patientRepository.findAll()){
            data.add(p);
        }
        model.addAttribute("patients", data);
        return "list";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus status){
        status.setComplete();
        return "redirect:/login";
    }


    @RequestMapping("/viewRecord")
    public String viewRecord(){
        return "viewRecord";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(name = "firstname") String firstName,
                         @RequestParam(name = "lastname") String lastName,
                         Model model){

        List<Patient> patients = new LinkedList<>();

        for(Patient p: patientRepository.findAllByFirstNameIgnoreCase(firstName)){
            if(lastName.isEmpty() || p.getLastName().equals(lastName)){
                patients.add(p);
            }
        }


        for(Patient p: patientRepository.findAllByLastNameIgnoreCase(lastName)){
            if(firstName.isEmpty() || p.getFirstName().equals(firstName)) {
                if (!patients.contains(p))
                    patients.add(p);
            }
        }


        model.addAttribute("patients", patients);
        return "list";

    }
    @RequestMapping("/details")
    public String details(@RequestParam(name = "id") Long id, Model model){

        Optional<Patient> patient = patientRepository.findById(id);
        patient.ifPresent(value -> model.addAttribute("medications", value.getMedicationList()));
        patient.ifPresent(value -> model.addAttribute("patient", value));

        return "details";
    }

    @RequestMapping("/newMedication")
    public String newMedication(@RequestParam(name = "id", required = false) Long id, Model model){
        Optional<Patient> patient = patientRepository.findById(id);
        Drugs drugList = mock.getDrugs();
        model.addAttribute("today", new Date());
        model.addAttribute("drugs", drugList.getDrugs());
        patient.ifPresent(value -> model.addAttribute("patient", value));
        return "newMedication";
    }
    private Date convertStringToDate(String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    @Transactional
    @RequestMapping("/insertMedication")
    public String insertMedication(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "date") String date,
                                   @RequestParam(name = "drugs") String drugs,
                                   @RequestParam(name = "dose") Float dose,
                                   @RequestParam(name = "days") int days,
                                   @RequestParam(name = "note") String note,
                                   Model model){

        Optional<Patient> patient = patientRepository.findById(id);
        patient.ifPresent(value -> model.addAttribute("patient", value));
        Drugs drugList = mock.getDrugs();
        model.addAttribute("id", id);
        model.addAttribute("drugs", drugList.getDrugs());
        if (drugs.equals("default")){
            model.addAttribute("feedback", "noDrug");
            return "newMedication";
        }
        float maxDose = drugList.getMaxDose(drugs);
        if(maxDose<dose){
            model.addAttribute("feedback", "overmax");
            return "newMedication";
        }


        if(patient.isPresent()){
            Patient updatedPatient = new Patient(patient.get());
            updatedPatient.setMedication(convertStringToDate(date), drugs, dose, days, note);

            patientRepository.save(updatedPatient);
            model.addAttribute("feedback", "ok");
            model.addAttribute("id", updatedPatient.getId());
            return "newMedication";
        }
        return "notfound";
    }

    private List<String> aviableSlots(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        List<String> aviableSlots = new LinkedList<>();
        List<LocalDateTime> lockedSlot = new LinkedList<>();
        for(Patient patient: patientRepository.findAll()){
            lockedSlot.addAll(patient.getAppointments());
        }

        for (int i = 1; i<17520; i ++){
            if (now.truncatedTo(ChronoUnit.HOURS).plusMinutes(i*30).getHour()<9 || now.truncatedTo(ChronoUnit.HOURS).plusMinutes(i*30).getHour()>17)
                continue;
            if (now.truncatedTo(ChronoUnit.HOURS).plusMinutes(i*30).getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                    now.truncatedTo(ChronoUnit.HOURS).plusMinutes(i*30).getDayOfWeek().equals(DayOfWeek.SUNDAY) )
                continue;
            if(lockedSlot.contains(now.truncatedTo(ChronoUnit.HOURS).plusMinutes(i*30)))
                continue;
            aviableSlots.add(now.truncatedTo(ChronoUnit.HOURS).plusMinutes(i*30).format(dtf));
        }
        return aviableSlots;
    }
    @RequestMapping("/newAppointment")
    public String newAppointment(@RequestParam(name = "id") Long id,
                                 Model model) {

        model.addAttribute("slots", aviableSlots());
        Optional<Patient> patient = patientRepository.findById(id);
        patient.ifPresent(value -> model.addAttribute("patient", value));

        return "newAppointment";
    }

    @Transactional
    @RequestMapping("/insertAppointment")
    public String insertAppointment(@RequestParam(name = "id") Long id,
                                    @RequestParam(name = "slots") String date,
                                    Model model){
        Optional<Patient> patient = patientRepository.findById(id);
        patient.ifPresent(value -> model.addAttribute("patient", value));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if(date.equals("default")) {
            model.addAttribute("feedback", "noSlots");
            model.addAttribute("id", id);
            model.addAttribute("slots", aviableSlots());
            return "newAppointment";
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        if(patient.isPresent()){
            Patient updatedPatient = new Patient(patient.get());
            updatedPatient.setAppointments(dateTime);

            patientRepository.save(updatedPatient);
            model.addAttribute("feedback", "ok");
            model.addAttribute("id", updatedPatient.getId());
            model.addAttribute("slots", aviableSlots());
            return "newAppointment";
        }
        return "notfound";
    }

    @RequestMapping("/appointments")
    public String appointments(@RequestParam(name = "id") Long id, Model model){

        Optional<Patient> patient = patientRepository.findById(id);
        patient.ifPresent(value -> model.addAttribute("appointments", value.getAppointments()));
        patient.ifPresent(value -> model.addAttribute("patient", value));

        return "appointments";
    }

    @RequestMapping("/appointmentList")
    public String appointmentList(Model model){
        Map<LocalDateTime, String> appointments = new TreeMap<>();
        for(Patient patient: patientRepository.findAll()){
            for(LocalDateTime appointment: patient.getNextAppointments()){
                appointments.put(appointment, patient.getFirstName() + " " + patient.getLastName());
            }
        }

        model.addAttribute("appointments", appointments);


        return "appointmentList";
    }

}

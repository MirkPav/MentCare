package it.univr;

import PO.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemTest {

    private WebDriver driver;
    @Before
    public void setUp() {
        org.openqa.selenium.chrome.ChromeOptions chrome_options = new ChromeOptions();
           chrome_options.addArguments("--headless");
        if(SystemUtils.IS_OS_WINDOWS){
            System.setProperty("webdriver.chrome.driver",
                    Paths.get("src/test/resources/chromedriver_win32_96/chromedriver.exe").toString());
        }
        else if (SystemUtils.IS_OS_MAC){
            System.setProperty("webdriver.chrome.driver",
                    Paths.get("src/test/resources/chromedriver_mac64_96/chromedriver").toString());
        }
        else if (SystemUtils.IS_OS_LINUX){
            System.setProperty("webdriver.chrome.driver",
                    Paths.get("src/test/resources/chromedriver_linux64_96/chromedriver").toString());
        }
        if (driver == null)
            driver = new ChromeDriver(chrome_options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }

    @Test
    public void loginTest() throws InterruptedException {

        driver.get("http://localhost:8080/");

        LoginPage loginPage = new LoginPage(driver);
        assertEquals("No message error appear", "", loginPage.getAlert());

        loginPage.enterCredential("fake", "1234");
        HomePage homepage = loginPage.login();
        loginPage.login();
        assertEquals("Wrong login", "Username or password are wrong.", loginPage.getAlert());

        loginPage.enterCredential("reception", "1234");
        homepage = loginPage.login();
        assertEquals("Reception navbar", "Home, New Patient, Patients List, Appointment List, Logout", homepage.navbarToString());
        loginPage = homepage.logout();

        loginPage.enterCredential("nurse", "1");
        homepage = loginPage.login();
        assertEquals("Nurse navbar", "Home, View Record, Edit Record, Logout", homepage.navbarToString());
        loginPage = homepage.logout();

        loginPage.enterCredential("doc", "12");
        homepage = loginPage.login();
        assertEquals("Doctor navbar", "Home, Patients List, View Record, Edit Record, Setup consultation, Generate report, Logout", homepage.navbarToString());
        loginPage = homepage.logout();

        loginPage.enterCredential("manager", "123");
        homepage = loginPage.login();
        assertEquals("Manager navbar", "Home, Patients List, Export statistics, Generate report, Logout", homepage.navbarToString());


    }
    @Test
    public void UserTest1Reception() throws InterruptedException {
        driver.get("http://localhost:8080/");
        LoginPage loginPage = new LoginPage(driver);

        /*reception*/
        loginPage.enterCredential("reception", "1234");
        HomePage homepage = loginPage.login();

        PatientListPage patientList = homepage.patientListPage();
        assertEquals("header patients list", "Patients List", patientList.getHeader());
        assertEquals("empty list", 0, patientList.rowNumber());
        homepage = patientList.homepage();

        InputPage newPatient = homepage.inputPage();
        assertEquals("Reception navbar", "Home, New Patient, Patients List, Appointment List, Logout", newPatient.navbarToString());
        assertEquals("header input page", "Insert new patient", newPatient.header());

        newPatient.enterPatient("Mirko", "Pavan", "mirko.pavan@me.it", "LOW");
        homepage = newPatient.submit();
        assertEquals("Reception navbar", "Home, New Patient, Patients List, Appointment List, Logout", homepage.navbarToString());
        newPatient = homepage.inputPage();
        newPatient.enterPatient("Luigi", "Gian", "gianluigi@gmail.com", "HIGH");
        homepage = newPatient.submit();
        patientList = homepage.patientListPage();
        assertEquals("header patients list", "Patients List", patientList.getHeader());
        assertEquals("rows in list", 2, patientList.rowNumber());
        assertEquals("list", "[Mirko, Pavan, mirko.pavan@me.it, LOW, new appointment, appointment], [Luigi, Gian, gianluigi@gmail.com, HIGH, new appointment, appointment]", patientList.patientsToString());
        AppointmentsPage appointmentsPage = patientList.appointmentsPage(0);
        assertEquals("header details page", "Patient Appointments", appointmentsPage.getHeader());
        assertEquals("list size", 0, appointmentsPage.rowNumber());
        assertEquals("Appointment list", "Mirko Pavan: []", appointmentsPage.appointmentsToString());
        patientList = appointmentsPage.patientListPage();
        NewAppointmentPage newAppointmentPage = patientList.newAppointmentPage(0);

        assertEquals("header new appointment", "New appointment for: Mirko Pavan", newAppointmentPage.getHeader());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String slot = "";
        if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY) || now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            if (now.getMonthValue() < 10)
                slot = now.getDayOfMonth() + 2 + "/0" + now.getMonthValue() + "/" + now.getYear() + " 10:00";
        } else
            slot = now.truncatedTo(ChronoUnit.HOURS).plusMinutes(30).format(dtf);
        newAppointmentPage.insertValue(slot);
        newAppointmentPage.submit();
        assertEquals("alert ok", "Appointment correctly inserted.", newAppointmentPage.getAlert());

        newAppointmentPage.submit();
        assertEquals("alert no slot", "Please select a slot.", newAppointmentPage.getAlert());

        patientList = newAppointmentPage.patientListPage();

        appointmentsPage = patientList.appointmentsPage(0);

        assertEquals("header details page", "Patient Appointments", appointmentsPage.getHeader());
        assertEquals("list size", 1, appointmentsPage.rowNumber());
        assertEquals("Appointment list", "Mirko Pavan: [" + slot + "]", appointmentsPage.appointmentsToString());


        AppointmentListPage appointmentListPage = homepage.appointmentListPage();
        assertEquals("header appointments list", "Appointment List", appointmentListPage.getHeader());
        assertEquals("rows in list", 1, appointmentListPage.rowNumber());
        assertEquals("list", "[Mirko Pavan, " + slot + "]", appointmentListPage.appointmentsListToString());


        homepage.logout();
    }

    @Test
    public void UserTest2Doctor() throws InterruptedException {

        driver.get("http://localhost:8080/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredential("doc", "12");
        HomePage homepage = loginPage.login();
        ViewRecordPage viewRecordPage = homepage.viewRecordPage();
        assertEquals("header viewRecord page", "Search patient", viewRecordPage.getHeader());
        PatientListPage patientList = homepage.patientListPage();
        assertEquals("header patients list", "Patients List", patientList.getHeader());
        assertEquals("list size", 2, patientList.rowNumber());
        assertEquals("list", "[Mirko, Pavan, mirko.pavan@me.it, LOW, show, new], [Luigi, Gian, gianluigi@gmail.com, HIGH, show, new]", patientList.patientsToString());

        DetailsPage detailsPage = patientList.detailsPage(0);
        assertEquals("header details page", "Patient Details", detailsPage.getHeader());
        assertEquals("list size", 0, detailsPage.rowNumber());
        assertEquals("Medication list", "Mirko Pavan: []", detailsPage.medicationsToString());

        patientList = detailsPage.patientListPage();
        NewMedicationPage newMedicationPage = patientList.NewMedicationPage(0);

        assertEquals("header new medication", "Insert new Medication for: Mirko Pavan", newMedicationPage.getHeader());
        newMedicationPage.insertValue("14/01/2024", "Xanax", "1.8", "2", "After lunch");
        newMedicationPage.submit();
        assertEquals("alert wrong dose", "ERROR!!! Cannot somministrate that dose.", newMedicationPage.getAlert());
        patientList = newMedicationPage.patientListPage();
        detailsPage = patientList.detailsPage(0);
        assertEquals("list size", 0, detailsPage.rowNumber());
        assertNotEquals("Medication list", "Mirko Pavan: [14/01/2024, Xanax, 1.8, 2, After lunch]", detailsPage.medicationsToString());

        patientList = detailsPage.patientListPage();
        newMedicationPage = patientList.NewMedicationPage(0);
        newMedicationPage.insertValue("14/01/2024", "", "1.0", "2", "After lunch");
        newMedicationPage.submit();
        assertEquals("alert no drug", "Please, select drug.", newMedicationPage.getAlert());
        newMedicationPage.insertValue("14/01/2024", "Xanax", "1.0", "2", "After lunch");
        newMedicationPage.submit();
        assertEquals("alert ok", "Medication correctly inserted.", newMedicationPage.getAlert());
        newMedicationPage.insertValue("16/01/2024", "Mirtazapine", "2.0", "3", "Before lunch");
        newMedicationPage.submit();
        patientList = newMedicationPage.patientListPage();
        detailsPage = patientList.detailsPage(0);
        assertEquals("list size", 2, detailsPage.rowNumber());
        assertEquals("Medication list", "Mirko Pavan: [14/01/2024, Xanax, 1.0, 2, After lunch], " +
                "[16/01/2024, Mirtazapine, 2.0, 3, Before lunch]", detailsPage.medicationsToString());

        detailsPage.logout();
    }
    @Test
    public void UserTest3Nurse() throws InterruptedException {

        driver.get("http://localhost:8080/");
        LoginPage loginPage = new LoginPage(driver);
        //nurse
        loginPage.enterCredential("nurse", "1");
        HomePage homepage = loginPage.login();

        ViewRecordPage viewRecordPage = homepage.viewRecordPage();
        assertEquals("header viewRecord page", "Search patient", viewRecordPage.getHeader());

        viewRecordPage.enterSearch("Mirko", "Pavan");
        PatientListPage patientList = viewRecordPage.submit();
        viewRecordPage = patientList.viewRecordPage();
        viewRecordPage.enterSearch("","Pavan");
        patientList = viewRecordPage.submit();
        assertEquals("header patients list", "Patients List", patientList.getHeader());
        assertEquals("list", 1, patientList.rowNumber());
        DetailsPage detailsPage = patientList.detailsPage(0);
        assertEquals("list size", 2, detailsPage.rowNumber());
        assertEquals("Medication list", "Mirko Pavan: [14/01/2024, Xanax, 1.0, 2, After lunch], " +
                "[16/01/2024, Mirtazapine, 2.0, 3, Before lunch]", detailsPage.medicationsToString());

        detailsPage.logout();

    }







}
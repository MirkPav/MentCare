package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PatientListPage extends HomePage{

    @FindBy(xpath = "//body//h1")
    private WebElement header;

    @FindBy(xpath="//tbody//tr")
    private List<WebElement> row;

    @FindBy(xpath="//tbody//tr//td")
    private List<WebElement> patient;

    @FindBy(name = "details")
    private List<WebElement> details;

    @FindBy(name = "newAppointment")
    private List<WebElement> newAppointment;

    @FindBy(name = "appointments")
    private List<WebElement> appointments;


    @FindBy(xpath="//tbody//tr//td//a")
    private List<WebElement> newMedication;



    public PatientListPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader(){
        return this.header.getText();
    }

    public int rowNumber(){
        return row.size();
    }

    public DetailsPage detailsPage(int pos){
        this.details.get(pos).click();
        return new DetailsPage(driver);

    }

    public NewAppointmentPage newAppointmentPage(int pos){
        this.newAppointment.get(pos).click();
        return new NewAppointmentPage(driver);

    }

    public AppointmentsPage appointmentsPage(int pos){
        this.appointments.get(pos).click();
        return new AppointmentsPage(driver);
    }

    public NewMedicationPage NewMedicationPage(int pos){
        this.newMedication.get(pos+1).click();
        return new NewMedicationPage(driver);

    }

    public String patientsToString(){
        String result = "[";
        if (patient.isEmpty())
            result="]";
        int i=1;
        for(WebElement element: patient){
            result += element.getText();
            if (i%(patient.size()/rowNumber())==0){
                if (i<patient.size()) {
                    result += "], [";
                } else {
                    result += "]";
                }
            } else {
                result += ", ";
            }
            i++;
        }
        return result;
    }
}

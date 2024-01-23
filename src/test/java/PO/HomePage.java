package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class HomePage extends PageObject{

    @FindBy(xpath = "//body//div//a")
    private List<WebElement> navbar;

    @FindBy(xpath = "//body//div//a[contains(@href,'/home')]")
    private WebElement home;

    @FindBy(xpath = "//body//div//a[contains(@href,'/input')]")
    private WebElement newPatient;

    @FindBy(xpath = "//body//div//a[contains(@href,'/list')]")
    private WebElement patientList;

    @FindBy(xpath = "//body//div//a[contains(@href,'/appointmentList')]")
    private WebElement appointmentList;

    @FindBy(xpath = "//body//div//a[contains(@href,'/logout')]")
    private WebElement logout;

    @FindBy(xpath = "//body//div//a[contains(@href,'/viewRecord')]")
    private WebElement viewRecord;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage homepage(){
        home.click();
        return new HomePage(driver);
    }

    public InputPage inputPage(){
        newPatient.click();
        return new InputPage(driver);
    }

    public PatientListPage patientListPage(){
        patientList.click();
        return new PatientListPage(driver);
    }

    public ViewRecordPage viewRecordPage(){
        viewRecord.click();
        return new ViewRecordPage(driver);
    }

    public LoginPage logout(){
        logout.click();
        return new LoginPage(driver);
    }

    public String navbarToString(){
        String result ="";
        for(WebElement element: navbar){
            if(result.equals("")){
                result+= element.getText();
            }else
                result += ", " + element.getText();
        }
        return result;
    }

    public AppointmentListPage appointmentListPage() {
        appointmentList.click();
        return new AppointmentListPage(driver);
    }
}

package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class NewMedicationPage extends HomePage{

    @FindBy(xpath = "//body//div//label")
    private List<WebElement> header;

    @FindBy(xpath= "//input[@name='date']")
    private WebElement date;

    @FindBy(xpath= "//select[@name='drugs']")
    private WebElement drugs;

    @FindBy(xpath= "//input[@name='dose']")
    private WebElement dose;

    @FindBy(xpath= "//input[@name='days']")
    private WebElement days;

    @FindBy(xpath= "//input[@name='note']")
    private WebElement notes;

    @FindBy(className = "alert")
    private WebElement alert;

    public NewMedicationPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader(){
        return header.get(0).getText() + " " + header.get(1).getText() + " " + header.get(2).getText();
    }

    public void insertValue(String date, String drugs, String dose, String days, String notes){
        this.date.clear();
        this.date.sendKeys(date);
        this.drugs.sendKeys(drugs);
        this.dose.clear();
        this.dose.sendKeys(dose);
        this.days.clear();
        this.days.sendKeys(days);
        this.notes.clear();
        this.notes.sendKeys(notes);
    }

    public NewMedicationPage submit(){
        this.notes.submit();
        return new NewMedicationPage(driver);
    }

    public String getAlert(){
        return alert.getText();
    }
}

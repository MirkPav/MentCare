package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewAppointmentPage extends HomePage{
    @FindBy(xpath = "//body//div//label")
    private List<WebElement> header;

    @FindBy(xpath= "//select[@name='slots']")
    private WebElement slots;

    @FindBy(className = "alert")
    private WebElement alert;

    public NewAppointmentPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader(){
        return header.get(0).getText() + " " + header.get(1).getText() + " " + header.get(2).getText();
    }

    public void insertValue(String slot){
        this.slots.sendKeys(slot);
    }

    public NewAppointmentPage submit(){
        this.slots.submit();
        return new NewAppointmentPage(driver);
    }

    public String getAlert(){
        return alert.getText();
    }

}

package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewRecordPage extends HomePage{

    @FindBy(xpath = "//body//div//label")
    private WebElement header;

    @FindBy(xpath= "//input[@name='firstname']")
    private WebElement firstName;

    @FindBy(xpath= "//input[@name='lastname']")
    private WebElement lastName;
    public ViewRecordPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader(){
        return this.header.getText();
    }
    public void enterSearch(String firstName, String lastName){
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
    }
    public PatientListPage submit(){
        this.lastName.submit();
        return new PatientListPage(driver);
    }
}

package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InputPage extends HomePage{

    @FindBy(xpath = "//body//div//label")
    private WebElement header;

    @FindBy(xpath= "//input[@name='firstname']")
    private WebElement firstName;

    @FindBy(xpath= "//input[@name='lastname']")
    private WebElement lastName;

    @FindBy(xpath= "//input[@name='email']")
    private WebElement email;

    @FindBy(xpath= "//select[@name='dangerouslevel']")
    private WebElement dangerousLevel;


    private WebElement submit;
    public InputPage(WebDriver driver) {
        super(driver);
    }

    public String header(){
        return this.header.getText();
    }
    public void enterPatient(String firstName, String lastName, String email, String dangerousLevel){
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
        this.email.clear();
        this.email.sendKeys(email);
        this.dangerousLevel.sendKeys(dangerousLevel);

    }

    public HomePage submit(){
        this.dangerousLevel.submit();
        return new HomePage(driver);
    }

}

package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends PageObject{

    @FindBy(className = "alert")
    private WebElement alert;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(id= "login")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getAlert(){
        return alert.getText();
    }

    public void enterCredential(String username, String password){
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);
    }

    public HomePage login(){
        loginButton.click();
        return new HomePage(driver);
    }
}

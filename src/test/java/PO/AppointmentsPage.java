package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AppointmentsPage extends HomePage{
    @FindBy(xpath = "//body//h1")
    private WebElement header;
    @FindBy(xpath="//tbody//tr")
    private List<WebElement> row;

    @FindBy(xpath="//b")
    private List<WebElement> patient;
    @FindBy(xpath="//tbody//tr//td")
    private List<WebElement> appointments;

    public AppointmentsPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader(){
        return this.header.getText();
    }

    public int rowNumber(){
        return row.size();
    }


    public String appointmentsToString(){
        String result = patient.get(0).getText() + " " + patient.get(1).getText() + ": ";
        result += "[";
        if (appointments.isEmpty())
            result+="]";
        int i=1;
        for(WebElement element: appointments){
            result += element.getText();
                if (i<appointments.size()) {
                    result += "], [";
                } else {
                    result += "]";
                }
            i++;
        }
        return result;
    }
}

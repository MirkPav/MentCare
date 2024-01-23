package PO;

import PO.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AppointmentListPage extends HomePage {

    @FindBy(xpath = "//body//h1")
    private WebElement header;

    @FindBy(xpath="//tbody//tr")
    private List<WebElement> row;

    @FindBy(xpath="//tbody//tr//td")
    private List<WebElement> appointments;
    public AppointmentListPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader() {
        return this.header.getText();
    }

    public int rowNumber() {
        return row.size();
    }

    public String appointmentsListToString(){
        String result = "[";
        if (appointments.isEmpty())
            result+="]";
        int i=1;
        for(WebElement element: appointments){
            result += element.getText();
            if (i%2==0) {
                if (i < appointments.size()) {
                    result += "], [";
                } else {
                    result += "]";
                }
            }else{
                    result += ", ";
                }
            i++;
        }
        return result;
    }
}

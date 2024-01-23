package PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DetailsPage extends HomePage{

    @FindBy(xpath = "//body//h1")
    private WebElement header;
    @FindBy(xpath="//tbody//tr")
    private List<WebElement> row;

    @FindBy(xpath="//b")
    private List<WebElement> patient;
    @FindBy(xpath="//tbody//tr//td")
    private List<WebElement> medications;

    public DetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader(){
        return this.header.getText();
    }

    public int rowNumber(){
        return row.size();
    }


    public String medicationsToString(){
        String result = patient.get(0).getText() + " " + patient.get(1).getText() + ": ";
        result += "[";
        if (medications.isEmpty())
            result+="]";
        int i=1;
        for(WebElement element: medications){
            result += element.getText();
            if (i%5==0){
                if (i<medications.size()) {
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

package acceptance.org.example;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 22/12/11
 * Time: 15.49
 * To change this template use File | Settings | File Templates.
 */
public abstract class AnimalQuizAutomationBuilder {
    protected HtmlPage page;

    public AnimalQuizAutomationBuilder(HtmlPage page) {
        this.page = page;
    }

    public HtmlPage getPage() {
        return this.page;
    }
    
    public abstract void clickOk() throws IOException;

    public abstract void selectYes() throws IOException;

    public abstract void selectNo() throws IOException;

    public abstract void typeOnInputText(String mouse) throws IOException;

    public abstract void typeReset() throws IOException;
    
    public abstract void clickSave() throws IOException;

    public abstract void clickLoad() throws IOException;

    public abstract void clickDelete() throws IOException;
}

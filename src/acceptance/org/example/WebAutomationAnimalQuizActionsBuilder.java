package acceptance.org.example;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 22/12/11
 * Time: 15.09
 * To change this template use File | Settings | File Templates.
 */
public class WebAutomationAnimalQuizActionsBuilder extends AnimalQuizAutomationBuilder {

    public WebAutomationAnimalQuizActionsBuilder(HtmlPage page) {
        super(page);
    }

    @Override
    public void clickOk() throws IOException {
        HtmlSubmitInput okSubmit = (HtmlSubmitInput) page.getElementById("ok");
        page = okSubmit.click();
    }

    @Override
    public void selectYes() throws IOException {
        HtmlForm yesNoForm=  (HtmlForm) page.getElementById("radioYesNo");
        HtmlRadioButtonInput radioButton = (HtmlRadioButtonInput) yesNoForm.getElementById("yes");
        radioButton.setChecked(true);

        HtmlSubmitInput okSubmit2 = (HtmlSubmitInput) yesNoForm.getElementById("ok");
        page = (HtmlPage) okSubmit2.click();
    }

    @Override
    public void selectNo() throws IOException{
        HtmlForm yesNoForm=  (HtmlForm) page.getElementById("radioYesNo");
        HtmlRadioButtonInput radioButton = (HtmlRadioButtonInput) yesNoForm.getElementById("no");
        radioButton.setChecked(true);

        HtmlSubmitInput okSubmit2 = (HtmlSubmitInput) yesNoForm.getElementById("ok");
        page = (HtmlPage) okSubmit2.click();
    }


    @Override
    public void typeOnInputText(String text) throws IOException {
        HtmlForm inputAnimal = (HtmlForm) page.getElementById("inputText");
        inputAnimal.getElementById("text").type(text);
        HtmlSubmitInput submit = (HtmlSubmitInput) inputAnimal.getElementById("ok");
        page= submit.click();
    }

    @Override
    public void typeReset() throws IOException {
        HtmlForm inputAnimal = (HtmlForm) page.getElementById("reset");
        HtmlSubmitInput submit = (HtmlSubmitInput) inputAnimal.getElementById("resetButtonId");
        page = submit.click();
    }

    @Override
    public void clickSave() throws IOException {
        HtmlForm inputAnimal = (HtmlForm) page.getElementById("saveForm");
        HtmlSubmitInput submit = (HtmlSubmitInput) inputAnimal.getElementById("buttonSaveId");
        page = submit.click();
    }

    @Override
    public void clickLoad() throws IOException {
        HtmlForm inputAnimal = (HtmlForm) page.getElementById("loadForm");
        HtmlSubmitInput submit = (HtmlSubmitInput) inputAnimal.getElementById("buttonLoadId");
        page = submit.click();
    }

    @Override
    public void clickDelete() throws IOException {
        HtmlForm inputAnimal = (HtmlForm) page.getElementById("deleteForm");
        HtmlSubmitInput submit = (HtmlSubmitInput) inputAnimal.getElementById("buttonDeleteId");
        page = submit.click();
    }

}

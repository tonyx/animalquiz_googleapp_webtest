package acceptance.org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 31/10/11
 * Time: 20.33
 * To change this template use File | Settings | File Templates.
 */
public class AnimalQuizWebTest {
    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void startUp() throws Exception {
        helper.setUp();
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickDelete();
    }

    @After
    public void tearDown() throws Exception {
        helper.setUp();
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickDelete();
    }





    private final static String DEPLOYMENT_SERVER="http://localhost:8080/step";
    //private final static String DEPLOYMENT_SERVER="http://guesstheanimal5.appspot.com";
    @Test
    public void shouldInviteToThinkAboutAnAnimal() throws Exception {
        // given
        final WebClient webClient = new WebClient();

        // when
        final HtmlPage page = webClient.getPage(DEPLOYMENT_SERVER);

        // then
        Assert.assertTrue(page.getBody().asText().contains("Think about an animal"));
        //Assert.assertEquals("",page.getBody().asText());
    }


    @Test
    public void whenClickOkAtTheFirstPageSholdAskIfItIsAnElephant() throws Exception {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));

        // when
        builder.clickOk();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("is it a elephant?"));


    }


    @Test
    public void whenItIsAnElephantShouldAnswerYeah() throws Exception {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));

        // when
        builder.clickOk();
        builder.clickOk();
        builder.selectYes();
        
        // then
        Assert.assertTrue(builder.getPage().asText().contains("yeah"));
    }


    @Test
    public void whenItIsNotAnElephantThenShouldAskWhatAnimalWas() throws Exception {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickOk();
        builder.clickOk();

        // when
        builder.selectNo();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("what animal was"));
    }


    @Test
    public void whenItIsNotAnElephantThenShouldAllowSpecifyingByInputTextTheAnimal() throws Exception {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickOk();
        builder.clickOk();
        builder.selectNo();

        // when
        builder.typeOnInputText("mouse");

        // then
        Assert.assertTrue(builder.getPage().asText().contains("a question that can be used to distinguish a mouse from a elephant"));

    }



    @Test
    public void submitTheDiscriminatingQuestion() throws Exception {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage)webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickOk();
        builder.clickOk();
        builder.selectNo();
        builder.typeOnInputText("mouse");

        // when

        builder.typeOnInputText("is it small?");

        // then
        Assert.assertTrue(builder.getPage().asText().contains("What should be the answer to the question \"is it small?\" to distinguish a mouse compared to a elephant?"));
    }

    @Test
    public void shouldSayThankYouAfterLearnedMouseFromElephant() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickOk();
        builder.clickOk();
        builder.selectNo();
        builder.typeOnInputText("mouse");
        builder.typeOnInputText("is it small?");

        // when
        builder.selectYes();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("Thank you"));
    }


    @Test
    public void afterLearnedTheMouseShouldRestartAskingToThinkAboutAnAnimal() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        builder.clickOk();
        builder.clickOk();
        builder.selectNo();
        builder.typeOnInputText("mouse");
        builder.typeOnInputText("is it small?");
        builder.selectYes();

        // when
        builder.clickOk();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("Think about an animal"));
    }


    @Test
    public void afterTheKnowledgeBaseLearnedToDistinguishTheMouseFromTheElephantShouldAskTheDiscriminatingQuestion() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);

        // when
        builder.clickOk();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("is it small?"));
    }


    @Test
    public void shouldReOfferTheFirstQuestionWhenTypingReset() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        builder.typeReset();

        // when
        builder.clickOk();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("is it small?"));

    }

    @Test
    @Ignore
    public void theSaveButtonShouldBeNeutral() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        String whatever = builder.getPage().asText();
        builder.clickSave();

        // when
        Assert.assertEquals(whatever,builder.getPage().asText());
       // builder.typeReset();
        
        // then
        //Assert.assertTrue(builder.getPage().asText().contains("Think about an animal"));
    }

    @Test
    public void shouldRestartFromTheFirstQuestionIfTypeReset() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        builder.typeReset();

        // when
        builder.clickOk();
        // then
        Assert.assertTrue(builder.getPage().asText().contains("small"));

    }




    @Test
    public void afterPopulatedAKnowledgeBaseAndItShouldRestartWithFreshState() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);

        // when

        builder.clickOk();

        Assert.assertTrue(builder.getPage().asText().contains("is it small?"));
    }

    @Test
    public void learnMouseAndGuessIt() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();

        // when
        builder.selectYes();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("is it a mouse?"));
    }



    @Test
    public void learnMouseAndIsNotItThenExpectAskWhatAnimalWas() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        builder.selectNo();

        // when
        builder.selectNo();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("what animal was"));
    }

    @Test
    public void learnMouseThenThinkAboutAnt() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        builder.selectYes(); // is it small
        builder.selectNo(); // is it a mouse

        // when
        builder.typeOnInputText("ant");

        // then
        //Assert.assertTrue(builder.getPage().asText().contains("what question can be used to distinguish a ant from a mouse?"));
        Assert.assertTrue(builder.getPage().asText().contains("a question that can be used to distinguish a ant from a mouse"));
    }

    @Test
    public void learnMouseThenThinkAntAnsAskHowToDistinguishItFromAMouse() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        builder.selectYes();
        builder.selectNo();
        builder.typeOnInputText("ant");

        // when
        builder.typeOnInputText("is it an insect?");

        // then
        Assert.assertTrue(builder.getPage().asText().contains("What should be the answer to the question \"is it an insect?\" to distinguish a ant compared to a mouse?"));
    }

    @Test
    public void learnMouseThenLearnAnt() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickOk();
        builder.selectYes(); // is it small
        builder.selectNo(); // is it a mouse
        builder.typeOnInputText("ant");
        builder.typeOnInputText("is it an insect?");

        // when
        builder.selectYes();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("Thank you"));
    }

    @Test
    public void learnMouseElephantThenResetThenSaveThenLoadTheKnowledgeBase() throws IOException {
        // given
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));
        learnToDistinguishElephantAndMouse(builder);
        builder.clickSave();
        builder.typeReset();
        builder.clickLoad();

        // when
        builder.clickOk();

        // then
        Assert.assertTrue(builder.getPage().asText().contains("is it small?"));
    }


    @Test
    public void aCompleteAutomationElephantMouseAndAnt() throws IOException {
        final WebClient webClient = new WebClient();
        WebAutomationAnimalQuizActionsBuilder builder = new WebAutomationAnimalQuizActionsBuilder((HtmlPage) webClient.getPage(DEPLOYMENT_SERVER));

        Assert.assertTrue(builder.getPage().getBody().asText().contains("Think about an animal"));
        builder.clickOk();
        builder.getPage().asText().contains("is it an elephant?");
        builder.selectNo();
        Assert.assertTrue(builder.getPage().asText().contains("what animal was"));
        builder.typeOnInputText("mouse");
        Assert.assertTrue(builder.getPage().asText().contains("a question that can be used to distinguish a mouse from a elephant"));
        builder.typeOnInputText("is it small?");
        Assert.assertTrue(builder.getPage().asText().contains("What should be the answer to the question \"is it small?\" to distinguish a mouse compared to a elephant?"));
        builder.selectYes();
        Assert.assertTrue(builder.getPage().asText().contains("Thank you"));
        // added mouse to the knowledge base

        builder.clickOk();
        Assert.assertTrue(builder.getPage().asText().contains("Think about an animal"));
        builder.clickOk();
        Assert.assertTrue(builder.getPage().asText().contains("is it small?"));
        builder.selectYes();
        Assert.assertTrue(builder.getPage().asText().contains("is it a mouse?"));
        builder.selectNo();
        Assert.assertTrue(builder.getPage().asText().contains("what animal was"));
        builder.typeOnInputText("ant");
        Assert.assertTrue(builder.getPage().asText().contains("a question that can be used to distinguish a ant from a mouse"));
        builder.typeOnInputText("is it an insect?");
        Assert.assertTrue(builder.getPage().asText().contains("What should be the answer to the question \"is it an insect?\" to distinguish a ant compared to a mouse?"));
        builder.selectYes();
        Assert.assertTrue(builder.getPage().asText().contains("Thank you"));
        // added ant

    }



    

    private void learnToDistinguishElephantAndMouse(WebAutomationAnimalQuizActionsBuilder builder) throws IOException {
        builder.clickOk();
        Assert.assertTrue(builder.getPage().asText().contains("is it a elephant?"));
        builder.selectNo();
        Assert.assertTrue(builder.getPage().asText().contains("what animal was"));
        builder.typeOnInputText("mouse");
        Assert.assertTrue(builder.getPage().asText().contains("write, in english language, a question that can be used to distinguish a mouse from a elephant"));
        builder.typeOnInputText("is it small?");
        Assert.assertTrue(builder.getPage().asText().contains("What should be the answer to the question \"is it small?\" to distinguish a mouse compared to a elephant?"));
        builder.selectYes();
        Assert.assertTrue(builder.getPage().asText().contains("Thank you"));
        builder.clickOk();
        
    }

}

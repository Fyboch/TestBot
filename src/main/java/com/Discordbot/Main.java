package com.Discordbot;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

import com.google.common.util.concurrent.Service;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;

import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.*;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.Event;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;


public class Main
{
   


    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/creds.json";


    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException
    {
        // Load client secrets.
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    public static void message(DiscordApi DApi)
    {

        DApi.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!bot")) {
                TextChannel channel = event.getChannel();
              //Optional<User> user = event.getMessageAuthor().asUser();
              // new MessageBuilder().setContent("LOL")
              //       .send(user.get());

                new MessageBuilder()
                        .setContent("Ïðèâåò!\n" +
                                "Òû íàõîäèøüñÿ â ìåíþ, â êîòîðîì ñîáðàíû îòâåòû íà ïîïóëÿðíûå âîïðîñû ïî ðàáîòå îòäåëà LeadGeneration.\n" +
                                "\n" +"Âûáåðè êàòåãîðèþ ñâîåãî âîïðîñà:")
                        .addComponents(
                                ActionRow.of(Button.primary("google", "Ãóãë"),
                                        Button.primary("linked", "Ëèíêåäèí"),
                                        Button.primary("CRM", "CPM")),
                                ActionRow.of(Button.primary("calendar", "Êàëåíäàðü"),
                                        Button.primary("update", "Àïäåéò"),
                                        Button.primary("timetrack", "Òàéì-òðåêêåð")),
                                ActionRow.of(Button.primary("contacts", "Êîíòàêòû"),
                                        Button.primary("bonus", "Áîíóñû è ÇÏ"),
                                        Button.primary("leads","Ëèäû")),
                                ActionRow.of(Button.primary("blueprints","Øàáëîíû"),
                                        Button.primary("connect","Êîííåêò"),
                                        Button.primary("followup","Ôîëëîóàï")),
                                ActionRow.of(Button.primary("country","Ñòðàíà"),
                                        Button.primary("status","Ñòàòóñ"),
                                        Button.primary("more","Åù¸")))
                        .send(channel);

            }
            if (event.getMessageContent().equalsIgnoreCase("!start"))
            {
                SHOULDSEND = false;
                new MessageBuilder()
                        .setContent("Çäðàâñòâóéòå!")
                        .addComponents(ActionRow.of(Button.primary("feedback1", "Íà÷í¸ì?")))
                        .send(event.getChannel());

            }


        });
    }


    public static void main(String[] args) throws IOException, GeneralSecurityException
    {
        SlashCommand command = SlashCommand.with("menu", "Checks the functionality of this command")
                .createGlobal(api)
                .join();


        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();

            event.getInteraction()
                    .createImmediateResponder()
                    .setContent("Ïðèâåò!\n" +
                            "Òû íàõîäèøüñÿ â ìåíþ, â êîòîðîì ñîáðàíû îòâåòû íà ïîïóëÿðíûå âîïðîñû ïî ðàáîòå îòäåëà LeadGeneration.\n" +
                            "\n" +"Âûáåðè êàòåãîðèþ ñâîåãî âîïðîñà:")
                    .addComponents(
                            ActionRow.of(Button.primary("google", "Ãóãë"),
                                    Button.primary("linked", "Ëèíêåäèí"),
                                    Button.primary("CRM", "CPM")),
                            ActionRow.of(Button.primary("calendar", "Êàëåíäàðü"),
                                    Button.primary("update", "Àïäåéò"),
                                    Button.primary("timetrack", "Òàéì-òðåêêåð")),
                            ActionRow.of(Button.primary("contacts", "Êîíòàêòû"),
                                    Button.primary("bonus", "Áîíóñû è ÇÏ"),
                                    Button.primary("leads","Ëèäû")),
                            ActionRow.of(Button.primary("blueprints","Øàáëîíû"),
                                    Button.primary("connect","Êîííåêò"),
                                    Button.primary("followup","Ôîëëîóàï")),
                            ActionRow.of(Button.primary("country","Ñòðàíà"),
                                    Button.primary("status","Ñòàòóñ"),
                                    Button.primary("more","Åù¸")))
                    .respond();

        });

        String path = (System.getProperty("user.dir")+"/resources");
        message(api);

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        // Add a listener which answers with "Pong!" if someone writes "!ping"

        //TODO: îáàâèòü êíîïêó "Îáðàòíî â ìåíþ", ê êàæäîìó ïóíêòó


        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
            String customId = messageComponentInteraction.getCustomId();

            sqlitecontroller.insert(messageComponentInteraction);

            switch (customId) {

                case "google":
                    SHOULDSEND = true;
                    stage = 1;
                    //stage = 16;

                    FeedbackResponder.feedbackResponer(1,messageComponentInteraction);

                    /*messageComponentInteraction.createImmediateResponder()
                            .setContent("Google.com")
                            .respond();*/

                    /*messageComponentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                            .addComponents(
                                    ActionRow.of(Button.primary("googChromeUserCreate", "Google new User")),
                                    ActionRow.of(Button.primary("googleFindCol", "Find Colleagues")),
                                    ActionRow.of(Button.primary("googleCalendar", "Where is google calendar")),
                                    ActionRow.of(Button.primary("googleSearch", "Google search operators")),
                                    ActionRow.of(Button.primary("googleUsefulAddons", "A few useful add-ons")) //,
                             ).send();*/

                  /*  messageComponentInteraction.createFollowupMessageBuilder()
                    .addComponents(
                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();*/

                    System.out.println(stage);
                    break;

                case "linked":
                    //stage = 16;
                    stage = 2;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder()
                            .setContent("Linked In")
                            .respond();
                    //TODO: Ïåðåâåñòè
                    messageComponentInteraction.createFollowupMessageBuilder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("linkCleanOld", "×èñòêà ñòàðûõ çàÿâîê")),
                                    ActionRow.of(Button.primary("linkAccBan", "Áàí àêêàóíòà")),
                                    ActionRow.of(Button.primary("linkAccLimits", "Ëèìèòû íà àêêàóíòå")),
                                    ActionRow.of(Button.primary("linkAccCountry", "Êàê ïîìåíÿòü ñòðàíó")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();
                    break;
                case "CRM":
                    //TODO: Ïåðåâåñòè
                    //stage = 16;
                    stage = 3;
                    SHOULDSEND = true;
                  /*messageComponentInteraction.createImmediateResponder()
                           .setContent("You selected CRM")
                           .respond();*/



                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("crmEnter", "Êàê âîéòè â ÑÐÌ")),
                                    ActionRow.of(Button.primary("crmAccess", "Êàê ïîëó÷èòü äîñòóï")),
                                    ActionRow.of(Button.primary("crmAcc", "Àêêàóíò äëÿ ðàáîòû â ÑÐÌ")),
                                    ActionRow.of(Button.primary("crmHowToAddLead", "Êàê äîáàâèòü ëèäà")),
                                    ActionRow.of(Button.primary("crmLeads", "Âêëàäêà Leads")))
                                    .respond();

                    messageComponentInteraction.createFollowupMessageBuilder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("crmLeadReports", "Âêëàäêà Lead Reports")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();

                    break;
                case "calendar":
                    //stage = 16; //TODO: Ïåðåâåñòè
                    stage = 4;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("calEnterMail", "Ïî÷òà äëÿ âõîäà")),
                                    ActionRow.of(Button.primary("calLeadEvent", "Èâåíò â êàëåíäàðå ëèäà")),
                                    ActionRow.of(Button.primary("calNewEvent", "Âíåñòè èâåíò â êàëåíäàðü")),
                                    ActionRow.of(Button.primary("calGet", "Ãäå âçÿòü êàëåíäàðü")),
                                    ActionRow.of(Button.primary("calFollowUp", "Ôîëëîó-àï â êàëåíäàðå")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                                    .addComponents(
                                            ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();
                    break;
                case "update":

                    //stage = 16;
                    stage = 5;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("updateWhatIs", "×òî òàêîå Àïäåéò")),
                                    ActionRow.of(Button.primary("updateWhy", "Çà÷åì íóæåí àïäåéò")),
                                    ActionRow.of(Button.primary("updateHowFind", "Êàê íàéòè àïäåéòîâ")),
                                    ActionRow.of(Button.primary("updateHowMake", "Êàê ñäåëàòü Àïäåéò")),
                                    ActionRow.of(Button.primary("updateDayNorm", "Íîðìà àïäåéòîâ â äåíü")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();

                    break;
                case "timetrack":

                    //stage = 16;
                    stage = 6;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("timeTurnOn", "Âêëþ÷èòü òàéì-òðåêåð")),
                                    ActionRow.of(Button.primary("timeGoogleSetting", "Íàñòðîéêà Google Chrome")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;
                case "contacts":

                    //stage = 16;
                    stage = 7;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder().setContent("select")
                            .setContent("Âëàä Ìîèñååíêî(àêêàóíò-ìåíåäæåð)\n" + "<@372427478267985930>" +
                                    "+380981101090\n" +"\n" +"Êàòÿ Èñòîìèíà (Áóõãàëòåðèÿ)\n"+ "<@918433181114454047>" +
                                    "+380714184225\n" +"\n" +"Þëèÿ Ìàðòûíèâ (Òèìëèä Sales)\n"+ "<@920656302546513950>" +
                                    "+380662227034\n" +"\n" +"Ìàðèÿ Áèãóí (Òèìëèä LeadGeneration)\n" + "<@921083222077620334>" +
                                    "+380634345683")
                            .addComponents(ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;
                case "bonus":

                   // stage = 16;
                    stage = 8;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("bonusMy", "Ìîè áîíóñû")),
                                    ActionRow.of(Button.primary("bonusWhen", "Êîãäà ÇÏ")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();

                    break;
                case "leads":

                    //stage = 16;
                    stage = 9;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("leadWhichLead", "Êàêèõ ëèäîâ íóæíî èñêàòü")),
                                    ActionRow.of(Button.primary("leadInfoFrom", "Äàííûå îò ëèäà")),
                                    ActionRow.of(Button.primary("leadAdd", "Äîáàâèòü ëèäà")),
                                    ActionRow.of(Button.primary("leadCardFill", "Çàïîëíèòü êàðòó ëèäà")),
                                    ActionRow.of(Button.primary("leadWhoFollowUp", "Êîìó äåëàòü ôîëëîó-àï")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("leadRight", "Ïðàâèëüíûå ëèäû")),
                                    ActionRow.of(Button.primary("leadHowMuch", "Ñêîëüêî ëèäîâ ÿ ñäåëàë")),
                                    ActionRow.of(Button.primary("leadDayNorm", "Íîðìà ëèäîâ â äåíü")),
                                    ActionRow.of(Button.primary("leadHowFind", "Êàê íàéòè íóæíîãî ëèäà")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();
                    break;
                case "blueprints":


                    //stage = 16;
                    stage = 10;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("blueFirstCon", "1-ûé êîííåêò ñ ëèäîì")),
                                    ActionRow.of(Button.primary("blueSecondCon", "2-îé êîííåêò ñ ëèäîì")),
                                    ActionRow.of(Button.primary("blueThirdCon", "3-èé êîííåêò ñ ëèäîì")),
                                    ActionRow.of(Button.primary("blueAbout", "Î ñïåöèàëüíîñòÿõ")),
                                    ActionRow.of(Button.primary("blueExp", "Îá îïûòå ñîäðóäíèêîâ"))) //blueThirdCon

                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("blueClientBad", "Êëèåíò íå çàèíòåðåñîâàí")),
                                    ActionRow.of(Button.primary("blueClientGood","Êëèåíò çàèíòåðåñîâàí")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .send();

                    break;
                case "connect":

                   // stage = 16;
                    stage = 11;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("conFirstCon", "1-ûé êîííåêò ñ ëèäîì")),
                                    ActionRow.of(Button.primary("conSecondCon", "2-îé êîííåêò ñ ëèäîì")),
                                    ActionRow.of(Button.primary("conThirdCon", "3-èé êîííåêò ñ ëèäîì")),
                                    ActionRow.of(Button.primary("conToLead", "Îòïðàâèòü êîííåêò ëèäó")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;
                case "followup":

                   // stage = 16;
                    stage = 12;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("followupWhat", "×òî òàêîå ôîëëó-àï")),
                                    ActionRow.of(Button.primary("followupCal", "Ôîëëó-àï â êàëåíäàðå")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;
                case "country":

                    //stage = 16;
                    stage = 13;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("counIWork", "Ñ êàêîé ñòðàíîé ÿ ðàáîòàþ")),
                                    ActionRow.of(Button.primary("counCanChange", "Ìîæíî ëè èçìåíèòü ñòðàíó")),
                                    ActionRow.of(Button.primary("counNotWork", "Ñòðàíû: ÍÅ ðàáîòàåì")),
                                    ActionRow.of(Button.primary("counWork", "Ñòðàíû: ðàáîòàåì")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;
                case "status":
                   // stage = 16;
                    stage = 14;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("statEvent", "Ñòàòóñ î íàçíà÷åíèè èâåíòà")),
                                    ActionRow.of(Button.primary("statUpdate", "Ñòàòóñ àïäåéò")),
                                    ActionRow.of(Button.primary("statCrm", "Âèäû ñòàòóñîâ â ÑÐÌ")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;
                case "more":
                    messageComponentInteraction.createImmediateResponder().setContent("\"Âåðíóòüñÿ íà ñòàðò\" - íà÷àòü ðàáîòó ñ áîòîì ñ íà÷àëà\n" +
                                    "\"Âåðíóòüñÿ íàçàä\" - â ìåíþ ñ îòâåòàìè íà âîïðîñû\n" +"\"Îáðàòíàÿ ñâÿçü\" - åñëè íå íàõîäèøü îòâåò íà ñâîé âîïðîñ")
                            .addComponents(
                                    ActionRow.of(Button.primary("moreStart", "Âåðíóòñÿ íà ñòàðò")),
                                    ActionRow.of(Button.primary("moreBack", "Âåðíóòüñÿ íàçàä")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                            .respond();
                    break;

                case "moreStart":
                  messageComponentInteraction.createImmediateResponder().setContent("Ïðîäîëæàåì")
                        .addComponents(
                                ActionRow.of(Button.primary("employeeNew","Íîâûé ñîòðóäíèê")),
                                ActionRow.of(Button.primary("employeeOld","Óæå ðàáîòàåøü ñ íàìè")),
                                ActionRow.of(Button.primary("feedback", "Âåðíóòüñÿ íàçàä")))
                          .respond();

                    break;


            }
        });



        String googleSearchBig = "Ïîèñê ïî äîëæíîñòÿì\n" +"site:(àäðåñ ñàéòà) ïðîáåë ( äîëæíîñòè )\n" +
                "Ïðèìåð  site:linkedin.com\n" +"CEO  Òàêîé çàïðîñ â ãóãëå âûäàñò Âàì âñå ñòðàíèöû íà ñàéòå ëèíêåäèíà óïîìèíàþùèå äîëæíîñòè ãåíäèðåêòîðîâ.\n" +
                "OR ( çàãëàâíûìè áóêâàìè ìåæäó ñëîâàìè, ÷åðåç ïðîáåë )\n" +
                "ïðèìåð: site:linkedin.com CEO OR Founder OR Director\n" +
                "(Ìèíóñ) \n" +"ïðèìåð: site:linkedin.com CEO OR Founder OR Director -news -Institute -become -jobs -wewwork -amazon -netflix -million\n" +
                "Ïîèñê ïî êîìïàíèÿì\n" +" (òèðå ñòàâÿòñÿ ìåæäó öèôðàìè 2-50)\n" +
                "ïðèìåð: site:linkedin.com Company size: 2-50 employees\n" +
                "Èñïîëüçóåì èìåííî òàêèå ðàçìåðû êîìïàíèé, ïîòîìó ÷òî îíè ïðîïèñàíû â øàáëîíàõ ñàìîãî LinkedIn  2-50, 51-200, 201-500 è ò.ä.\n" +
                "AND ( ñîâìåùàåò îáà çàïðîñà â ïîèñêîâîé âûäà÷å )\n" +"ïðèìåð: site:linkedin.com Founded: 2005..2019 AND Company size: 51-200 employees\n" +
                " (Êàâû÷êè)  ôðàçîâûé îïåðàòîð, èñïîëüçóåòñÿ äëÿ òîãî, ÷òîáû èñêàòü èìåííî ôðàçó, à íå êàæäîå ñëîâî ïî îòäåëüíîñòè\n" +
                "ïðèìåð: site:linkedin.com Specialties: Email Marketing\n" + "* (çâåçäî÷êà)\n" +"site:linkedin.com Specialties: Email Marketing*\n" +
                "\n" +"Headquarters: (ëîêàöèÿ)\n" +"ïðèìåð: site:linkedin.com Founded: 2005..2019 AND Company size: 51-200 employees OR Headquarters: France\n";


        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction step2MessageCompInteraction = event.getMessageComponentInteraction();
            String ChosenInteraction = step2MessageCompInteraction.getCustomId();

           // sqlitecontroller.insert(step2MessageCompInteraction);

            switch (ChosenInteraction)
            {//Google Responses________________________________________________________
                case "notyet3":
                case "googChromeUserCreate": //Ñîçäàíèå íîâîãî ïîëüçîâàòåëÿ Õðîìà

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("ß ïîìîãó òåáå ýòî ñäåëàòü.\n" + "*Îòêðîé áðàóçåð Ãóãë Õðîì*. Íàæìè íà îêîøêî ïîëüçîâàòåëÿ â âåðõíåì ïðàâîì óãëó. Êëèêàé *Óïðàâëÿòü ïîëüçîâàòåëÿìè*.\n")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .addAttachment(new File(path+ "/chromeuser1.png"))
                            .addComponents(ActionRow.of(Button.primary("goog1", "Äàëüøå.")))
                            .send().join();


                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Âûáåðè *Äîáàâèòü ïîëüçîâàòåëÿ* â íèæíåì ïðàâîì óãëó.\n" +
                                    "Íàçîâè ñâîåãî ïîëüçîâàòåëÿ òàê *ÈÌß_LinkedIn*.\n" + "Äîáàâü àâàòàðêó.\n" +
                                    "Ïîñòàâü ãàëî÷êó *Ñîçäàòü ÿðëûê ýòîãî ïðîôèëÿ íà ðàáî÷åì ñòîëå*.\n" + "Íàæìè *Äîáàâèòü*.")
                            .send().join();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .addAttachment(new File(path+ "/chromeuser3.png"))
                            .addComponents(ActionRow.of(Button.primary("goog2","Äàëüøå")))
                            .send().join();


                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .append("Ïîäêëþ÷è ðàáî÷óþ ïî÷òó, êîòîðóþ òû ñîçäàë, ê Õðîì-ïîëüçîâàòåëþ. \n" + "Âóàëÿ! Íîâûé ïîëüçîâàòåëü ÃóãëÕðîì ãîòîâ.\n" +
                                    "\n" + "_Àâòîìàòè÷åñêè îòêðîåòñÿ íîâàÿ âêëàäêà Õðîìà ïîä íîâûì ïîëüçîâàòåëåì_.\n" +
                                    "\n" + "_Íà ðàáî÷åì ñòîëå ïîÿâèëñÿ ÿðëûê äëÿ âõîäà èìåííî â ýòîãî ïîëüçîâàòåëÿ_.")
                            .send().join();

                    break;
                case "googleFindCol": //Íàõîæäåíèå êîëëåã
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("×òîáû *íàéòè ñâîèõ êîëëåã ñ ïîìîùüþ Ãóãë-ïîèñêà è äîáàâèòü èç â äðóçüÿ íà Ëèíêåäèí*, â ñòðîêå ïîèñêà âáåé îïåðàòîð:\n" +
                                    "\n" +
                                    "site:linkedin.com remote helpers")
                            .respond();
                    break;
                case "googleCalendar":// Ãóãë êàëåíäàðü
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ãäå âçÿòü *Ãóãë-êàëåíäàðü*:\n" + "\n" +"Îòêðîé íîâóþ âêëàäêó â áðàóçåðå ãóãë-õðîì.\n" +
                                    "\n" +"*Â ïðàâîì âåðõíåì óãëó, íàæìè íà ìåíþ *(â âèäå òî÷åê).\n" +
                                    "Îòêðîåòñÿ ìåíþ ðàñøèðåíèé Ãóãë.\n" +"\n" +"*Âûáåðè Êàëåíäàðü*\n" +"\n" + "_Åñëè òåáå íóæíî íàçíà÷èòü èâåíò, îòêðîé êàëåíäàðü ñ êîðïîðàòèâíîé ïî÷òû info@rh-s.com_.")
                            .respond();
                    break;
                case "googleSearch"://Îïåðàòîðû ïîèñêà â ãóãëå
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent(googleSearchBig).respond();
                    break;
                case "googleUsefulAddons": //"ïîëåçíûå àääîíû

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ïîëåçíûå *ðàñøèðåíèÿ â ãóãë* äëÿ ðàáîòû:" +"\n")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("\"https://chrome.google.com/webstore/detail/find-anyones-email-contac/jjdemeiffadmmjhkbbpglgnlgeafomjo" +"\n" +
                                    "https://chrome.google.com/webstore/detail/rocketreach-chrome-extens/oiecklaabeielolbliiddlbokpfnmhba"+ "\n" +
                                   "https://chrome.google.com/webstore/detail/free-vpn-for-chrome-vpn-p/majdfhpaihoncoakbjgbdhglocklcgno" + "\n")
                            .send();
                    break;


                //òóò íà÷èíàåòñÿ LinkedIN________________________________________________________
                case "linkCleanOld":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè íà ñâîé ðàáî÷èé àêêàóíò íà Ëèíêåäèí.")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Ââåðõó ïåðåéäè íà *âêëàäêó My Network*, ñïðàâà íàæìè íà *âêëàäêó Manage*.\n" +
                                    "Òåáå îòêðîåòñÿ âêëàäêà ñ èñõîäÿùèìè è âõîäÿùèìè ïðèãëàøåíèÿìè.\n" +
                                    "\n" +"Ïåðåéäè íà *âêëàäêó Sent*. Òû âèäèøü ñïèñîê çàÿâîê, êîòîðûå áûëè îòïðàâëåíû ëèäàì.\n" +
                                    "*Íàæìè íà êíîïêó Withdraw* è óäàëè ñòàðûé êîííåêò.\n" + "\n" +"Óäàëè âñå çàÿâêè êîòîðûå áûëè îòîñëàíû 2 è áîëåå íåäåëü íàçàä.Åñëè çà ýòî âðåìÿ ëèä íå îäîáðèë êîííåêò, îí áóäåò âèñåòü ïîêà òû åãî íå óäàëèøü.\n" +
                                    "\n" +"Ïðîâîäè òàêóþ ÷èñòêó íà âñåõ ñâîèõ ðàáî÷èõ àêêàóíòàõ.")
                            .send();
                    break;
                case "linkAccBan"://Áàí àêêàóíòà â Ëèíêåä Èí________________________________________________________

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Êàê èçáåæàòü ïîïàäàíèÿ â áàí íà Ëèíêåäèí*")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("1) Äåëàé *ïàóçû ìåæäó ïîâòîðÿþùèìèñÿ äåéñòâèÿìè* (êîííåêò, êîïèðîâàíèå èíôû, ðàññûëêà è ò.ä.);\n" + "\n" +
                                    "2) C 10-þ êîííåêòàìè íå ñòó÷èñü â äðóçüÿ ê ïðåçèäåíòó;\n" + "\n"+
                                    "3) *Íå ñïàìèòü, âåñòè ñåáÿ ìàêñèìàëüíî åñòåñòâåííî*. Îáùåíèå äîëæíî áûòü æèâûì, õîòü è ïîñòðîåíî â äåëîâîì ñòèëå.\n" + "\n"+
                                    "4) Ïîëüçîâàòüñÿ *îòäåëüíûì þçåðîì Ãóãë Õðîìà äëÿ âõîäà â ñâîé Ëèíêåäèí*.\n" + "\n"+
                                    "5) *Ðàçäåëè ðàáîòó íà ÷àñòè*. Íàïðèìåð, ìèíóò 10-15 äîáàâëÿåøü ëþäåé (êàæäûå 30 ñåêóíä), ïîòîì ïåðåêëþ÷àåøüñÿ íà ðàññûëêó ïèñåì ïî ïî÷òå. È òàê íåñêîëüêî ïîäõîäîâ.\n" + "\n"+
                                    "6) Â èäåàëå âåäè ïðîôèëü. Ìàêñèìàëüíî çàïîëíè àêêàóíò èíôîðìàöèåé, ôîòî, ïîñòàìè. Ýòî ïðèâëåêàåò âíèìàíèå ïîòåíöèàëüíûõ êëèåíòîâ, êîòîðûå ÑÀÌÈ çàõîòÿò ñâÿçàòüñÿ ñ òîáîé. Ñèñòåìà ñîöñåòè íå ïîäóìàåò, ÷òî çäåñü ÷òî-òî íå òàê. Íóæíî òàêæå äåëàòü ðåïîñòû èç ãðóïïû íàøåé êîìïàíèè, ëàéêàé ïîñòû êîíòàêòîâ")
                            .send();
                    break;
                case "linkAccLimits": // Ëèìèò íà àêêàóíò â Ëèíêåä Èí
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Åñëè ïîÿâèëñÿ *ëèìèò íà àêêàóíòå* Ëèíêåäèí, *îáðàòèñü ê àêêàóíò-ìåíåäæåðó*")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Âëàä\n" + "+380981101090\n")
                            .send();
                    break;

                case "linkAccCountry": //Ñìåíà ñòðàíû àêêàóíòà íà Ëèêåä Èí
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("_Òâîé àêêàóíò Ëèíêåäèí çàêðåïëåí çà îïðåäåëåííîé ñòðàíîé. Ýòè äàííûå ìîæåò çàìåíèòü òîëüêî àêêàóíò-ìåíåäæåð_\n")
                            .respond();
                    break;

                case "linkFindLeads": //Íàõîæäåíèå ëèäîâ
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Çàéäè* íà ñâîþ ñòðàíèöó â Ëèíêåäèí, çàòåì *âî âêëàäêó My Network*, ðàñïîëîæåííóþ íà âåðõíåé ïàíåëè ñàéòà.\n" +
                                    "\n" + "Âíèçó ñòðàíèöû òû íàéäåøü âñå ïðîôèëè (connections), êîòîðûå ìîãóò áûòü òåáå ïîëåçíû.")
                            .respond();
                    break;

                //Òóò íà÷èíàåòñÿ ÑÐÌ________________________________________________________

                case "crmEnter":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè â ÑÐÌ." +"\n" + "https://crm.rh-s.com/" + "\n" +"Èñïîëüçóé äîñòóïû, êîòîðûå âûäàë òåáå íàø àêêàóíò-ìåíåäæåð.")
                            .respond();
                    break;
                case "crmAccess":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Âëàä" + "\n" + "+380981101090" + "\n"+ "Äîñòóïû ê ÑÐÌ è ðàáî÷èì àêêàóíòàì âûäàåò àêêàóíò-ìåíåäæåð. Íàïèøè åìó.")
                            .respond();
                case "crmAcc":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè â ñâîþ ÑÐÌ.\n" +"\n" +"Íàæìè âêëàäêó *Add new lead*\n" +
                                    "\n" + "*Âûáåðè Ëèíêåäèí-àêêàóíò, êîòîðûé çàêðåïëåí çà òîáîé* (àêêàóíò, êîòîðûé òåáå âûäàë àêêàóíò-ìåíåäæåð ñïåöèàëüíî äëÿ ðàáîòû)")
                            .respond();
                    break;
                case "crmHowToAddLead":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("×òîáû *äîáàâèòü ëèäà â ÑÐÌ*:\n" +
                                    "1) Îòêðîé ÑÐÌ\n" +
                                    "2) Ïåðåéäè íà *âêëàäêó Leads*, â ïðàâîì âåðõíåì óãëó íàæìè íà *êíîïêó Add new lead*.\n" +
                                    "3) Çàïîëíè íåîáõîäèìóþ èíôîðìàöèþ î ëèäå.\n" +
                                    "Ïîëÿ ïîìå÷åíûå êðàñíîé çâåçäî÷êîé* îáÿçàòåëüíî äîëæíû áûòü çàïîëíåíû, èíà÷å íîâûé ëèä íå áóäåò ñîõðàíåí.\n" +
                                    "4) Ïîëÿ, êîòîðûå *çàïîëíÿþòñÿ àâòîìàòè÷åñêè: LG Manager, Lead Source, Lead Status è Country*.\n" +
                                    "5) Â *ïîëå Linkedin accounts* âûáåðè àêêàóíò íà êîòîðîì ñåé÷àñ ðàáîòàåøü.\n" +
                                    "6) Â *ïîëå Company* äîáàâü êîìïàíèþ ëèäà.\n" +
                                    "7) Â *Company Size, Industry è Position* âûáåðè ïîäõîäÿùèé âàðèàíò èç âûïàäàþùåãî ñïèñêà.\n" +
                                    "8) Íàæìè *Save*.")
                            .respond();
                    break;

                case "crmLeads":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Âñåõ ëèäîâ òû ìîæåøü ïðîñìîòðåòü âî âêëàäêå Leads â ñâîåé ÑÐÌ*.\n" +
                                    "Äëÿ òîãî, ÷òîáû íàéòè êîíêðåòíîãî ëèäà, èñïîëüçóé ôèëüòðû.")
                            .respond();
                    break;
                case "crmLeadReports":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Íà *âêëàäêå Lead Reports* òû ìîæåøü ïðîñìîòðåòü âñåõ ëèäîâ, êîòîðûõ òû âíåñ â ÑÐÌ.\n" +
                                    "Äëÿ óäîáñòâà èñïîëüçóé ôèëüòðû.\n" +"\n" +"Íàïîìèíàåì - *íîðìà 40-50 ëèäîâ â äåíü*.")
                            .respond();
                    break;


                //ÒÓÒ íà÷èíàåòñÿ Êàëåíäàðü________________________________________________________
                case "calEnterMail":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè â Ãóãë Êàëåíäàðü ñ êîðïîðàòèâíîé ïî÷òû info@rh-s.com")
                            .respond();
                    break;

                case "calLeadEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("_Êîãäà ëèä ñîãëàøàåòñÿ íà çâîíîê è ñáðàñûâàåò ññûëêó íà ñâîé êàëåíäàðü, â íåì íóæíî çàáðîíèðîâàòü âñòðå÷ó â óäîáíîå è äëÿ íàñ, è äëÿ íåãî âðåìÿ_")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("*Ïåðåéäè ïî ññûëêå, êîòîðóþ îí îòïðàâèë*.\n" +"\n" +"*Îòêðîé íàø êàëåíäàðü. Çäåñü íàéäè ñâîáîäíîå âðåìÿ äëÿ çâîíêà. Âåðíèñü â êàëåíäàðü ëèäà è âûáåðè äàòó è âðåìÿ, êîòîðûå íàì ïîäõîäÿò*.\n" +
                                    "\n" +"Âíèçó *âûáåðè íàø ÷àñîâîé ïîÿñ (GMT +3)*. Âðåìÿ ïåðåâîäèòü íå íóæíî, ïîñêîëüêó ìû âûáèðàåì ÷àñû ïî êèåâñêîìó âðåìåíè.\n" +
                                    "\n" +"Ïîñëå òîãî êàê òû *âûáðàë äàòó è âðåìÿ, íàæìè confirm*.\n" +"Çàïîëíè *íàçâàíèå èâåíòà, äîáàâü ïî÷òó sales@rh-s.com. Â êîììåíòàðèÿõ îáÿçàòåëüíî äîáàâü îïèñàíèå èâåíòà*.\n" +
                                    "\n" +"Íàæìè *êíîïêó add guests, è äîáàâü ïî÷òó info@rh-s.com*.\n" +"\n" +"Ïîäòâåðäè è ñîõðàíè íàø èâåíò â êàëåíäàðå êëèåíòà.\n" +"\n" +"*Çàéäè â íàø êàëåíäàðü ñ ïî÷òû info@rh-s.com*. Íàéäè ýòîò èâåíò, îòêðîé åãî è äîáàâü âñþ íåîáõîäèìóþ èíôîðìàöèþ î êëèåíòå, êîòîðàÿ åñòü ó òåáÿ (ñàéòû êîìïàíèé, êîììåíòàðèé, èìÿ ìåíåäæåðà è òä).")
                            .send();
                    break;

                case "calNewEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"*Çàéäè â Ãóãë Êàëåíäàðü ñ êîðïîðàòèâíîé ïî÷òû* info@rh-s.com .\n" +
                                    "\n" +"*Âûáåðè äàòó è âðåìÿ*, íà êîòîðûå âû äîãîâîðèëèñü ñ êëèåíòîì.\n" +
                                    "\n" +"*Ó÷èòûâàé ÷àñîâûå ïîÿñà* ïðè íàçíà÷åíèè çâîíêà. Â êàëåíäàðå ñòîèò Êèåâñêîå âðåìÿ (GMT+2). ×òîáû íå ñ÷èòàòü âðåìÿ è áûòü òî÷íî óâåðåííûì â ïðàâèëüíîñòè, èñïîëüçóé çàïðîñ â ãóãëå: EST to Ukraine time. Ãóãë âûäàñò òåáå ññûëêè íà îíëàéí êîíâåðòîðû âðåìåíè.\n" +
                                    "\n" +"*AM*  ýòî íî÷íîå è óòðåííåå âðåìÿ (ñ ïîëíî÷è 12am äî ïîëóäíÿ 12pm), *PM*  íàîáîðîò äíåâíîå è âå÷åðíåå âðåìÿ (ñ ïîëóäíÿ 12pm äî ïîëíî÷è 12am).\n" +
                                    "\n" +"Çâîíêè âíîñè â êàëåíäàðü ñ ÏÍ ïî ÏÒ, ñ 9:00 äî 18:00. Ëþáîå äðóãîå âðåìÿ íåîáõîäèìî ñîãëàñîâàòü ñ Sales-îòäåëîì.\n")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Âûáåðè *êíîïêó \"More options\"* è ïåðåéäè â ðàñøèðåííîå ìåíþ íàñòðîåê èâåíòà.\n" +
                                    "Âàæíî - äëèòåëüíîñòü èâåíòà äîëæíà áûòü 30 ìèí. Îáû÷íî äîëüøå ðàçãîâîð íå äëèòñÿ.\n" +
                                    "\n" +
                                    "Çàïîëíè ïóñòûå ïîëÿ.\n" +
                                    "Çàãîëîâîê *Ñompany name - Remote Helpers*\n" +
                                    "Îïèñàíèå:\n" +
                                    "We are going to discuss options for partnership in managing human resources for \"Name of the partner-company\" in Ukraine.\n" +
                                    "Website: rh-s.com\n" +
                                    "Website: \"his website\"")
                            .send();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Ïîëå *Notifications/Íàïîìèíàíèÿ*.\n" +
                                    "Ðåêîìåíäóåì ñòàâèòü 2 íàïîìèíàíèÿ: çà 20 ìèí è çà 10 ìèí.\n" +
                                    "Åñëè ó òåáÿ òîëüêî îäíî ïîëå ñ óâåäîìëåíèåì, òî íàæìè add notification, ÷òîáû äîáàâèòü âòîðîå.\n" +
                                    "\n" +
                                    "*Ïîëå Comments/Êîììåíòàðèé* î çàêàç÷èêå: îáÿçàòåëüíî ïèøåì èìÿ êëèåíòà è ññûëêó íà åãî ëèíêåäèí àêêàóíò, êòî åìó íóæåí è çà÷åì (èñõîäÿ èç ïåðåïèñêè íà ëèíêåäå èëè ïî÷òå, âñå ÷òî ìîæåò ïðèãîäèòüñÿ íà çâîíêå: èíôà î åãî êîìïàíèè, ñâîáîäíîé âàêàíñèè; èëè æå îí ïðîñòî ñêàçàë, ÷òî õî÷åò óçíàòü ïî êàêîé ìîäåëè ñòðîèòñÿ íàøà ðàáîòà).\n" +
                                    "Âñå êîììåíòàðèè ïèñàòü íà àíãëèéñêîì, ÷òîáû êëèåíò òîæå ïîíèìàë î ÷åì ðå÷ü.")
                            .send();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("*Manager: Your name* (ýòî â Âàøèõ èíòåðåñàõ, ÷òîáû ìû çíàëè êîìó äàòü áîíóñ )\n" +
                                    "\n" +
                                    "*Ïîëå Guests*  ñþäà âíåñè èìåéëû âñåõ òåõ, êîãî ìû õîòèì âèäåòü íà çâîíêå:\n" +
                                    "- íàøó ïî÷òó sales@rh-s.com,\n" +
                                    "- ïî÷òó êëèåíòà (ñ åãî ñòîðîíû òàêæå ìîæåò áûòü íåñêîëüêî ïî÷ò, åñëè îí ïîïðîñèë ïîäêëþ÷èòü íà çâîíîê ê ïðèìåðó åãî ïàðòíåðà).\n" +
                                    "Èì ïðèäåò îïîâåùåíèå î äàííîì çâîíêå íà ïî÷òó, è ïåðåä ñàìûì çâîíêîì ïîÿâèòñÿ îïîâåùåíèå èç êàëåíäàðÿ.\n" +
                                    "\n" + "Íàæèìàåì *SAVE*, è êîãäà ïîÿâèòñÿ *Invite external guests, íàæèìàåì YES*.")
                            .send();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Â ÑÐÌ èçìåíè ñòàòóñ Ëèäà íà Event,  äîáàâü äàòó è âðåìÿ çâîíêà.\n" +
                                    "\n" +"Çà 30 ìèí äî çâîíêà, âåæëèâî íàïîìíè êëèåíòó, î òîì, ÷òî ó íàñ íàçíà÷åíà áåñåäà.\n" +
                                    "Åñëè âîçíèêàþò êàêèå-òî îáñòîÿòåëüñòâà è çâîíîê íå ñîñòîèòñÿ, ñîîáùè îá ýòîì sales ìåíåäæåðó.\n" +
                                    "Ïîñëå çâîíêà, íóæíî ñâÿçàòüñÿ ñ sales ìåíåäæåðîì è ïîèíòåðåñîâàòüñÿ ðåçóëüòàòàìè, ïîñëå ÷åãî ïîñòàâèòü ñòàòóñ Call.")
                            .send();
                    break;

                case"calGet":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Îòêðîé *íîâóþ âêëàäêó â áðàóçåðå ãóãë-õðîì*.\n" +
                                    "\n" +"Â ïðàâîì âåðõíåì óãëó, *íàæìè íà ìåíþ* (â âèäå òî÷åê).\n" +
                                    "Îòêðîåòñÿ ìåíþ ðàñøèðåíèé Ãóãë.\n" + "\n" +"Âûáåðè *Êàëåíäàðü*.\n" + "\n" +"Äëÿ *íàçíà÷åíèÿ èâåíòà, îòêðîé êàëåíäàðü ñ êîðïîðàòèâíîé ïî÷òû info@rh-s.com*.")
                            .respond();
                    break;

                case "callFollowUp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Îòêðîé _Ãóãë-êàëåíäàðü_. Ñîçäàé íîâîå íàïîìèíàíèå.\n" +
                                    "*Çàãîëîâîê* êàëåíäàðíîãî ñîáûòèÿ  *íàçâàíèå êîìïàíèè FollowUp*\n" +
                                    "\n" +"*Âûáåðè äàòó* íà êîòîðóþ íàäî ñäåëàòü íàïîìèíàíèå è ïðîäîëæèòåëüíîñòü: *ñòàâü ãàëî÷êó ÍÀ ÂÅÑÜ ÄÅÍÜ*. Òàê òâîè èâåíòû áóäóò îòîáðàæàòüñÿ ââåðõó êàëåíäàðÿ.\n" +
                                    "\n" +"Âàæíî: â êàëåíäàðå *âûäåëÿåì ôîëëîó-àïû ÎÐÀÍÆÅÂÛÌ öâåòîì*.\n" +
                                    "\n" +"*Çàïîëíè êàðòî÷êó íàïîìèíàíèÿ*\n" +"- â îïèñàíèè óêàæè *îïèñàíèå êëèåíòà, åãî âîïðîñû/îòâåòû, åãî êîíòàêòû*.\n" +"- îáÿçàòåëüíî óêàæè *Manager* - ñåáÿ, ÷òîáû ìû çíàëè, êòî ïðèâåë ëèäà.\n" +"- â *Guests* - îáÿçàòåëüíî äîáàâü ïî÷òó sales@rh-s.com ÷òîáû òâîå íàïîìèíàíèå îòîáðàçèëîñü è ó ðåáÿò, êîòîðûå íåïîñðåäñòâåííî ñîçâàíèâàþòñÿ ñ êëèåíòàìè.")
                            .respond();
                    break;

                //Òóò íà÷èíàåòñÿ Àïäåéò________________________________________________________

                case "updateWhatIs":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Àïäåéòû* - ýòî ëèäû, êîòîðûå óæå åñòü â ÑÐÌ, áûëè äîáàâëåíû òîáîé èëè äðóãèì ìåíåäæåðîì áîëåå òðåõ ìåñÿöåâ íàçàä.\n" +
                                    "\n" +"Òàêèì ëèäàì *ìû ïèøåì ïîâòîðíûå ñîîáùåíèÿ è îòïðàâëÿåì ïîâòîðíûé êîííåêò*.")
                            .respond();
                    break;

                case "updateWhy":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ýòè ëèäàì ìû ïèøåì ïîâòîðíûå ñîîáùåíèÿ è îòïðàâëÿåì ïîâòîðíûé êîííåêò.\n" +
                                    "\n" +"*Òàê ìû íàïîìèíàåì ïîòåíöèàëüíûì êëèåíòàì î ñåáå è ñâîèõ óñëóãàõ. Ñî âðåìåíåì â êîìïàíèÿõ ìåíÿþòñÿ çàäà÷è, ïîÿâëÿþòñÿ íîâûå ïðîåêòû è ìû ìîæåì ïðåäëîæèòü ñîòðóäíè÷åñòâî â íóæíûé ìîìåíò äëÿ ëèäà.*")
                            .respond();
                    break;

                case "updateHowFind":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Êàê íàéòè àïäåéòîâ:\n" +"\n" +"Â ñâîåé ÑÐÌ îòêðîé âêëàäêó Leads.\n" +"Âûáåðè íåîáõîäèìûå òåáå ïîëÿ:\n" +"*Country* - ñòðàíà ñ êîòîðîé ñåé÷àñ  ðàáîòàåøü\n" +"*Lead Status* - âñå ñòàòóñû, êðîìå Call\n" +"Íàæìè *Apply*.\n" + "Äàëåå îòêðîåòñÿ ñïèñîê âñåõ ëèäîâ, êîòîðûå áûëè âíåñåíû çà âñå âðåìÿ ïîä íóæíûì òåáå ñòàòóñîì è ñòðàíîé, ñ êîòîðîé òû ñåé÷àñ ðàáîòàåøü.\n" +
                                    "\n" + "Ëèñòàé âíèç ñòðàíèöû, è íà÷èíàé ðàáîòàòü ñ ëèäàìè ñ êîíöå ñïèñêà, ò.å. ñ ñàìûìè ñòàðûìè - êîòîðûå áûëè ñäåëàíû 3 è áîëåå ìåñÿöåâ íàçàä.\n" +
                                    "\n" + "Ïðîâåðü äàòó, êîãäà ëèä áûë ñîçäàí \"CREATED ON\" è äàòó, êîãäà ëèäó áûë ñäåëàí ïîñëåäíèé àïäåéò UPDATE ON. Îáðàòè âíèìàíèå íà êîììåíòàðèè.")
                            .respond();
                    break;
                case "updateHowMake":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè â êàðòó ëèäà, è ïåðåéäè ïî ññûëêå *Contacts LinkedIn* íà ñòðàíè÷êó ëèäà â LinkedIn.\n" +
                                    "Îòïðàâü åìó ïîâòîðíûé êîííåêò âìåñòå ñ øàáëîíîì: connect-add note.\n" +
                                    "\n" +
                                    "Äàëåå â ÑÐÌ â êàðòå ëèäà íàæìè *Edit* è îòðåäàêòèðóé èíôîðìàöèþ:\n" +
                                    "- ïîëå *Active Agents* - óêàæè ñâîè äàííûå, ÷òîáû ëèä áûë çàñ÷èòàí èìåííî òåáå.\n" +
                                    "- ïîëå *LinkedIn Accounts* - óêàæè àêêàóíò ñ êîòîðîãî òû îòïðàâèë êîííåêò. \n" +
                                    "- ïîëå *Lead Status* - ïîñòàâü ñòàòóñ Sent Request\n" +
                                    "- ïîëå *Note* - óêàæè êîììåíòàðèé - updated (ëèä îáíîâëåí)\n" +
                                    "Â çàâåðøåíèå ðåäàêòèðîâàíèÿ êàðòû ëèäà íàæìè *Update*.")
                            .respond();
                    break;

                case "updateDayNorm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Íîðìà àïäåéòîâ - 120+ êîìïàíèé â äåíü.")
                            .respond();
                    break;
                //ÒÓÒ Òàéì Òðåêåð________________________________________________________

                case "timeTurnOn":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"Çàéäè â ÑÐÌ.\n" +"\n" +"Íàæìè êíîïêó Login â ïðàâîì âåðõíåì óãëó ýêðàíà.\n" +
                                    "\n" +"Ââåäè ñâîþ ýëåêòðîííóþ ïî÷òó â ïîëå Email è ïàðîëü â ïîëå Password è íàæìè êíîïêó LOG IN.\n" +
                                    "\n" +"Ïðè ïåðåõîäå íà ñëåäóþùóþ ñòðàíèöó (âêëàäêà Dashboard) áðàóçåð âûâåäåò îêíî çàïðîñà íà *äîñòóï ê äàííûì î òâîåì ìåñòîïîëîæåíèè. Íàæìè Ðàçðåøèòü.*\n" +
                                    "\n" +"Èòîã: *Íà÷àëî ðàáî÷åãî äíÿ >>> Clock In >>> Îáåäåííûé ïåðåðûâ >>> Clock Out >>> Âîçâðàùåíèå ñ îáåäåííîãî ïåðåðûâà >>> Clock In >>> Îêîí÷àíèå ðàáî÷åãî äíÿ >>> Clock Out*\n")
                            .respond();
                    break;

                case "timeGoogleSetting":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Íàæìè êíîïêó _ìåíþ â ïðàâîì âåðõíåì óãëó îêíà áðàóçåðà_, â ïîÿâèâøåìñÿ ñïèñêå íàæìè íà âêëàäêó *Íàñòðîéêè*.\n" +
                                    "\n" +"Â ñïèñêå ñëåâà âûáåðè *âêëàäêó Êîíôèäåíöèàëüíîñòü è áåçîïàñíîñòü*.\n" +
                                    "\n" +"Ïðîëèñòàé íåìíîãî âíèç ïîêà íå âñòðåòèøü *âêëàäêó Íàñòðîéêè ñàéòîâ*, íàæìè íà íåå.\n" +
                                    "\n" +"Íàæìè íà *âêëàäêó Ïîñìîòðåòü òåêóùèå ðàçðåøåíèÿ è ñîõðàíåííûå äàííûå ñàéòîâ*.\n" +
                                    "\n" +"*Â ïîèñêîâîé ñòðîêå â ïðàâîì âåðõíåì óãëó ââåäè crm.rh-s.com*, íàæìè íà ïîÿâèâøóþñÿ êíîïêó ñ ýòèì àäðåñîì.\n" +
                                    "\n" +"Â ïîÿâèâøåìñÿ ñïèñêå íàéäè *ñòðîêó Ãåîäàííûå* è ñïðàâà âûáåðè âàðèàíò *Ðàçðåøèòü*.")
                            .respond();

                    break;

                    //Òóò áîíóñû________________________________________________________
                case "bonusMy":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ìû ïðåäëàãàåì áîíóñ çà êàæäûé íàçíà÷åííûé è ñîñòîÿâøèéñÿ çâîíîê. Äåòàëüíåå ðàçáåðåì áîíóñíóþ ñèñòåìó íèæå.\n" +
                                    "\n" +"200 ãðí - ñòàíäàðòíûé áîíóñ çà íàçíà÷åííûé è ñîñòîÿâøèéñÿ çâîíîê ñ êëèåíòîì.\n" +
                                    "+200 ãðí - åñëè âû íàçíà÷èëè çâîíîê, íà êîòîðîì ñðàçó áóäåò ñîáåñåäîâàíèå ñ êîíêðåòíûìè êàíäèäàòàìè/êàíäèäàòîì (áåç ñåéëçîâ).\n" +
                                    "+500 ãðí - åñëè ñîâåðøåíà ïðîäàæà íàøåãî ñîòðóäíèêà.\n" +
                                    "\n" +"Çâîíîê çàñ÷èòûâàåòñÿ òîëüêî åñëè:\n" +"à) çâîíîê ñ êëèåíòîì ñîñòîÿëñÿ;\n" +
                                    "á) êëèåíò â ïîëíîé ìåðå ïîíÿë, êàêèå óñëóãè ìû ïðåäëàãàåì (ò.å. Èíôîðìàöèÿ ïðåäîñòàâëåíà êëèåíòó êîððåêòíî);\n" +
                                    "â) ëèä ðåëåâàíòíûé.\n" +"\n" +"Áîíóñû ïîäâîäÿòñÿ â êîíöå êàæäîãî ìåñÿöà.\n" +
                                    "Äàííûå ñóììû íå ïðåäåë, âñå çàâèñèò îò âàñ è âàøèõ ñòàðàíèé.\n" +
                                    "Äàííûå áîíóñû ìîãóò áûòü ïåðâûìè ïðèçíàêàìè âàøåãî êàðüåðíîãî ðîñòà â íàøåé êîìïàíèè, íî îòíþäü íå èõ ïðåäåëîì. \n")
                            .respond();
                    break;

                case "bonusWhen":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàðïëàòà íà÷èñëÿåòñÿ äâà ðàçà â ìåñÿö ñ 5 ïî 10 è ñ 20 ïî 25 ÷èñëà êàæäîãî ìåñÿöà íà êàðòî÷êó èëè íàëè÷íûìè.")
                            .respond();
                    break;

                //Òóò ëèäû________________________________________________________
                case "leadWhichLead":
                case "leadRight":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Îáðàòè âíèìàíèå:\n" + "\n" +"- íà _ðîä äåÿòåëüíîñòè ëèäà_.\n" +"*Äîáàâëÿåì: CEO, COO, Founder, CTO, Owner, Director, co-Founder* (õîòÿ åñëè åñòü âîçìîæíîñòü äîáàâèòü íå ñîîñíîâàòåëÿ, òî îòäàéòå ïðåäïî÷òåíèå äðóãîé äîëæíîñòè), *Entrepreneur*.\n" +
                                    "\n" +"- íà _ñòðàíó ëèäà_.\n" +"*Ðàáîòàåì ñ: Australia, Austria, Belgium, Canada, Denmark, Estonia, Finland, France, Germany, Ireland, Italy, Israel, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, USA*.\n" +
                                    "\n" +"- èìÿ è âíåøíîñòü ëèäà.\n" +"Åñëè â ïðîôèëå ó ÷åëîâåêà ñòîèò îäíà èç âûøåïåðå÷èñëåííûõ ñòðàí, íî òåáÿ ñìóùàåò åãî èìÿ èëè âíåøíîñòü, íàïðèìåð îí âûëèòûé èíäóñ èëè àôðîàìåðèêàíåö (óïîìèíàþ èõ, ïîòîìó ÷òî îíè ÷àñòî ìàñêèðóþòñÿ), òî òàêèå ëþäè íàì òîæå íå ïîäõîäÿò.\n" +
                                    "\n" +"- _íàëè÷èå îáùèõ ïðîôèëåé_.\n" +"Ìîæåò îêàçàòüñÿ, ÷òî íàøè ìåíåäæåðû óæå ñ íèì ñâÿçûâàëèñü, âîçìîæíî, ìû óæå èìååì ñ ýòèì ÷åëîâåêîì êàêèå-ëèáî äåëîâûå îòíîøåíèÿ. Âñåãäà ïðîâåðÿéòå íàëè÷èå êîìïàíèè, â êîòîðîé ðàáîòàåò ëèä, â íàøåé ÑÐÌ ñèñòåìå.\n" +
                                    "Åñëè îáùèõ ïðîôèëåé áîëüøå 8  ýòî íå çíà÷èò, ÷òî íå ñòîèò îáðàùàòü íà ÷åëîâåêà âíèìàíèå. Âñå ýòè ëþäè ìîãóò íå îòíîñèòüñÿ ê íàøåé êîìïàíèè. Ãëàâíîå  ïðîâåðèòü.\n" +
                                    "Ïðîâåðèòü ìîæíî, êëèêíóâ íà ñòðî÷êó ñ êîëè÷åñòâîì îáùèõ ïðîôèëåé.\n" +
                                    "\n" +"Åñëè âñå îê, òî æìè êíîïêó *Connect*.")
                            .respond();
                    break;
                case "leadInfoFrom":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Åñëè êëèåíò çàèíòåðåñîâàí è õî÷åò íàçíà÷èòü çâîíîê, òâîÿ çàäà÷à óòî÷íèòü ó íåãî:\n" +
                                    "- *âðåìÿ*, êîãäà åìó áóäåò óäîáíî ñîçâîíèòüñÿ\n" +"- *âðåìåííóþ çîíó èëè ãîðîä* â êîòîðîì îí íàõîäèòñÿ\n" +"- *ïî÷òó*, êóäà îòïðàâèòü ïðèãëàøåíèå íà çâîíîê\n" +"- ãäå åìó óäîáíåé áûëî áû ñîçâîíèòüñÿ: Skype, Whatsapp, Hangout, Zoom.\n" +
                                    "\n" +"Òàêæå *íå çàáûâàé âñþ èíôîðìàöèþ î ëèäå (êîìïàíèÿ, ñòðàíà, ëþáûå êîíòàêòû, è òä - âñå, ÷òî ìîæåò áûòü ïîëåçíî äëÿ ïîñëåäó.ùåé êîììóíèêàöèè ñ êëèåíòîì)*.")
                            .respond();
                    break;

                case "leadAdd":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("×òîáû äîáàâèòü ëèäà â ÑÐÌ:\n" +
                                    "\n" +"Îòêðîé ÑÐÌ\n" +"Ïåðåéäè íà âêëàäêó *Leads*, â ïðàâîì âåðõíåì óãëó íàæìè íà êíîïêó *Add new lead*.\n" +
                                    "\n" +"Çàïîëíè íåîáõîäèìóþ èíôîðìàöèþ î ëèäå.\n" +
                                    "Ïîëÿ ïîìå÷åíûå êðàñíîé çâåçäî÷êîé* îáÿçàòåëüíî äîëæíû áûòü çàïîëíåíû, èíà÷å íîâûé ëèä íå áóäåò ñîõðàíåí.\n" +
                                    "\n" +"Ïîëÿ, êîòîðûå *çàïîëíÿþòñÿ àâòîìàòè÷åñêè: LG Manager, Lead Source, Lead Status è Country*.\n" +
                                    "\n" +"Â *ïîëå Linkedin accounts* âûáåðè àêêàóíò íà êîòîðîì ñåé÷àñ ðàáîòàåøü.\n" +
                                    "\n" +"Â *ïîëå Company* äîáàâü êîìïàíèþ ëèäà.\n" +
                                    "\n" +"Â *Company Size, Industry è Position* âûáåðè ïîäõîäÿùèé âàðèàíò èç âûïàäàþùåãî ñïèñêà.\n" +
                                    "\n" +"Íàæìè *Save*.")
                            .respond();
                    break;

                case "leadCardFill":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Â êàðòó íîâîãî ëèäà íåîáõîäèìî âíåñòè âñþ èíôîðìàöèþ î íåì, êîòîðóþ òåáå óäàëîñü ñîáðàòü â ïðîöåññå êîììóíèêàöèè.*\n" +
                                    "Ïîëÿ ïîìå÷åíûå êðàñíîé çâåçäî÷êîé* îáÿçàòåëüíî äîëæíû áûòü çàïîëíåíû, èíà÷å íîâûé ëèä íå áóäåò ñîõðàíåí.\n" +
                                    "\n" +"*Ïîëÿ, êîòîðûå çàïîëíÿþòñÿ àâòîìàòè÷åñêè*: LG Manager, Lead Source, Lead Status è Country.\n" +
                                    "\n" +"Â *ïîëå Linkedin accounts* âûáåðè àêêàóíò íà êîòîðîì ñåé÷àñ ðàáîòàåøü.\n" +
                                    "\n" +"Â *ïîëå Company* äîáàâü êîìïàíèþ ëèäà.\n" +"\n" +"Â *Company Size, Industry è Position* âûáåðè ïîäõîäÿùèé âàðèàíò èç âûïàäàþùåãî ñïèñêà.\n" +
                                    "\n" +"Íàæìè *Save*.")
                            .respond();
                    break;

                case "leadWhoFollowUp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Åñëè êëèåíò ïðîñèò ñâÿçàòüñÿ ñ íèì ïîçæå èëè îòâå÷àåò, ÷òî ñåé÷àñ íàøè óñëóãè íå àêòóàëüíû, íî ÷åðåç ïàðó ìåñÿöåâ îí íà÷èíàåò íîâûé ïðîåêò è îíè ïîíàäîáÿòñÿ, íóæíî ñòàâèòü êëèåíòà íà ôîëëîó àï*.\n" +
                                    "\n" +"Åñëè òû îòïðàâëÿë ïðåçåíòàöèþ, ýòîò èíñòðóìåíò òàêæå ìîæåò ïðèãîäèòüñÿ ÷òîáû ñïðîñèòü, ÷òî êëèåíò î íåé äóìàåò.")
                            .respond();
                    break;

                case "leadHowMuch":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè â ñâîþ ÑÐÌ. Îòêðîé âêëàäêó *Lead Reports*.\n" +
                                    "\n" +"Èñïîëüçóÿ ôèëüòðû òû ìîæåøü ïðîñìîòðåòü âñåõ ëèäîâ, êîòîðûõ òû âíåñ â ÑÐÌ.")
                            .respond();
                    break;

                case "leadDayNorm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Íîðìà - *40-50 ëèäîâ â äåíü*.")
                            .respond();
                    break;

                case "leadHowFind":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Âñåõ ëèäîâ òû ìîæåøü ïðîñìîòðåòü âî âêëàäêå *Leads* â ñâîåé ÑÐÌ.\n" +
                                    "Äëÿ òîãî, ÷òîáû íàéòè êîíêðåòíîãî ëèäà, èñïîëüçóé ôèëüòðû.")
                            .respond();
                    break;

                //Òóò Øàáëîíû________________________________________________________

                case "blueFirstCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ïðèâåòñòâèå:\n" +
                                    "Hello %Name%,\n" +
                                    "Would you like to hire our Remote Marketing Employees from Ukraine part-time or full-time? Can we connect?\n")
                            .respond();
                    break;

                case "blueSecondCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Âàðèàíòû êîííåêòà:\n" + "Âòîðîå ñîîáùåíèå ïîñëå êîííåêòà: \n" + "What information are you interested in?\n" +
                                    "1) more info about our company;\n" +"2) available positions to choose from (departments)\n" +
                                    "3) prices and conditions\n" + "4) Do you want to share your company needs?\n" +
                                    "Appreciate your answer,\n" +"Remote Helpers,\n" +"https://www.rh-s.com\n" +
                                    "Âòîðîå ñîîáùåíèå (íåãàòèâíûé îòâåò/îòêàç):\n" +"Ok, thanks your answer \n" +
                                    "May I ask you for an email to send our presentation in case of your future needs?\n")
                            .respond();

                    break;

                case "blueThirdCon":

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Òðåòüå ñîîáùåíèå (ïîñëå îòâåòà íà âòîðîå ñîîáùåíèå):\n" +
                                    "Ìû îïèñûâàåì äëÿ òåáÿ íåñêîëüêî âàðèàíòîâ îòâåòîâ, â çàâèñèìîñòè îò òîãî, î ÷åì òåáÿ ìîæåò ñïðîñèòü êëèåíò.\n" +
                                    "Ïðåçåíòàöèÿ - https://docs.google.com/presentation/d/e/2PACX-1vSleEpaKL1TOz8NV7d2OY7wtS111idiEdrSM_f88_GiCe-F4pqdzX7SUwZn3vDOog/pub?start=true&loop=true&delayms=15000&slide=id.g1180199a397_3_22\n" +
                                    "We have candidates:\n" + "-Sales (lead generation managers (B2B), sales)\n" +
                                    "-Admin (project managers, administrators, personal assistants, finance)\n" +
                                    "-Creative (designers, illustrators, video editors, motion designers)\n" +
                                    "-Paid advertisement (google, facebook, linkedin, media buying)\n" +
                                    "-Content marketing (smm, copywriting, influence managers, content managers)\n" +
                                    "-Development (frontend/backend)\n" +"What sphere are you interested in?\n" +"Conditions:\n" +"No prepayment\n" +
                                    "Open termination date\n" +"Only dedicated employees\n" +"Prices(full time):\n" +
                                    "Admin and sales 1000 EUR\n" +"Content marketing 1400 EUR\n" +"Creative 1200-1400 EUR\n" +"Development 1900 EUR\n" + "Please share what you would be interested in?\n")
                            .respond();
                    break;

                case "blueAbout":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Åñëè *êëèåíò ñïðàøèâàåò î ñïåöèàëüíîñòÿõ*, âàðèàíò òâîåãî îòâåòà:\n" +
                                    "\n" +"Candidates we offer you to hire can work in the following positions: Lead Generation Managers, Customer Support, Personal Assistants, Social Media Managers, Designers, Media Buyers, PPC, SEO, AdOps, English teachers etc. Can we set a call to see if we can find a fit for you?\n" +
                                    "\n" +"*Ñëåäóþùåå ñîîáùåíèå ìîæåò èñïîëüçîâàòüñÿ â ñëó÷àÿõ, êîãäà êëèåíò ñïðàøèâàåò ïðî ÷àñòè÷íóþ çàíÿòîñòü èëè î êîíêðåòíîì çàêàçå*. Íàïðèìåð êëèåíò èíòåðåñóåòñÿ íå ìîãëè áû ìû ñäåëàòü äëÿ íåãî ñàéò?\n" +
                                    "\n" +"To keep price advantages comparing to freelancers that charge minimum 15 per hour, our maximum cost is 7,5 per hour. But there should be enough work for a full time  it is 160 hours a month, 5 days a week,  8 hours a day. So the final prices are 1200 for designers, 1200 for marketers and 1000 for managers per month. Can we set a call to discuss all the details?")
                            .respond();
                    break;

                case "blueExp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("How much experience do they have? Can you send me some CVs?\n" +
                                    "Most of our employees have a good knowledge of English, worked a while in digital marketing, but still there should be a team lead from your side to run training. Each company has its own specifics and employees need time to get into things.\n" +
                                    "Anyway you can find CVs of available employees on our Telegram channel or website:\n" +
                                    "https://t.me/RemoteHelpers\n" +"https://www.rh-s.com/\n" +
                                    "Ïîäáîð êàíäèäàòîâ:\n" +"Let me share the most suitable candidates for you, you can look through their CVs and let me know who is the best match for you:\n" +
                                    "...link\n" +"...link\n" +"...link\n" +
                                    "What about setting up a call with candidates? This will be the best way to get to know them and learn more about their experience, skills, and so on.\n")
                            .respond();
                    break;

                case "blueClientBad":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Åñëè *êëèåíò íàïèñàë, ÷òî íå çàèíòåðåñîâàí â íàøåì ïðåäëîæåíèè*, òâîé îòâåò:\n" +
                                    "\n" +"Thank you for the reply. May I send you our presentation and prices in case you will need our services in the future?\n" +
                                    "\n" +"*Îòïðàâëÿé èíôîðìàöèþ, à ïîòîì óòî÷íè ó êëèåíòà ïîëó÷èë ëè îí íàøå ïèñüìî, åñòü ëè êàêèå-òî âîïðîñû*. \n" +
                                    "Íóæíî îòïðàâëÿòü ïèñüìà âñåãäà, ïîñëå òîãî êàê êëèåíò äàë ñâîþ ïî÷òó, òàê îí íå ïîòåðÿåò íàøè êîíòàêòû è ñìîæåò îáðàòèòüñÿ â íóæíûé ìîìåíò.\n" +
                                    "È êîíå÷íî æå, *íå çàáóäü ïîñòàâèòü â êàëåíäàðå èâåíò íà FollowUp*, ÷òîáû íå ïîòåðÿòü êîíòàêò, à ñâÿçàòüñÿ ñ íèì â áóäóùåì +ñòàòóñ FollowUp è äàòó â CRM.")
                            .respond();
                    break;

                case "blueClientGood":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"Åñëè *êëèåíò çàèíòåðåñîâàí è õî÷åò íàçíà÷èòü çâîíîê*, òâîÿ çàäà÷à óòî÷íèòü ó íåãî:\n" +
                                    "- âðåìÿ, êîãäà åìó áóäåò óäîáíî ñîçâîíèòüñÿ\n" +
                                    "- ÷àñîâóþ çîíó èëè ãîðîä â êîòîðîì îí íàõîäèòñÿ\n" +
                                    "- ïî÷òó, êóäà îòïðàâèòü ïðèãëàøåíèå íà çâîíîê\n" +
                                    "- ãäå åìó óäîáíåé áûëî áû ñîçâîíèòüñÿ: ñêàéï, âîòñàï, õýíãàóò, zoom\n" +
                                    "Ïîñëå ÷åãî, íóæíî áóäåò ñðàâíèòü ñêîëüêî ýòî áóäåò ïî íàøåìó âðåìåíè è ñîçäàòü èâåíò â êàëåíäàðå\n")
                            .respond();
                    break;

                //Òóò íà÷èíàåòñÿ Êîííåêò_______________________________________________________________

                case "conFirstCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ïðèâåòñòâèå:\n" +"Hello %Name%,\n" +
                                    "\n" +"Âàðèàíòû êîííåêòà:\n" +
                                    "\n" +"Would you consider hiring Ukrainian remote marketing employees for 1000EUR per full-time month? You may find CVs on our website https://bit.ly/3kyi4Ro\n" +
                                    "\n" +"Would you consider hiring Ukrainian remote marketing employees on a full/part time basis for a reasonable price?\n" +
                                    "\n" +"We offer to hire Ukrainian remote employees in marketing and design field to promote your business for a very reasonable price. Do you want to hear more information?\n" +
                                    "\n" +"Dedicated Ukrainian online employees for a very competitive price on a full/part time basis. Do you need more details?\n" +
                                    "\n" +"I would like to connect with you within offering to hire Ukrainian remote marketing employees from our company. Can we have a short chat?\n" +
                                    "\n" +"I represent an outstaffing company from Ukraine. We have a huge number of remote employees for online work and would be happy to propose you cooperation. Interested?\n" +
                                    "\n" +"I represent an outstaffing company from Ukraine. We provide our clients all over the world with remote employees in the digital marketing field. Might you be interested in hearing more?\n" +
                                    "\n" +"I represent an outstaffing company from Ukraine. Would you consider possibility of hiring remote employees in different fields of marketing and design?")
                            .respond();
                    break;

                case "conSecondCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Âàðèàíòû êîííåêòà:\n" +"Âòîðîå ñîîáùåíèå ïîñëå êîííåêòà:\n" +"What information are you interested in?\n" +
                                    "1) more info about our company;\n" +"2) available positions to choose from (departments)\n" +
                                    "3) prices and conditions\n" +"4) Do you want to share your company needs?\n" +
                                    "Appreciate your answer,\n" +"Remote Helpers,\n" +
                                    "https://www.rh-s.com\n" +"Âòîðîå ñîîáùåíèå (íåãàòèâíûé îòâåò/îòêàç):\n" +
                                    "Ok, thanks your answer \n" + "May I ask you for an email to send our presentation in case of your future needs?\n")
                            .respond();
                        break;

                case "conThirdCon":

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Òðåòüå ñîîáùåíèå (ïîñëå îòâåòà íà âòîðîå ñîîáùåíèå):\n" +
                                    "Ìû îïèñûâàåì äëÿ òåáÿ íåñêîëüêî âàðèàíòîâ îòâåòîâ, â çàâèñèìîñòè îò òîãî, î ÷åì òåáÿ ìîæåò ñïðîñèòü êëèåíò.\n" +
                                    "Ïðåçåíòàöèÿ - https://docs.google.com/presentation/d/e/2PACX-1vSleEpaKL1TOz8NV7d2OY7wtS111idiEdrSM_f88_GiCe-F4pqdzX7SUwZn3vDOog/pub?start=true&loop=true&delayms=15000&slide=id.g1180199a397_3_22\n" +
                                    "We have candidates:\n" + "-Sales (lead generation managers (B2B), sales)\n" +
                                    "-Admin (project managers, administrators, personal assistants, finance)\n" +
                                    "-Creative (designers, illustrators, video editors, motion designers)\n" +
                                    "-Paid advertisement (google, facebook, linkedin, media buying)\n" +
                                    "-Content marketing (smm, copywriting, influence managers, content managers)\n" +
                                    "-Development (frontend/backend)\n" +"What sphere are you interested in?\n" +"Conditions:\n" +"No prepayment\n" +
                                    "Open termination date\n" +"Only dedicated employees\n" +"Prices(full time):\n" +
                                    "Admin and sales 1000 EUR\n" +"Content marketing 1400 EUR\n" +"Creative 1200-1400 EUR\n" +"Development 1900 EUR\n" + "Please share what you would be interested in?\n")
                            .send().join();
                    break;

                case "conToLead":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Çàéäè íà ñâîþ ñòðàíèöó â Ëèíêåäèí, çàòåì âî âêëàäêó My Network, ðàñïîëîæåííóþ íà âåðõíåé ïàíåëè ñàéòà.\n" +
                                    "\n" +"Âíèçó ñòðàíèöû òû íàéäåøü âñå ïðîôèëè (connections), êîòîðûå ìîãóò áûòü òåáå ïîëåçíû.\n" +
                                    "\n" +"Îáðàòè âíèìàíèå:\n" +"\n" +"- íà _ðîä äåÿòåëüíîñòè ëèäà_.\n" +
                                    "*Äîáàâëÿåì: CEO, COO, Founder, CTO, Owner, Director, co-Founder* (õîòÿ åñëè åñòü âîçìîæíîñòü äîáàâèòü íå ñîîñíîâàòåëÿ, òî îòäàéòå ïðåäïî÷òåíèå äðóãîé äîëæíîñòè), *Entrepreneur*.\n" +
                                    "\n" +"- íà _ñòðàíó ëèäà_.\n" +"*Ðàáîòàåì ñ: Australia, Austria, Belgium, Canada, Denmark, Estonia, Finland, France, Germany, Ireland, Italy, Israel, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, USA*.\n" +
                                    "\n" +"- èìÿ è âíåøíîñòü ëèäà.\n" +"Åñëè â ïðîôèëå ó ÷åëîâåêà ñòîèò îäíà èç âûøåïåðå÷èñëåííûõ ñòðàí, íî òåáÿ ñìóùàåò åãî èìÿ èëè âíåøíîñòü, íàïðèìåð îí âûëèòûé èíäóñ èëè àôðîàìåðèêàíåö (óïîìèíàþ èõ, ïîòîìó ÷òî îíè ÷àñòî ìàñêèðóþòñÿ), òî òàêèå ëþäè íàì òîæå íå ïîäõîäÿò.\n" +
                                    "\n" +"- _íàëè÷èå îáùèõ ïðîôèëåé_.\n" +"Ìîæåò îêàçàòüñÿ, ÷òî íàøè ìåíåäæåðû óæå ñ íèì ñâÿçûâàëèñü, âîçìîæíî, ìû óæå èìååì ñ ýòèì ÷åëîâåêîì êàêèå-ëèáî äåëîâûå îòíîøåíèÿ. Âñåãäà ïðîâåðÿéòå íàëè÷èå êîìïàíèè, â êîòîðîé ðàáîòàåò ëèä, â íàøåé ÑÐÌ ñèñòåìå.\n" +
                                    "Åñëè îáùèõ ïðîôèëåé áîëüøå 8  ýòî íå çíà÷èò, ÷òî íå ñòîèò îáðàùàòü íà ÷åëîâåêà âíèìàíèå. Âñå ýòè ëþäè ìîãóò íå îòíîñèòüñÿ ê íàøåé êîìïàíèè. Ãëàâíîå  ïðîâåðèòü.\n" +
                                    "Ïðîâåðèòü ìîæíî, êëèêíóâ íà ñòðî÷êó ñ êîëè÷åñòâîì îáùèõ ïðîôèëåé.\n" +
                                    "\n" +"Åñëè âñå îê, òî æìè êíîïêó *Connect*.")
                            .respond();
                    break;

                //Òóò íà÷èíàåò Ôîëëó-àï

                case "followupWhat":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ôîëëîó-àï -ýòî òàêîé óäîáíûé èíñòðóìåíò, ÷òîáû íàïîìíèòü î ñåáå ÷åëîâåêó, êîòîðûé, âîçìîæíî, î íàñ çàáûë. *Ôîëëîó-àï íåîáõîäèì äëÿ òîãî, ÷òîáû ïîääåðæèâàòü èíòåðåñ çàêàç÷èêà, âåäü âåðîÿòíîñòü òîãî, ÷òî ñäåëêà ñîâåðøèòñÿ, áîëüøå, åñëè ìåæäó íàìè è êëèåíòîì âûñòðîåíû äîâåðèòåëüíûå îòíîøåíèÿ.*\n" +
                                    "\n" +"*Åñëè êàêîé-ëèáî êëèåíò ïðîñèò ñâÿçàòüñÿ ñ íèì ïîçæå èëè îòâå÷àåò, ÷òî ñåé÷àñ íàøè óñëóãè íå àêòóàëüíû, íî ÷åðåç ïàðó ìåñÿöåâ îí íà÷èíàåò íîâûé ïðîåêò è îíè ïîíàäîáÿòñÿ, íóæíî ñòàâèòü êëèåíòà íà ôîëëîó àï.*\n" +
                                    "\n" +"Åñëè âû îòïðàâëÿëè ïðåçåíòàöèþ, ýòîò èíñòðóìåíò òàêæå ìîæåò ïðèãîäèòüñÿ ÷òîá ñïðîñèòü, ÷òî êëèåíò î íåé äóìàåò.")
                            .respond();
                    break;


                case "followupCal":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Îòêðîé *Ãóãë-êàëåíäàðü*. Ñîçäàé *íîâîå íàïîìèíàíèå*.\n" +
                                    "Çàãîëîâîê êàëåíäàðíîãî ñîáûòèÿ  *íàçâàíèå êîìïàíèè FollowUp*\n" +
                                    "\n" +"*Âûáåðè äàòó íà êîòîðóþ íàäî ñäåëàòü íàïîìèíàíèå è ïðîäîëæèòåëüíîñòü: ñòàâü ãàëî÷êó ÍÀ ÂÅÑÜ ÄÅÍÜ*. Òàê òâîè èâåíòû áóäóò îòîáðàæàòüñÿ ââåðõó êàëåíäàðÿ.\n" +
                                    "\n" +"Âàæíî: â êàëåíäàðå *âûäåëÿåì ôîëëîó-àïû ÎÐÀÍÆÅÂÛÌ öâåòîì*.\n" +
                                    "\n" +"*Çàïîëíè êàðòî÷êó íàïîìèíàíèÿ*\n" +
                                    "- â îïèñàíèè óêàæè âñå îïèñàíèå î êëèåíòå, åãî âîïðîñàõ/îòâåòàõ, åãî êîíòàêòû.\n" +
                                    "- îáÿçàòåëüíî óêàæè Manager - ñåáÿ, ÷òîáû ìû çíàëè, êòî ïðèâåë ëèäà.\n" +
                                    "- â Guests - îáÿçàòåëüíî äîáàâü ïî÷òó sales@rh-s.com ÷òîáû òâîå íàïîìèíàíèå îòîáðàçèëîñü è ó ðåáÿò, êîòîðûå íåïîñðåäñòâåííî ñîçâàíèâàþòñÿ ñ êëèåíòàìè.")
                            .respond();
                    break;

                case "counIWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Êàæäûé ìåíåäæåð ëèäîãåíåðàöèè ðàáîòàåò ñ îïðåäåëåííîé ñòðàíîé*. Ò.å. çà òîáîé óæå çàêðåïëåíà îïðåäåëåííàÿ ñòðàíà, â êîòîðîé òåáå íóæíî èñêàòü ëèäîâ.\n" +
                                         "\n" +"Äëÿ òîãî, ÷òîáû òû íà÷àë ïîèñê ëèäîâ, íóæíî óçíàòü, ñ êàêîé ñòðàíîé òû ðàáîòàåøü íà îïðåäåëåííîì àêêàóíòå. Äëÿ ýòîãî:\n" +
                                         "\n" +"- çàéäè â ñâîþ ÑÐÌ\n" +
                                         "\n" +"- íàæìè êíîïêó *Add new lead*\n" +
                                         "- *âûáåðè Ëèíêåäèí-àêêàóíò, êîòîðûé çàêðåïëåí çà òîáîé* (àêêàóíò, êîòîðûé òåáå âûäàë àêêàóíò-ìåíåäæåð ñïåöèàëüíî äëÿ ðàáîòû)\n" +
                                         "- *â ÑÐÌ â ïîëå \"Country\" ïîÿâèòñÿ íàçâàíèå ñòðàíû, ñ êîòîðîé òåáå íóæíî ðàáîòàòü*\n" +
                                         "Ýòî ïîëå íåëüçÿ îòðåäàêòèðîâàòü. Åãî íàñòðàèâàåò àêêàóíò-ìåíåäæåð.")
                            .respond();
                    break;

                case "counCanChange":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Òâîé àêêàóíò Ëèíêåäèí çàêðåïëåí çà îïðåäåëåííîé ñòðàíîé. Ýòè äàííûå ìîæåò çàìåíèòü òîëüêî àêêàóíò-ìåíåäæåð.")
                            .respond();
                    break;

                case "counNotWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ñòðàíû, êîòîðûå *ìàëî èíòåðåñíû*\n" +
                                    "_Japan, Singapore, Cyprus, China_. Òàê êàê êîììóíèêàöèÿ ñ àçèàòàìè òðóäíîâàòà èç-çà ïëîõîãî àíãëèéñêîãî, ðàññìàòðèâàåì êàæäûé ñëó÷àé îòäåëüíî.\n" +
                                    "\n" +"Ñòðàíû, ñ êîòîðûìè *íå ðàáîòàåì*\n" +"Andorra, Argentina, Bahamas, Belarus, Bolivia, Brazil, Brunei, Bulgaria, Chile, Colombia, Costa, Rica, Dominican Republic, Ecuador, Egypt, Fiji, Guyana, Indonesia, Kazakhstan, Macao, Malaysia, Mexico, Morocco, Nepal, Oman, Panama, Paraguay, Peru, Philippines, Puerto, Rico, Qatar, Republic of Korea (South), Romania, Russian Federation, Saudi Arabia, South, Africa, Thailand, Turkey, Ukraine, " +
                                    "United Arab Emirates, Uruguay, Vanuatu, Albania, Algeria, Angola, Armenia, " + "Azerbaijan, Bahrain, Bangladesh, Barbados, Belize, Benin, Botswana, Burkina, Faso, Burundi, Cambodia, Cameroon," +
                                    " Cape Verde, Chad, Comoros, Congo, El Salvador, Ethiopia, Gabon, Georgia, Guatemala, Guinea, Haiti, Honduras, " +
                                    "India, Iraq, Jamaica, Jordan, Kenya, Kuwait, Kyrgyzstan, Laos, Lebanon, Lesotho, Macedonia, Madagascar, Mali, Mauritania, Mauritius, Moldova, Mongolia, Mozambique, Namibia, Nicaragua, Niger, Nigeria, Pakistan, Senegal, Sri Lanka, Suriname, Swaziland, Tajikistan, Tanzania, Togo, Trinidad and Tobago, Tunisia, Turkmenistan, Uganda, Uzbekistan, Vietnam, Zambia.\n" +
                                    "\n" +"Ñòðàíû ÑÍÃ  Óêðàèíà, Ðîññèÿ, Áåëàðóñü, Ìîëäîâà, Êàçàõñòàí, Ãðóçèÿ, Àçåðáàéäæàí, Àðìåíèÿ, Óçáåêèñòàí, Òàäæèêèñòàí.\n" +
                                    "Ïðàêòè÷åñêè âñå ñòðàíû Àôðèêè è íåêîòîðûå ñòðàíû Àçèè  òàêæå ÿâëÿþòñÿ íåàêòóàëüíûìè äëÿ íàøåãî áèçíåñà.")
                            .respond();

                    break;

                case "counWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ñòðàíû, ñ êîòîðûìè ìû ðàáîòàåì:\n" +"_Australia, Austria, Belgium, Canada, Denmark, Finland, France, Germany, Ireland, Italy, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, United States of America, Israel, Latvia, Lithuania, Estonia, Czech Republic_")
                            .respond();

                    break;

                //ÒÓò íà÷èíàþòñÿ Ñòàòóñû

                case "statEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ñòàòóñ ëèäà â ÑÐÌ ïðè íàçíà÷åíèè èâåíòà - *Event*")
                            .respond();
                    break;

                case "statUpdate":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Åñëè òû èùåøü ëèäîâ â ÑÐÌ äëÿ àïäåéòà, òî â Lead Status âûáåðè âñå ñòàòóñû, êðîìå Call.\n" +
                                    "\n" +"Åñëè óæå ñäåëàë àïäåéò, òî â êàðòå ëèäà, â ïîëå Lead Status ïîñòàâü - *Sent Request*.")
                            .respond();
                    break;

                case "statCrm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*sent request* - îòïðàâëåíà çàÿâêà íà êîííåêò\n" +
                                    "\n" +"*connected* - ëèä ïðèíÿë òâîþ çàÿâêó íà êîííåêò, è ó òåáÿ åñòü åãî êîíòàêòû (ïî÷òà è òåëåôîí)\n" +
                                    "\n" +"*interested* - ëèä îòâåòèë íà òâîå ñîîáùåíèå è çàèíòåðåñîâàëñÿ íàøèì ïðåäëîæåíèåì\n" +
                                    "\n" +"*not interested* - ëèä îòâåòèë, ÷òî íàøå ïðåäëîæåíèå åãî íå èíòåðåñóåò\n" +
                                    "\n" +"*ignoring* - ëèä îáùàëñÿ ñ òîáîé íåêîòîðîå âðåìÿ, à ïîòîì ïåðåñòàë îòâå÷àòü íà ñîîáùåíèÿ\n" +
                                    "\n" +"*follow up* - ëèä îòâåòèë, ÷òî â äàííûé ìîìåíò íå çàèíòåðåñîâàí â íàøåì ïðåäëîæåíèè, íî âîçìîæíî ðàññìîòðèò åãî â áóäóùåì\n" +
                                    "\n" +"*event* - ëèä ñîãëàñèëñÿ íà çâîíîê è ìû äåëàåì èâåíò â êàëåíäàðå\n" +
                                    "\n" +"*call* - êîãäà ñåéëç ìåíåäæåð ñîîáùèë òåáå, ÷òî çâîíîê ñîñòîÿëñÿ\n" +
                                    "\n" +"*not relevant for us* - êîãäà ñëó÷àéíî äîáàâèë â ÑÐÌ ëèäà, êîòîðûé íàì íå ïîäõîäèò")
                            .respond();
                    break;

                //Òóò íà÷èíàåòñÿ "Åù¸"
                     case "menu":
                     case "moreBack":
                     case "employeeOld":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Ïðèâåò!\n" +
                                    "Òû íàõîäèøüñÿ â ìåíþ, â êîòîðîì ñîáðàíû îòâåòû íà ïîïóëÿðíûå âîïðîñû ïî ðàáîòå îòäåëà LeadGeneration.\n" +
                                    "\n" +
                                    "Âûáåðè êàòåãîðèþ ñâîåãî âîïðîñà:")
                            .addComponents(
                                    ActionRow.of(Button.primary("google", "Ãóãë"),
                                            Button.primary("linked", "Ëèíêåäèí"),
                                            Button.primary("CRM", "CPM")),
                                    ActionRow.of(Button.primary("calendar", "Êàëåíäàðü"),
                                            Button.primary("update", "Àïäåéò"),
                                            Button.primary("timetrack", "Òàéì-òðåêêåð")),
                                    ActionRow.of(Button.primary("contacts", "Êîíòàêòû"),
                                            Button.primary("bonus", "Áîíóñû è ÇÏ"),
                                            Button.primary("leads","Ëèäû")),
                                    ActionRow.of(Button.primary("blueprints","Øàáëîíû"),
                                            Button.primary("connect","Êîííåêò"),
                                            Button.primary("followup","Ôîëëîóàï")),
                                    ActionRow.of(Button.primary("country","Ñòðàíà"),
                                            Button.primary("status","Ñòàòóñ"),
                                            Button.primary("more","Åù¸")))
                            .respond();
                    break;

                case "menu2":
                        FeedbackResponder.modalResponder(stage, step2MessageCompInteraction);
                    break;
                default:
                    System.out.println("asd");
                    if (SHOULDSEND)
                    { // TODO: ïåðåèìåíîâàòü â ãëàâíîå ìåíþ è "íàçàä"
                    step2MessageCompInteraction.createFollowupMessageBuilder().addComponents(
                            ActionRow.of(Button.primary("menu", "Îáðàòíî â ãëàâ. ìåíþ"), Button.primary("menu2", "Îáðàòíî â ïðåä. ìåíþ"))).send()
                    ;}
                }
                });

        api.addMessageComponentCreateListener(event ->
                {
                    MessageComponentInteraction feedBackloop = event.getMessageComponentInteraction();
                    String feedBackloopCustomId = feedBackloop.getCustomId();

                    sqlitecontroller.insert(feedBackloop);

                    switch (feedBackloopCustomId)
                    {

                        case "feedback":

                            SHOULDSEND = false;
                            feedBackloop.respondWithModal("feedbackResponseModal","Âàø îòçûâ:",ActionRow.of(TextInput.create(TextInputStyle.SHORT, "feedbackResponse", "Îòçûâ")));


                            break;

                        case "feedback1":
                            SHOULDSEND = false;
                            nameRecord = true;

                            String modalTest = "Äîáðî ïîæàëîâàòü â êîìïàíèþ!";


                            feedBackloop.respondWithModal("mFeedback", modalTest,ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mName", "Èìÿ")),ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mSure", "Ôàìèëèÿ")), ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mPhone", "Òåëåôîí")));

                            /*
                            feedBackloop.createImmediateResponder()
                                    .setContent("Åñëè åñòü ïðîáëåìû/âîïðîñû/ïðåäëîæåíèÿ")
                                    .addComponents(ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                                    .respond();

                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Ïðîäîëæàåì")
                                    .addComponents(
                                            ActionRow.of(Button.primary("employeeNew","Íîâûé ñîòðóäíèê")),
                                            ActionRow.of(Button.primary("employeeOld","Óæå ðàáîòàåøü ñ íàìè")),
                                            ActionRow.of(Button.primary("feedback", "Âåðíóòüñÿ íàçàä"))) */
                            break;





                        case "employeeNew":
                            SHOULDSEND = false;
                            feedBackloop.createImmediateResponder().setContent("Òû ïîäïèñàë *äîãîâîð è çàïîëíèë êàðòó ñîòðóäíèêà*?")
                                    .addComponents(ActionRow.of(Button.primary("empnew1", "Ñäåëàíî! ×òî äàëüøå?")),
                                            ActionRow.of(Button.primary("notyet1", "Åù¸ íåò.")))
                                    .respond();

                            break;

                        case "notyet1":
                            SHOULDSEND = false;
                            feedBackloop.createImmediateResponder()
                                    .setContent("Ïîæàëóéñòà, íàïèøè ðåêðóòåðó, êîòîðûé ñ òîáîé îáùàëñÿ.")
                                    .addComponents(ActionRow.of(Button.primary("empnew1","Äàëåå")))
                                    .respond();

                            break;

                        case "empnew1":
                            SHOULDSEND = false;
                            feedBackloop.createImmediateResponder()
                                    .setContent("Òû óæå ñîçäàë *Ãóãë-ïî÷òó*?")
                                    .addComponents(ActionRow.of(Button.primary("empnew2", "Ñäåëàíî! ×òî äàëüøå?")),
                                            ActionRow.of(Button.primary("notyet2", "Åù¸ íåò. Ïîêàæè êàê")))
                                    .respond();
                            break;

                        case "notyet2":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Îêåé. ß ïîìîãó òåáå ýòî ñäåëàòü." + "\n" + "*Çàéäè íà Gmail è íàæìè \"Ñîçäàòü àêêàóíò\"*" + "\n" + "Äàëåå ñëåäóé èíñòðóêöèè Gmail...")
                                    .addComponents(ActionRow.of(Button.primary("notyet2_continue", "×òî äàëüøå?")))
                                    .append("https://www.google.com/intl/uk/gmail/about/")
                                    .respond();
                            break;

                        case "notyet2_continue":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Çàïðîñ íà çàêðåïëåíèå íîìåðà òåëåôîíà ê àêêàóíòó - íàæìè \"Ïðîïóñòèòü\""+ "\n" + "*Íîìåð òåëåôîíà íå óêàçûâàé!*\n" +
                                            "\n" + "Êàê ðåçåðâíóþ ïî÷òó, ìîæåøü óêàçàòü - niko@rh-s.com\n" +
                                            "\n" + "Óêàæè ñâîþ äàòó ðîæäåíèÿ è ñâîé ïîë." +"\n"+ "*Ïðèíèìàé ïðàâèëà Google*, ïðîëèñòûâàé èõ äî êîíöà.\n" +
                                            "\n" +"https://youtu.be/7rVH13AHp5o")
                                    .addComponents(ActionRow.of(Button.primary("empnew2", "Êðóòî! ×òî äàëüøå?")),
                                            ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                                    .respond();
                            break;

                        case "empnew2":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Òû óæå íàñòðîèë Õðîì-ïîëüçîâàòåëÿ?")
                                    .addComponents(ActionRow.of(Button.primary("empnew3", "Ñäåëàíî, ×òî äàëüøå?")),
                                            ActionRow.of(Button.primary("notyet3", "Íåò, ïîêàæè êàê.")))
                                    .respond();
                            break;

                        case "notyet3":

                            feedBackloop.createImmediateResponder()
                                    .setContent("ß ïîìîãó òåáå ýòî ñäåëàòü.\n" + "*Îòêðîé áðàóçåð Ãóãë Õðîì*. Íàæìè íà îêîøêî ïîëüçîâàòåëÿ â âåðõíåì ïðàâîì óãëó. Êëèêàé *Óïðàâëÿòü ïîëüçîâàòåëÿìè*.\n")
                                    .respond();
                            feedBackloop.createFollowupMessageBuilder()
                                    .addAttachment(new File(path+ "/chromeuser1.png"))
                                    .send().join();



                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Âûáåðè *Äîáàâèòü ïîëüçîâàòåëÿ* â íèæíåì ïðàâîì óãëó.\n" +
                                            "Íàçîâè ñâîåãî ïîëüçîâàòåëÿ òàê *ÈÌß_LinkedIn*.\n" + "Äîáàâü àâàòàðêó.\n" +
                                            "Ïîñòàâü ãàëî÷êó *Ñîçäàòü ÿðëûê ýòîãî ïðîôèëÿ íà ðàáî÷åì ñòîëå*.\n" + "Íàæìè *Äîáàâèòü*.")

                                    .addAttachment(new File(path+ "/chromeuser3.png"))
                                    .send().join();
                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Ïîäêëþ÷è ðàáî÷óþ ïî÷òó, êîòîðóþ òû ñîçäàë, ê Õðîì-ïîëüçîâàòåëþ. \n" + "Âóàëÿ! Íîâûé ïîëüçîâàòåëü ÃóãëÕðîì ãîòîâ.\n" +
                                            "\n" + "_Àâòîìàòè÷åñêè îòêðîåòñÿ íîâàÿ âêëàäêà Õðîìà ïîä íîâûì ïîëüçîâàòåëåì_.\n" +
                                            "\n" + "_Íà ðàáî÷åì ñòîëå ïîÿâèëñÿ ÿðëûê äëÿ âõîäà èìåííî â ýòîãî ïîëüçîâàòåëÿ_.")
                                    .addComponents(ActionRow.of(Button.primary("empnew3", "Êðóòî! ×òî äàëüøå?")),
                                    ActionRow.of(Button.primary("feedback", "Îáðàòíàÿ ñâÿçü")))
                                    .send();
                            break;


                        case"empnew3":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Ìîëîäåö! \n" +"\n" +"À *Ëèíêåäèí-àêêàóíò* óæå ñäåëàë?")
                                    .addComponents(ActionRow.of(Button.primary("empnew4", "Êðóòî! ×òî äàëüøå?")),
                                            ActionRow.of(Button.primary("notyet4", "Åù¸ íåò, ïîêàæè êàê.")))
                                    .respond();
                            break;

                        case"notyet4":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Îêåé, ÿ ïîìîãó òåáå ñîçäàòü àêêàóíò íà Ëèíêåäèí \n" +
                                            "\n" + "_Íàæìè íà êíîïêó_")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_1","Øàã Ïåðâûé")))
                                    .respond();
                            break;

                        case "notyet4_1":
                            feedBackloop.createImmediateResponder()
                                    .setContent("1) Çàéäè íà LinkedIn.\n" +
                                            "\n" +"2) Íà÷íè ðåãèñòðàöèþ.\n" +
                                            "\n" +"3) *Çàðåãèñòðèðóé ïðîôèëü Ëèíêåäèí íà Ãóãë-ïî÷òó, êîòîðóþ òû ñîçäàë ðàíåå*.\n" +
                                            "ÂÀÆÍÎ: *âñÿ èíôîðìàöèÿ â òâîåì ïðîôèëå äîëæíà áûòü íà àíãëèéñêîì* òàê, êàê âñÿ ïåðåïèñêà ñ ëèäàìè âåäåòñÿ èìåííî íà ýòîì ÿçûêå.\n" +
                                            "\n" +"https://youtu.be/SlvxQQTFFxg"+ "\n" +"\n"+ "Ãîòîâ?\n" +
                                            "Ïåðåõîäè êî âòîðîìó øàãó")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_2","Øàã Âòîðîé")))
                                    .respond();
                            break;

                        case "notyet4_2":

                            feedBackloop.createImmediateResponder()
                                    .setContent("*Óêàæè ñâîè íàñòîÿùèå èìÿ è ôàìèëèþ íà àíãëèéñêîì ÿçûêå*.\n" +
                                            "Çàïðåùåíî: óêàçûâàòü íèêíåéìû èëè ñîêðàùåíèÿ èìåíè.\n" +
                                            "\n" +
                                            "*Çàãðóçè ñâîå ôîòî* \n" +
                                            "Ïàðàìåòðû ôîòî: ïîðòðåò, áåç ëèøíåãî ôîíà, æåëàòåëüíî áîëåå îôèöèàëüíîå.\n" +
                                            "\n" +"*Ïåðåâåäè ïðîôèëü íà àíãëèéñêèé ÿçûê*\n" +
                                            "Êàê ýòî ñäåëàòü:\n" +
                                            "- â ïðàâîì âåðõíåì óãëó òû âèäèøü ñâîå ôîòî\n" +
                                            "- íàæìè íà ñòðåëî÷êó ïîä ôîòî\n" +
                                            "- îòêðîåòñÿ ìåíþ\n" +
                                            "- âûáåðè \"ßçûê/Language\"\n" +
                                            "\n" +"Åñëè ãîòîâ, ïåðåõîäè ê òðåòüåìó øàãó")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_3","Øàã Òðåòèé")))
                                    .respond();
                            break;

                        case "notyet4_3":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Ïîëå *Îáðàçîâàíèå/Education*\n" +
                                            "Óêàæè àêòóàëüíûå äàííûå î ñâîåì âûñøåì îáðàçîâàíèè íà àíãëèéñêîì ÿçûêå.\n" +
                                            "\n" +"Åñëè òû åùå ñòóäåíò, â ïîëå \"Äàòà îêîí÷àíèÿ\" óêàæè 2020 ãîä èëè áîëåå ðàííèé, à â ïîëå \"Äàòà íà÷àëà\" - ãîä íà 4-5 ëåò ðàíüøå.\n" +
                                            "\n" +"Ãîòîâ? Æìè êíîïêó\n")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_4","Øàã ×åòâ¸ðòûé")))
                                    .respond();
                            break;

                        case "notyet4_4":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Ïîëå *Îïûò ðàáîòû/Edit experience*\n" +
                                            "\n" +
                                            "*Çàïîëíè â òî÷íîñòè ïî èíñòðóêöèè*\n" +
                                            "\n" +
                                            "- äîëæíîñòü: Account manager\n" +
                                            "- ãðàôèê ðàáîòû: Full-time\n" +
                                            "- êîìïàíèÿ: Remote Helpers\n" +
                                            "- ëîêàöèÿ: òâîå òåêóùåå ìåñòîïîëîæåíèå (Ãîðîä, Óêðàèíà)\n" +
                                            "- âðåìÿ ðàáîòû â êîìïàíèè: ëþáîé ìåñÿö/ãîä äî íàñòîÿùåãî âðåìåíè + ïîñòàâü ãàëî÷êó to present.\n" +
                                            "\n" +"Ãîòîâî? Æìè êíîïêó")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_5","Øàã Ïÿòûé")))
                                    .respond();
                            break;

                        case "notyet4_5":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Ïîëå *Íàâûêè/Skills*\n" +
                                            "*Óêàçûâàé ñêèëëû íà àíãëèéñêîì ÿçûêå*.\n" +
                                            "\n" +"Ñïèñîê òâîèõ âîçìîæíûõ ñêèëëîâ:\n" +
                                            "- Email marketing\n" +"- Lead Generation\n" +
                                            "- Social media marketing\n" +"- Online Advertising\n" +"- Data Analysis\n" +
                                            "- Searching skills\n" +"- Targeting\n" +"- WordPress\n"
                                            +"- English language\n" +"- Design\n" +"- è äðóãèå íàâûêè èç âûïàäàþùåãî ñïèñêà.\n" +
                                            "\n" +"*Èñïîëüçóé øòóê 5-8*\n" +
                                            "\n" +"Ãîòîâî? Æìè êíîïêó")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_6","Øàã Øåñòîé")))
                                    .respond();
                            break;

                        case "notyet4_6":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Çàìåíè *ñòàòóñ ïðîôèëÿ*.\n" +
                                            "Êàê ýòî ñäåëàòü:\n" +
                                            "- îòêðîé ñâîé ïðîôèëü\n" +"- íàæìè íà êàðàíäàøèê ñïðàâà îò ôîòî ïðîôèëÿ\n" +
                                            "- çàìåíè ïîëå *Headline/Ñòàòóñ*\n" +"\n" +"Âàðèàíòû çàìåíû:\n" +
                                            "\n" +"_Hire online full-time remote employees| Marketing| Content Managers| SMM| Designers| Devs_\n" +
                                            "\n" +"_Dedicated virtual assistants in Ukraine: Lead Generation| SMM| Media| Design| Developers_\n" +
                                            "\n" +"_Build your online team in few clicks| Lead Generation| Marketing| Media| Design| Devs_\n" +
                                            "\n" +"Òåáå íå îáÿçàòåëüíî êîïèðîâàòü ñëîâî â ñëîâî. Îñíîâíîé ïîñûë: ìû ïðåäëàãàåì êëèåíòàì ðàñøèðèòü èõ êîìàíäó, íàíÿâ óäàëåííûõ ñîòðóäíèêîâ èç Óêðàèíû, ñïåöèàëüíîñòè âèäèòå âûøå.\n" +
                                            "\n" +"Ãîòîâî? Æìè êíîïêó")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_7","Øàã Ñåäüìîé")))
                                    .respond();
                            break;


                        case "notyet4_7":

                            feedBackloop.createImmediateResponder()
                                    .setContent("*Êðàñèâàÿ ññûëêà íà òâîé ïðîôèëü*\n" +
                                            "\n" +"Êàê ýòî ñäåëàòü:\n" +"- çàéäè íà ñâîþ ñòðàíèöó\n" +
                                            "- _â ïðàâîì âåðõíåì óãëó íàâåäè ìûøêó íà ñâîå ôîòî, îòêðîåòñÿ âûïàäàþùèé ñïèñîê_\n" +
                                            "- âûáåðè íà íåì _View profile_\n" +
                                            "- íà îòêðûâøåéñÿ ñòðàíèöå â ïðàâîì âåðõíåì óãëó íàæèìàåì _Edit public profile & URL_\n" +
                                            "- ñíîâà â ïðàâîì âåðõíåì óãëó íàæìè íà _Edit your custom URL_, âíèçó áóäåò òâîÿ ññûëêà è çíà÷îê êàðàíäàøà\n" +
                                            "- íàæìè íà êàðàíäàøèê è óäàëè âñå íåíóæíûå öèôðû è ñèìâîëû. Îñòàâü òîëüêî ñâîå èìÿ è ôàìèëèþ.\n" +
                                            "- íàæìè _Save_.\n" +"\n" +"URL îáíîâèòñÿ â òå÷åíèè íåñêîëüêèõ ìèíóò.\n" +
                                            "\n" +"Ãîòîâî? Æìè êíîïêó")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_8","Øàã Âîñüìîé")))
                                    .respond();

                            break;
                    //https://www.linkedin.com/search/results/people/?currentCompany=%5B%2210127817%22%5D&origin=COMPANY_PAGE_CANNED_SEARCH&sid=cst
                        case "notyet4_8":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Ðàñøèðü ñåòü ñâîèõ êîíòàêòîâ. Äîáàâü â äðóçüÿ ñâîèõ êîëëåã.\n" +
                                            "\n" +
                                            "*Âàðèàíò 1 - çàéäè íà ñòðàíèöó êîìïàíèè Remote Helpers â Ëèíêåäèí, â ðàçäåë ñîòðóäíèêè*\n" +
                                             "https://www.linkedin.com/search/results/people/?currentCompany=%5B%2210127817%22%5D&origin=COMPANY_PAGE_CANNED_SEARCH&sid=cst" + "\n"+
                                            "*Âàðèàíò 2 - èñïîëüçóé ïîèñê â Ãóãë*\n" +
                                            "https://www.google.com/search?q=site%3Alinkedin.com+remote+helpers&oq=site%3Alinkedin.com+remote+helpers")

                                    .respond();

                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Âóàëÿ! Ïðîôèëü â Ëèíêåäèí ãîòîâ!")
                                    .addComponents(ActionRow.of(Button.primary("empnew4", "Äàëåå! ×òî äàëüøå?")))
                                    .send().join();
                            break;



                        case "empnew4":
                            nameRecord = true;

                            feedBackloop.respondWithModal("accountEnter", "Îòïðàâü äîñòóïû ê Ãóãë è ËèíêåäÈí",ActionRow.of(TextInput.create(TextInputStyle.SHORT,"gName","Ãóãë ìåèë")),ActionRow.of(TextInput.create(TextInputStyle.SHORT,"gPass","Ãóãë ïàðîëü")),ActionRow.of(TextInput.create(TextInputStyle.SHORT,"lName","Linked In ìåèë:")), ActionRow.of(TextInput.create(TextInputStyle.SHORT,"lPass","LinkedIn ïàðîëü")));

                            break;

                    }


                    api.addModalSubmitListener(modalEvent ->
                            {

                                ModalInteraction modalInteraction = modalEvent.getModalInteraction();
                                String modalInteractionCustomId = modalInteraction.getCustomId();

                                if(Objects.equals(modalInteractionCustomId, "mFeedback"))
                                {
                                    SHOULDSEND = false;
                                    modalInteraction.createImmediateResponder().setContent("Ïðîäîëæàåì")
                                            .addComponents(
                                                    ActionRow.of(Button.primary("employeeNew","Íîâûé ñîòðóäíèê")),
                                                    ActionRow.of(Button.primary("employeeOld","Óæå ðàáîòàåøü ñ íàìè")),
                                                    ActionRow.of(Button.primary("feedback", "Âåðíóòüñÿ íàçàä")))
                                            .respond();

                                    User user = modalInteraction.getUser();
                                    String userId = user.getIdAsString();
                                    String userName = user.getDiscriminatedName();

                                    String modalName = modalInteraction.getTextInputValueByCustomId("mName").get();
                                    String modalSure = modalInteraction.getTextInputValueByCustomId("mSure").get();
                                    String modalPhone = modalInteraction.getTextInputValueByCustomId("mPhone").get();

                                    System.out.println(userName);

                                    if (modalName.equals("") || modalSure.equals("") || modalPhone.equals(""))
                                    {
                                        System.out.println("ERROR");
                                        modalInteraction.createImmediateResponder().setContent("Ó âàñ îøèáêà!"+"\n"+ " Ïîæàëóéñòà, íàæìèòå íà ïðåäûäóùóþ êíîïêó è ïîëíîñòüþ çàïîëíèòå ïîëÿ.").respond();
                                    }

                                    else
                                    {

                                        try
                                        {
                                            NameListner.wright(service, nameRecord,spreadsheetId, userId, userName, modalPhone, modalName, modalSure);
                                            //check(service, userId, userName, modalPhone, modalName, modalSure);
                                        } catch (IOException | GeneralSecurityException e)
                                        {
                                            throw new RuntimeException(e);
                                        }

                                    }


                                }

                                if (Objects.equals(modalInteractionCustomId, "accountEnter"))
                                {
                                    User user = modalInteraction.getUser();
                                    String userId = user.getIdAsString();

                                    String userName = user.getDiscriminatedName();

                                    String modalGName = modalInteraction.getTextInputValueByCustomId("gName").get();
                                    String modalGPass = modalInteraction.getTextInputValueByCustomId("gPass").get();
                                    String modalLName = modalInteraction.getTextInputValueByCustomId("lName").get();
                                    String modalLPass = modalInteraction.getTextInputValueByCustomId("lPass").get();

                                    if (modalGName.equals("") || modalGPass.equals("") || modalLName.equals("") || modalLPass.equals(""))
                                    {
                                        System.out.println("ERROR");
                                        modalInteraction.createImmediateResponder().setContent("Ó âàñ îøèáêà!"+"\n"+ " Ïîæàëóéñòà, íàæìèòå íà ïðåäûäóùóþ êíîïêó è ïîëíîñòüþ çàïîëíèòå ïîëÿ.").respond();
                                    }

                                    else
                                    {

                                        try
                                        {
                                            check(service, userName, modalGName, modalGPass, modalLName, modalLPass);
                                        } catch (IOException e)
                                        {
                                            throw new RuntimeException(e);
                                        } catch (GeneralSecurityException e)
                                        {
                                            throw new RuntimeException(e);
                                        }

                                        modalInteraction.createImmediateResponder().setContent("Ïðîäîëæàåì")
                                                .addComponents(
                                                        ActionRow.of(Button.primary("employeeNew","Íîâûé ñîòðóäíèê")),
                                                        ActionRow.of(Button.primary("employeeOld","Óæå ðàáîòàåøü ñ íàìè")),
                                                        ActionRow.of(Button.primary("feedback", "Âåðíóòüñÿ íàçàä")))
                                                .respond();
                                    }
                                }

                                if (Objects.equals(modalInteractionCustomId, "feedbackResponseModal"))
                                {
                                    User user = modalInteraction.getUser();
                                    String userId = user.getIdAsString();
                                    String userName = user.getDiscriminatedName();

                                    String userFeedback = modalInteraction.getTextInputValueByCustomId("feedbackResponse").get();

                                    System.out.println(userFeedback);
                                        try
                                        {
                                            NameListner.wrightFeedback(service,spreadsheetId, userName, userFeedback);
                                        } catch (GoogleJsonResponseException e)
                                        {
                                            throw new RuntimeException(e);
                                        }
                                    System.out.println(stage);
                                    //modalInteraction.createImmediateResponder().setContent("asd").respond();
                                    //FeedbackResponder.modalResponder(stage, modalInteraction);
                                    modalInteraction.createImmediateResponder().setContent("").respond();
                                    modalInteraction.createFollowupMessageBuilder().setContent("Ñïàñèáî çà Âàø îòçûâ! Ïîñëå ðàññìîòðåíèÿ, ìû ñâÿæåìñÿ ñ Âàìè!").send();
                                }


                            });
                });
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());


        //TODO: Áîò, ïðîâåðÿþùèé ïðèñóòñòâèå ÷åëîâåêà îí-ëàéí, ÷åðåç ëè÷êó, ñ òàéìåðîì â 5 ìèíóò. Åñëè íå îòìåòèëñÿ â äèñêîðäå, òî îòâåò â âàéáåð

    }

    public static void check(Sheets service, String discordId, String discordMame, String number, String name, String surename) throws IOException, GeneralSecurityException
    {
       //MessageCreateEvent event,
        // String name =event.getMessageAuthor().getDiscriminatedName();

        if (nameRecord) //
        {
            AppendValuesResponse result;
            try
            {
                List<List<Object>> values;

                ValueRange appendValue = new ValueRange()
                        .setValues((Arrays.asList(Arrays.asList(discordId,discordMame, number, name, surename))));

                result = service.spreadsheets().values()
                        .append(spreadsheetId, "TEST2", appendValue)
                        .setValueInputOption("RAW")
                        .setInsertDataOption("INSERT_ROWS")
                        .execute();

            } catch (GoogleJsonResponseException e) {
                GoogleJsonError error = e.getDetails();
                if (error.getCode() == 404)
                {
                    System.out.println("sad");
                }else { throw e;}
            }
           // System.out.println(name);
        }

        nameRecord = false;
    }

}

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
    static String token = "OTg4NzQxNzc1NzA5NTkzNjYw.GgeQPP.AFPcZGJa1Dv3lq3eige7wqrHleAmo1PoS2yp_E";
    public static DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
    public static Boolean nameRecord = false;

    private static final String APPLICATION_NAME = "GPI Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    public static final String spreadsheetId = "1JEv29lEp3nXdE6l5gONynn5EvuUTD14kRXPUyynnJdA"; //1i4Lfj86Y4ONBb1wvosjpUbRerByuUiw0-aeHt_2KUkw 1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms


    public static int stage;


    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/creds.json";


    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
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
                        .setContent("������!\n" +
                                "�� ���������� � ����, � ������� ������� ������ �� ���������� ������� �� ������ ������ LeadGeneration.\n" +
                                "\n" +"������ ��������� ������ �������:")
                        .addComponents(
                                ActionRow.of(Button.primary("google", "����"),
                                        Button.primary("linked", "��������"),
                                        Button.primary("CRM", "CPM")),
                                ActionRow.of(Button.primary("calendar", "���������"),
                                        Button.primary("update", "������"),
                                        Button.primary("timetrack", "����-�������")),
                                ActionRow.of(Button.primary("contacts", "��������"),
                                        Button.primary("bonus", "������ � ��"),
                                        Button.primary("leads","����")),
                                ActionRow.of(Button.primary("blueprints","�������"),
                                        Button.primary("connect","�������"),
                                        Button.primary("followup","��������")),
                                ActionRow.of(Button.primary("country","������"),
                                        Button.primary("status","������"),
                                        Button.primary("more","���")))
                        .send(channel);

            }
            if (event.getMessageContent().equalsIgnoreCase("!start"))
            {

                new MessageBuilder()
                        .setContent("������������!")
                        .addComponents(ActionRow.of(Button.primary("feedback1", "�����?")))

                        .send(event.getChannel());

            }


        });
    }


    public static void main(String[] args) throws IOException, GeneralSecurityException
    {
        String path = (System.getProperty("user.dir")+"/resources");
        message(api);

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        // Add a listener which answers with "Pong!" if someone writes "!ping"

        //TODO: ������� ������ "������� � ����", � ������� ������


        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
            String customId = messageComponentInteraction.getCustomId();

            switch (customId) {
                case "google":

                    stage = 1;

                    FeedbackResponder.feedbackResponer(1,messageComponentInteraction);
                    /*messageComponentInteraction.createImmediateResponder()
                            .setContent("Google.com")
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                            .addComponents(
                                    ActionRow.of(Button.primary("googChromeUserCreate", "Google new User")),
                                    ActionRow.of(Button.primary("googleFindCol", "Find Colleagues")),
                                    ActionRow.of(Button.primary("googleCalendar", "Where is google calendar")),
                                    ActionRow.of(Button.primary("googleSearch", "Google search operators")),
                                    ActionRow.of(Button.primary("googleUsefulAddons", "A few useful add-ons")) //,
                             ).send();

                    messageComponentInteraction.createFollowupMessageBuilder()
                    .addComponents(
                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();*/

                    System.out.println(stage);
                    break;

                case "linked":
                    stage = 2;

                    FeedbackResponder.feedbackResponer(stage, messageComponentInteraction);

                    /*messageComponentInteraction.createImmediateResponder()
                            .setContent("Linked In")
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                            .addComponents(
                                    ActionRow.of(Button.primary("linkCleanOld", "������ ������ ������")),
                                    ActionRow.of(Button.primary("linkAccBan", "��� ��������")),
                                    ActionRow.of(Button.primary("linkAccLimits", "������ �� ��������")),
                                    ActionRow.of(Button.primary("linkAccCountry", "��� �������� ������")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();*/
                    break;
                case "CRM":
                    stage = 3;
                  messageComponentInteraction.createImmediateResponder()
                           .setContent("You selected CRM")
                           .respond();

                    messageComponentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                            .addComponents(
                                    ActionRow.of(Button.primary("crmEnter", "��� ����� � ���")),
                                    ActionRow.of(Button.primary("crmAccess", "��� �������� ������")),
                                    ActionRow.of(Button.primary("crmAcc", "������� ��� ������ � ���")),
                                    ActionRow.of(Button.primary("crmHowToAddLead", "��� �������� ����")),
                                    ActionRow.of(Button.primary("crmLeads", "������� Leads")))
                                    .send();

                    messageComponentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                            .addComponents(
                                    ActionRow.of(Button.primary("crmLeadReports", "������� Lead Reports")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();

                    break;
                case "calendar":
                    stage = 4;
                    messageComponentInteraction.createImmediateResponder().setContent("select")
                            .addComponents(
                                    ActionRow.of(Button.primary("calEnterMail", "����� ��� �����")),
                                    ActionRow.of(Button.primary("calLeadEvent", "����� � ��������� ����")),
                                    ActionRow.of(Button.primary("calNewEvent", "������ ����� � ���������")),
                                    ActionRow.of(Button.primary("calGet", "��� ����� ���������")),
                                    ActionRow.of(Button.primary("calFollowUp", "�����-�� � ���������")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                                    .addComponents(
                                            ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();
                    break;
                case "update":
                    stage = 5;
                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("updateWhatIs", "��� ����� ������")),
                                    ActionRow.of(Button.primary("updateWhy", "����� ����� ������")),
                                    ActionRow.of(Button.primary("updateHowFind", "��� ����� ��������")),
                                    ActionRow.of(Button.primary("updateHowMake", "��� ������� ������")),
                                    ActionRow.of(Button.primary("updateDayNorm", "����� �������� � ����")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();

                    break;
                case "timetrack":
                    stage = 6;
                    messageComponentInteraction.createImmediateResponder().setContent("Select")
                            .addComponents(
                                    ActionRow.of(Button.primary("timeTurnOn", "�������� ����-������")),
                                    ActionRow.of(Button.primary("timeGoogleSetting", "��������� Google Chrome")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();
                    break;
                case "contacts":
                    stage = 7;//TODO: �������� ���������� ������ �� �������-����
                    messageComponentInteraction.createImmediateResponder().setContent("select")
                            .setContent("���� (�������-��������)\n" +
                                    "+380981101090\n" +"\n" +"���� �������� (�����������)\n" +
                                    "+380714184225\n" +"\n" +"���� �������� (������ LeadGeneration)\n" +
                                    "+380662227034\n" +"\n" +"������� ������� (������ Sales)\n" +
                                    "+380506284605")
                            .addComponents(ActionRow.of(Button.primary("feedback", "�������� �����")))//TODO: �������� ���� ������.
                            .respond();
                    break;
                case "bonus":

                    stage = 8;

                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("bonusMy", "��� ������")),
                                    ActionRow.of(Button.primary("bonusWhen", "����� ��")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();

                    break;
                case "leads":
                    stage = 9;
                    messageComponentInteraction.createImmediateResponder().setContent("Select")
                            .addComponents(
                                    ActionRow.of(Button.primary("leadWhichLead", "����� ����� ����� ������")),
                                    ActionRow.of(Button.primary("leadInfoFrom", "������ �� ����")),
                                    ActionRow.of(Button.primary("leadAdd", "�������� ����")),
                                    ActionRow.of(Button.primary("leadCardFill", "��������� ����� ����")),
                                    ActionRow.of(Button.primary("leadWhoFollowUp", "���� ������ ������-��")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("leadRight", "���������� ����")),
                                    ActionRow.of(Button.primary("leadHowMuch", "������� ����� � ������")),
                                    ActionRow.of(Button.primary("leadDayNorm", "����� ����� � ����")),
                                    ActionRow.of(Button.primary("leadHowFind", "��� ����� ������� ����")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();
                    break;
                case "blueprints":
                    stage = 10;
                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("blueFirstCon", "1-�� ������� � �����")),
                                    ActionRow.of(Button.primary("blueSecondCon", "2-�� ������� � �����")),
                                    ActionRow.of(Button.primary("blueAbout", "� ��������������")),
                                    ActionRow.of(Button.primary("blueExp", "�� ����� �����������")),
                                    ActionRow.of(Button.primary("blueClientBad", "������ �� �������������")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("blueClientGood","������ �������������")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .send();

                    break;
                case "connect":
                    stage = 11;
                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("conFirstCon", "1-�� ������� � �����")),
                                    ActionRow.of(Button.primary("conSecondCon", "2-�� ������� � �����")),
                                    ActionRow.of(Button.primary("conToLead", "��������� ������� ����")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();
                    break;
                case "followup":
                    stage = 12;
                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("followupWhat", "��� ����� �����-��")),
                                    ActionRow.of(Button.primary("followupCal", "�����-�� � ���������")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();
                    break;
                case "country":
                    stage = 13;
                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("counIWork", "� ����� ������� � �������")),
                                    ActionRow.of(Button.primary("counCanChange", "����� �� �������� ������")),
                                    ActionRow.of(Button.primary("counNotWork", "������: �� ��������")),
                                    ActionRow.of(Button.primary("counWork", "������: ��������")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();
                    break;
                case "status":
                    stage = 14;
                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("statEvent", "������ � ���������� ������")),
                                    ActionRow.of(Button.primary("statUpdate", "������ ������")),
                                    ActionRow.of(Button.primary("statCrm", "���� �������� � ���")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();
                    break;
                case "more":
                    messageComponentInteraction.createImmediateResponder().setContent("\"��������� �� �����\" - ������ ������ � ����� � ������\n" +
                                    "\"��������� �����\" - � ���� � �������� �� �������\n" +"\"�������� �����\" - ���� �� �������� ����� �� ���� ������")
                            .addComponents(
                                    ActionRow.of(Button.primary("moreStart", "�������� �� �����")),
                                    ActionRow.of(Button.primary("moreBack", "��������� �����")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                            .respond();
                    break;

            }
        });



        String googleSearchBig = "��������� ������ � ����\n" + "\n" + "*����� �� ����������*. ��������:\n" + "\n" + "*site*: (����� �����) ������ (���������)\n" + "\n" + "*OR* : ������ site:linkedin.com CEO OR Founder OR Director\n" +
                "\n" +"*- (�����)* � : ������ site:linkedin.com CEO OR Founder OR Director -news -Institute -become -jobs -wewwork -amazon -netflix -million\n" +
                "\n" +"*����� �� ���������*. ��������:\n" + "\n" +"*� *(���� �������� ����� ������� 2-50) ������ site:linkedin.com Company size: 2-50 employees\n" +
                "\n" + "*AND* ( ��������� ��� ������� � ��������� ������ ) site:linkedin.com Founded: 2005..2019 AND Company size: 51-200 employees\n" + "\n" +"*��� (�������)* � �������� ��������, ������������ ��� ����, ����� ������ ������ �����, � �� ������ ����� �� �����������. ������ site:linkedin.com �Specialties: Email Marketing�\n" + "\n" + "* * (���������)* ������: site:linkedin.com �Specialties: Email Marketing�*\n" + "\n" + "*Headquarters*: (�������) ������: site:linkedin.com Founded: 2005..2019 AND Company size: 51-200 employees OR Headquarters: France";

        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction step2MessageCompInteraction = event.getMessageComponentInteraction();
            String ChosenInteraction = step2MessageCompInteraction.getCustomId();


            switch (ChosenInteraction)
            {//Google Responses________________________________________________________
                case "notyet3":
                case "googChromeUserCreate": //�������� ������ ������������ �����
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("� ������ ���� ��� �������.\n" + "*������ ������� ���� ����*. ����� �� ������ ������������ � ������� ������ ����. ������ *��������� ��������������*.\n")
                            .respond().join();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .addAttachment(new File(path+ "/chromeuser1.png"))
                            .send().join();



                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .append("������ *�������� ������������* � ������ ������ ����.\n" +
                                    "������ ������ ������������ ��� *���_LinkedIn*.\n" + "������ ��������.\n" +
                                    "������� ������� *������� ����� ����� ������� �� ������� �����*.\n" + "����� *��������*.")

                            .addAttachment(new File(path+ "/chromeuser3.png"))
                            .send().join();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .append("�������� ������� �����, ������� �� ������, � ����-������������. \n" + "�����! ����� ������������ �������� �����.\n" +
                                    "\n" + "_������������� ��������� ����� ������� ����� ��� ����� �������������_.\n" +
                                    "\n" + "_�� ������� ����� �������� ����� ��� ����� ������ � ����� ������������_.")
                            .send();

                    break;
                case "googleFindCol": //���������� ������
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� *����� ����� ������ � ������� ����-������ � �������� �� � ������ �� ��������*, � ������ ������ ���� ��������:\n" +
                                    "\n" +
                                    "site:linkedin.com remote helpers")
                            .respond();
                    break;
                case "googleCalendar":// ���� ���������
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("��� ����� *����-���������*:\n" + "\n" +"������ ����� ������� � �������� ����-����.\n" +
                                    "\n" +"*� ������ ������� ����, ����� �� ���� *(� ���� �����).\n" +
                                    "��������� ���� ���������� ����.\n" +"\n" +"*������ ���������*\n" +"\n" + "_���� ���� ����� ��������� �����, ������ ��������� � ������������� ����� info@rh-s.com_.")
                            .respond();
                    break;
                case "googleSearch"://��������� ������ � �����
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent(googleSearchBig).respond();
                    break;
                case "googleUsefulAddons": //"�������� ������

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("�������� *���������� � ����* ��� ������:" +"\n")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("\"https://chrome.google.com/webstore/detail/find-anyones-email-contac/jjdemeiffadmmjhkbbpglgnlgeafomjo" +"\n" +
                                    "https://chrome.google.com/webstore/detail/rocketreach-chrome-extens/oiecklaabeielolbliiddlbokpfnmhba"+ "\n" +
                                   "https://chrome.google.com/webstore/detail/free-vpn-for-chrome-vpn-p/majdfhpaihoncoakbjgbdhglocklcgno" + "\n")
                            .send();
                    break;


                //��� ���������� LinkedIN________________________________________________________
                case "linkCleanOld":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� �� ���� ������� ������� �� ��������.")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("������ ������� �� *������� My Network*, ������ ����� �� *������� Manage*.\n" +
                                    "���� ��������� ������� � ���������� � ��������� �������������.\n" +
                                    "\n" +"������� �� *������� Sent*. �� ������ ������ ������, ������� ���� ���������� �����.\n" +
                                    "*����� �� ������ Withdraw* � ����� ������ �������.\n" + "\n" +"����� ��� ������ ������� ���� �������� 2 � ����� ������ �����.���� �� ��� ����� ��� �� ������� �������, �� ����� ������ ���� �� ��� �� �������.\n" +
                                    "\n" +"������� ����� ������ �� ���� ����� ������� ���������.")
                            .send();
                    break;
                case "linkAccBan"://��� �������� � ������ ��________________________________________________________

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*��� �������� ��������� � ��� �� ��������*")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("1) ����� *����� ����� �������������� ����������* (�������, ����������� ����, �������� � �.�.);\n" + "\n" +
                                    "2) C 10-� ���������� �� ������� � ������ � ����������;\n" + "\n"+
                                    "3) *�� �������, ����� ���� ����������� �����������*. ������� ������ ���� �����, ���� � ��������� � ������� �����.\n" + "\n"+
                                    "4) ������������ *��������� ������ ���� ����� ��� ����� � ���� ��������*.\n" + "\n"+
                                    "5) *������� ������ �� �����*. ��������, ����� 10-15 ���������� ����� (������ 30 ������), ����� �������������� �� �������� ����� �� �����. � ��� ��������� ��������.\n" + "\n"+
                                    "6) � ������ ���� �������. ����������� ������� ������� �����������, ����, �������. ��� ���������� �������� ������������� ��������, ������� ���� ������� ��������� � �����. ������� ������� �� ��������, ��� ����� ���-�� �� ���. ����� ����� ������ ������� �� ������ ����� ��������, ������ ����� ���������")
                            .send();
                    break;
                case "linkAccLimits": // ����� �� ������� � ������ ��
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� �������� *����� �� ��������* ��������, *�������� � �������-���������*")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("����\n" + "+380981101090\n")
                            .send();
                    break;

                case "linkAccCountry": //����� ������ �������� �� ����� ��
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("_���� ������� �������� ��������� �� ������������ �������. ��� ������ ����� �������� ������ �������-��������_\n")
                            .respond();
                    break;

                case "linkFindLeads": //���������� �����
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*�����* �� ���� �������� � ��������, ����� *�� ������� My Network*, ������������� �� ������� ������ �����.\n" +
                                    "\n" + "����� �������� �� ������� ��� ������� (connections), ������� ����� ���� ���� �������.")
                            .respond();
                    break;

                //��� ���������� ���________________________________________________________

                case "crmEnter":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� � ���." +"\n" + "https://crm.rh-s.com/" + "\n" +"��������� �������, ������� ����� ���� ��� �������-��������.")
                            .respond();
                    break;
                case "crmAccess":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����" + "\n" + "+380981101090" + "\n"+ "������� � ��� � ������� ��������� ������ �������-��������. ������ ���.")
                            .respond();
                case "crmAcc":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� � ���� ���.\n" +"\n" +"����� ������� *Add new lead*\n" +
                                    "\n" + "*������ ��������-�������, ������� ��������� �� �����* (�������, ������� ���� ����� �������-�������� ���������� ��� ������)")
                            .respond();
                    break;
                case "crmHowToAddLead":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� *�������� ���� � ���*:\n" +
                                    "1) ������ ���\n" +
                                    "2) ������� �� *������� Leads*, � ������ ������� ���� ����� �� *������ Add new lead*.\n" +
                                    "3) ������� ����������� ���������� � ����.\n" +
                                    "���� ��������� ������� ����������* ����������� ������ ���� ���������, ����� ����� ��� �� ����� ��������.\n" +
                                    "4) ����, ������� *����������� �������������: LG Manager, Lead Source, Lead Status � Country*.\n" +
                                    "5) � *���� Linkedin accounts* ������ ������� �� ������� ������ ���������.\n" +
                                    "6) � *���� Company* ������ �������� ����.\n" +
                                    "7) � *Company Size, Industry � Position* ������ ���������� ������� �� ����������� ������.\n" +
                                    "8) ����� *Save*.")
                            .respond();
                    break;

                case "crmLeads":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*���� ����� �� ������ ����������� �� ������� Leads � ����� ���*.\n" +
                                    "��� ����, ����� ����� ����������� ����, ��������� �������.")
                            .respond();
                    break;
                case "crmLeadReports":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("�� *������� Lead Reports* �� ������ ����������� ���� �����, ������� �� ���� � ���.\n" +
                                    "��� �������� ��������� �������.\n" +"\n" +"���������� - *����� 70-80 ����� � ����*.")
                            .respond();
                    break;


                //��� ���������� ���������________________________________________________________
                case "calEnterMail":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� � ���� ��������� � ������������� ����� info@rh-s.com")
                            .respond();
                    break;

                case "calLeadEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("_����� ��� ����������� �� ������ � ���������� ������ �� ���� ���������, � ��� ����� ������������� ������� � ������� � ��� ���, � ��� ���� �����_")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("*������� �� ������, ������� �� ��������*.\n" +"\n" +"*������ ��� ���������. ����� ����� ��������� ����� ��� ������. ������� � ��������� ���� � ������ ���� � �����, ������� ��� ��������*.\n" +
                                    "\n" +"����� *������ ��� ������� ���� (GMT +3)*. ����� ���������� �� �����, ��������� �� �������� ���� �� ��������� �������.\n" +
                                    "\n" +"����� ���� ��� �� *������ ���� � �����, ����� confirm*.\n" +"������� *�������� ������, ������ ����� sales@rh-s.com. � ������������ ����������� ������ �������� ������*.\n" +
                                    "\n" +"����� *������ add guests, � ������ ����� info@rh-s.com*.\n" +"\n" +"��������� � ������� ��� ����� � ��������� �������.\n" +"\n" +"*����� � ��� ��������� � ����� info@rh-s.com*. ����� ���� �����, ������ ��� � ������ ��� ����������� ���������� � �������, ������� ���� � ���� (����� ��������, �����������, ��� ��������� � ��).")
                            .send();
                    break;

                case "calNewEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"*����� � ���� ��������� � ������������� �����* info@rh-s.com .\n" +
                                    "\n" +"*������ ���� � �����*, �� ������� �� ������������ � ��������.\n" +
                                    "\n" +"*�������� ������� �����* ��� ���������� ������. � ��������� ����� �������� ����� (GMT+2). ����� �� ������� ����� � ���� ����� ��������� � ������������, ��������� ������ � �����: EST to Ukraine time. ���� ������ ���� ������ �� ������ ���������� �������.\n" +
                                    "\n" +"*AM* � ��� ������ � �������� ����� (� ������� 12am �� ������� 12pm), *PM* � �������� ������� � �������� ����� (� ������� 12pm �� ������� 12am).\n" +
                                    "\n" +"������ ����� � ��������� � �� �� ��, � 9:00 �� 18:00. ����� ������ ����� ���������� ����������� � Sales-�������.\n")
                            .respond();
                    break;

                case"calGet":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������ *����� ������� � �������� ����-����*.\n" +
                                    "\n" +"� ������ ������� ����, *����� �� ����* (� ���� �����).\n" +
                                    "��������� ���� ���������� ����.\n" + "\n" +"������ *���������*.\n" + "\n" +"��� *���������� ������, ������ ��������� � ������������� ����� info@rh-s.com*.")
                            .respond();
                    break;

                case "callFollowUp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������ _����-���������_. ������ ����� �����������.\n" +
                                    "*���������* ������������ ������� � *��������� �������� FollowUp*\n" +
                                    "\n" +"*������ ����* �� ������� ���� ������� ����������� � �����������������: *����� ������� �� ���� ����*. ��� ���� ������ ����� ������������ ������ ���������.\n" +
                                    "\n" +"�����: � ��������� *�������� ������-��� ��������� ������*.\n" +
                                    "\n" +"*������� �������� �����������*\n" +"- � �������� ����� *�������� �������, ��� �������/������, ��� ��������*.\n" +"- ����������� ����� *Manager* - ����, ����� �� �����, ��� ������ ����.\n" +"- � *Guests* - ����������� ������ ����� sales@rh-s.com ����� ���� ����������� ������������ � � �����, ������� ��������������� ������������� � ���������.")
                            .respond();
                    break;

                //��� ���������� ������________________________________________________________

                case "updateWhatIs":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*�������* - ��� ����, ������� ��� ���� � ���, ���� ��������� ����� ��� ������ ���������� ����� ���� ������� �����.\n" +
                                    "\n" +"����� ����� *�� ����� ��������� ��������� � ���������� ��������� �������*.")
                            .respond();
                    break;

                case "updateWhy":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("��� ����� �� ����� ��������� ��������� � ���������� ��������� �������.\n" +
                                    "\n" +"*��� �� ���������� ������������� �������� � ���� � ����� �������. �� �������� � ��������� �������� ������, ���������� ����� ������� � �� ����� ���������� �������������� � ������ ������ ��� ����.*")
                            .respond();
                    break;

                case "updateHowFind":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("��� ����� ��������:\n" +"\n" +"� ����� ��� ������ ������� Leads.\n" +"������ ����������� ���� ����:\n" +"*Country* - ������ � ������� ������  ���������\n" +"*Lead Status* - ��� �������, ����� Call\n" +"����� *Apply*.\n" + "����� ��������� ������ ���� �����, ������� ���� ������� �� ��� ����� ��� ������ ���� �������� � �������, � ������� �� ������ ���������.\n" +
                                    "\n" + "������ ���� ��������, � ������� �������� � ������ � ����� ������, �.�. � ������ ������� - ������� ���� ������� 3 � ����� ������� �����.\n" +
                                    "\n" + "������� ����, ����� ��� ��� ������ \"CREATED ON\" � ����, ����� ���� ��� ������ ��������� ������ UPDATE ON. ������ �������� �� �����������.")
                            .respond();
                    break;
                case "updateHowMake":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� � ����� ����, � ������� �� ������ *Contact�s LinkedIn* �� ��������� ���� � LinkedIn.\n" +
                                    "������� ��� ��������� ������� ������ � ��������: connect-add note.\n" +
                                    "\n" +
                                    "����� � ��� � ����� ���� ����� *Edit* � ������������ ����������:\n" +
                                    "- ���� *Active Agents* - ����� ���� ������, ����� ��� ��� �������� ������ ����.\n" +
                                    "- ���� *LinkedIn Accounts* - ����� ������� � �������� �� �������� �������. \n" +
                                    "- ���� *Lead Status* - ������� ������ Sent Request\n" +
                                    "- ���� *Note* - ����� ����������� - updated (��� ��������)\n" +
                                    "� ���������� �������������� ����� ���� ����� *Update*.")
                            .respond();
                    break;

                case "updateDayNorm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� �������� - 200 ����� � ����.")
                            .respond();
                    break;
                //��� ���� ������________________________________________________________

                case "timeTurnOn":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"����� � ���.\n" +"\n" +"����� ������ Login � ������ ������� ���� ������.\n" +
                                    "\n" +"����� ���� ����������� ����� � ���� Email � ������ � ���� Password � ����� ������ LOG IN.\n" +
                                    "\n" +"��� �������� �� ��������� �������� (������� Dashboard) ������� ������� ���� ������� �� *������ � ������ � ����� ��������������. ����� �����������.*\n" +
                                    "\n" +"����: *������ �������� ��� >>> Clock In >>> ��������� ������� >>> Clock Out >>> ����������� � ���������� �������� >>> Clock In >>> ��������� �������� ��� >>> Clock Out*\n")
                            .respond();
                    break;

                case "timeGoogleSetting":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� ������ _���� � ������ ������� ���� ���� ��������_, � ����������� ������ ����� �� ������� *���������*.\n" +
                                    "\n" +"� ������ ����� ������ *������� ������������������ � ������������*.\n" +
                                    "\n" +"��������� ������� ���� ���� �� ��������� *������� ��������� ������*, ����� �� ���.\n" +
                                    "\n" +"����� �� *������� ���������� ������� ���������� � ����������� ������ ������*.\n" +
                                    "\n" +"*� ��������� ������ � ������ ������� ���� ����� crm.rh-s.com*, ����� �� ����������� ������ � ���� �������.\n" +
                                    "\n" +"� ����������� ������ ����� *������ ���������* � ������ ������ ������� *���������*.")
                            .respond();

                    break;

                    //��� ������________________________________________________________
                case "bonusMy":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("��� ��������� ����� ������� � ��� �������� �� ��������� ��� ������ ��� ������.\n" +
                                    "��� �� ������ ������ � ������� �����, ������� �� � ���������� ��� ������, ��� ������������� �� ����������� ������.\n" +
                                    "�������� ���� �������� � ������� ������� �����. ������� ���������� ����� �� ������ ����������� � ������������ ������.\n" +
                                    "����� �� ������ � 150 ���.\n" +"������ ��������� ������ ����:\n" +"�) �� ���������;\n" +"�) ������� ����� ���������, ��� �� ��� ���������� (�� ���� �� ��� �������� ���� �����������);\n" +
                                    "�) ���� ������� �� �� ������, � ������� �� �� ��������.\n" +"������ ���������� � ����� ������� ������.\n" +"���������� ����� �� �����:\n" +"10 ������� � 1500 ��� ������������� � ����� ���������� �����.\n" +
                                    "������ ������ ����� ���� ������� ���������� ������ ���������� ����� � ����� ��������.\n" +
                                    "�������� ��� :)")
                            .respond();
                    break;

                case "bonusWhen":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("�������� ����������� ��� ���� � ����� � 5 �� 10 � � 20 �� 25 ����� ������� ������ �� �������� ��� ���������.")
                            .respond();
                    break;

                //��� ����________________________________________________________
                case "leadWhichLead":
                case "leadRight":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������ ��������:\n" + "\n" +"- �� _��� ������������ ����_.\n" +"*���������: CEO, COO, Founder, CTO, Owner, Director, co-Founder* (���� ���� ���� ����������� �������� �� ������������, �� ������� ������������ ������ ���������), *Entrepreneur*.\n" +
                                    "\n" +"- �� _������ ����_.\n" +"*�������� �: Australia, Austria, Belgium, Canada, Denmark, Estonia, Finland, France, Germany, Ireland, Italy, Israel, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, USA*.\n" +
                                    "\n" +"- ��� � ��������� ����.\n" +"���� � ������� � �������� ����� ���� �� ����������������� �����, �� ���� ������� ��� ��� ��� ���������, �������� �� ������� ����� ��� �������������� (�������� ��, ������ ��� ��� ����� �����������), �� ����� ���� ��� ���� �� ��������.\n" +
                                    "\n" +"- _������� ����� ��������_.\n" +"����� ���������, ��� ���� ��������� ��� � ��� �����������, ��������, �� ��� ����� � ���� ��������� �����-���� ������� ���������. ������ ���������� ������� ��������, � ������� �������� ���, � ����� ��� �������.\n" +
                                    "���� ����� �������� ������ 8 � ��� �� ������, ��� �� ����� �������� �� �������� ��������. ��� ��� ���� ����� �� ���������� � ����� ��������. ������� � ���������.\n" +
                                    "��������� �����, ������� �� ������� � ����������� ����� ��������.\n" +
                                    "\n" +"���� ��� ��, �� ��� ������ *Connect*.")
                            .respond();
                    break;
                case "leadInfoFrom":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� ������ ������������� � ����� ��������� ������, ���� ������ �������� � ����:\n" +
                                    "- *�����*, ����� ��� ����� ������ �����������\n" +"- *��������� ���� ��� �����* � ������� �� ���������\n" +"- *�����*, ���� ��������� ����������� �� ������\n" +"- ��� ��� ������� ���� �� �����������: Skype, Whatsapp, Hangout, Zoom.\n" +
                                    "\n" +"����� *�� ������� ��� ���������� � ���� (��������, ������, ����� ��������, � �� - ���, ��� ����� ���� ������� ��� �������.��� ������������ � ��������)*.")
                            .respond();
                    break;

                case "leadAdd":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� �������� ���� � ���:\n" +
                                    "\n" +"������ ���\n" +"������� �� ������� *Leads*, � ������ ������� ���� ����� �� ������ *Add new lead*.\n" +
                                    "\n" +"������� ����������� ���������� � ����.\n" +
                                    "���� ��������� ������� ����������* ����������� ������ ���� ���������, ����� ����� ��� �� ����� ��������.\n" +
                                    "\n" +"����, ������� *����������� �������������: LG Manager, Lead Source, Lead Status � Country*.\n" +
                                    "\n" +"� *���� Linkedin accounts* ������ ������� �� ������� ������ ���������.\n" +
                                    "\n" +"� *���� Company* ������ �������� ����.\n" +
                                    "\n" +"� *Company Size, Industry � Position* ������ ���������� ������� �� ����������� ������.\n" +
                                    "\n" +"����� *Save*.")
                            .respond();
                    break;

                case "leadCardFill":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*� ����� ������ ���� ���������� ������ ��� ���������� � ���, ������� ���� ������� ������� � �������� ������������.*\n" +
                                    "���� ��������� ������� ����������* ����������� ������ ���� ���������, ����� ����� ��� �� ����� ��������.\n" +
                                    "\n" +"*����, ������� ����������� �������������*: LG Manager, Lead Source, Lead Status � Country.\n" +
                                    "\n" +"� *���� Linkedin accounts* ������ ������� �� ������� ������ ���������.\n" +
                                    "\n" +"� *���� Company* ������ �������� ����.\n" +"\n" +"� *Company Size, Industry � Position* ������ ���������� ������� �� ����������� ������.\n" +
                                    "\n" +"����� *Save*.")
                            .respond();
                    break;

                case "leadWhoFollowUp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*���� ������ ������ ��������� � ��� ����� ��� ��������, ��� ������ ���� ������ �� ���������, �� ����� ���� ������� �� �������� ����� ������ � ��� �����������, ����� ������� ������� �� ������ ��*.\n" +
                                    "\n" +"���� �� ��������� �����������, ���� ���������� ����� ����� ����������� ����� ��������, ��� ������ � ��� ������.")
                            .respond();
                    break;

                case "leadHowMuch":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� � ���� ���. ������ ������� *Lead Reports*.\n" +
                                    "\n" +"��������� ������� �� ������ ����������� ���� �����, ������� �� ���� � ���.")
                            .respond();
                    break;

                case "leadDayNorm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� - *70-80 ����� � ����*.")
                            .respond();
                    break;

                case "leadHowFind":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� ����� �� ������ ����������� �� ������� *Leads* � ����� ���.\n" +
                                    "��� ����, ����� ����� ����������� ����, ��������� �������.")
                            .respond();
                    break;

                //��� �������________________________________________________________

                case "blueFirstCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"�������� ��������:\n" +
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

                case "blueSecondCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("�������� ��������:\n" +"\n" +"Thanks for adding me! \n" +
                                    "We feel that the market is starting to work, and you? Are you interested in our services?\n" +
                                    "\n" +"Thanks for connection. We provide foreign companies with help in such spheres as lead generation, content management, design, video editing, SEO, PPC etc. What service might be relevant to you?\n" +
                                    "\n" +"Thank you for the answer. May I get more information about your business and purposes so to understand what sphere are you engaged in?\n" +
                                    "\n" +"Thank you for connection. Please tell us a few words about your business to understand your needs better. If you can share your email I send you the presentation about us.\n" +
                                    "\n" +"As I see your company is engaged in � �������� �� �����(marketing sphere). We can offer you help in such fields like lead generation, SEO, PPC, content management. Or might you need any changes in design or technical part? This also might be a point for cooperation.")
                            .respond();
                    break;

                case "blueAbout":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� *������ ���������� � ��������������*, ������� ������ ������:\n" +
                                    "\n" +"Candidates we offer you to hire can work in the following positions: Lead Generation Managers, Customer Support, Personal Assistants, Social Media Managers, Designers, Media Buyers, PPC, SEO, AdOps, English teachers etc. Can we set a call to see if we can find a fit for you?\n" +
                                    "\n" +"*��������� ��������� ����� �������������� � �������, ����� ������ ���������� ��� ��������� ��������� ��� � ���������� ������*. �������� ������ ������������ �� ����� �� �� ������� ��� ���� ����?\n" +
                                    "\n" +"�To keep price advantages comparing to freelancers that charge minimum 15� per hour, our maximum cost is 7,5� per hour. But there should be enough work for a full time � it is 160 hours a month, 5 days a week,  8 hours a day. So the final prices are 1200� for designers, 1200� for marketers and 1000� for managers per month. Can we set a call to discuss all the details?�")
                            .respond();
                    break;

                case "blueExp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� *������ ���������� �� ����� ����� �����������*, ���� �����:\n" +
                                    "\n" +"�Most of our employees have a good knowledge of English, worked a while in digital marketing, but still there should be a team lead from your side to run training. Each company has its own specifics and employees need time to get into things.�\n" +
                                    "\n" +"Anyway you can find CVs of available employees on our Telegram channel or website\n" +
                                    "\n" +"https://t.me/RemoteHelpers\n" +
                                    "\n" +"https://bit.ly/3LAJimqhttp://rh-s.com/")
                            .respond();
                    break;

                case "blueClientBad":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� *������ �������, ��� �� ������������� � ����� �����������*, ���� �����:\n" +
                                    "\n" +"�Thank you for the reply. May I send you our presentation and prices in case you will need our services in the future?�\n" +
                                    "\n" +"*��������� ����������, � ����� ������ � ������� ������� �� �� ���� ������, ���� �� �����-�� �������*. \n" +
                                    "����� ���������� ������ ������, ����� ���� ��� ������ ��� ���� �����, ��� �� �� �������� ���� �������� � ������ ���������� � ������ ������.\n" +
                                    "� ������� ��, *�� ������ ��������� � ��������� ����� �� FollowUp*, ����� �� �������� �������, � ��������� � ��� � ������� +������ FollowUp � ���� � CRM.")
                            .respond();
                    break;

                case "blueClientGood":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"���� *������ ������������� � ����� ��������� ������*, ���� ������ �������� � ����:\n" +
                                    "- �����, ����� ��� ����� ������ �����������\n" +
                                    "- ������� ���� ��� ����� � ������� �� ���������\n" +
                                    "- �����, ���� ��������� ����������� �� ������\n" +
                                    "- ��� ��� ������� ���� �� �����������: �����, ������, �������, zoom\n" +
                                    "����� ����, ����� ����� �������� ������� ��� ����� �� ������ ������� � ������� ����� � ���������\n")
                            .respond();
                    break;

                //��� ���������� �������_______________________________________________________________

                case "conFirstCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("�����������:\n" +"Hello %Name%,\n" +
                                    "\n" +"�������� ��������:\n" +
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
                            .setContent("\n" +"�������� ��������:\n" +
                                    "\n" +"Thanks for adding me! \n" +
                                    "We feel that the market is starting to work, and you? Are you interested in our services?\n" +
                                    "\n" +"Thanks for connection. We provide foreign companies with help in such spheres as lead generation, content management, design, video editing, SEO, PPC etc. What service might be relevant to you?\n" +
                                    "\n" +"Thank you for the answer. May I get more information about your business and purposes so to understand what sphere are you engaged in?\n" +
                                    "\n" +"Thank you for connection. Please tell us a few words about your business to understand your needs better. If you can share your email I send you the presentation about us.\n" +
                                    "\n" +"As I see your company is engaged in � �������� �� �����(marketing sphere). We can offer you help in such fields like lead generation, SEO, PPC, content management. Or might you need any changes in design or technical part? This also might be a point for cooperation.\n")
                            .respond();
                    break;

                case "conToLead":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("����� �� ���� �������� � ��������, ����� �� ������� My Network, ������������� �� ������� ������ �����.\n" +
                                    "\n" +"����� �������� �� ������� ��� ������� (connections), ������� ����� ���� ���� �������.\n" +
                                    "\n" +"������ ��������:\n" +"\n" +"- �� _��� ������������ ����_.\n" +
                                    "*���������: CEO, COO, Founder, CTO, Owner, Director, co-Founder* (���� ���� ���� ����������� �������� �� ������������, �� ������� ������������ ������ ���������), *Entrepreneur*.\n" +
                                    "\n" +"- �� _������ ����_.\n" +"*�������� �: Australia, Austria, Belgium, Canada, Denmark, Estonia, Finland, France, Germany, Ireland, Italy, Israel, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, USA*.\n" +
                                    "\n" +"- ��� � ��������� ����.\n" +"���� � ������� � �������� ����� ���� �� ����������������� �����, �� ���� ������� ��� ��� ��� ���������, �������� �� ������� ����� ��� �������������� (�������� ��, ������ ��� ��� ����� �����������), �� ����� ���� ��� ���� �� ��������.\n" +
                                    "\n" +"- _������� ����� ��������_.\n" +"����� ���������, ��� ���� ��������� ��� � ��� �����������, ��������, �� ��� ����� � ���� ��������� �����-���� ������� ���������. ������ ���������� ������� ��������, � ������� �������� ���, � ����� ��� �������.\n" +
                                    "���� ����� �������� ������ 8 � ��� �� ������, ��� �� ����� �������� �� �������� ��������. ��� ��� ���� ����� �� ���������� � ����� ��������. ������� � ���������.\n" +
                                    "��������� �����, ������� �� ������� � ����������� ����� ��������.\n" +
                                    "\n" +"���� ��� ��, �� ��� ������ *Connect*.")
                            .respond();
                    break;

                //��� �������� �����-��

                case "followupWhat":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������-�� -��� ����� ������� ����������, ����� ��������� � ���� ��������, �������, ��������, � ��� �����. *������-�� ��������� ��� ����, ����� ������������ ������� ���������, ���� ����������� ����, ��� ������ ����������, ������, ���� ����� ���� � �������� ��������� ������������� ���������.*\n" +
                                    "\n" +"*���� �����-���� ������ ������ ��������� � ��� ����� ��� ��������, ��� ������ ���� ������ �� ���������, �� ����� ���� ������� �� �������� ����� ������ � ��� �����������, ����� ������� ������� �� ������ ��.*\n" +
                                    "\n" +"���� �� ���������� �����������, ���� ���������� ����� ����� ����������� ���� ��������, ��� ������ � ��� ������.")
                            .respond();
                    break;


                case "followupCal":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������ *����-���������*. ������ *����� �����������*.\n" +
                                    "��������� ������������ ������� � *��������� �������� FollowUp*\n" +
                                    "\n" +"*������ ���� �� ������� ���� ������� ����������� � �����������������: ����� ������� �� ���� ����*. ��� ���� ������ ����� ������������ ������ ���������.\n" +
                                    "\n" +"�����: � ��������� *�������� ������-��� ��������� ������*.\n" +
                                    "\n" +"*������� �������� �����������*\n" +
                                    "- � �������� ����� ��� �������� � �������, ��� ��������/�������, ��� ��������.\n" +
                                    "- ����������� ����� Manager - ����, ����� �� �����, ��� ������ ����.\n" +
                                    "- � Guests - ����������� ������ ����� sales@rh-s.com ����� ���� ����������� ������������ � � �����, ������� ��������������� ������������� � ���������.")
                            .respond();
                    break;

                case "counIWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*������ �������� ������������� �������� � ������������ �������*. �.�. �� ����� ��� ���������� ������������ ������, � ������� ���� ����� ������ �����.\n" +
                                         "\n" +"��� ����, ����� �� ����� ����� �����, ����� ������, � ����� ������� �� ��������� �� ������������ ��������. ��� �����:\n" +
                                         "\n" +"- ����� � ���� ���\n" +
                                         "\n" +"- ����� ������ *Add new lead*\n" +
                                         "- *������ ��������-�������, ������� ��������� �� �����* (�������, ������� ���� ����� �������-�������� ���������� ��� ������)\n" +
                                         "- *� ��� � ���� \"Country\" �������� �������� ������, � ������� ���� ����� ��������*\n" +
                                         "��� ���� ������ ���������������. ��� ����������� �������-��������.")
                            .respond();
                    break;

                case "counCanChange":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� ������� �������� ��������� �� ������������ �������. ��� ������ ����� �������� ������ �������-��������.")
                            .respond();
                    break;

                case "counNotWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������, ������� *���� ���������*\n" +
                                    "_Japan, Singapore, Cyprus, China_. ��� ��� ������������ � �������� ���������� ��-�� ������� �����������, ������������� ������ ������ ��������.\n" +
                                    "\n" +"������, � �������� *�� ��������*\n" +"Andorra, Argentina, Bahamas, Belarus, Bolivia, Brazil, Brunei, Bulgaria, Chile, Colombia, Costa, Rica, Dominican Republic, Ecuador, Egypt, Fiji, Guyana, Indonesia, Kazakhstan, Macao, Malaysia, Mexico, Morocco, Nepal, Oman, Panama, Paraguay, Peru, Philippines, Puerto, Rico, Qatar, Republic of Korea (South), Romania, Russian Federation, Saudi Arabia, South, Africa, Thailand, Turkey, Ukraine, " +
                                    "United Arab Emirates, Uruguay, Vanuatu, Albania, Algeria, Angola, Armenia, " + "Azerbaijan, Bahrain, Bangladesh, Barbados, Belize, Benin, Botswana, Burkina, Faso, Burundi, Cambodia, Cameroon," +
                                    " Cape Verde, Chad, Comoros, Congo, El Salvador, Ethiopia, Gabon, Georgia, Guatemala, Guinea, Haiti, Honduras, " +
                                    "India, Iraq, Jamaica, Jordan, Kenya, Kuwait, Kyrgyzstan, Laos, Lebanon, Lesotho, Macedonia, Madagascar, Mali, Mauritania, Mauritius, Moldova, Mongolia, Mozambique, Namibia, Nicaragua, Niger, Nigeria, Pakistan, Senegal, Sri Lanka, Suriname, Swaziland, Tajikistan, Tanzania, Togo, Trinidad and Tobago, Tunisia, Turkmenistan, Uganda, Uzbekistan, Vietnam, Zambia.\n" +
                                    "\n" +"������ ��� � �������, ������, ��������, �������, ���������, ������, �����������, �������, ����������, �����������.\n" +
                                    "����������� ��� ������ ������ � ��������� ������ ���� � ����� �������� ������������� ��� ������ �������.")
                            .respond();

                    break;

                case "counWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������, � �������� �� ��������:\n" +"_Australia, Austria, Belgium, Canada, Denmark, Finland, France, Germany, Ireland, Italy, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, United States of America, Israel, Latvia, Lithuania, Estonia, Czech Republic_")
                            .respond();

                    break;

                //��� ���������� �������

                case "statEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������ ���� � ��� ��� ���������� ������ - *Event*")
                            .respond();
                    break;

                case "statUpdate":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("���� �� ����� ����� � ��� ��� �������, �� � Lead Status ������ ��� �������, ����� Call.\n" +
                                    "\n" +"���� ��� ������ ������, �� � ����� ����, � ���� Lead Status ������� - *Sent Request*.")
                            .respond();
                    break;

                case "statCrm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*sent request* - ���������� ������ �� �������\n" +
                                    "\n" +"*connected* - ��� ������ ���� ������ �� �������, � � ���� ���� ��� �������� (����� � �������)\n" +
                                    "\n" +"*interested* - ��� ������� �� ���� ��������� � ��������������� ����� ������������\n" +
                                    "\n" +"*not interested* - ��� �������, ��� ���� ����������� ��� �� ����������\n" +
                                    "\n" +"*ignoring* - ��� ������� � ����� ��������� �����, � ����� �������� �������� �� ���������\n" +
                                    "\n" +"*follow up* - ��� �������, ��� � ������ ������ �� ������������� � ����� �����������, �� �������� ���������� ��� � �������\n" +
                                    "\n" +"*event* - ��� ���������� �� ������ � �� ������ ����� � ���������\n" +
                                    "\n" +"*call* - ����� ����� �������� ������� ����, ��� ������ ���������\n" +
                                    "\n" +"*not relevant for us* - ����� �������� ������� � ��� ����, ������� ��� �� ��������")
                            .respond();
                    break;

                //��� ���������� "���"

                case "moreBack":
                case "employeeOld":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("������!\n" +
                                    "�� ���������� � ����, � ������� ������� ������ �� ���������� ������� �� ������ ������ LeadGeneration.\n" +
                                    "\n" +
                                    "������ ��������� ������ �������:")
                            .addComponents(
                                    ActionRow.of(Button.primary("google", "����"),
                                            Button.primary("linked", "��������"),
                                            Button.primary("CRM", "CPM")),
                                    ActionRow.of(Button.primary("calendar", "���������"),
                                            Button.primary("update", "������"),
                                            Button.primary("timetrack", "����-�������")),
                                    ActionRow.of(Button.primary("contacts", "��������"),
                                            Button.primary("bonus", "������ � ��"),
                                            Button.primary("leads","����")),
                                    ActionRow.of(Button.primary("blueprints","�������"),
                                            Button.primary("connect","�������"),
                                            Button.primary("followup","��������")),
                                    ActionRow.of(Button.primary("country","������"),
                                            Button.primary("status","������"),
                                            Button.primary("more","���")))
                            .respond();
                    break;


                }



                });

        api.addMessageComponentCreateListener(event ->
                {
                    MessageComponentInteraction feedBackloop = event.getMessageComponentInteraction();
                    String feedBackloopCustomId = feedBackloop.getCustomId();
                    switch (feedBackloopCustomId)
                    {

                        case "feedback":

                            nameRecord = true;
                            //TODO: ���� �������� ����� -- �������
                            //TODO: ����������� ���������� �� �����
                            //TODO: ��������� ������ "�������� �����"

                            feedBackloop.respondWithModal("feedbackResponseModal","��� �����:",ActionRow.of(TextInput.create(TextInputStyle.SHORT, "feedbackResponse", "�����")));


                            break;

                        case "feedback1":

                            nameRecord = true;

                            String modalTest = "����� ���������� � ��������!";


                            feedBackloop.respondWithModal("mFeedback", modalTest,ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mName", "���")),ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mSure", "�������")), ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mPhone", "�������")));

                            /*
                            feedBackloop.createImmediateResponder()
                                    .setContent("���� ���� ��������/�������/�����������")
                                    .addComponents(ActionRow.of(Button.primary("feedback", "�������� �����")))
                                    .respond();

                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("����������")
                                    .addComponents(
                                            ActionRow.of(Button.primary("employeeNew","����� ���������")),
                                            ActionRow.of(Button.primary("employeeOld","��� ��������� � ����")),
                                            ActionRow.of(Button.primary("feedback", "��������� �����"))) */
                            break;





                        case "employeeNew":

                            feedBackloop.createImmediateResponder().setContent("�� �������� *������� � �������� ����� ����������*?")
                                    .addComponents(ActionRow.of(Button.primary("empnew1", "�������! ��� ������?")),
                                            ActionRow.of(Button.primary("notyet1", "��� ���.")))
                                    .respond();

                            break;

                        case "notyet1":

                            feedBackloop.createImmediateResponder()
                                    .setContent("����������, ������ ���������, ������� � ����� �������.")
                                    .addComponents(ActionRow.of(Button.primary("empnew1","�����")))
                                    .respond();

                            break;

                        case "empnew1":

                            feedBackloop.createImmediateResponder()
                                    .setContent("�� ��� ������ *����-�����*?")
                                    .addComponents(ActionRow.of(Button.primary("empnew2", "�������! ��� ������?")),
                                            ActionRow.of(Button.primary("notyet2", "��� ���. ������ ���")))
                                    .respond();
                            break;

                        case "notyet2":

                            feedBackloop.createImmediateResponder()
                                    .setContent("����. � ������ ���� ��� �������." + "\n" + "*����� �� Gmail � ����� \"������� �������\"*" + "\n" + "����� ������ ���������� Gmail...")
                                    .addComponents(ActionRow.of(Button.primary("notyet2_continue", "��� ������?")))
                                    .append("https://www.google.com/intl/uk/gmail/about/")
                                    .respond();
                            break;

                        case "notyet2_continue":

                            feedBackloop.createImmediateResponder()
                                    .setContent("������ �� ����������� ������ �������� � �������� - ����� \"����������\""+ "\n" + "*����� �������� �� ��������!*\n" +
                                            "\n" + "��� ��������� �����, ������ ������� - niko@rh-s.com\n" +
                                            "\n" + "����� ���� ���� �������� � ���� ���." +"\n"+ "*�������� ������� Google*, ����������� �� �� �����.\n" +
                                            "\n" +"https://youtu.be/7rVH13AHp5o")
                                    .addComponents(ActionRow.of(Button.primary("empnew2", "�����! ��� ������?")),
                                            ActionRow.of(Button.primary("feedback", "�������� �����")))
                                    .respond();
                            break;

                        case "empnew2":
                            feedBackloop.createImmediateResponder()
                                    .setContent("�� ��� �������� ����-������������?")
                                    .addComponents(ActionRow.of(Button.primary("empnew3", "�������, ��� ������?")),
                                            ActionRow.of(Button.primary("notyet3", "���, ������ ���.")))
                                    .respond();
                            break;

                        case "notyet3":

                            feedBackloop.createImmediateResponder()
                                    .setContent("� ������ ���� ��� �������.\n" + "*������ ������� ���� ����*. ����� �� ������ ������������ � ������� ������ ����. ������ *��������� ��������������*.\n")
                                    .respond();
                            feedBackloop.createFollowupMessageBuilder()
                                    .addAttachment(new File(path+ "/chromeuser1.png"))
                                    .send().join();



                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("������ *�������� ������������* � ������ ������ ����.\n" +
                                            "������ ������ ������������ ��� *���_LinkedIn*.\n" + "������ ��������.\n" +
                                            "������� ������� *������� ����� ����� ������� �� ������� �����*.\n" + "����� *��������*.")

                                    .addAttachment(new File(path+ "/chromeuser3.png"))
                                    .send().join();
                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("�������� ������� �����, ������� �� ������, � ����-������������. \n" + "�����! ����� ������������ �������� �����.\n" +
                                            "\n" + "_������������� ��������� ����� ������� ����� ��� ����� �������������_.\n" +
                                            "\n" + "_�� ������� ����� �������� ����� ��� ����� ������ � ����� ������������_.")
                                    .addComponents(ActionRow.of(Button.primary("empnew3", "�����! ��� ������?")),
                                    ActionRow.of(Button.primary("feedback", "�������� �����")))
                                    .send();
                            break;


                        case"empnew3":
                            feedBackloop.createImmediateResponder()
                                    .setContent("�������! \n" +"\n" +"� *��������-�������* ��� ������?")
                                    .addComponents(ActionRow.of(Button.primary("empnew4", "�����! ��� ������?")),
                                            ActionRow.of(Button.primary("notyet4", "��� ���, ������ ���.")))
                                    .respond();
                            break;

                        case"notyet4":
                            feedBackloop.createImmediateResponder()
                                    .setContent("����, � ������ ���� ������� ������� �� �������� \n" +
                                            "\n" + "_����� �� ������_")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_1","��� ������")))
                                    .respond();
                            break;

                        case "notyet4_1":
                            feedBackloop.createImmediateResponder()
                                    .setContent("1) ����� �� LinkedIn.\n" +
                                            "\n" +"2) ����� �����������.\n" +
                                            "\n" +"3) *������������� ������� �������� �� ����-�����, ������� �� ������ �����*.\n" +
                                            "�����: *��� ���������� � ����� ������� ������ ���� �� ����������* ���, ��� ��� ��������� � ������ ������� ������ �� ���� �����.\n" +
                                            "\n" +"https://youtu.be/SlvxQQTFFxg"+ "\n" +"\n"+ "�����?\n" +
                                            "�������� �� ������� ����")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_2","��� ������")))
                                    .respond();
                            break;

                        case "notyet4_2":

                            feedBackloop.createImmediateResponder()
                                    .setContent("*����� ���� ��������� ��� � ������� �� ���������� �����*.\n" +
                                            "���������: ��������� �������� ��� ���������� �����.\n" +
                                            "\n" +
                                            "*������� ���� ����* \n" +
                                            "��������� ����: �������, ��� ������� ����, ���������� ����� �����������.\n" +
                                            "\n" +"*�������� ������� �� ���������� ����*\n" +
                                            "��� ��� �������:\n" +
                                            "- � ������ ������� ���� �� ������ ���� ����\n" +
                                            "- ����� �� ��������� ��� ����\n" +
                                            "- ��������� ����\n" +
                                            "- ������ \"����/Language\"\n" +
                                            "\n" +"���� �����, �������� � �������� ����")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_3","��� ������")))
                                    .respond();
                            break;

                        case "notyet4_3":
                            feedBackloop.createImmediateResponder()
                                    .setContent("���� *�����������/Education*\n" +
                                            "����� ���������� ������ � ����� ������ ����������� �� ���������� �����.\n" +
                                            "\n" +"���� �� ��� �������, � ���� \"���� ���������\" ����� 2020 ��� ��� ����� ������, � � ���� \"���� ������\" - ��� �� 4-5 ��� ������.\n" +
                                            "\n" +"�����? ��� ������\n")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_4","��� ��������")))
                                    .respond();
                            break;

                        case "notyet4_4":

                            feedBackloop.createImmediateResponder()
                                    .setContent("���� *���� ������/Edit experience*\n" +
                                            "\n" +
                                            "*������� � �������� �� ����������*\n" +
                                            "\n" +
                                            "- ���������: Account manager\n" +
                                            "- ������ ������: Full-time\n" +
                                            "- ��������: Remote Helpers\n" +
                                            "- �������: ���� ������� �������������� (�����, �������)\n" +
                                            "- ����� ������ � ��������: ����� �����/��� �� ���������� ������� + ������� ������� to present.\n" +
                                            "\n" +"������? ��� ������")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_5","��� �����")))
                                    .respond();
                            break;

                        case "notyet4_5":
                            feedBackloop.createImmediateResponder()
                                    .setContent("���� *������/Skills*\n" +
                                            "*�������� ������ �� ���������� �����*.\n" +
                                            "\n" +"������ ����� ��������� �������:\n" +
                                            "- Email marketing\n" +"- Lead Generation\n" +
                                            "- Social media marketing\n" +"- Online Advertising\n" +"- Data Analysis\n" +
                                            "- Searching skills\n" +"- Targeting\n" +"- WordPress\n"
                                            +"- English language\n" +"- Design\n" +"- � ������ ������ �� ����������� ������.\n" +
                                            "\n" +"*��������� ���� 5-8*\n" +
                                            "\n" +"������? ��� ������")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_6","��� ������")))
                                    .respond();
                            break;

                        case "notyet4_6":

                            feedBackloop.createImmediateResponder()
                                    .setContent("������ *������ �������*.\n" +
                                            "��� ��� �������:\n" +
                                            "- ������ ���� �������\n" +"- ����� �� ���������� ������ �� ���� �������\n" +
                                            "- ������ ���� *Headline/������*\n" +"\n" +"�������� ������:\n" +
                                            "\n" +"_Hire online full-time remote employees| Marketing| Content Managers| SMM| Designers| Devs_\n" +
                                            "\n" +"_Dedicated virtual assistants in Ukraine: Lead Generation| SMM| Media| Design| Developers_\n" +
                                            "\n" +"_Build your online team in few clicks| Lead Generation| Marketing| Media| Design| Devs_\n" +
                                            "\n" +"���� �� ����������� ���������� ����� � �����. �������� �����: �� ���������� �������� ��������� �� �������, ����� ��������� ����������� �� �������, ������������� ������ ����.\n" +
                                            "\n" +"������? ��� ������")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_7","��� �������")))
                                    .respond();
                            break;


                        case "notyet4_7":

                            feedBackloop.createImmediateResponder()
                                    .setContent("*�������� ������ �� ���� �������*\n" +
                                            "\n" +"��� ��� �������:\n" +"- ����� �� ���� ��������\n" +
                                            "- _� ������ ������� ���� ������ ����� �� ���� ����, ��������� ���������� ������_\n" +
                                            "- ������ �� ��� _View profile_\n" +
                                            "- �� ����������� �������� � ������ ������� ���� �������� _Edit public profile & URL_\n" +
                                            "- ����� � ������ ������� ���� ����� �� _Edit your custom URL_, ����� ����� ���� ������ � ������ ���������\n" +
                                            "- ����� �� ���������� � ����� ��� �������� ����� � �������. ������ ������ ���� ��� � �������.\n" +
                                            "- ����� _Save_.\n" +"\n" +"URL ��������� � ������� ���������� �����.\n" +
                                            "\n" +"������? ��� ������")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_8","��� �������")))
                                    .respond();

                            break;
                    //https://www.linkedin.com/search/results/people/?currentCompany=%5B%2210127817%22%5D&origin=COMPANY_PAGE_CANNED_SEARCH&sid=cst
                        case "notyet4_8":

                            feedBackloop.createImmediateResponder()
                                    .setContent("������� ���� ����� ���������. ������ � ������ ����� ������.\n" +
                                            "\n" +
                                            "*������� 1 - ����� �� �������� �������� Remote Helpers � ��������, � ������ ����������*\n" +
                                             "https://www.linkedin.com/search/results/people/?currentCompany=%5B%2210127817%22%5D&origin=COMPANY_PAGE_CANNED_SEARCH&sid=cst" + "\n"+
                                            "*������� 2 - ��������� ����� � ����*\n" +
                                            "https://www.google.com/search?q=site%3Alinkedin.com+remote+helpers&oq=site%3Alinkedin.com+remote+helpers")

                                    .respond();

                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("�����! ������� � �������� �����!")
                                    .addComponents(ActionRow.of(Button.primary("empnew4", "�����! ��� ������?")))
                                    .send().join();
                            break;



                        case "empnew4":
                            nameRecord = true;

                            feedBackloop.respondWithModal("accountEnter", "������� ������� � ���� � ��������",ActionRow.of(TextInput.create(TextInputStyle.SHORT,"gName","���� ����")),ActionRow.of(TextInput.create(TextInputStyle.SHORT,"gPass","���� ������")),ActionRow.of(TextInput.create(TextInputStyle.SHORT,"lName","Linked In ����:")), ActionRow.of(TextInput.create(TextInputStyle.SHORT,"lPass","LinkedIn ������")));

                            break;

                    }


                    api.addModalSubmitListener(modalEvent ->
                            {

                                ModalInteraction modalInteraction = modalEvent.getModalInteraction();
                                String modalInteractionCustomId = modalInteraction.getCustomId();

                                if(Objects.equals(modalInteractionCustomId, "mFeedback"))
                                {

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
                                        modalInteraction.createImmediateResponder().setContent("� ��� ������!"+"\n"+ " ����������, ������� �� ���������� ������ � ��������� ��������� ����.").respond();
                                    }

                                    else
                                    {

                                        try
                                        {
                                            NameListner.wright(service, nameRecord,spreadsheetId, userId, userName, modalPhone, modalName, modalSure);
                                           //check(service, userId, userName, modalPhone, modalName, modalSure);
                                        } catch (IOException e)
                                        {
                                            throw new RuntimeException(e);
                                        } catch (GeneralSecurityException e)
                                        {
                                            throw new RuntimeException(e);
                                        }

                                        modalInteraction.createImmediateResponder().setContent("����������")
                                                .addComponents(
                                                        ActionRow.of(Button.primary("employeeNew","����� ���������")),
                                                        ActionRow.of(Button.primary("employeeOld","��� ��������� � ����")),
                                                        ActionRow.of(Button.primary("feedback", "��������� �����")))
                                                .respond();
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
                                        modalInteraction.createImmediateResponder().setContent("� ��� ������!"+"\n"+ " ����������, ������� �� ���������� ������ � ��������� ��������� ����.").respond();
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
                                            // TODO: �������� ������-����������� �� ��������
                                        modalInteraction.createImmediateResponder().setContent("����������")
                                                .addComponents(
                                                        ActionRow.of(Button.primary("employeeNew","����� ���������")),
                                                        ActionRow.of(Button.primary("employeeOld","��� ��������� � ����")),
                                                        ActionRow.of(Button.primary("feedback", "��������� �����")))
                                                .respond();
                                    }
                                }

                                if (Objects.equals(modalInteractionCustomId, "feedbackResponseModal"))
                                {
                                    User user = modalInteraction.getUser();
                                    String userId = user.getIdAsString();
                                    String userName = user.getDiscriminatedName();

                                    String userFeedback = modalInteraction.getTextInputValueByCustomId("feedbackResponse").get();


                                        try
                                        {
                                            NameListner.wrightFeedback(service,spreadsheetId, userName, userFeedback);
                                        } catch (GoogleJsonResponseException e)
                                        {
                                            throw new RuntimeException(e);
                                        }
                                        System.out.println(stage);
                                    FeedbackResponder.modalResponder(stage, modalInteraction);
                                    //modalInteraction.createImmediateResponder().setContent("������� �� ��� �����!").respond();
                                }


                            });
                });
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());


        //TODO: ���, ����������� ����������� �������� ��-����, ����� �����, � �������� � 5 �����. ���� �� ��������� � ��������, �� ����� � ������

    }

    public static void check(Sheets service, String discordId, String discordMame, String number, String name, String surename) throws IOException, GeneralSecurityException
    {
       //MessageCreateEvent event,
        // String name =event.getMessageAuthor().getDiscriminatedName();
            //TODO: ������ �������� �� ������������� ������
        if (nameRecord) //
        {
            AppendValuesResponse result;
            try
            {
                List<List<Object>> values;

                ValueRange appendValue = new ValueRange()
                        .setValues((Arrays.asList(Arrays.asList(discordId,discordMame, number, name +" "+ surename))));



                result = service.spreadsheets().values()
                        .append(spreadsheetId, "TEST", appendValue)
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

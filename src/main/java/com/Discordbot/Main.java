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
    private static boolean SHOULDSEND = false;
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
                        .setContent("Привет!\n" +
                                "Ты находишься в меню, в котором собраны ответы на популярные вопросы по работе отдела LeadGeneration.\n" +
                                "\n" +"Выбери категорию своего вопроса:")
                        .addComponents(
                                ActionRow.of(Button.primary("google", "Гугл"),
                                        Button.primary("linked", "Линкедин"),
                                        Button.primary("CRM", "CPM")),
                                ActionRow.of(Button.primary("calendar", "Календарь"),
                                        Button.primary("update", "Апдейт"),
                                        Button.primary("timetrack", "Тайм-треккер")),
                                ActionRow.of(Button.primary("contacts", "Контакты"),
                                        Button.primary("bonus", "Бонусы и ЗП"),
                                        Button.primary("leads","Лиды")),
                                ActionRow.of(Button.primary("blueprints","Шаблоны"),
                                        Button.primary("connect","Коннект"),
                                        Button.primary("followup","Фоллоуап")),
                                ActionRow.of(Button.primary("country","Страна"),
                                        Button.primary("status","Статус"),
                                        Button.primary("more","Ещё")))
                        .send(channel);

            }
            if (event.getMessageContent().equalsIgnoreCase("!start"))
            {
                SHOULDSEND = false;
                new MessageBuilder()
                        .setContent("Здравствуйте!")
                        .addComponents(ActionRow.of(Button.primary("feedback1", "Начнём?")))
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
                    .setContent("Привет!\n" +
                            "Ты находишься в меню, в котором собраны ответы на популярные вопросы по работе отдела LeadGeneration.\n" +
                            "\n" +"Выбери категорию своего вопроса:")
                    .addComponents(
                            ActionRow.of(Button.primary("google", "Гугл"),
                                    Button.primary("linked", "Линкедин"),
                                    Button.primary("CRM", "CPM")),
                            ActionRow.of(Button.primary("calendar", "Календарь"),
                                    Button.primary("update", "Апдейт"),
                                    Button.primary("timetrack", "Тайм-треккер")),
                            ActionRow.of(Button.primary("contacts", "Контакты"),
                                    Button.primary("bonus", "Бонусы и ЗП"),
                                    Button.primary("leads","Лиды")),
                            ActionRow.of(Button.primary("blueprints","Шаблоны"),
                                    Button.primary("connect","Коннект"),
                                    Button.primary("followup","Фоллоуап")),
                            ActionRow.of(Button.primary("country","Страна"),
                                    Button.primary("status","Статус"),
                                    Button.primary("more","Ещё")))
                    .respond();

        });

        String path = (System.getProperty("user.dir")+"/resources");
        message(api);

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        // Add a listener which answers with "Pong!" if someone writes "!ping"

        //TODO: обавить кнопку "Обратно в меню", к каждому пункту


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
                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
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
                    //TODO: Перевести
                    messageComponentInteraction.createFollowupMessageBuilder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("linkCleanOld", "Чистка старых заявок")),
                                    ActionRow.of(Button.primary("linkAccBan", "Бан аккаунта")),
                                    ActionRow.of(Button.primary("linkAccLimits", "Лимиты на аккаунте")),
                                    ActionRow.of(Button.primary("linkAccCountry", "Как поменять страну")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .send();
                    break;
                case "CRM":
                    //TODO: Перевести
                    //stage = 16;
                    stage = 3;
                    SHOULDSEND = true;
                  /*messageComponentInteraction.createImmediateResponder()
                           .setContent("You selected CRM")
                           .respond();*/



                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("crmEnter", "Как войти в СРМ")),
                                    ActionRow.of(Button.primary("crmAccess", "Как получить доступ")),
                                    ActionRow.of(Button.primary("crmAcc", "Аккаунт для работы в СРМ")),
                                    ActionRow.of(Button.primary("crmHowToAddLead", "Как добавить лида")),
                                    ActionRow.of(Button.primary("crmLeads", "Вкладка Leads")))
                                    .respond();

                    messageComponentInteraction.createFollowupMessageBuilder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("crmLeadReports", "Вкладка Lead Reports")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .send();

                    break;
                case "calendar":
                    //stage = 16; //TODO: Перевести
                    stage = 4;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("calEnterMail", "Почта для входа")),
                                    ActionRow.of(Button.primary("calLeadEvent", "Ивент в календаре лида")),
                                    ActionRow.of(Button.primary("calNewEvent", "Внести ивент в календарь")),
                                    ActionRow.of(Button.primary("calGet", "Где взять календарь")),
                                    ActionRow.of(Button.primary("calFollowUp", "Фоллоу-ап в календаре")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                                    .addComponents(
                                            ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .send();
                    break;
                case "update":

                    //stage = 16;
                    stage = 5;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("updateWhatIs", "Что такое Апдейт")),
                                    ActionRow.of(Button.primary("updateWhy", "Зачем нужен апдейт")),
                                    ActionRow.of(Button.primary("updateHowFind", "Как найти апдейтов")),
                                    ActionRow.of(Button.primary("updateHowMake", "Как сделать Апдейт")),
                                    ActionRow.of(Button.primary("updateDayNorm", "Норма апдейтов в день")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .send();

                    break;
                case "timetrack":

                    //stage = 16;
                    stage = 6;
                    SHOULDSEND = true;



                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("timeTurnOn", "Включить тайм-трекер")),
                                    ActionRow.of(Button.primary("timeGoogleSetting", "Настройка Google Chrome")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;
                case "contacts":

                    //stage = 16;
                    stage = 7;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder().setContent("select")
                            .setContent("Влад Моисеенко(аккаунт-менеджер)\n" + "<@372427478267985930>" +
                                    "+380981101090\n" +"\n" +"Катя Истомина (Бухгалтерия)\n"+ "<@918433181114454047>" +
                                    "+380714184225\n" +"\n" +"Юлия Мартынив (Тимлид Sales)\n"+ "<@920656302546513950>" +
                                    "+380662227034\n" +"\n" +"Мария Бигун (Тимлид LeadGeneration)\n" + "<@921083222077620334>" +
                                    "+380634345683")
                            .addComponents(ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;
                case "bonus":

                   // stage = 16;
                    stage = 8;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("bonusMy", "Мои бонусы")),
                                    ActionRow.of(Button.primary("bonusWhen", "Когда ЗП")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();

                    break;
                case "leads":

                    //stage = 16;
                    stage = 9;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder().setContent("")
                            .addComponents(
                                    ActionRow.of(Button.primary("leadWhichLead", "Каких лидов нужно искать")),
                                    ActionRow.of(Button.primary("leadInfoFrom", "Данные от лида")),
                                    ActionRow.of(Button.primary("leadAdd", "Добавить лида")),
                                    ActionRow.of(Button.primary("leadCardFill", "Заполнить карту лида")),
                                    ActionRow.of(Button.primary("leadWhoFollowUp", "Кому делать фоллоу-ап")))
                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("leadRight", "Правильные лиды")),
                                    ActionRow.of(Button.primary("leadHowMuch", "Сколько лидов я сделал")),
                                    ActionRow.of(Button.primary("leadDayNorm", "Норма лидов в день")),
                                    ActionRow.of(Button.primary("leadHowFind", "Как найти нужного лида")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .send();
                    break;
                case "blueprints":


                    //stage = 16;
                    stage = 10;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("blueFirstCon", "1-ый коннект с лидом")),
                                    ActionRow.of(Button.primary("blueSecondCon", "2-ой коннект с лидом")),
                                    ActionRow.of(Button.primary("blueThirdCon", "3-ий коннект с лидом")),
                                    ActionRow.of(Button.primary("blueAbout", "О специальностях")),
                                    ActionRow.of(Button.primary("blueExp", "Об опыте содрудников"))) //blueThirdCon

                            .respond();

                    messageComponentInteraction.createFollowupMessageBuilder()
                            .addComponents(
                                    ActionRow.of(Button.primary("blueClientBad", "Клиент не заинтересован")),
                                    ActionRow.of(Button.primary("blueClientGood","Клиент заинтересован")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .send();

                    break;
                case "connect":

                   // stage = 16;
                    stage = 11;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("conFirstCon", "1-ый коннект с лидом")),
                                    ActionRow.of(Button.primary("conSecondCon", "2-ой коннект с лидом")),
                                    ActionRow.of(Button.primary("conThirdCon", "3-ий коннект с лидом")),
                                    ActionRow.of(Button.primary("conToLead", "Отправить коннект лиду")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;
                case "followup":

                   // stage = 16;
                    stage = 12;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("followupWhat", "Что такое фоллу-ап")),
                                    ActionRow.of(Button.primary("followupCal", "Фоллу-ап в календаре")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;
                case "country":

                    //stage = 16;
                    stage = 13;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("counIWork", "С какой страной я работаю")),
                                    ActionRow.of(Button.primary("counCanChange", "Можно ли изменить страну")),
                                    ActionRow.of(Button.primary("counNotWork", "Страны: НЕ работаем")),
                                    ActionRow.of(Button.primary("counWork", "Страны: работаем")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;
                case "status":
                   // stage = 16;
                    stage = 14;
                    SHOULDSEND = true;

                    messageComponentInteraction.createImmediateResponder()
                            .addComponents(
                                    ActionRow.of(Button.primary("statEvent", "Статус о назначении ивента")),
                                    ActionRow.of(Button.primary("statUpdate", "Статус апдейт")),
                                    ActionRow.of(Button.primary("statCrm", "Виды статусов в СРМ")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;
                case "more":
                    messageComponentInteraction.createImmediateResponder().setContent("\"Вернуться на старт\" - начать работу с ботом с начала\n" +
                                    "\"Вернуться назад\" - в меню с ответами на вопросы\n" +"\"Обратная связь\" - если не находишь ответ на свой вопрос")
                            .addComponents(
                                    ActionRow.of(Button.primary("moreStart", "Вернутся на старт")),
                                    ActionRow.of(Button.primary("moreBack", "Вернуться назад")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                            .respond();
                    break;

                case "moreStart":
                  messageComponentInteraction.createImmediateResponder().setContent("Продолжаем")
                        .addComponents(
                                ActionRow.of(Button.primary("employeeNew","Новый сотрудник")),
                                ActionRow.of(Button.primary("employeeOld","Уже работаешь с нами")),
                                ActionRow.of(Button.primary("feedback", "Вернуться назад")))
                          .respond();

                    break;


            }
        });



        String googleSearchBig = "Поиск по должностям\n" +"site:(адрес сайта) пробел ( должности )\n" +
                "Пример – site:linkedin.com\n" +"CEO – Такой запрос в гугле выдаст Вам все страницы на сайте линкедина упоминающие должности гендиректоров.\n" +
                "OR ( заглавными буквами между словами, через пробел )\n" +
                "пример: site:linkedin.com CEO OR Founder OR Director\n" +
                "(Минус) –\n" +"пример: site:linkedin.com CEO OR Founder OR Director -news -Institute -become -jobs -wewwork -amazon -netflix -million\n" +
                "Поиск по компаниям\n" +"– (тире ставятся между цифрами 2-50)\n" +
                "пример: site:linkedin.com Company size: 2-50 employees\n" +
                "Используем именно такие размеры компаний, потому что они прописаны в шаблонах самого LinkedIn – 2-50, 51-200, 201-500 и т.д.\n" +
                "AND ( совмещает оба запроса в поисковой выдаче )\n" +"пример: site:linkedin.com Founded: 2005..2019 AND Company size: 51-200 employees\n" +
                "“…” (Кавычки) – фразовый оператор, используется для того, чтобы искать именно фразу, а не каждое слово по отдельности\n" +
                "пример: site:linkedin.com “Specialties: Email Marketing”\n" + "* (звездочка)\n" +"site:linkedin.com “Specialties: Email Marketing”*\n" +
                "\n" +"Headquarters: (локация)\n" +"пример: site:linkedin.com Founded: 2005..2019 AND Company size: 51-200 employees OR Headquarters: France\n";


        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction step2MessageCompInteraction = event.getMessageComponentInteraction();
            String ChosenInteraction = step2MessageCompInteraction.getCustomId();

           // sqlitecontroller.insert(step2MessageCompInteraction);

            switch (ChosenInteraction)
            {//Google Responses________________________________________________________
                case "notyet3":
                case "googChromeUserCreate": //Создание нового пользователя Хрома

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Я помогу тебе это сделать.\n" + "*Открой браузер Гугл Хром*. Нажми на окошко пользователя в верхнем правом углу. Кликай *Управлять пользователями*.\n")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .addAttachment(new File(path+ "/chromeuser1.png"))
                            .addComponents(ActionRow.of(Button.primary("goog1", "Дальше.")))
                            .send().join();


                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Выбери *Добавить пользователя* в нижнем правом углу.\n" +
                                    "Назови своего пользователя так *ИМЯ_LinkedIn*.\n" + "Добавь аватарку.\n" +
                                    "Поставь галочку *Создать ярлык этого профиля на рабочем столе*.\n" + "Нажми *Добавить*.")
                            .send().join();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .addAttachment(new File(path+ "/chromeuser3.png"))
                            .addComponents(ActionRow.of(Button.primary("goog2","Дальше")))
                            .send().join();


                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .append("Подключи рабочую почту, которую ты создал, к Хром-пользователю. \n" + "Вуаля! Новый пользователь ГуглХром готов.\n" +
                                    "\n" + "_Автоматически откроется новая вкладка Хрома под новым пользователем_.\n" +
                                    "\n" + "_На рабочем столе появился ярлык для входа именно в этого пользователя_.")
                            .send().join();

                    break;
                case "googleFindCol": //Нахождение коллег
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Чтобы *найти своих коллег с помощью Гугл-поиска и добавить из в друзья на Линкедин*, в строке поиска вбей оператор:\n" +
                                    "\n" +
                                    "site:linkedin.com remote helpers")
                            .respond();
                    break;
                case "googleCalendar":// Гугл календарь
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Где взять *Гугл-календарь*:\n" + "\n" +"Открой новую вкладку в браузере гугл-хром.\n" +
                                    "\n" +"*В правом верхнем углу, нажми на меню *(в виде точек).\n" +
                                    "Откроется меню расширений Гугл.\n" +"\n" +"*Выбери Календарь*\n" +"\n" + "_Если тебе нужно назначить ивент, открой календарь с корпоративной почты info@rh-s.com_.")
                            .respond();
                    break;
                case "googleSearch"://Операторы поиска в гугле
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent(googleSearchBig).respond();
                    break;
                case "googleUsefulAddons": //"полезные аддоны

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Полезные *расширения в гугл* для работы:" +"\n")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("\"https://chrome.google.com/webstore/detail/find-anyones-email-contac/jjdemeiffadmmjhkbbpglgnlgeafomjo" +"\n" +
                                    "https://chrome.google.com/webstore/detail/rocketreach-chrome-extens/oiecklaabeielolbliiddlbokpfnmhba"+ "\n" +
                                   "https://chrome.google.com/webstore/detail/free-vpn-for-chrome-vpn-p/majdfhpaihoncoakbjgbdhglocklcgno" + "\n")
                            .send();
                    break;


                //тут начинается LinkedIN________________________________________________________
                case "linkCleanOld":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зайди на свой рабочий аккаунт на Линкедин.")
                            .respond();
                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Вверху перейди на *вкладку My Network*, справа нажми на *вкладку Manage*.\n" +
                                    "Тебе откроется вкладка с исходящими и входящими приглашениями.\n" +
                                    "\n" +"Перейди на *вкладку Sent*. Ты видишь список заявок, которые были отправлены лидам.\n" +
                                    "*Нажми на кнопку Withdraw* и удали старый коннект.\n" + "\n" +"Удали все заявки которые были отосланы 2 и более недель назад.Если за это время лид не одобрил коннект, он будет висеть пока ты его не удалишь.\n" +
                                    "\n" +"Проводи такую чистку на всех своих рабочих аккаунтах.")
                            .send();
                    break;
                case "linkAccBan"://Бан аккаунта в Линкед Ин________________________________________________________

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Как избежать попадания в бан на Линкедин*")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("1) Делай *паузы между повторяющимися действиями* (коннект, копирование инфы, рассылка и т.д.);\n" + "\n" +
                                    "2) C 10-ю коннектами не стучись в друзья к президенту;\n" + "\n"+
                                    "3) *Не спамить, вести себя максимально естественно*. Общение должно быть живым, хоть и построено в деловом стиле.\n" + "\n"+
                                    "4) Пользоваться *отдельным юзером Гугл Хрома для входа в свой Линкедин*.\n" + "\n"+
                                    "5) *Раздели работу на части*. Например, минут 10-15 добавляешь людей (каждые 30 секунд), потом переключаешься на рассылку писем по почте. И так несколько подходов.\n" + "\n"+
                                    "6) В идеале веди профиль. Максимально заполни аккаунт информацией, фото, постами. Это привлекает внимание потенциальных клиентов, которые САМИ захотят связаться с тобой. Система соцсети не подумает, что здесь что-то не так. Нужно также делать репосты из группы нашей компании, лайкай посты контактов")
                            .send();
                    break;
                case "linkAccLimits": // Лимит на аккаунт в Линкед Ин
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Если появился *лимит на аккаунте* Линкедин, *обратись к аккаунт-менеджеру*")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Влад\n" + "+380981101090\n")
                            .send();
                    break;

                case "linkAccCountry": //Смена страны аккаунта на Ликед Ин
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("_Твой аккаунт Линкедин закреплен за определенной страной. Эти данные может заменить только аккаунт-менеджер_\n")
                            .respond();
                    break;

                case "linkFindLeads": //Нахождение лидов
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Зайди* на свою страницу в Линкедин, затем *во вкладку My Network*, расположенную на верхней панели сайта.\n" +
                                    "\n" + "Внизу страницы ты найдешь все профили (connections), которые могут быть тебе полезны.")
                            .respond();
                    break;

                //Тут начинается СРМ________________________________________________________

                case "crmEnter":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зайди в СРМ." +"\n" + "https://crm.rh-s.com/" + "\n" +"Используй доступы, которые выдал тебе наш аккаунт-менеджер.")
                            .respond();
                    break;
                case "crmAccess":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Влад" + "\n" + "+380981101090" + "\n"+ "Доступы к СРМ и рабочим аккаунтам выдает аккаунт-менеджер. Напиши ему.")
                            .respond();
                case "crmAcc":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зайди в свою СРМ.\n" +"\n" +"Нажми вкладку *Add new lead*\n" +
                                    "\n" + "*Выбери Линкедин-аккаунт, который закреплен за тобой* (аккаунт, который тебе выдал аккаунт-менеджер специально для работы)")
                            .respond();
                    break;
                case "crmHowToAddLead":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Чтобы *добавить лида в СРМ*:\n" +
                                    "1) Открой СРМ\n" +
                                    "2) Перейди на *вкладку Leads*, в правом верхнем углу нажми на *кнопку Add new lead*.\n" +
                                    "3) Заполни необходимую информацию о лиде.\n" +
                                    "Поля помеченые красной звездочкой* обязательно должны быть заполнены, иначе новый лид не будет сохранен.\n" +
                                    "4) Поля, которые *заполняются автоматически: LG Manager, Lead Source, Lead Status и Country*.\n" +
                                    "5) В *поле Linkedin accounts* выбери аккаунт на котором сейчас работаешь.\n" +
                                    "6) В *поле Company* добавь компанию лида.\n" +
                                    "7) В *Company Size, Industry и Position* выбери подходящий вариант из выпадающего списка.\n" +
                                    "8) Нажми *Save*.")
                            .respond();
                    break;

                case "crmLeads":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Всех лидов ты можешь просмотреть во вкладке Leads в своей СРМ*.\n" +
                                    "Для того, чтобы найти конкретного лида, используй фильтры.")
                            .respond();
                    break;
                case "crmLeadReports":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("На *вкладке Lead Reports* ты можешь просмотреть всех лидов, которых ты внес в СРМ.\n" +
                                    "Для удобства используй фильтры.\n" +"\n" +"Напоминаем - *норма 40-50 лидов в день*.")
                            .respond();
                    break;


                //ТУТ начинается Календарь________________________________________________________
                case "calEnterMail":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зайди в Гугл Календарь с корпоративной почты info@rh-s.com")
                            .respond();
                    break;

                case "calLeadEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("_Когда лид соглашается на звонок и сбрасывает ссылку на свой календарь, в нем нужно забронировать встречу в удобное и для нас, и для него время_")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("*Перейди по ссылке, которую он отправил*.\n" +"\n" +"*Открой наш календарь. Здесь найди свободное время для звонка. Вернись в календарь лида и выбери дату и время, которые нам подходят*.\n" +
                                    "\n" +"Внизу *выбери наш часовой пояс (GMT +3)*. Время переводить не нужно, поскольку мы выбираем часы по киевскому времени.\n" +
                                    "\n" +"После того как ты *выбрал дату и время, нажми confirm*.\n" +"Заполни *название ивента, добавь почту sales@rh-s.com. В комментариях обязательно добавь описание ивента*.\n" +
                                    "\n" +"Нажми *кнопку add guests, и добавь почту info@rh-s.com*.\n" +"\n" +"Подтверди и сохрани наш ивент в календаре клиента.\n" +"\n" +"*Зайди в наш календарь с почты info@rh-s.com*. Найди этот ивент, открой его и добавь всю необходимую информацию о клиенте, которая есть у тебя (сайты компаний, комментарий, имя менеджера и тд).")
                            .send();
                    break;

                case "calNewEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"*Зайди в Гугл Календарь с корпоративной почты* info@rh-s.com .\n" +
                                    "\n" +"*Выбери дату и время*, на которые вы договорились с клиентом.\n" +
                                    "\n" +"*Учитывай часовые пояса* при назначении звонка. В календаре стоит Киевское время (GMT+2). Чтобы не считать время и быть точно уверенным в правильности, используй запрос в гугле: EST to Ukraine time. Гугл выдаст тебе ссылки на онлайн конверторы времени.\n" +
                                    "\n" +"*AM* – это ночное и утреннее время (с полночи 12am до полудня 12pm), *PM* – наоборот дневное и вечернее время (с полудня 12pm до полночи 12am).\n" +
                                    "\n" +"Звонки вноси в календарь с ПН по ПТ, с 9:00 до 18:00. Любое другое время необходимо согласовать с Sales-отделом.\n")
                            .respond();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Выбери *кнопку \"More options\"* и перейди в расширенное меню настроек ивента.\n" +
                                    "Важно - длительность ивента должна быть 30 мин. Обычно дольше разговор не длится.\n" +
                                    "\n" +
                                    "Заполни пустые поля.\n" +
                                    "Заголовок *Сompany name - Remote Helpers*\n" +
                                    "Описание:\n" +
                                    "We are going to discuss options for partnership in managing human resources for \"Name of the partner-company\" in Ukraine.\n" +
                                    "Website: rh-s.com\n" +
                                    "Website: \"his website\"")
                            .send();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Поле *Notifications/Напоминания*.\n" +
                                    "Рекомендуем ставить 2 напоминания: за 20 мин и за 10 мин.\n" +
                                    "Если у тебя только одно поле с уведомлением, то нажми add notification, чтобы добавить второе.\n" +
                                    "\n" +
                                    "*Поле Comments/Комментарий* о заказчике: обязательно пишем имя клиента и ссылку на его линкедин аккаунт, кто ему нужен и зачем (исходя из переписки на линкеде или почте, все что может пригодиться на звонке: инфа о его компании, свободной вакансии; или же он просто сказал, что хочет узнать по какой модели строится наша работа).\n" +
                                    "Все комментарии писать на английском, чтобы клиент тоже понимал о чем речь.")
                            .send();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("*Manager: Your name* (это в Ваших интересах, чтобы мы знали кому дать бонус )\n" +
                                    "\n" +
                                    "*Поле Guests* – сюда внеси имейлы всех тех, кого мы хотим видеть на звонке:\n" +
                                    "- нашу почту sales@rh-s.com,\n" +
                                    "- почту клиента (с его стороны также может быть несколько почт, если он попросил подключить на звонок к примеру его партнера).\n" +
                                    "Им придет оповещение о данном звонке на почту, и перед самым звонком появится оповещение из календаря.\n" +
                                    "\n" + "Нажимаем *SAVE*, и когда появится *Invite external guests, нажимаем YES*.")
                            .send();

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("В СРМ измени статус Лида на Event,  добавь дату и время звонка.\n" +
                                    "\n" +"За 30 мин до звонка, вежливо напомни клиенту, о том, что у нас назначена беседа.\n" +
                                    "Если возникают какие-то обстоятельства и звонок не состоится, сообщи об этом sales менеджеру.\n" +
                                    "После звонка, нужно связаться с sales менеджером и поинтересоваться результатами, после чего поставить статус Call.")
                            .send();
                    break;

                case"calGet":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Открой *новую вкладку в браузере гугл-хром*.\n" +
                                    "\n" +"В правом верхнем углу, *нажми на меню* (в виде точек).\n" +
                                    "Откроется меню расширений Гугл.\n" + "\n" +"Выбери *Календарь*.\n" + "\n" +"Для *назначения ивента, открой календарь с корпоративной почты info@rh-s.com*.")
                            .respond();
                    break;

                case "callFollowUp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Открой _Гугл-календарь_. Создай новое напоминание.\n" +
                                    "*Заголовок* календарного события – *“название компании” FollowUp*\n" +
                                    "\n" +"*Выбери дату* на которую надо сделать напоминание и продолжительность: *ставь галочку НА ВЕСЬ ДЕНЬ*. Так твои ивенты будут отображаться вверху календаря.\n" +
                                    "\n" +"Важно: в календаре *выделяем фоллоу-апы ОРАНЖЕВЫМ цветом*.\n" +
                                    "\n" +"*Заполни карточку напоминания*\n" +"- в описании укажи *описание клиента, его вопросы/ответы, его контакты*.\n" +"- обязательно укажи *Manager* - себя, чтобы мы знали, кто привел лида.\n" +"- в *Guests* - обязательно добавь почту sales@rh-s.com чтобы твое напоминание отобразилось и у ребят, которые непосредственно созваниваются с клиентами.")
                            .respond();
                    break;

                //Тут начинается Апдейт________________________________________________________

                case "updateWhatIs":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Апдейты* - это лиды, которые уже есть в СРМ, были добавлены тобой или другим менеджером более трех месяцев назад.\n" +
                                    "\n" +"Таким лидам *мы пишем повторные сообщения и отправляем повторный коннект*.")
                            .respond();
                    break;

                case "updateWhy":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Эти лидам мы пишем повторные сообщения и отправляем повторный коннект.\n" +
                                    "\n" +"*Так мы напоминаем потенциальным клиентам о себе и своих услугах. Со временем в компаниях меняются задачи, появляются новые проекты и мы можем предложить сотрудничество в нужный момент для лида.*")
                            .respond();
                    break;

                case "updateHowFind":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Как найти апдейтов:\n" +"\n" +"В своей СРМ открой вкладку Leads.\n" +"Выбери необходимые тебе поля:\n" +"*Country* - страна с которой сейчас  работаешь\n" +"*Lead Status* - все статусы, кроме Call\n" +"Нажми *Apply*.\n" + "Далее откроется список всех лидов, которые были внесены за все время под нужным тебе статусом и страной, с которой ты сейчас работаешь.\n" +
                                    "\n" + "Листай вниз страницы, и начинай работать с лидами с конце списка, т.е. с самыми старыми - которые были сделаны 3 и более месяцев назад.\n" +
                                    "\n" + "Проверь дату, когда лид был создан \"CREATED ON\" и дату, когда лиду был сделан последний апдейт UPDATE ON. Обрати внимание на комментарии.")
                            .respond();
                    break;
                case "updateHowMake":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зайди в карту лида, и перейди по ссылке *Contact’s LinkedIn* на страничку лида в LinkedIn.\n" +
                                    "Отправь ему повторный коннект вместе с шаблоном: connect-add note.\n" +
                                    "\n" +
                                    "Далее в СРМ в карте лида нажми *Edit* и отредактируй информацию:\n" +
                                    "- поле *Active Agents* - укажи свои данные, чтобы лид был засчитан именно тебе.\n" +
                                    "- поле *LinkedIn Accounts* - укажи аккаунт с которого ты отправил коннект. \n" +
                                    "- поле *Lead Status* - поставь статус Sent Request\n" +
                                    "- поле *Note* - укажи комментарий - updated (лид обновлен)\n" +
                                    "В завершение редактирования карты лида нажми *Update*.")
                            .respond();
                    break;

                case "updateDayNorm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Норма апдейтов - 120+ компаний в день.")
                            .respond();
                    break;
                //ТУТ Тайм Трекер________________________________________________________

                case "timeTurnOn":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"Зайди в СРМ.\n" +"\n" +"Нажми кнопку Login в правом верхнем углу экрана.\n" +
                                    "\n" +"Введи свою электронную почту в поле Email и пароль в поле Password и нажми кнопку LOG IN.\n" +
                                    "\n" +"При переходе на следующую страницу (вкладка Dashboard) браузер выведет окно запроса на *доступ к данным о твоем местоположении. Нажми “Разрешить”.*\n" +
                                    "\n" +"Итог: *Начало рабочего дня >>> Clock In >>> Обеденный перерыв >>> Clock Out >>> Возвращение с обеденного перерыва >>> Clock In >>> Окончание рабочего дня >>> Clock Out*\n")
                            .respond();
                    break;

                case "timeGoogleSetting":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Нажми кнопку _меню в правом верхнем углу окна браузера_, в появившемся списке нажми на вкладку *Настройки*.\n" +
                                    "\n" +"В списке слева выбери *вкладку Конфиденциальность и безопасность*.\n" +
                                    "\n" +"Пролистай немного вниз пока не встретишь *вкладку Настройки сайтов*, нажми на нее.\n" +
                                    "\n" +"Нажми на *вкладку Посмотреть текущие разрешения и сохраненные данные сайтов*.\n" +
                                    "\n" +"*В поисковой строке в правом верхнем углу введи crm.rh-s.com*, нажми на появившуюся кнопку с этим адресом.\n" +
                                    "\n" +"В появившемся списке найди *строку Геоданные* и справа выбери вариант *Разрешить*.")
                            .respond();

                    break;

                    //Тут бонусы________________________________________________________
                case "bonusMy":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Мы предлагаем бонус за каждый назначенный и состоявшийся звонок. Детальнее разберем бонусную систему ниже.\n" +
                                    "\n" +"200 грн - стандартный бонус за назначенный и состоявшийся звонок с клиентом.\n" +
                                    "+200 грн - если вы назначили звонок, на котором сразу будет собеседование с конкретными кандидатами/кандидатом (без сейлзов).\n" +
                                    "+500 грн - если совершена продажа нашего сотрудника.\n" +
                                    "\n" +"Звонок засчитывается только если:\n" +"а) звонок с клиентом состоялся;\n" +
                                    "б) клиент в полной мере понял, какие услуги мы предлагаем (т.е. Информация предоставлена клиенту корректно);\n" +
                                    "в) лид релевантный.\n" +"\n" +"Бонусы подводятся в конце каждого месяца.\n" +
                                    "Данные суммы не предел, все зависит от вас и ваших стараний.\n" +
                                    "Данные бонусы могут быть первыми признаками вашего карьерного роста в нашей компании, но отнюдь не их пределом. \n")
                            .respond();
                    break;

                case "bonusWhen":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зарплата начисляется два раза в месяц с 5 по 10 и с 20 по 25 числа каждого месяца на карточку или наличными.")
                            .respond();
                    break;

                //Тут лиды________________________________________________________
                case "leadWhichLead":
                case "leadRight":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Обрати внимание:\n" + "\n" +"- на _род деятельности лида_.\n" +"*Добавляем: CEO, COO, Founder, CTO, Owner, Director, co-Founder* (хотя если есть возможность добавить не сооснователя, то отдайте предпочтение другой должности), *Entrepreneur*.\n" +
                                    "\n" +"- на _страну лида_.\n" +"*Работаем с: Australia, Austria, Belgium, Canada, Denmark, Estonia, Finland, France, Germany, Ireland, Italy, Israel, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, USA*.\n" +
                                    "\n" +"- имя и внешность лида.\n" +"Если в профиле у человека стоит одна из вышеперечисленных стран, но тебя смущает его имя или внешность, например он вылитый индус или афроамериканец (упоминаю их, потому что они часто маскируются), то такие люди нам тоже не подходят.\n" +
                                    "\n" +"- _наличие общих профилей_.\n" +"Может оказаться, что наши менеджеры уже с ним связывались, возможно, мы уже имеем с этим человеком какие-либо деловые отношения. Всегда проверяйте наличие компании, в которой работает лид, в нашей СРМ системе.\n" +
                                    "Если общих профилей больше 8 – это не значит, что не стоит обращать на человека внимание. Все эти люди могут не относиться к нашей компании. Главное – проверить.\n" +
                                    "Проверить можно, кликнув на строчку с количеством общих профилей.\n" +
                                    "\n" +"Если все ок, то жми кнопку *Connect*.")
                            .respond();
                    break;
                case "leadInfoFrom":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Если клиент заинтересован и хочет назначить звонок, твоя задача уточнить у него:\n" +
                                    "- *время*, когда ему будет удобно созвониться\n" +"- *временную зону или город* в котором он находится\n" +"- *почту*, куда отправить приглашение на звонок\n" +"- где ему удобней было бы созвониться: Skype, Whatsapp, Hangout, Zoom.\n" +
                                    "\n" +"Также *не забывай всю информацию о лиде (компания, страна, любые контакты, и тд - все, что может быть полезно для последу.щей коммуникации с клиентом)*.")
                            .respond();
                    break;

                case "leadAdd":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Чтобы добавить лида в СРМ:\n" +
                                    "\n" +"Открой СРМ\n" +"Перейди на вкладку *Leads*, в правом верхнем углу нажми на кнопку *Add new lead*.\n" +
                                    "\n" +"Заполни необходимую информацию о лиде.\n" +
                                    "Поля помеченые красной звездочкой* обязательно должны быть заполнены, иначе новый лид не будет сохранен.\n" +
                                    "\n" +"Поля, которые *заполняются автоматически: LG Manager, Lead Source, Lead Status и Country*.\n" +
                                    "\n" +"В *поле Linkedin accounts* выбери аккаунт на котором сейчас работаешь.\n" +
                                    "\n" +"В *поле Company* добавь компанию лида.\n" +
                                    "\n" +"В *Company Size, Industry и Position* выбери подходящий вариант из выпадающего списка.\n" +
                                    "\n" +"Нажми *Save*.")
                            .respond();
                    break;

                case "leadCardFill":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*В карту нового лида необходимо внести всю информацию о нем, которую тебе удалось собрать в процессе коммуникации.*\n" +
                                    "Поля помеченые красной звездочкой* обязательно должны быть заполнены, иначе новый лид не будет сохранен.\n" +
                                    "\n" +"*Поля, которые заполняются автоматически*: LG Manager, Lead Source, Lead Status и Country.\n" +
                                    "\n" +"В *поле Linkedin accounts* выбери аккаунт на котором сейчас работаешь.\n" +
                                    "\n" +"В *поле Company* добавь компанию лида.\n" +"\n" +"В *Company Size, Industry и Position* выбери подходящий вариант из выпадающего списка.\n" +
                                    "\n" +"Нажми *Save*.")
                            .respond();
                    break;

                case "leadWhoFollowUp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Если клиент просит связаться с ним позже или отвечает, что сейчас наши услуги не актуальны, но через пару месяцев он начинает новый проект и они понадобятся, нужно ставить клиента на фоллоу ап*.\n" +
                                    "\n" +"Если ты отправлял презентацию, этот инструмент также может пригодиться чтобы спросить, что клиент о ней думает.")
                            .respond();
                    break;

                case "leadHowMuch":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Зайди в свою СРМ. Открой вкладку *Lead Reports*.\n" +
                                    "\n" +"Используя фильтры ты можешь просмотреть всех лидов, которых ты внес в СРМ.")
                            .respond();
                    break;

                case "leadDayNorm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Норма - *40-50 лидов в день*.")
                            .respond();
                    break;

                case "leadHowFind":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Всех лидов ты можешь просмотреть во вкладке *Leads* в своей СРМ.\n" +
                                    "Для того, чтобы найти конкретного лида, используй фильтры.")
                            .respond();
                    break;

                //Тут Шаблоны________________________________________________________

                case "blueFirstCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Приветствие:\n" +
                                    "Hello %Name%,\n" +
                                    "Would you like to hire our Remote Marketing Employees from Ukraine part-time or full-time? Can we connect?\n")
                            .respond();
                    break;

                case "blueSecondCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Варианты коннекта:\n" + "Второе сообщение после коннекта: \n" + "What information are you interested in?\n" +
                                    "1) more info about our company;\n" +"2) available positions to choose from (departments)\n" +
                                    "3) prices and conditions\n" + "4) Do you want to share your company needs?\n" +
                                    "Appreciate your answer,\n" +"Remote Helpers,\n" +"https://www.rh-s.com\n" +
                                    "Второе сообщение (негативный ответ/отказ):\n" +"Ok, thanks your answer \n" +
                                    "May I ask you for an email to send our presentation in case of your future needs?\n")
                            .respond();

                    break;

                case "blueThirdCon":

                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Третье сообщение (после ответа на второе сообщение):\n" +
                                    "Мы описываем для тебя несколько вариантов ответов, в зависимости от того, о чем тебя может спросить клиент.\n" +
                                    "Презентация - https://docs.google.com/presentation/d/e/2PACX-1vSleEpaKL1TOz8NV7d2OY7wtS111idiEdrSM_f88_GiCe-F4pqdzX7SUwZn3vDOog/pub?start=true&loop=true&delayms=15000&slide=id.g1180199a397_3_22\n" +
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
                            .setContent("Если *клиент спрашивает о специальностях*, вариант твоего ответа:\n" +
                                    "\n" +"Candidates we offer you to hire can work in the following positions: Lead Generation Managers, Customer Support, Personal Assistants, Social Media Managers, Designers, Media Buyers, PPC, SEO, AdOps, English teachers etc. Can we set a call to see if we can find a fit for you?\n" +
                                    "\n" +"*Следующее сообщение может использоваться в случаях, когда клиент спрашивает про частичную занятость или о конкретном заказе*. Например клиент интересуется не могли бы мы сделать для него сайт?\n" +
                                    "\n" +"“To keep price advantages comparing to freelancers that charge minimum 15€ per hour, our maximum cost is 7,5€ per hour. But there should be enough work for a full time – it is 160 hours a month, 5 days a week,  8 hours a day. So the final prices are 1200€ for designers, 1200€ for marketers and 1000€ for managers per month. Can we set a call to discuss all the details?”")
                            .respond();
                    break;

                case "blueExp":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("How much experience do they have? Can you send me some CVs?\n" +
                                    "“Most of our employees have a good knowledge of English, worked a while in digital marketing, but still there should be a team lead from your side to run training. Each company has its own specifics and employees need time to get into things.”\n" +
                                    "Anyway you can find CVs of available employees on our Telegram channel or website:\n" +
                                    "https://t.me/RemoteHelpers\n" +"https://www.rh-s.com/\n" +
                                    "Подбор кандидатов:\n" +"Let me share the most suitable candidates for you, you can look through their CVs and let me know who is the best match for you:\n" +
                                    "...link\n" +"...link\n" +"...link\n" +
                                    "What about setting up a call with candidates? This will be the best way to get to know them and learn more about their experience, skills, and so on.\n")
                            .respond();
                    break;

                case "blueClientBad":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Если *клиент написал, что не заинтересован в нашем предложении*, твой ответ:\n" +
                                    "\n" +"“Thank you for the reply. May I send you our presentation and prices in case you will need our services in the future?”\n" +
                                    "\n" +"*Отправляй информацию, а потом уточни у клиента получил ли он наше письмо, есть ли какие-то вопросы*. \n" +
                                    "Нужно отправлять письма всегда, после того как клиент дал свою почту, так он не потеряет наши контакты и сможет обратиться в нужный момент.\n" +
                                    "И конечно же, *не забудь поставить в календаре ивент на FollowUp*, чтобы не потерять контакт, а связаться с ним в будущем +статус FollowUp и дату в CRM.")
                            .respond();
                    break;

                case "blueClientGood":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("\n" +"Если *клиент заинтересован и хочет назначить звонок*, твоя задача уточнить у него:\n" +
                                    "- время, когда ему будет удобно созвониться\n" +
                                    "- часовую зону или город в котором он находится\n" +
                                    "- почту, куда отправить приглашение на звонок\n" +
                                    "- где ему удобней было бы созвониться: скайп, вотсап, хэнгаут, zoom\n" +
                                    "После чего, нужно будет сравнить сколько это будет по нашему времени и создать ивент в календаре\n")
                            .respond();
                    break;

                //Тут начинается Коннект_______________________________________________________________

                case "conFirstCon":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Приветствие:\n" +"Hello %Name%,\n" +
                                    "\n" +"Варианты коннекта:\n" +
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
                            .setContent("Варианты коннекта:\n" +"Второе сообщение после коннекта:\n" +"What information are you interested in?\n" +
                                    "1) more info about our company;\n" +"2) available positions to choose from (departments)\n" +
                                    "3) prices and conditions\n" +"4) Do you want to share your company needs?\n" +
                                    "Appreciate your answer,\n" +"Remote Helpers,\n" +
                                    "https://www.rh-s.com\n" +"Второе сообщение (негативный ответ/отказ):\n" +
                                    "Ok, thanks your answer \n" + "May I ask you for an email to send our presentation in case of your future needs?\n")
                            .respond();
                        break;

                case "conThirdCon":

                    step2MessageCompInteraction.createFollowupMessageBuilder()
                            .setContent("Третье сообщение (после ответа на второе сообщение):\n" +
                                    "Мы описываем для тебя несколько вариантов ответов, в зависимости от того, о чем тебя может спросить клиент.\n" +
                                    "Презентация - https://docs.google.com/presentation/d/e/2PACX-1vSleEpaKL1TOz8NV7d2OY7wtS111idiEdrSM_f88_GiCe-F4pqdzX7SUwZn3vDOog/pub?start=true&loop=true&delayms=15000&slide=id.g1180199a397_3_22\n" +
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
                            .setContent("Зайди на свою страницу в Линкедин, затем во вкладку My Network, расположенную на верхней панели сайта.\n" +
                                    "\n" +"Внизу страницы ты найдешь все профили (connections), которые могут быть тебе полезны.\n" +
                                    "\n" +"Обрати внимание:\n" +"\n" +"- на _род деятельности лида_.\n" +
                                    "*Добавляем: CEO, COO, Founder, CTO, Owner, Director, co-Founder* (хотя если есть возможность добавить не сооснователя, то отдайте предпочтение другой должности), *Entrepreneur*.\n" +
                                    "\n" +"- на _страну лида_.\n" +"*Работаем с: Australia, Austria, Belgium, Canada, Denmark, Estonia, Finland, France, Germany, Ireland, Italy, Israel, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, USA*.\n" +
                                    "\n" +"- имя и внешность лида.\n" +"Если в профиле у человека стоит одна из вышеперечисленных стран, но тебя смущает его имя или внешность, например он вылитый индус или афроамериканец (упоминаю их, потому что они часто маскируются), то такие люди нам тоже не подходят.\n" +
                                    "\n" +"- _наличие общих профилей_.\n" +"Может оказаться, что наши менеджеры уже с ним связывались, возможно, мы уже имеем с этим человеком какие-либо деловые отношения. Всегда проверяйте наличие компании, в которой работает лид, в нашей СРМ системе.\n" +
                                    "Если общих профилей больше 8 – это не значит, что не стоит обращать на человека внимание. Все эти люди могут не относиться к нашей компании. Главное – проверить.\n" +
                                    "Проверить можно, кликнув на строчку с количеством общих профилей.\n" +
                                    "\n" +"Если все ок, то жми кнопку *Connect*.")
                            .respond();
                    break;

                //Тут начинает Фоллу-ап

                case "followupWhat":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Фоллоу-ап -это такой удобный инструмент, чтобы напомнить о себе человеку, который, возможно, о нас забыл. *Фоллоу-ап необходим для того, чтобы поддерживать интерес заказчика, ведь вероятность того, что сделка совершится, больше, если между нами и клиентом выстроены доверительные отношения.*\n" +
                                    "\n" +"*Если какой-либо клиент просит связаться с ним позже или отвечает, что сейчас наши услуги не актуальны, но через пару месяцев он начинает новый проект и они понадобятся, нужно ставить клиента на фоллоу ап.*\n" +
                                    "\n" +"Если вы отправляли презентацию, этот инструмент также может пригодиться чтоб спросить, что клиент о ней думает.")
                            .respond();
                    break;


                case "followupCal":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Открой *Гугл-календарь*. Создай *новое напоминание*.\n" +
                                    "Заголовок календарного события – *“название компании” FollowUp*\n" +
                                    "\n" +"*Выбери дату на которую надо сделать напоминание и продолжительность: ставь галочку НА ВЕСЬ ДЕНЬ*. Так твои ивенты будут отображаться вверху календаря.\n" +
                                    "\n" +"Важно: в календаре *выделяем фоллоу-апы ОРАНЖЕВЫМ цветом*.\n" +
                                    "\n" +"*Заполни карточку напоминания*\n" +
                                    "- в описании укажи все описание о клиенте, его вопросах/ответах, его контакты.\n" +
                                    "- обязательно укажи Manager - себя, чтобы мы знали, кто привел лида.\n" +
                                    "- в Guests - обязательно добавь почту sales@rh-s.com чтобы твое напоминание отобразилось и у ребят, которые непосредственно созваниваются с клиентами.")
                            .respond();
                    break;

                case "counIWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*Каждый менеджер лидогенерации работает с определенной страной*. Т.е. за тобой уже закреплена определенная страна, в которой тебе нужно искать лидов.\n" +
                                         "\n" +"Для того, чтобы ты начал поиск лидов, нужно узнать, с какой страной ты работаешь на определенном аккаунте. Для этого:\n" +
                                         "\n" +"- зайди в свою СРМ\n" +
                                         "\n" +"- нажми кнопку *Add new lead*\n" +
                                         "- *выбери Линкедин-аккаунт, который закреплен за тобой* (аккаунт, который тебе выдал аккаунт-менеджер специально для работы)\n" +
                                         "- *в СРМ в поле \"Country\" появится название страны, с которой тебе нужно работать*\n" +
                                         "Это поле нельзя отредактировать. Его настраивает аккаунт-менеджер.")
                            .respond();
                    break;

                case "counCanChange":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Твой аккаунт Линкедин закреплен за определенной страной. Эти данные может заменить только аккаунт-менеджер.")
                            .respond();
                    break;

                case "counNotWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Страны, которые *мало интересны*\n" +
                                    "_Japan, Singapore, Cyprus, China_. Так как коммуникация с азиатами трудновата из-за плохого английского, рассматриваем каждый случай отдельно.\n" +
                                    "\n" +"Страны, с которыми *не работаем*\n" +"Andorra, Argentina, Bahamas, Belarus, Bolivia, Brazil, Brunei, Bulgaria, Chile, Colombia, Costa, Rica, Dominican Republic, Ecuador, Egypt, Fiji, Guyana, Indonesia, Kazakhstan, Macao, Malaysia, Mexico, Morocco, Nepal, Oman, Panama, Paraguay, Peru, Philippines, Puerto, Rico, Qatar, Republic of Korea (South), Romania, Russian Federation, Saudi Arabia, South, Africa, Thailand, Turkey, Ukraine, " +
                                    "United Arab Emirates, Uruguay, Vanuatu, Albania, Algeria, Angola, Armenia, " + "Azerbaijan, Bahrain, Bangladesh, Barbados, Belize, Benin, Botswana, Burkina, Faso, Burundi, Cambodia, Cameroon," +
                                    " Cape Verde, Chad, Comoros, Congo, El Salvador, Ethiopia, Gabon, Georgia, Guatemala, Guinea, Haiti, Honduras, " +
                                    "India, Iraq, Jamaica, Jordan, Kenya, Kuwait, Kyrgyzstan, Laos, Lebanon, Lesotho, Macedonia, Madagascar, Mali, Mauritania, Mauritius, Moldova, Mongolia, Mozambique, Namibia, Nicaragua, Niger, Nigeria, Pakistan, Senegal, Sri Lanka, Suriname, Swaziland, Tajikistan, Tanzania, Togo, Trinidad and Tobago, Tunisia, Turkmenistan, Uganda, Uzbekistan, Vietnam, Zambia.\n" +
                                    "\n" +"Страны СНГ – Украина, Россия, Беларусь, Молдова, Казахстан, Грузия, Азербайджан, Армения, Узбекистан, Таджикистан.\n" +
                                    "Практически все страны Африки и некоторые страны Азии – также являются неактуальными для нашего бизнеса.")
                            .respond();

                    break;

                case "counWork":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Страны, с которыми мы работаем:\n" +"_Australia, Austria, Belgium, Canada, Denmark, Finland, France, Germany, Ireland, Italy, Luxembourg, Netherlands, Norway, Spain, Sweden, Switzerland, United Kingdom, United States of America, Israel, Latvia, Lithuania, Estonia, Czech Republic_")
                            .respond();

                    break;

                //ТУт начинаются Статусы

                case "statEvent":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Статус лида в СРМ при назначении ивента - *Event*")
                            .respond();
                    break;

                case "statUpdate":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Если ты ищешь лидов в СРМ для апдейта, то в Lead Status выбери все статусы, кроме Call.\n" +
                                    "\n" +"Если уже сделал апдейт, то в карте лида, в поле Lead Status поставь - *Sent Request*.")
                            .respond();
                    break;

                case "statCrm":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("*sent request* - отправлена заявка на коннект\n" +
                                    "\n" +"*connected* - лид принял твою заявку на коннект, и у тебя есть его контакты (почта и телефон)\n" +
                                    "\n" +"*interested* - лид ответил на твое сообщение и заинтересовался нашим предложением\n" +
                                    "\n" +"*not interested* - лид ответил, что наше предложение его не интересует\n" +
                                    "\n" +"*ignoring* - лид общался с тобой некоторое время, а потом перестал отвечать на сообщения\n" +
                                    "\n" +"*follow up* - лид ответил, что в данный момент не заинтересован в нашем предложении, но возможно рассмотрит его в будущем\n" +
                                    "\n" +"*event* - лид согласился на звонок и мы делаем ивент в календаре\n" +
                                    "\n" +"*call* - когда сейлз менеджер сообщил тебе, что звонок состоялся\n" +
                                    "\n" +"*not relevant for us* - когда случайно добавил в СРМ лида, который нам не подходит")
                            .respond();
                    break;

                //Тут начинается "Ещё"
                     case "menu":
                     case "moreBack":
                     case "employeeOld":
                    step2MessageCompInteraction.createImmediateResponder()
                            .setContent("Привет!\n" +
                                    "Ты находишься в меню, в котором собраны ответы на популярные вопросы по работе отдела LeadGeneration.\n" +
                                    "\n" +
                                    "Выбери категорию своего вопроса:")
                            .addComponents(
                                    ActionRow.of(Button.primary("google", "Гугл"),
                                            Button.primary("linked", "Линкедин"),
                                            Button.primary("CRM", "CPM")),
                                    ActionRow.of(Button.primary("calendar", "Календарь"),
                                            Button.primary("update", "Апдейт"),
                                            Button.primary("timetrack", "Тайм-треккер")),
                                    ActionRow.of(Button.primary("contacts", "Контакты"),
                                            Button.primary("bonus", "Бонусы и ЗП"),
                                            Button.primary("leads","Лиды")),
                                    ActionRow.of(Button.primary("blueprints","Шаблоны"),
                                            Button.primary("connect","Коннект"),
                                            Button.primary("followup","Фоллоуап")),
                                    ActionRow.of(Button.primary("country","Страна"),
                                            Button.primary("status","Статус"),
                                            Button.primary("more","Ещё")))
                            .respond();
                    break;

                case "menu2":
                        FeedbackResponder.modalResponder(stage, step2MessageCompInteraction);
                    break;
                default:
                    System.out.println("asd");
                    if (SHOULDSEND)
                    { // TODO: переименовать в главное меню и "назад"
                    step2MessageCompInteraction.createFollowupMessageBuilder().addComponents(
                            ActionRow.of(Button.primary("menu", "Обратно в глав. меню"), Button.primary("menu2", "Обратно в пред. меню"))).send()
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
                            feedBackloop.respondWithModal("feedbackResponseModal","Ваш отзыв:",ActionRow.of(TextInput.create(TextInputStyle.SHORT, "feedbackResponse", "Отзыв")));


                            break;

                        case "feedback1":
                            SHOULDSEND = false;
                            nameRecord = true;

                            String modalTest = "Добро пожаловать в компанию!";


                            feedBackloop.respondWithModal("mFeedback", modalTest,ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mName", "Имя")),ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mSure", "Фамилия")), ActionRow.of(TextInput.create(TextInputStyle.SHORT, "mPhone", "Телефон")));

                            /*
                            feedBackloop.createImmediateResponder()
                                    .setContent("Если есть проблемы/вопросы/предложения")
                                    .addComponents(ActionRow.of(Button.primary("feedback", "Обратная связь")))
                                    .respond();

                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Продолжаем")
                                    .addComponents(
                                            ActionRow.of(Button.primary("employeeNew","Новый сотрудник")),
                                            ActionRow.of(Button.primary("employeeOld","Уже работаешь с нами")),
                                            ActionRow.of(Button.primary("feedback", "Вернуться назад"))) */
                            break;





                        case "employeeNew":
                            SHOULDSEND = false;
                            feedBackloop.createImmediateResponder().setContent("Ты подписал *договор и заполнил карту сотрудника*?")
                                    .addComponents(ActionRow.of(Button.primary("empnew1", "Сделано! Что дальше?")),
                                            ActionRow.of(Button.primary("notyet1", "Ещё нет.")))
                                    .respond();

                            break;

                        case "notyet1":
                            SHOULDSEND = false;
                            feedBackloop.createImmediateResponder()
                                    .setContent("Пожалуйста, напиши рекрутеру, который с тобой общался.")
                                    .addComponents(ActionRow.of(Button.primary("empnew1","Далее")))
                                    .respond();

                            break;

                        case "empnew1":
                            SHOULDSEND = false;
                            feedBackloop.createImmediateResponder()
                                    .setContent("Ты уже создал *Гугл-почту*?")
                                    .addComponents(ActionRow.of(Button.primary("empnew2", "Сделано! Что дальше?")),
                                            ActionRow.of(Button.primary("notyet2", "Ещё нет. Покажи как")))
                                    .respond();
                            break;

                        case "notyet2":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Окей. Я помогу тебе это сделать." + "\n" + "*Зайди на Gmail и нажми \"Создать аккаунт\"*" + "\n" + "Далее следуй инструкции Gmail...")
                                    .addComponents(ActionRow.of(Button.primary("notyet2_continue", "Что дальше?")))
                                    .append("https://www.google.com/intl/uk/gmail/about/")
                                    .respond();
                            break;

                        case "notyet2_continue":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Запрос на закрепление номера телефона к аккаунту - нажми \"Пропустить\""+ "\n" + "*Номер телефона не указывай!*\n" +
                                            "\n" + "Как резервную почту, можешь указать - niko@rh-s.com\n" +
                                            "\n" + "Укажи свою дату рождения и свой пол." +"\n"+ "*Принимай правила Google*, пролистывай их до конца.\n" +
                                            "\n" +"https://youtu.be/7rVH13AHp5o")
                                    .addComponents(ActionRow.of(Button.primary("empnew2", "Круто! Что дальше?")),
                                            ActionRow.of(Button.primary("feedback", "Обратная связь")))
                                    .respond();
                            break;

                        case "empnew2":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Ты уже настроил Хром-пользователя?")
                                    .addComponents(ActionRow.of(Button.primary("empnew3", "Сделано, Что дальше?")),
                                            ActionRow.of(Button.primary("notyet3", "Нет, покажи как.")))
                                    .respond();
                            break;

                        case "notyet3":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Я помогу тебе это сделать.\n" + "*Открой браузер Гугл Хром*. Нажми на окошко пользователя в верхнем правом углу. Кликай *Управлять пользователями*.\n")
                                    .respond();
                            feedBackloop.createFollowupMessageBuilder()
                                    .addAttachment(new File(path+ "/chromeuser1.png"))
                                    .send().join();



                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Выбери *Добавить пользователя* в нижнем правом углу.\n" +
                                            "Назови своего пользователя так *ИМЯ_LinkedIn*.\n" + "Добавь аватарку.\n" +
                                            "Поставь галочку *Создать ярлык этого профиля на рабочем столе*.\n" + "Нажми *Добавить*.")

                                    .addAttachment(new File(path+ "/chromeuser3.png"))
                                    .send().join();
                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Подключи рабочую почту, которую ты создал, к Хром-пользователю. \n" + "Вуаля! Новый пользователь ГуглХром готов.\n" +
                                            "\n" + "_Автоматически откроется новая вкладка Хрома под новым пользователем_.\n" +
                                            "\n" + "_На рабочем столе появился ярлык для входа именно в этого пользователя_.")
                                    .addComponents(ActionRow.of(Button.primary("empnew3", "Круто! Что дальше?")),
                                    ActionRow.of(Button.primary("feedback", "Обратная связь")))
                                    .send();
                            break;


                        case"empnew3":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Молодец! \n" +"\n" +"А *Линкедин-аккаунт* уже сделал?")
                                    .addComponents(ActionRow.of(Button.primary("empnew4", "Круто! Что дальше?")),
                                            ActionRow.of(Button.primary("notyet4", "Ещё нет, покажи как.")))
                                    .respond();
                            break;

                        case"notyet4":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Окей, я помогу тебе создать аккаунт на Линкедин \n" +
                                            "\n" + "_Нажми на кнопку_")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_1","Шаг Первый")))
                                    .respond();
                            break;

                        case "notyet4_1":
                            feedBackloop.createImmediateResponder()
                                    .setContent("1) Зайди на LinkedIn.\n" +
                                            "\n" +"2) Начни регистрацию.\n" +
                                            "\n" +"3) *Зарегистрируй профиль Линкедин на Гугл-почту, которую ты создал ранее*.\n" +
                                            "ВАЖНО: *вся информация в твоем профиле должна быть на английском* так, как вся переписка с лидами ведется именно на этом языке.\n" +
                                            "\n" +"https://youtu.be/SlvxQQTFFxg"+ "\n" +"\n"+ "Готов?\n" +
                                            "Переходи ко второму шагу")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_2","Шаг Второй")))
                                    .respond();
                            break;

                        case "notyet4_2":

                            feedBackloop.createImmediateResponder()
                                    .setContent("*Укажи свои настоящие имя и фамилию на английском языке*.\n" +
                                            "Запрещено: указывать никнеймы или сокращения имени.\n" +
                                            "\n" +
                                            "*Загрузи свое фото* \n" +
                                            "Параметры фото: портрет, без лишнего фона, желательно более официальное.\n" +
                                            "\n" +"*Переведи профиль на английский язык*\n" +
                                            "Как это сделать:\n" +
                                            "- в правом верхнем углу ты видишь свое фото\n" +
                                            "- нажми на стрелочку под фото\n" +
                                            "- откроется меню\n" +
                                            "- выбери \"Язык/Language\"\n" +
                                            "\n" +"Если готов, переходи к третьему шагу")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_3","Шаг Третий")))
                                    .respond();
                            break;

                        case "notyet4_3":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Поле *Образование/Education*\n" +
                                            "Укажи актуальные данные о своем высшем образовании на английском языке.\n" +
                                            "\n" +"Если ты еще студент, в поле \"Дата окончания\" укажи 2020 год или более ранний, а в поле \"Дата начала\" - год на 4-5 лет раньше.\n" +
                                            "\n" +"Готов? Жми кнопку\n")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_4","Шаг Четвёртый")))
                                    .respond();
                            break;

                        case "notyet4_4":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Поле *Опыт работы/Edit experience*\n" +
                                            "\n" +
                                            "*Заполни в точности по инструкции*\n" +
                                            "\n" +
                                            "- должность: Account manager\n" +
                                            "- график работы: Full-time\n" +
                                            "- компания: Remote Helpers\n" +
                                            "- локация: твое текущее местоположение (Город, Украина)\n" +
                                            "- время работы в компании: любой месяц/год до настоящего времени + поставь галочку to present.\n" +
                                            "\n" +"Готово? Жми кнопку")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_5","Шаг Пятый")))
                                    .respond();
                            break;

                        case "notyet4_5":
                            feedBackloop.createImmediateResponder()
                                    .setContent("Поле *Навыки/Skills*\n" +
                                            "*Указывай скиллы на английском языке*.\n" +
                                            "\n" +"Список твоих возможных скиллов:\n" +
                                            "- Email marketing\n" +"- Lead Generation\n" +
                                            "- Social media marketing\n" +"- Online Advertising\n" +"- Data Analysis\n" +
                                            "- Searching skills\n" +"- Targeting\n" +"- WordPress\n"
                                            +"- English language\n" +"- Design\n" +"- и другие навыки из выпадающего списка.\n" +
                                            "\n" +"*Используй штук 5-8*\n" +
                                            "\n" +"Готово? Жми кнопку")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_6","Шаг Шестой")))
                                    .respond();
                            break;

                        case "notyet4_6":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Замени *статус профиля*.\n" +
                                            "Как это сделать:\n" +
                                            "- открой свой профиль\n" +"- нажми на карандашик справа от фото профиля\n" +
                                            "- замени поле *Headline/Статус*\n" +"\n" +"Варианты замены:\n" +
                                            "\n" +"_Hire online full-time remote employees| Marketing| Content Managers| SMM| Designers| Devs_\n" +
                                            "\n" +"_Dedicated virtual assistants in Ukraine: Lead Generation| SMM| Media| Design| Developers_\n" +
                                            "\n" +"_Build your online team in few clicks| Lead Generation| Marketing| Media| Design| Devs_\n" +
                                            "\n" +"Тебе не обязательно копировать слово в слово. Основной посыл: мы предлагаем клиентам расширить их команду, наняв удаленных сотрудников из Украины, специальности видите выше.\n" +
                                            "\n" +"Готово? Жми кнопку")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_7","Шаг Седьмой")))
                                    .respond();
                            break;


                        case "notyet4_7":

                            feedBackloop.createImmediateResponder()
                                    .setContent("*Красивая ссылка на твой профиль*\n" +
                                            "\n" +"Как это сделать:\n" +"- зайди на свою страницу\n" +
                                            "- _в правом верхнем углу наведи мышку на свое фото, откроется выпадающий список_\n" +
                                            "- выбери на нем _View profile_\n" +
                                            "- на открывшейся странице в правом верхнем углу нажимаем _Edit public profile & URL_\n" +
                                            "- снова в правом верхнем углу нажми на _Edit your custom URL_, внизу будет твоя ссылка и значок карандаша\n" +
                                            "- нажми на карандашик и удали все ненужные цифры и символы. Оставь только свое имя и фамилию.\n" +
                                            "- нажми _Save_.\n" +"\n" +"URL обновится в течении нескольких минут.\n" +
                                            "\n" +"Готово? Жми кнопку")
                                    .addComponents(ActionRow.of(Button.primary("notyet4_8","Шаг Восьмой")))
                                    .respond();

                            break;
                    //https://www.linkedin.com/search/results/people/?currentCompany=%5B%2210127817%22%5D&origin=COMPANY_PAGE_CANNED_SEARCH&sid=cst
                        case "notyet4_8":

                            feedBackloop.createImmediateResponder()
                                    .setContent("Расширь сеть своих контактов. Добавь в друзья своих коллег.\n" +
                                            "\n" +
                                            "*Вариант 1 - зайди на страницу компании Remote Helpers в Линкедин, в раздел сотрудники*\n" +
                                             "https://www.linkedin.com/search/results/people/?currentCompany=%5B%2210127817%22%5D&origin=COMPANY_PAGE_CANNED_SEARCH&sid=cst" + "\n"+
                                            "*Вариант 2 - используй поиск в Гугл*\n" +
                                            "https://www.google.com/search?q=site%3Alinkedin.com+remote+helpers&oq=site%3Alinkedin.com+remote+helpers")

                                    .respond();

                            feedBackloop.createFollowupMessageBuilder()
                                    .setContent("Вуаля! Профиль в Линкедин готов!")
                                    .addComponents(ActionRow.of(Button.primary("empnew4", "Далее! Что дальше?")))
                                    .send().join();
                            break;



                        case "empnew4":
                            nameRecord = true;

                            feedBackloop.respondWithModal("accountEnter", "Отправь доступы к Гугл и ЛинкедИн",ActionRow.of(TextInput.create(TextInputStyle.SHORT,"gName","Гугл меил")),ActionRow.of(TextInput.create(TextInputStyle.SHORT,"gPass","Гугл пароль")),ActionRow.of(TextInput.create(TextInputStyle.SHORT,"lName","Linked In меил:")), ActionRow.of(TextInput.create(TextInputStyle.SHORT,"lPass","LinkedIn пароль")));

                            break;

                    }


                    api.addModalSubmitListener(modalEvent ->
                            {

                                ModalInteraction modalInteraction = modalEvent.getModalInteraction();
                                String modalInteractionCustomId = modalInteraction.getCustomId();

                                if(Objects.equals(modalInteractionCustomId, "mFeedback"))
                                {
                                    SHOULDSEND = false;
                                    modalInteraction.createImmediateResponder().setContent("Продолжаем")
                                            .addComponents(
                                                    ActionRow.of(Button.primary("employeeNew","Новый сотрудник")),
                                                    ActionRow.of(Button.primary("employeeOld","Уже работаешь с нами")),
                                                    ActionRow.of(Button.primary("feedback", "Вернуться назад")))
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
                                        modalInteraction.createImmediateResponder().setContent("У вас ошибка!"+"\n"+ " Пожалуйста, нажмите на предыдущую кнопку и полностью заполните поля.").respond();
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
                                        modalInteraction.createImmediateResponder().setContent("У вас ошибка!"+"\n"+ " Пожалуйста, нажмите на предыдущую кнопку и полностью заполните поля.").respond();
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

                                        modalInteraction.createImmediateResponder().setContent("Продолжаем")
                                                .addComponents(
                                                        ActionRow.of(Button.primary("employeeNew","Новый сотрудник")),
                                                        ActionRow.of(Button.primary("employeeOld","Уже работаешь с нами")),
                                                        ActionRow.of(Button.primary("feedback", "Вернуться назад")))
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
                                    modalInteraction.createFollowupMessageBuilder().setContent("Спасибо за Ваш отзыв! После рассмотрения, мы свяжемся с Вами!").send();
                                }


                            });
                });
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());


        //TODO: Бот, проверяющий присутствие человека он-лайн, через личку, с таймером в 5 минут. Если не отметился в дискорде, то ответ в вайбер

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

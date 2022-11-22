package com.Discordbot;

import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.ModalInteraction;

public class FeedbackResponder2
{
    public static void feedbackResponer(int param, MessageComponentInteraction componentInteraction)
    {
        switch (param)
        {
            case 1:
                //FeedbackResponder.feedbackResponer(1, componentInteraction);
                componentInteraction.createImmediateResponder()
                        .setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("googChromeUserCreate", "Новый Гугл пользователь")),
                                ActionRow.of(Button.primary("googleFindCol", "Найти коллег")),
                                ActionRow.of(Button.primary("googleCalendar", "Где Гугл календарь")),
                                ActionRow.of(Button.primary("googleSearch", "Операторы поиска")),
                                ActionRow.of(Button.primary("googleUsefulAddons", "Полезные аддоны")) //,
                        )
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;

            case 2:
                componentInteraction.createImmediateResponder()
                        .setContent("Linked In")
                        .respond();

               componentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                        .addComponents(
                                ActionRow.of(Button.primary("linkCleanOld", "Чистка старых заявок")),
                                ActionRow.of(Button.primary("linkAccBan", "Бан аккаунта")),
                                ActionRow.of(Button.primary("linkAccLimits", "Лимиты на аккаунте")),
                                ActionRow.of(Button.primary("linkAccCountry", "Как поменять страну")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;
       // componentInteraction.createImmediateResponder().setContent("Asda").respond();
        }
    }
    public static void modalResponder(int param, ModalInteraction componentInteraction)
    {
        switch (param)
        {
           /* case 0:

                componentInteraction.createImmediateResponder().setContent("Спасибо за Ваш Отзыв!").respond();
                break;*/

            case 1:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("googChromeUserCreate", "Новый Гугл пользователь")),
                                ActionRow.of(Button.primary("googleFindCol", "найти коллег")),
                                ActionRow.of(Button.primary("googleCalendar", "Где Гугл календарь")),
                                ActionRow.of(Button.primary("googleSearch", "Операторы поиска")),
                                ActionRow.of(Button.primary("googleUsefulAddons", "Полезные аддоны"))  //,
                        ).respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;
            case 2:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("linkCleanOld", "Чистка старых заявок")),
                                ActionRow.of(Button.primary("linkAccBan", "Бан аккаунта")),
                                ActionRow.of(Button.primary("linkAccLimits", "Лимиты на аккаунте")),
                                ActionRow.of(Button.primary("linkAccCountry", "Как поменять страну")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;
            case 3:
                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("crmEnter", "Как войти в СРМ")),
                                ActionRow.of(Button.primary("crmAccess", "Как получить доступ")),
                                ActionRow.of(Button.primary("crmAcc", "Аккаунт для работы в СРМ")),
                                ActionRow.of(Button.primary("crmHowToAddLead", "Как добавить лида")),
                                ActionRow.of(Button.primary("crmLeads", "Вкладка Leads")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                        .addComponents(
                                ActionRow.of(Button.primary("crmLeadReports", "Вкладка Lead Reports")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;
            case 4:
                componentInteraction.createImmediateResponder().setContent("select")
                        .addComponents(
                                ActionRow.of(Button.primary("calEnterMail", "Почта для входа")),
                                ActionRow.of(Button.primary("calLeadEvent", "Ивент в календаре лида")),
                                ActionRow.of(Button.primary("calNewEvent", "Внести ивент в календарь")),
                                ActionRow.of(Button.primary("calGet", "Где взять календарь")),
                                ActionRow.of(Button.primary("calFollowUp", "Фоллу-ап в календаре")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();

                break;
            case 5:

                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("updateWhatIs", "Что такое Апдейт")),
                                ActionRow.of(Button.primary("updateWhy", "Зачем нужен апдейт")),
                                ActionRow.of(Button.primary("updateHowFind", "Как найти апдейтов")),
                                ActionRow.of(Button.primary("updateHowMake", "Как сделать Апдейт")),
                                ActionRow.of(Button.primary("updateDayNorm", "Норма апдейтов в день")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;

            case 6:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("timeTurnOn", "Включить тайм-трекер")),
                                ActionRow.of(Button.primary("timeGoogleSetting", "Настройка Google Chrome")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;

            case 7:
                componentInteraction.createImmediateResponder().setContent("select")
                        .setContent("Влад (аккаунт-менеджер)\n + Kuzmik#5058 + \n" +
                                "+380981101090\n" +"\n" +"Катя Истомина (Бухгалтерия)\n + kaeitlin#6426+\n" +
                                "+380714184225\n" +"\n" +"Юлия Мартынив (Тимлид LeadGeneration)\n +Juliya Martyniv#6454+ \n" +
                                "+380662227034\n" +"\n" +"Наталья Шиянова (Тимлид Sales)\n +Наталья Шиянова#4909+\n" +
                                "+380506284605")
                        .addComponents(ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;

            case 8:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("bonusMy", "Мои бонусы")),
                                ActionRow.of(Button.primary("bonusWhen", "Когда ЗП")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;

            case 9:
                componentInteraction.createImmediateResponder().setContent("Select")
                        .addComponents(
                                ActionRow.of(Button.primary("leadWhichLead", "Каких лидов нужно искать")),
                                ActionRow.of(Button.primary("leadInfoFrom", "Данные от лида")),
                                ActionRow.of(Button.primary("leadAdd", "Добавить лида")),
                                ActionRow.of(Button.primary("leadCardFill", "Заполнить карту лида")),
                                ActionRow.of(Button.primary("leadWhoFollowUp", "Кому делать фоллоу-ап")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("leadRight", "Правильные лиды")),
                                ActionRow.of(Button.primary("leadHowMuch", "Сколько лидов я сделал")),
                                ActionRow.of(Button.primary("leadDayNorm", "Норма лидов в день")),
                                ActionRow.of(Button.primary("leadHowFind", "Как найти нужного лида")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;

            case 10:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("blueFirstCon", "1-ый коннект с лидом")),
                                ActionRow.of(Button.primary("blueSecondCon", "2-ой коннект с лидом")),
                                ActionRow.of(Button.primary("blueThirdCon", "3-ий коннект с лидом")),
                                ActionRow.of(Button.primary("blueAbout", "О специальностях")),
                                ActionRow.of(Button.primary("blueExp", "Об опыте содрудников")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("blueClientBad", "Клиент не заинтересован")),
                                ActionRow.of(Button.primary("blueClientGood","Клиент заинтересован")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .send();
                break;

            case 11:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("conFirstCon", "1-ый коннект с лидом")),
                                ActionRow.of(Button.primary("conSecondCon", "2-ой коннект с лидом")),
                                ActionRow.of(Button.primary("conThirdCon", "3-ий коннект с лидом")),
                                ActionRow.of(Button.primary("conToLead", "Отправить коннект лиду")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;
            case 12:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("followupWhat", "Что такое фоллу-ап")),
                                ActionRow.of(Button.primary("followupCal", "Фоллоу-ап в календаре")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;
            case 13:
               componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("counIWork", "С какой страной я работаю")),
                                ActionRow.of(Button.primary("counCanChange", "Можно ли изменить страну")),
                                ActionRow.of(Button.primary("counNotWork", "Страны: НЕ работаем")),
                                ActionRow.of(Button.primary("counWork", "Страны: работаем")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();

                break;
            case 14:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("statEvent", "Статус о назначении ивента")),
                                ActionRow.of(Button.primary("statUpdate", "Статус апдейт")),
                                ActionRow.of(Button.primary("statCrm", "Виды статусов в СРМ")),
                                ActionRow.of(Button.primary("feedback", "Обратная связь")))
                        .respond();
                break;

            case 16:
                componentInteraction.createImmediateResponder()
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

            default: componentInteraction.createImmediateResponder().setContent("Спасибо за ваш отзыв").respond();
        }
    }

}

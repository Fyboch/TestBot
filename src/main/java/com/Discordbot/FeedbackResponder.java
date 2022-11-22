package com.Discordbot;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.ModalInteraction;

public class FeedbackResponder
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
                                ActionRow.of(Button.primary("googChromeUserCreate", "����� ���� ������������")),
                                ActionRow.of(Button.primary("googleFindCol", "����� ������")),
                                ActionRow.of(Button.primary("googleCalendar", "��� ���� ���������")),
                                ActionRow.of(Button.primary("googleSearch", "��������� ������")),
                                ActionRow.of(Button.primary("googleUsefulAddons", "�������� ������")) //,
                        )
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .send();
                break;

            case 2:
                componentInteraction.createImmediateResponder()
                        .setContent("Linked In")
                        .respond();

               componentInteraction.createFollowupMessageBuilder().setContent("Click on one of these Buttons!")
                        .addComponents(
                                ActionRow.of(Button.primary("linkCleanOld", "������ ������ ������")),
                                ActionRow.of(Button.primary("linkAccBan", "��� ��������")),
                                ActionRow.of(Button.primary("linkAccLimits", "������ �� ��������")),
                                ActionRow.of(Button.primary("linkAccCountry", "��� �������� ������")),
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .send();
                break;
       // componentInteraction.createImmediateResponder().setContent("Asda").respond();
        }
    }
    public static void modalResponder(int param, MessageComponentInteraction componentInteraction)
    {
        switch (param)
        {
           /* case 0:

                componentInteraction.createImmediateResponder().setContent("������� �� ��� �����!").respond();
                break;*/

            case 1:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("googChromeUserCreate", "����� ���� ������������")),
                                ActionRow.of(Button.primary("googleFindCol", "����� ������")),
                                ActionRow.of(Button.primary("googleCalendar", "��� ���� ���������")),
                                ActionRow.of(Button.primary("googleSearch", "��������� ������")),
                                ActionRow.of(Button.primary("googleUsefulAddons", "�������� ������")) //,
                        ).respond();

              /*  componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .send();*/
                break;
            case 2:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("linkCleanOld", "������ ������ ������")),
                                ActionRow.of(Button.primary("linkAccBan", "��� ��������")),
                                ActionRow.of(Button.primary("linkAccLimits", "������ �� ��������")),
                                ActionRow.of(Button.primary("linkAccCountry", "��� �������� ������")),
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .respond();
                break;
            case 3:
                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("crmEnter", "��� ����� � ���")),
                                ActionRow.of(Button.primary("crmAccess", "��� �������� ������")),
                                ActionRow.of(Button.primary("crmAcc", "������� ��� ������ � ���")),
                                ActionRow.of(Button.primary("crmHowToAddLead", "��� �������� ����")),
                                ActionRow.of(Button.primary("crmLeads", "������� Leads")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("crmLeadReports", "������� Lead Reports"))
                                ).send();
                break;
            case 4:
                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("calEnterMail", "����� ��� �����")),
                                ActionRow.of(Button.primary("calLeadEvent", "����� � ��������� ����")),
                                ActionRow.of(Button.primary("calNewEvent", "������ ����� � ���������")),
                                ActionRow.of(Button.primary("calGet", "��� ����� ���������")),
                                ActionRow.of(Button.primary("calFollowUp", "�����-�� � ���������")))
                        .respond();

                /*componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .send();*/

                break;
            case 5:

                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("updateWhatIs", "��� ����� ������")),
                                ActionRow.of(Button.primary("updateWhy", "����� ����� ������")),
                                ActionRow.of(Button.primary("updateHowFind", "��� ����� ��������")),
                                ActionRow.of(Button.primary("updateHowMake", "��� ������� ������")),
                                ActionRow.of(Button.primary("updateDayNorm", "����� �������� � ����")))
                        .respond();

                /*componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .send();*/
                break;

            case 6:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("timeTurnOn", "�������� ����-������")),
                                ActionRow.of(Button.primary("timeGoogleSetting", "��������� Google Chrome")),
                                ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .respond();
                break;

            case 7:
                componentInteraction.createImmediateResponder().setContent("")
                        .setContent("���� (�������-��������)\n" + "<@372427478267985930>" +
                                "+380981101090\n" +"\n" +"���� �������� (�����������)\n"+ "<@918433181114454047>" +
                                "+380714184225\n" +"\n" +"���� �������� (������ LeadGeneration)\n"+ "<@920656302546513950>" +
                                "+380662227034\n" +"\n" +"������� ������� (������ Sales)\n" + "������� �������#4909" +
                                "+380506284605")
                        .addComponents(ActionRow.of(Button.primary("feedback", "�������� �����")))
                        .respond();
                break;

            case 8:

                componentInteraction.createImmediateResponder().setContent("")
                        .addComponents(
                                ActionRow.of(Button.primary("bonusMy", "��� ������")),
                                ActionRow.of(Button.primary("bonusWhen", "����� ��")))
                        .respond();
                break;

            case 9:
                componentInteraction.createImmediateResponder().setContent("Select")
                        .addComponents(
                                ActionRow.of(Button.primary("leadWhichLead", "����� ����� ����� ������")),
                                ActionRow.of(Button.primary("leadInfoFrom", "������ �� ����")),
                                ActionRow.of(Button.primary("leadAdd", "�������� ����")),
                                ActionRow.of(Button.primary("leadCardFill", "��������� ����� ����")),
                                ActionRow.of(Button.primary("leadWhoFollowUp", "���� ������ ������-��")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("leadRight", "���������� ����")),
                                ActionRow.of(Button.primary("leadHowMuch", "������� ����� � ������")),
                                ActionRow.of(Button.primary("leadDayNorm", "����� ����� � ����")),
                                ActionRow.of(Button.primary("leadHowFind", "��� ����� ������� ����")))
                        .send();
                break;

            case 10:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("blueFirstCon", "1-�� ������� � �����")),
                                ActionRow.of(Button.primary("blueSecondCon", "2-�� ������� � �����")),
                                ActionRow.of(Button.primary("blueAbout", "� ��������������")),
                                ActionRow.of(Button.primary("blueExp", "�� ����� �����������")),
                                ActionRow.of(Button.primary("blueClientBad", "������ �� �������������")))
                        .respond();

                componentInteraction.createFollowupMessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.primary("blueClientGood","������ �������������")))
                        .send();
                break;

            case 11:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("conFirstCon", "1-�� ������� � �����")),
                                ActionRow.of(Button.primary("conSecondCon", "2-�� ������� � �����")),
                                ActionRow.of(Button.primary("conToLead", "��������� ������� ����")))
                        .respond();
                break;
            case 12:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("followupWhat", "��� ����� �����-��")),
                                ActionRow.of(Button.primary("followupCal", "�����-�� � ���������")))
                        .respond();
                break;
            case 13:
               componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("counIWork", "� ����� ������� � �������")),
                                ActionRow.of(Button.primary("counCanChange", "����� �� �������� ������")),
                                ActionRow.of(Button.primary("counNotWork", "������: �� ��������")),
                                ActionRow.of(Button.primary("counWork", "������: ��������")))
                        .respond();

                break;
            case 14:
                componentInteraction.createImmediateResponder()
                        .addComponents(
                                ActionRow.of(Button.primary("statEvent", "������ � ���������� ������")),
                                ActionRow.of(Button.primary("statUpdate", "������ ������")),
                                ActionRow.of(Button.primary("statCrm", "���� �������� � ���")))
                        .respond();
                break;

            case 16:
                componentInteraction.createImmediateResponder()
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


            //default: componentInteraction.createImmediateResponder().setContent("������� �� ��� �����").respond();
        }
    }

}

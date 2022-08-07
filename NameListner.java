package com.Discordbot;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class NameListner
{
    public static void wright(Sheets service, boolean nameRecord, String spreadsheetId,String discordId, String discordMame, String number, String name, String surename) throws IOException, GeneralSecurityException
    {
        //MessageCreateEvent event,
        // String name =event.getMessageAuthor().getDiscriminatedName();
        //TODO: ВВести проверку на существование записи
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


    public static void wrightFeedback(Sheets service, String spreadsheetId, String discordId, String feedback) throws GoogleJsonResponseException
    {
        AppendValuesResponse result;
        try
        {
            List<List<Object>> values;

            ValueRange appendValue = new ValueRange()
                    .setValues((Arrays.asList(Arrays.asList(discordId, feedback))));



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
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}

package com.Discordbot;
import java.sql.*;
import java.util.Date;
import org.javacord.api.interaction.*;

public class sqlitecontroller
{
    static String url = "jdbc:sqlite:test.db";
    public static void sqlstart()
    {
        //String url = "jdbc:sqlite:test.db";
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url);


            System.out.println("Done");

        } catch (SQLException e)
        {
            System.out.println("ssssssssssssssss");
            System.out.println(e.getMessage());
        }

        String createUsageTable = "CREATE TABLE IF NOT EXISTS botusage(totalusage INT  NOT NULL, daily DATE, weekly DATE, monthly DATE,  INT, weeklyus INT, monthlyus INTL);";
        String createUsersTable = " CREATE TABLE botusers (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, userdiscordid TEXT, usetime DATE);";
        String trigger = " CREATE TRIGGER testtrigger AFTER INSERT ON botusers FOR EACH ROW BEGIN UPDATE botusage SET totalusage = totalusage + 1; END;";

        try
        {
            Connection connTable = DriverManager.getConnection(url);
            Statement statement = connTable.createStatement();
            statement.execute(createUsageTable);
            statement.execute(trigger);
            statement.execute(createUsersTable);
            connTable.close();
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("couldn't Createtable");
        }
    }

    public static void insert(MessageComponentInteraction nameInteraction)
    {
        try
        {
           // date = new java.sql.Date(System.currentTimeMillis());
            String name = nameInteraction.getUser().getDiscriminatedName();
            Connection connection = DriverManager.getConnection(url);
           // Statement insertname = connection.createStatement();

            String sqlinsert =  "INSERT INTO botusers(userdiscordid, usetime) VALUES(?,  date('now'))";

          PreparedStatement preparedInsert = connection.prepareStatement(sqlinsert);

          preparedInsert.setString(1, name);
          //preparedInsert.setDate(2,date);

          preparedInsert.executeUpdate();

          connection.close();

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}


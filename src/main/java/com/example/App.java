package com.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {


    public static void main(String[] args) throws SQLException{

        System.out.println("Hello, das ist die Shisha-Tabbak-Datenbank:)");

        String db_link = "jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0548692__datenbank_beleg";
        // String db_link = "jdbc:postgresql://fb1-mysql-01";
        String loginName = "s0548692";



        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Bitte das Passwort eingeben: ");

        String passwort = "7HazarD!7";
        //String passwort = reader.next(); // Scans the next token of the input as an int.

        //reader.close();


        Connection dbverbindung = null;
        Statement stmt = null;
        int num_columns=0;
        ResultSetMetaData meda;
        int auswahl = 0;
        try {
            // stellt eine verbindung mit der datenbank her
            dbverbindung = DriverManager.getConnection(db_link, loginName, passwort);



            // Erzeugen eines Statement-Objekts zum Versenden von Anfragen

            stmt = dbverbindung.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs= stmt.executeQuery("select * from Produkt");
            meda= rs.getMetaData();
            num_columns = meda.getColumnCount();

            do{
                System.out.println("\n\t*#Main#*\n");
                System.out.println("Tabelle Produkte");
                System.out.println("1.Ausgabe DatensÃ¤tze");
                System.out.println("2.EinfÃ¼gen Datensatz");
                System.out.println("3.LÃ¶schen Datensatz");
                System.out.println("4.DatensÃ¤tze einzeln anschauen(n=next, p=previous, exit=MainmenÃ¼)");
                System.out.println("5.Verbindung Trennen.(nicht vergessen!)");

                int auswahl1 = reader.nextInt();

                switch(auswahl1){
                    case 1:
                        rs= stmt.executeQuery("select * from Produkt");
                        meda= rs.getMetaData();
                        num_columns = meda.getColumnCount();
                        System.out.println("Die Tabelle Produkt hat "+num_columns+" Spalten.");
                        display(rs, num_columns);
                        break;

                    case 2:

                        System.out.println("Gebe die ProduktID der neuen Ware an zb: 5012 : ");
                        int l = reader.nextInt();



                        System.out.println("Gebe die Anzahl der neuen Ware an : ");
                        int k = reader.nextInt();

                        System.out.println("Gebe den Namen der neuen Ware an : ");
                        String s = reader.next();

                        //	int s = reader.nextInt();

                        System.out.println("Gebe den Preis der neuen Ware an : ");
                        //	String s = reader.nextLine();
                        int a = reader.nextInt();



                        String sql = "insert into Produkt"
                                +"(Produkt_ID, Name, Anzahl,Preis)"
                                +"values ('"+l+"', '"+s+"', '"+k+"','"+a+"')";

                        stmt.executeUpdate(sql);
                        break;

                    case 3:
                        System.out.println("Gebe die lid an zum lÃ¶schen des Datensatzes: ");
                        int x = reader.nextInt();

                        //IOTools.readInteger("Gebe die lid an zum lÃ¶schen des Datensatzes: ");
                        //	String sql1 ="delete from lager where lid = "+x+"";

                        String sql1 ="delete from produkt where produkt_id = "+x+"";


                        int rowsAffected = stmt.executeUpdate(sql1);

                        System.out.println("Rows affected: "+ rowsAffected);
                        System.out.println("DELETE complete.");
                        break;


                    case 4:
                        String navi;
                        do{
                            navi = reader.nextLine();
                            switch(navi){
                                case "n":
                                    try{
                                        rs.next();
                                        for (int i=1; i<=num_columns;i++){
                                            System.out.print (rs.getString(i)+", ");}
                                    }catch (SQLException e){}
                                    break;
                                case "p":
                                    try{
                                        rs.previous();
                                        for (int i=1; i<=num_columns;i++){
                                            System.out.print (rs.getString(i)+", ");}
                                    }catch (SQLException e){}
                                    break;
                                case "exit":
                                    navi = "x";
                            }
                        }while(navi!="x");
                        break;

                    case 5:
                        auswahl1=6;
                        System.out.print("Verbindung zur Datenbank wurde geschlossen.");
                        break;

                    default:
                        auswahl1=0;
                        break;

                }//Switch case
            }while(auswahl<6);//MenÃ¼schleife


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (dbverbindung != null) {
                dbverbindung.close();
            }
        }
    }
    private static void display(ResultSet rs,int num_columns) throws SQLException {
        while (rs.next()){
            for (int i=1; i<=num_columns;i++){
                System.out.print (rs.getString(i)+", ");
                if(i == num_columns){
                    System.out.print("\n");
                }
            }
        }}}


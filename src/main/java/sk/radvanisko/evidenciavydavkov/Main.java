package sk.radvanisko.evidenciavydavkov;


import com.itextpdf.text.DocumentException;
import sk.radvanisko.evidenciavydavkov.model.Sluzby;
import sk.radvanisko.evidenciavydavkov.model.Vydavok;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException, DocumentException, IOException {

        Connection conn = null;

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("problem s pripojenim");;
        }


        String url = "jdbc:mysql://localhost:3306/vydavky";
        String username = "root";
        String password = "password";
/*
        String url = "jdbc:mysql://192.168.1.100:3306/vydavky.vydavky01";
        String username = "root";
        String password = "admin";
*/

        System.out.println("Spájam sa s databázou ...");

        if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);
//            conn = DriverManager.getConnection("jdbc:h2:C:\\Users\\radvanisko\\JavaProjekty\\Databaza/vydavky", "markus", "password");
            System.out.println("Databaza pripojena" + conn);

        }
        Sluzby sluzby=new Sluzby();
        sluzby.vypisMenu();

        String menuvolba;
        Scanner sc = new Scanner(System.in);

//        ArrayList<Vydavok> zoznamNakladov= TestovacieDataUtility.naplnTestovacimiUdajmi();
        ArrayList<Vydavok> zoznamVydavkov= new ArrayList<>();
        HashMap <String, Double> vyber=new HashMap<String,Double>();

        while (true) {
            menuvolba = sc.next();

            switch (menuvolba) {
                case "1":

                    Scanner sc1 = new Scanner(System.in);
                    String vstup;
                    double vstupcena=0;
                    System.out.println("Zadaj novú položku do zoznamu nákladov");
                    vstup = sc1.nextLine();

                    if (!vstup.equals("")) {
                        Vydavok polozka=new Vydavok();
                        polozka.setPopisVydavku(vstup);
                        System.out.println("Zadaj Sumu :");
                        try
                        { vstupcena = sc1.nextDouble(); }
                        catch (Exception e) {

                            System.out.println(" Nezadal si spravne hodnotu nakladu, táto položka sa nezapísala");
                            System.out.println("Zadaj svoju volbu:");
                            break;
                        }
                        polozka.setSuma(vstupcena);

                        System.out.println("Zadaj kategóriu nákladu");
                        vstup = sc1.next();
                        polozka.setKategoria(vstup);

                        Calendar currenttime = Calendar.getInstance();
                        Date dnesnydatum= new Date((currenttime.getTime()).getTime());
                         polozka.setDatum(dnesnydatum);

                        System.out.println("Zadany výdavok:");
                        System.out.println(polozka.getPopisVydavku());
                        System.out.println(polozka.getSuma());
                        System.out.println(polozka.getDatum());

                        sluzby.vlozVydavokMySql(conn,polozka);

                    }

                    sluzby.vypisMenu();
                    break;

                case "2":

                    // ako dostať do arraylistu zo sluzby
                    zoznamVydavkov=sluzby.vyberVsetkyMySql(conn);
                    System.out.println(" Zoznam výdavkov: ");
                    System.out.println("____________________________________________________________");
                    System.out.println(String.format("%-5s %15s   %7s %12s %12s", "ID","Popis výdavku","suma","Dátum","Kategoria"));
                    System.out.println("____________________________________________________________");
                    for (Vydavok polozka: zoznamVydavkov) {
                    System.out.println(String.format("%-5s %15s   %7s %12s %12s", polozka.getId(),polozka.getPopisVydavku(),polozka.getSuma(),polozka.getDatum(),polozka.getKategoria()));
                    }
                        System.out.println("_______________________________________________________");
                        System.out.println();
                        System.out.println("Zadaj svoju volbu:");

                    break;

                case "3":
                    double suma=0;
                    suma= sluzby.sumaVydavkovAll(conn);
                    System.out.println("Suma tvojich výdavkov  je: "  +suma);
                    System.out.println("Počet položiek je : "+ sluzby.pocetPoloziek(conn));
                    System.out.println();
                    System.out.println("Zadaj svoju volbu:");

                    /*
                    for (Vydavok  polozka1 : zoznamVydavkov) {
                        suma=suma+polozka1.getSuma();
                    }
                    System.out.println("Suma tvojich nakladov je: " +suma + "  pocet zaznamov:  " +   zoznamVydavkov.size());
                    System.out.println();
                    System.out.println("Zadaj svoju volbu:");*/
                    break;

                case  "4": // oprav zaznam


                    System.out.println("Zadaj číslo záznamu, ktorý chceš editovať!");
                    Scanner sc4 = new Scanner(System.in);
                    int id;
                    id=sc4.nextInt();  //todo treba osetrit vstup

                    Vydavok polozka=new Vydavok();
                    polozka=sluzby.vlozVydavok();

//                    polozka.setId(id);
                    System.out.println("Zadaný výdavok:");
                    System.out.println(String.format("%-5s %15s   %7s %12s %12s", polozka.getId(),polozka.getPopisVydavku(),polozka.getSuma(),polozka.getDatum(),polozka.getKategoria()));

                    sluzby.aktualizujVydavokMySql(id,conn,polozka);  //todo dokončiť
                    System.out.println("Záznam číslo " + id + "  bol aktualizovaný!");

                    break;

                case "5":

                    vyber=sluzby.sumaVydavkovKategoria(conn);
                    System.out.println(vyber);


                    System.out.println("Zadaj svoju volbu:");
                    break;
                case "6":
                    //vypis  suma podla konkretnej kategorie
                    // zobrazi zoznam kategorii
                    System.out.println("Zoznam kategorii :");
                    System.out.println("---------------------------");
                    vyber=sluzby.sumaVydavkovKategoria(conn);


                    for ( String kat: vyber.keySet()) {
                        System.out.println(kat);

                    }
                    System.out.println("---------------------------");
                    Scanner sc6 = new Scanner(System.in);
                    String kategoria="";
                    System.out.println("Zadaj kategoriu vydavkov ");
                    kategoria = sc6.nextLine();

                    vyber=sluzby.sumaVydavkovKategoria(conn);
                    System.out.println(vyber.get(kategoria));


                    System.out.println("Zadaj svoju volbu:");
                    break;

                case "7":

                    sluzby.vytlacMySql2Pdf(conn);
                    System.out.println("Subor bol vytvoreny!!");

                    System.out.println("Zadaj svoju volbu:");
                    break;

                case "8":
                    System.out.println("Zadaj číslo zaznamu, ktorý chceš vymazať!");
                    Scanner sc8 = new Scanner(System.in);

                    try { id=sc8.nextInt();} catch (Exception e) {
                        System.out.println(" Nezadal si spravne hodnotu ID , žiadna položka sa nezmazala!");
                        System.out.println("Zadaj svoju volbu:");
                        break;
                    }
                    sluzby.odstranVydavokMySql(id,conn);
                    System.out.println("Záznam číslo " + id + "  bol vymazaný!");


                    System.out.println("Zadaj svoju volbu:");
                    break;

                case "0":


                    System.out.println("Zadaj svoju volbu:");
                    break;

                case "m":
                    sluzby.vypisMenu();
                    break;
                case "q":
                    System.out.println("--------Koniec programu----------");
                    sc.close();
                    return;

                default:
                    for (int i = 0; i < 50; ++i) System.out.println();
                    sluzby.vypisMenu();

            }


        }
    }

}

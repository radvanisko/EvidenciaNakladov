package sk.radvaniskoevidencianakladov;


import sk.radvanisko.evidencianakladov.model.Sluzby;
import sk.radvanisko.evidencianakladov.model.Vydavok;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/vydavky";
        String username = "root";
        String password = "password";
        System.out.println("Spájam sa s databázou ...");

        if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Databáza je pripojená!");
        }


        Sluzby sluzby=new Sluzby();
        System.out.println(sluzby.sumaVydavkovAll(conn));

        sluzby.vypisMenu();

        String menuvolba;
        Scanner sc = new Scanner(System.in);

//        ArrayList<Vydavok> zoznamNakladov= TestovacieDataUtility.naplnTestovacimiUdajmi();
        ArrayList<Vydavok> zoznamVydavkov= new ArrayList<>();

        while (true) {
            menuvolba = sc.next();

            switch (menuvolba) {
                case "1":
                    Scanner sc1 = new Scanner(System.in);
                    String vstup;
                    double vstupcena=0;
                    System.out.println("Zadaj novú položku do zoznamu nákladov");
                    vstup = sc1.next();

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

//                        zoznamVydavkov.add(polozka);

                        //  kod na ulozenie arraylistu do dtb
                        sluzby.vlozVydavokMySql(conn,polozka);

                    }

                    sluzby.vypisMenu();
                    break;

                case "2":

                    // ako dostať do arraylistu zo sluzby
                    zoznamVydavkov=sluzby.vyberVsetkyMySql(conn);
                    System.out.println(" Zoznam vydavkov: ");
                    System.out.println("_______________________________________________________");
                    for (Vydavok polozka: zoznamVydavkov) {

                        System.out.println(polozka.getPopisVydavku()+"  "+polozka.getSuma()+ "  "+polozka.getDatum()+polozka.getKategoria() );
                                                                    }
                    System.out.println("_______________________________________________________");
                    System.out.println("Zadaj svoju volbu:");

                    break;

                case "3":
                    double suma=0;
                    suma= sluzby.sumaVydavkovAll(conn);
                    System.out.println("Suma tvojich výdavkov  je: "  +suma);
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

                case "6":

                    System.out.println("Zadaj svoju volbu:");
                    break;

                case "7":

                    System.out.println("Zadaj svoju volbu:");
                    break;

                case "8":

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

package sk.radvaniskoevidencianakladov;


import sk.radvanisko.evidencianakladov.model.Sluzby;
import sk.radvanisko.evidencianakladov.model.Vydavok;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    int vstupInt;
                    double vstupcena=0;
                    System.out.println("Zadaj novú položku do zoznamu nákladov");

                    vstup = sc1.next();

                    if (!vstup.equals("")) {
                        Vydavok polozka=new Vydavok();
                        polozka.setPopisVydavku(vstup);

                        System.out.println("Zadaj Sumu :");
                        try
                        {
                            vstupcena = sc1.nextDouble();

                        }
                        catch (Exception e) {

                            System.out.println(" Nezadal si spravne hodnotu nakladu, táto položka sa nezapísala");
                            System.out.println("Zadaj svoju volbu:");
                            break;
                        }

                        polozka.setSuma(vstupcena);

/*
                        Date obdobie=new Date();
                        sc1 = new Scanner(System.in);
                        System.out.println("Obdobie kedy vznikol vydavok - Zadaj deň :");
                        vstupInt = sc1.nextInt();
                      obdobie.setDen(vstupInt);



                        sc1 = new Scanner(System.in);
                        System.out.println("Obdobie kedy vznikol vydavok - Zadaj  mesiac :");
                        vstupInt = sc1.nextInt();
                        obdobie.setMesiac(vstupInt);


                        sc1 = new Scanner(System.in);
                        System.out.println("Zadaj  rok  :");
                        vstupInt = sc1.nextInt();
                        obdobie.setRok(vstupInt);

//                            polozka.setDatumZadania(vstup); // zmena za Objekt obdobie
                        polozka.setObdobie(obdobie);
*/


                        System.out.println("Zadany naklad:");
                        System.out.println(polozka.getPopisVydavku());
//                            System.out.println(polozka.getDatumZadania());
                        System.out.println(polozka.getSuma());

                        zoznamVydavkov.add(polozka);

                    }

                    sluzby.vypisMenu();
                    break;

                case "2":


                    System.out.println("Zadaj svoju volbu:");

                    break;

                case "3":
                    double suma=0;

                    for (Vydavok  polozka1 : zoznamVydavkov) {
                        suma=suma+polozka1.getSuma();
                    }

                    System.out.println("Suma tvojich nakladov je: " +suma + "  pocet zaznamov:  " +   zoznamVydavkov.size());
                    System.out.println();

                    System.out.println("Zadaj svoju volbu:");
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

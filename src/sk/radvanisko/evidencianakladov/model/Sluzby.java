package sk.radvanisko.evidencianakladov.model;

import java.sql.*;
import java.util.ArrayList;

public class Sluzby implements InterfaceSluzby {


    private Connection conn;


    public Sluzby() {

    }

    @Override
    public void vlozVydavokMySql(Vydavok vydavok) {

    }

    @Override
    public void aktualizujVydavokMySql(int id, Vydavok vydavok) {

    }

    @Override
    public ArrayList<Vydavok> vyberVsetkyMySql() {
        return null;
    }

    @Override
    public void odstranVydavokMySql(int id) {

    }

    @Override
    public void vlozVydavok(Vydavok vydavok) {

    }

    @Override
    public  void vypisMenu() {

        System.out.println();
        System.out.println(" ---------- EVIDENCIA VYDAVKOV ---------");
        System.out.println("---------------------------------------------------");
        System.out.println("MENU>      (m)= MENU,....                     (q)= quit : ");
        System.out.println("---------------------------------------------------");
        System.out.println("MENU>      (1)= Zadaj novu položku            (q)= quit : ");
        System.out.println("MENU>      (2)= Vypíš zoznam položiek         (q)= quit : ");
        System.out.println("MENU>      (3)= Spočítaj sumu položiek        (q)= quit : ");
//        System.out.println("MENU>      (6)=                             (q)= quit : ");
//        System.out.println("MENU>      (7)=                             (q)= quit : ");
//
//        System.out.println("MENU>      (8)=                             (q)= quit : ");
//        System.out.println("MENU>      (9)=                             (q)= quit : ");
//        System.out.println("MENU>      (0)=                             (q)= quit : ");


        System.out.println("---------------------------------------------------");
        System.out.println("Zadaj svoju volbu:");


    }

    @Override
    public void vytlacDoPdf() {

    }



    @Override
    public double sumaVydavkovAll(Connection conn) {

        String query="SELECT SUM(suma) AS sucet FROM  vydavky01;";
               try {
            PreparedStatement stmt= conn.prepareStatement(query);
            ResultSet resultSet=stmt.executeQuery(query);
            if (resultSet.next()) {
                double sucet =resultSet.getDouble("sucet");
                return sucet;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0 ;
    }

    @Override
    public int pocetPoloziek() {
        return 0;
    }
}

package sk.radvanisko.evidencianakladov.model;

import java.sql.*;
import java.util.ArrayList;

public class Sluzby implements InterfaceSluzby {


//    private Connection conn;


    public Sluzby() {

    }

    @Override
    public void vlozVydavokMySql(Connection conn, Vydavok vydavok) throws SQLException {
        String sql = "INSERT INTO vydavky.vydavky01 (popisvydavku, suma,datum,kategoria) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, vydavok.getPopisVydavku());
        statement.setDouble(2, vydavok.getSuma());
        statement.setDate(3, vydavok.getDatum());
        statement.setString(4, vydavok.getKategoria());

        statement.executeUpdate();


    }

    @Override
    public void aktualizujVydavokMySql(int id, Connection conn, Vydavok vydavok) {

    }

    @Override
    public ArrayList<Vydavok> vyberVsetkyMySql(Connection conn) throws SQLException {

        String sql = "SELECT * FROM vydavky01";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        int id = 1;

        ArrayList<Vydavok> zoznam = new ArrayList<Vydavok>();

        while (result.next()) {
            Vydavok polozka = new Vydavok();

            polozka.setId(result.getInt("id"));
            polozka.setPopisVydavku(result.getString("popisvydavku"));
            polozka.setSuma(result.getFloat("suma"));
            polozka.setDatum(result.getDate("datum"));
            polozka.setKategoria(result.getString("kategoria"));

            zoznam.add(polozka);

        }
        return zoznam;

    }

    @Override
    public void odstranVydavokMySql(int id, Connection connection) throws SQLException {
        String sql = "DELETE FROM vydavky.vydavky01 WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();


    }

    @Override
    public void vlozVydavok(Vydavok vydavok) {

    }

    @Override
    public void vypisMenu() {

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
        System.out.println("MENU>      (8)=Vymaž konkrétny záznam (ID)    (q)= quit : ");
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

        String query = "SELECT SUM(suma) AS sucet FROM  vydavky01;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                double sucet = resultSet.getDouble("sucet");
                return sucet;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int pocetPoloziek(Connection conn) throws SQLException {
        int pocet = 0;
        String query = "SELECT COUNT(*) AS pocet FROM  vydavky.vydavky01";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery(query);
        resultSet.next();
        pocet= resultSet.getInt("pocet");
        return pocet;
   }

}

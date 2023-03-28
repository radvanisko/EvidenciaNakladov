package sk.radvanisko.evidenciavydavkov.model;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class Sluzby implements InterfaceSluzby {



//    private Connection conn;


    public Sluzby() {

    }

    @Override
    public void vlozVydavokMySql(Connection conn, Vydavok vydavok) throws SQLException {
        String query = "INSERT INTO VYDAVKY.VYDAVKY01 (popisvydavku, suma,datum,kategoria) VALUES (?, ?, ?, ?)";
//        String query = "INSERT INTO vydavky (popisvydavku, suma,datum,kategoria) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.setString(1, vydavok.getPopisVydavku());
        pstmt.setDouble(2, vydavok.getSuma());
        pstmt.setDate(3, vydavok.getDatum());
        pstmt.setString(4, vydavok.getKategoria());
//        pstmt.executeQuery();
        pstmt.executeUpdate();

        System.out.println(String.format("%-5s %15s   %7s %12s %12s", vydavok.getId(),vydavok.getPopisVydavku(),vydavok.getSuma(),vydavok.getDatum(),vydavok.getKategoria()));
//        System.out.println(rs.next());

    }

    @Override
    public void vlozVydavokH2(Connection conn, Vydavok vydavok) throws SQLException {
        String query = "INSERT INTO VYDAVKY.VYDAVKY01 (popisvydavku, suma,datum,kategoria) VALUES (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

//        Statement stmt = conn.createStatement();
//        ResultSet result = stmt.executeQuery(query);


// Get the generated primary key value

        ResultSet rs = pstmt.getGeneratedKeys();

        if (rs.next()) {
            int id = 0;
            id = rs.getInt(0);
            System.out.println("Inserted record with ID: " + id);
            System.out.println(String.format("%-5s %15s   %7s %12s %12s", vydavok.getId(), vydavok.getPopisVydavku(), vydavok.getSuma(), vydavok.getDatum(), vydavok.getKategoria()));
        }
//        pstmt.setInt(0, pstmt.RETURN_GENERATED_KEYS);
        pstmt.setString(1, vydavok.getPopisVydavku());
        pstmt.setDouble(2, vydavok.getSuma());
        pstmt.setDate(3, vydavok.getDatum());
        pstmt.setString(4, vydavok.getKategoria());
        pstmt.executeUpdate();


        System.out.println(String.format("%-5s %15s   %7s %12s %12s", vydavok.getId(), vydavok.getPopisVydavku(), vydavok.getSuma(), vydavok.getDatum(), vydavok.getKategoria()));
    }

    @Override
    public void aktualizujVydavokMySql(int id, Connection conn, Vydavok vydavok) throws SQLException {

       String query="UPDATE vydavky.vydavky01 SET popisvydavku = ?, suma = ?, datum = ?, kategoria = ?  WHERE id = ?";
//        String query="UPDATE vydavky SET popisvydavku = ?, suma = ?, datum = ?, kategoria = ?  WHERE id = ?";

        PreparedStatement statement = conn.prepareStatement(query);


        statement.setString(1, vydavok.getPopisVydavku());
        statement.setDouble(2, vydavok.getSuma());
        statement.setDate(3, vydavok.getDatum());
        statement.setString(4, vydavok.getKategoria());

        statement.setInt(5, id);

        statement.executeUpdate();

    }

    @Override
    public ArrayList<Vydavok> vyberVsetkyMySql(Connection conn) throws SQLException {

        String sql = "SELECT * FROM vydavky.vydavky01;";
//        String sql = "SELECT * FROM vydavky";
//        PreparedStatement statement = conn.prepareStatement(sql);
//        ResultSet result = statement.executeQuery();

        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);

//        int id = 1;

        ArrayList<Vydavok> zoznam = new ArrayList<Vydavok>();

        while (result.next()) {
            Vydavok vydavok = new Vydavok();

            vydavok.setId(result.getInt("id"));
            vydavok.setPopisVydavku(result.getString("popisvydavku"));
            vydavok.setSuma(result.getFloat("suma"));
            vydavok.setDatum(result.getDate("datum"));
            vydavok.setKategoria(result.getString("kategoria"));

            zoznam.add(vydavok);

        }
        return zoznam;

    }

    @Override
    public void odstranVydavokMySql(int id, Connection connection) throws SQLException {

        String sql = "DELETE FROM vydavky.vydavky01 WHERE id = ?";
//        String sql = "DELETE FROM vydavky WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();


    }

    @Override
    public Vydavok  vlozVydavok() {

        Scanner sc1 = new Scanner(System.in);
        String vstup;
        double vstupcena=0;
        System.out.println("Zadaj popis výdavku");
        vstup = sc1.nextLine();

        if (!vstup.equals("")) {
            Vydavok vydavok = new Vydavok();
            vydavok.setPopisVydavku(vstup);
            System.out.println("Zadaj Sumu :");
            try {
                vstupcena = sc1.nextDouble();
            } catch (Exception e) {

                System.out.println(" Nezadal si spravne hodnotu nakladu, táto položka sa nezapísala");
                System.out.println("Zadaj svoju volbu:");
                 return null;
            }
            vydavok.setSuma((float) vstupcena);

            System.out.println("Zadaj kategóriu nákladu");
            vstup = sc1.next();
            vydavok.setKategoria(vstup);

            Calendar currenttime = Calendar.getInstance();
            Date dnesnydatum = new Date((currenttime.getTime()).getTime());
            vydavok.setDatum(dnesnydatum);


            return vydavok;
        }

            return null;
        }

    @Override
    public void vypisMenu() {

        System.out.println();
        System.out.println(" ---------- EVIDENCIA VYDAVKOV --------------------------------");
        System.out.println("---------------------------------------------------------------");
        System.out.println("MENU>      (m)= MENU,....                          (q)= quit : ");
        System.out.println("---------------------------------------------------------------");
        System.out.println("MENU>      (1)= Zadaj novu položku                 (q)= quit : ");
        System.out.println("MENU>      (2)= Vypíš zoznam položiek              (q)= quit : ");
        System.out.println("MENU>      (3)= Spočítaj sumu položiek             (q)= quit : ");
        System.out.println("MENU>      (4)= Oprav záznam (ID)                  (q)= quit : ");
        System.out.println("MENU>      (5)= Spočítaj sumy všetkých kategórii   (q)= quit : ");
        System.out.println("MENU>      (6)= Spočítaj sumu jednej kategórie     (q)= quit : ");
        System.out.println("MENU>      (7)= Vytlač do PDF                      (q)= quit : ");
//
        System.out.println("MENU>      (8)=Vymaž konkrétny záznam (ID)        (q)= quit : ");
//        System.out.println("MENU>      (9)=                             (q)= quit : ");
//        System.out.println("MENU>      (0)=                             (q)= quit : ");


        System.out.println("---------------------------------------------------");
        System.out.println("Zadaj svoju volbu:");


    }

    @Override
    public void vytlacDoPdf() {

    }

    @Override
    public void vytlacMySql2Pdf(Connection conn) throws SQLException, DocumentException, IOException {
        ArrayList<Vydavok> vydavky =new ArrayList<Vydavok>();
        int pocet =pocetPoloziek(conn);
        double sucet= sumaVydavkovAll(conn);

        vydavky=vyberVsetkyMySql(conn);

        Calendar currenttime = Calendar.getInstance();
        Date dnesnydatum = new Date((currenttime.getTime()).getTime());

        // START - generovanie PDF
        Document document = new Document(); // vytvorime prazdny PDF Dokument


        String unicodeFontPath="C:/Windows/Fonts/Tahoma.ttf";
        BaseFont unicode = BaseFont.createFont(unicodeFontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(unicode, 12, Font.NORMAL);
        Font fontNadpis = new Font(unicode, 18, Font.BOLD);

//        font=new Font(Font.FontFamily.TIMES_ROMAN, 20);


        try {
            // vytvori konkretny subor HelloWorld.pdf, ktorý umiestni do priečinka \\Mac\Home\Documents\
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("report_vydavkov" + dnesnydatum+".pdf" ));
            document.open(); // dokument musime ho otvorit
//            document.add(new Header
            document.add(new Paragraph("Zoznam výdavkov :   ",fontNadpis)); // do dokumentu vpiseme text  PDF documentu

            for (Vydavok vystup: vydavky) {
                document.add(new Paragraph(String.format("%-5s %15s   %7s %12s %12s", vystup.getId(),vystup.getPopisVydavku(),vystup.getSuma(),vystup.getDatum(),vystup.getDatum()),font));
//                System.out.println(String.format("%-5s %15s   %7s %12s %12s", "ID",vystup.getPopisVydavku(),vystup.getSuma(),vystup.getDatum(),vystup.getDatum()));
            }
            document.add(new Paragraph("Suma výdavkov je: "+ sucet + "  Počet položiek je:  " +pocet,fontNadpis));

            document.close(); // zatvorime dokument
            writer.close(); // zatvorime subor

            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("report_vydavkov" + dnesnydatum+ ".pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }


        } catch (DocumentException e) {
            System.out.println("Nastal problém s vytváraním dokumentu");
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Problém so súborom!");
        }
// END - generovanie PDF



    }


    @Override
    public double sumaVydavkovAll(Connection conn) {

        String query = "SELECT SUM(suma) AS sucet FROM  vydavky01;";
//        String query = "SELECT SUM(suma) AS sucet FROM  vydavky;";

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
//        String query = "SELECT SUM(suma) AS sucet FROM  vydavky;";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery(query);
        resultSet.next();
        pocet= resultSet.getInt("pocet");
        return pocet;
   }

    @Override
    public HashMap sumaVydavkovKategoria(Connection conn) throws SQLException {

//        String query ="SELECT kategoria, SUM(suma)AS 'Suma podľa kategorie' FROM vydavky01 GROUP BY kategoria";
        String query ="SELECT kategoria, SUM(suma)AS 'Suma podľa kategorie' FROM vydavky GROUP BY kategoria";
        PreparedStatement statement=conn.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        HashMap <String, Double> vyber=new HashMap<String,Double>();

        while (result.next()) {
            vyber.put(result.getString("kategoria"), result.getDouble("Suma podľa kategorie"));
        }

        return vyber;

    }


    // QUERY EXAMPLES
    //        SELECT kategoria, SUM(suma) FROM vydavky01 GROUP BY kategoria;
    //    SELECT kategoria, SUM(suma)AS 'Suma podľa kategorie' FROM vydavky01 GROUP BY kategoria;


    @Override
    public int cisloposlednyZaznam(Connection conn) throws SQLException {
      int posledny;
    String query= "SELECT * FROM vydavky.vydavky01 WHERE id=(SELECT MAX(id) FROM vydavky.vydavky01)";
    PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery(query);
        resultSet.next();
        posledny= resultSet.getInt("id");
        return posledny;
    }

    @Override
    public int cisloposlednyZaznamH2(Connection conn) throws SQLException {
        int posledny;
        String query= "SELECT * FROM vydavky.vydavky01 WHERE id=(SELECT MAX(id) FROM vydavky.vydavky01)";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        posledny= resultSet.getInt("id");
        return posledny;

/*
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);
*/


    }




    @Override
    public Connection otvorDatabazu() throws SQLException {

        // vytvaranie prippojenia na Databazu

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/vydavky";
        String username = "root";
        String password = "password";

        if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Databáza MySql je pripojená!");
//            getLabel().setText("Databaza MySql je pripojena");
        }
        //-----------------------------------------------------
        return conn;
    }

    @Override
    public Connection otvorH2() throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:h2:C:\\Users\\radvanisko\\JavaProjekty\\EvidenciaNakladov\\EvidenciaNakladov\\databaza\\vydavky","sa","");
        /*if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);*/
            System.out.println("Databáza H2  je pripojená!");
        return conn;
    }

}

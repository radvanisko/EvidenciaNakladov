package sk.radvanisko.evidenciavydavkov.model;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface InterfaceSluzby {

    // MySql  CRUD
    void vlozVydavokMySql(Connection conn,Vydavok vydavok) throws SQLException;

    void vlozVydavokH2(Connection conn,Vydavok vydavok) throws SQLException;


    void aktualizujVydavokMySql (int id, Connection conn,Vydavok vydavok) throws SQLException;
     ArrayList<Vydavok> vyberVsetkyMySql(Connection conn) throws SQLException;
     void odstranVydavokMySql(int id,Connection connection) throws SQLException;

    // praca s modelom
     Vydavok vlozVydavok ();


     // Vseobecne
     void vypisMenu();
     void vytlacDoPdf();

    void vytlacMySql2Pdf(Connection conn) throws SQLException, DocumentException, IOException;

    double sumaVydavkovAll(Connection conn);
     int pocetPoloziek(Connection conn) throws SQLException;

     int cisloposlednyZaznam (Connection conn) throws SQLException;
    int cisloposlednyZaznamH2(Connection conn) throws SQLException;

     Connection otvorDatabazu () throws SQLException;

     Connection otvorH2 () throws SQLException;




     HashMap <String, Double> sumaVydavkovKategoria (Connection conn)throws SQLException;;




}

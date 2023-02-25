package sk.radvanisko.evidencianakladov.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InterfaceSluzby {

    // MySql  CRUD
    void vlozVydavokMySql(Connection conn,Vydavok vydavok) throws SQLException;


    void aktualizujVydavokMySql (int id, Connection conn,Vydavok vydavok);
     ArrayList<Vydavok> vyberVsetkyMySql(Connection conn) throws SQLException;
     void odstranVydavokMySql(int id);

    // praca s modelom
     void vlozVydavok (Vydavok vydavok);


     // Vseobecne
     void vypisMenu();
     void vytlacDoPdf();
     double sumaVydavkovAll(Connection conn);
     int pocetPoloziek();




}

package sk.radvanisko.evidencianakladov.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface InterfaceSluzby {

    // MySql  CRUD
    void vlozVydavokMySql(Vydavok vydavok);
    void aktualizujVydavokMySql (int id, Vydavok vydavok);
     ArrayList<Vydavok> vyberVsetkyMySql();
     void odstranVydavokMySql(int id);

    // praca s modelom
     void vlozVydavok (Vydavok vydavok);


     // Vseobecne
     void vypisMenu();
     void vytlacDoPdf();
     double sumaVydavkovAll(Connection conn);
     int pocetPoloziek();




}

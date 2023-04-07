package sk.radvanisko.evidenciavydavkov.test;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class testDatum {

    public static void main(String[] args) {

        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        System.out.println("Current Date: "+date);

    }

}

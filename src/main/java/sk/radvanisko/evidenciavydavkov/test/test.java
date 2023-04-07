package sk.radvanisko.evidenciavydavkov.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (line.equalsIgnoreCase("quit") == false) {
            line = in.readLine();
            System.out.println("co som asi stlacil");
            //do something
        }

        in.close();
    }

}

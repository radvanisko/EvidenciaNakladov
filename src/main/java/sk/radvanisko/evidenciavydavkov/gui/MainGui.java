package sk.radvanisko.evidenciavydavkov.gui;

import sk.radvanisko.evidenciavydavkov.model.Sluzby;
import sk.radvanisko.evidenciavydavkov.model.Vydavok;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainGui {

    public static void main(String[] args) throws SQLException {


        Sluzby sluzby=new Sluzby();


//      Object data[][] = null;
        Object data[][] = { { "", "","",""},  { "", "", "",""} };
        Object columnNames[] = {"Popis vydavku", "Suma", "Datum ", "Kategoria"};

        JTable table1=new JTable();
        JPanel paneltable=new JPanel();

        JTableHeader header = table1.getTableHeader();
        table1 = new JTable(data, columnNames);
        DefaultTableModel daDefaultTableModel = new DefaultTableModel(0, 0);
        daDefaultTableModel.setColumnIdentifiers(columnNames);
        table1.setModel(daDefaultTableModel);



        VydavkyGui vydavkyGui=new VydavkyGui(table1);

        paneltable.setLayout(new BorderLayout());
        paneltable.add(header, BorderLayout.NORTH);
        paneltable.add(table1, BorderLayout.CENTER);
        paneltable.updateUI();




//        scrollPane.setBounds(10, 38, 414, 212);

// generovanie dummy riadkov
        for (int i = 1; i <20; i++) {
            daDefaultTableModel.addRow(new Object[] {"Popis"+(i),100-2*i,"15-3-2023","kategoria"});
        }

        table1.updateUI();


        JFrame frame= new JFrame("Zoznam vydavkov");
        frame.setContentPane(vydavkyGui.panelHlavny);


        frame.setMinimumSize(new Dimension(500,700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // vycentrovanie okna
        frame.pack();
        frame.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(10, 38, 414, 212);
//        paneltable.add(table1);
        paneltable.add(scrollPane);


        // vytvaranie prippojenia na Databazu

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/vydavky";
        String username = "root";
        String password = "password";

        if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Databáza je pripojená!");
            vydavkyGui.getLabel().setText("Databaza je pripojena");
        }
        //-----------------------------------------------------


        ArrayList<Vydavok> vydavky = new ArrayList<Vydavok>();

        vydavky=sluzby.vyberVsetkyMySql(conn);  //naplnil som arraylist vydavky z dtb







    }
}

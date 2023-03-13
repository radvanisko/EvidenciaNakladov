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
        DefaultTableModel daDefaultTableModel = new DefaultTableModel(2, 4);

//      Object data[][] = null;
        Object data[][] = { { "", "","",""},  { "", "", "",""} };
        Object columnNames[] = {"Popis vydavku", "Suma", "Datum ", "Kategoria"};


        JTable table1=new JTable();

        JTableHeader header = table1.getTableHeader();
        table1 = new JTable(data, columnNames);
        daDefaultTableModel.setColumnIdentifiers(columnNames);
        table1.setModel(daDefaultTableModel);

        VydavkyGui vydavkyGui=new VydavkyGui();

        vydavkyGui.paneltable.setLayout(new BorderLayout());
        vydavkyGui.paneltable.add(header, BorderLayout.NORTH);
        vydavkyGui.paneltable.add(table1, BorderLayout.CENTER);
        vydavkyGui.paneltable.updateUI();




// generovanie dummy riadkov
        for (int i = 1; i <20; i++) {
            daDefaultTableModel.addRow(new Object[] {"Popis"+(i),100-2*i,"15-3-2023","kategoria"});
        }
        table1.updateUI();


        JFrame frame= new JFrame("Zoznam vydavkov");
        frame.setContentPane(vydavkyGui.panelHlavny);
        JScrollPane scrollPane = new JScrollPane(table1);

        frame.setMinimumSize(new Dimension(700,700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // vycentrovanie okna
        frame.pack();
        frame.setVisible(true);


        scrollPane.setBounds(100, 40, 500, 500);
        vydavkyGui.paneltable.add(scrollPane);
//        vydavkyGui.panelHlavny.add(table1);

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

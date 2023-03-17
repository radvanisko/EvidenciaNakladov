package sk.radvanisko.evidenciavydavkov.gui;

import sk.radvanisko.evidenciavydavkov.model.Sluzby;
import sk.radvanisko.evidenciavydavkov.model.Vydavok;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class VydavkyGui {

    JPanel panelHlavny;
    private JTable table1;
    private JButton button4;
    private JButton zadajVýdavokButton;
    private JButton button1;
    private JButton button2;
    private JPanel panelButtony;
    private JPanel panel3;
    private JLabel label;
    JPanel paneltable;

// konstruktors

    public VydavkyGui(JPanel panelHlavny) {  this.panelHlavny = panelHlavny; }
    public VydavkyGui(JTable table1) {
        this.table1 = table1;
    }



    //gettery /settery
    public JLabel getLabel() {
        return label;
    }
    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public VydavkyGui() {

        zadajVýdavokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                ZadajNovy dialog = new ZadajNovy(VydavkyGui.this);
                dialog.pack();
                dialog.setLocationRelativeTo(null); // vycentrovanie okna
                dialog.setVisible(true);
//                System.exit(0);

            }
        });

    }


    public static void main(String[] args) throws SQLException {

        Sluzby sluzby = new Sluzby();
        JTable table1 = new JTable();
        VydavkyGui vydavkyGui = new VydavkyGui();


//      Object data[][] = null;
        Object data[][] = {{"","", "", "", ""}, {"", "", "", ""}};
        Object columnNames[] = {"Id","Popis vydavku", "Suma", "Datum ", "Kategoria"};

        DefaultTableModel daDefaultTableModel = new DefaultTableModel(0, 0);
        table1 = new JTable(data, columnNames);

        JTableHeader header = table1.getTableHeader();

        daDefaultTableModel.setColumnIdentifiers(columnNames);
        table1.setModel(daDefaultTableModel);

        vydavkyGui.paneltable.setLayout(new BorderLayout());
        vydavkyGui.paneltable.add(header, BorderLayout.NORTH);
        vydavkyGui.paneltable.add(table1, BorderLayout.CENTER);
        vydavkyGui.paneltable.updateUI();


// generovanie dummy riadkov

/*
        for (int i = 1; i <20; i++) {
            daDefaultTableModel.addRow(new Object[] {"Popis"+(i),100-2*i,"15-3-2023","kategoria"});
        }
        table1.updateUI();
*/


        JFrame frame = new JFrame("Zoznam vydavkov");
        frame.setContentPane(vydavkyGui.panelHlavny);
        JScrollPane scrollPane = new JScrollPane(table1);

        frame.setMinimumSize(new Dimension(700, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // vycentrovanie okna
        frame.pack();
        frame.setVisible(true);


//        scrollPane.setBounds(100, 40, 500, 500);
        vydavkyGui.paneltable.add(scrollPane);


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

        vydavky = sluzby.vyberVsetkyMySql(conn);  //naplnil som arraylist vydavky z dtb



        //naplnenie tabulky z arraylistu
        for (Vydavok zoznam : vydavky) {
            daDefaultTableModel.addRow(new Object[]{zoznam.getId(),zoznam.getPopisVydavku(), zoznam.getSuma(), zoznam.getDatum(), zoznam.getKategoria()});
        }


    }
}

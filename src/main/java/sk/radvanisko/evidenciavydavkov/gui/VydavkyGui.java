//todo connh2  vyuzitie databazy H2


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


    public VydavkyGui(JPanel paneltable) {
        this.paneltable = paneltable;
    }

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

    public VydavkyGui() throws SQLException {

//        JTable table1 = new JTable();
//      Object data[][] = null;
        Object data[][] = {{"","", "", "", ""}, {"", "", "", ""}};
        Object columnNames[] = {"Id","Popis vydavku", "Suma", "Datum ", "Kategoria"};

        DefaultTableModel daDefaultTableModel = new DefaultTableModel(0, 0);
        table1 = new JTable(data, columnNames);

        JTableHeader header = table1.getTableHeader();
        daDefaultTableModel.setColumnIdentifiers(columnNames);
        table1.setModel(daDefaultTableModel);

        paneltable.setLayout(new BorderLayout());
        paneltable.add(header, BorderLayout.NORTH);
        paneltable.add(table1, BorderLayout.CENTER);
        paneltable.updateUI();

        JScrollPane scrollPane = new JScrollPane(table1);
//        scrollPane.setBounds(100, 40, 500, 500);
        paneltable.add(scrollPane);


        // vytvaranie prippojenia na Databazu

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/vydavky";
        String username = "root";
        String password = "password";

        if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Databáza je pripojená!");
            getLabel().setText("Databaza je pripojena");
        }
        //-----------------------------------------------------

        Sluzby sluzby = new Sluzby();
        ArrayList<Vydavok> vydavky = new ArrayList<Vydavok>();
        vydavky = sluzby.vyberVsetkyMySql(conn);  //naplnil som arraylist vydavky z dtb

        //naplnenie tabulky z arraylistu
        for (Vydavok zoznam : vydavky) {
            daDefaultTableModel.addRow(new Object[]{zoznam.getId(),zoznam.getPopisVydavku(), zoznam.getSuma(), zoznam.getDatum(), zoznam.getKategoria()});
        }


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

        JFrame frame = new JFrame("Zoznam vydavkov");
        frame.setContentPane(new VydavkyGui().panelHlavny);
        frame.setMinimumSize(new Dimension(700, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // vycentrovanie okna
        frame.pack();
        frame.setVisible(true);

    }
}

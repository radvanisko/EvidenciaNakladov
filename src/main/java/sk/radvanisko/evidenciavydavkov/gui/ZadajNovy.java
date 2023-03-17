package sk.radvanisko.evidenciavydavkov.gui;

import sk.radvanisko.evidenciavydavkov.model.Sluzby;
import sk.radvanisko.evidenciavydavkov.model.Vydavok;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ZadajNovy extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    private final VydavkyGui vydavkyGui;

    public ZadajNovy(VydavkyGui vydavkyGui) {

        this.vydavkyGui = vydavkyGui;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() throws SQLException {
        // add your code here
        System.out.println("bol stlačeny OK");

        Sluzby sluzby=new Sluzby();

        // vlozenie do objektu novy vydavok
        Vydavok novyvydavok=new Vydavok();


            novyvydavok.setPopisVydavku(textField1.getText());

            novyvydavok.setSuma(Double.parseDouble(textField2.getText())); //treba osetrit

//        novyvydavok.setDatum(textField3.getText());
        novyvydavok.setKategoria(textField4.getText());


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


        sluzby.vlozVydavokMySql(conn,novyvydavok);  //vloz do databazy conn

        JTable table1 = vydavkyGui.getTable1();

        // novyvydavok.getId() toto asi nie v objekte

//todo aktualiyovat tabulku
        System.out.println(novyvydavok.getId()+novyvydavok.getPopisVydavku()+novyvydavok.getSuma()+novyvydavok.getKategoria());

        Object[] data = {novyvydavok.getId(),novyvydavok.getPopisVydavku(), novyvydavok.getSuma(), novyvydavok.getDatum(), novyvydavok.getKategoria()};
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addRow(data);
        table1.updateUI();

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        System.out.println("bol stlaceny Cancel");
        dispose();
    }

}

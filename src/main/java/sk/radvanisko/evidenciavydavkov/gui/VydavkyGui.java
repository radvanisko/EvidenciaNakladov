//todo connh2  vyuzitie databazy H2


package sk.radvanisko.evidenciavydavkov.gui;

import com.itextpdf.text.DocumentException;
import sk.radvanisko.evidenciavydavkov.model.Sluzby;
import sk.radvanisko.evidenciavydavkov.model.Vydavok;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class VydavkyGui {

    JPanel panelHlavny;
    private JTable table1;
    private JButton zmažVýdavokButton;
    private JButton zadajVýdavokButton;
    private JButton editujButton;
    private JButton vytlačPdfButton;
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


        //  nastavenie sirky stlpcov
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=table1.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(5);
        colModel.getColumn(1).setPreferredWidth(150);
        colModel.getColumn(2).setPreferredWidth(20);
        colModel.getColumn(3).setPreferredWidth(50);


        // vytvaranie prippojenia na Databazu

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/vydavky";
        String username = "root";
        String password = "password";

        if (conn == null) { // vytvorili sme tzv. singleton
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Databáza MySql je pripojená!");
            getLabel().setText("Databaza MySql je pripojena");
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

        Connection finalConn = conn;
        vytlačPdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    sluzby.vytlacMySql2Pdf(finalConn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (DocumentException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog (null, "Súbor bol  vytvorený", "Tlač zoznamu do pdf", JOptionPane.INFORMATION_MESSAGE);




            }
        });
        editujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog (null, "Editovanie, alebo ina funkcia", "Nazov funkcie", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        zmažVýdavokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id=0; int row=0;int column = 0;

                row = table1.getSelectedRow();
//                System.out.println("Nastavenie :  " + row + column + id);


              if ((row<0))
                {JOptionPane.showMessageDialog (null, "Zvoľ si prosím zaznam, ktorý chceš vymazať", "Vymaž záznam", JOptionPane.INFORMATION_MESSAGE);}
              else {
                  id = (int) table1.getModel().getValueAt(row, column);

                  int input=JOptionPane.showConfirmDialog (null, " Naozaj chceš zmazať záznam s číslom :  " + id, "Vymaž záznam", JOptionPane.OK_CANCEL_OPTION);
                  System.out.println(input);

                  if (input==0) {
                      try {
                          sluzby.odstranVydavokMySql( id,finalConn);
                          row = table1.getSelectedRow();
                          System.out.println("rekord :  " +id + "\n" + "row v tabulke : " + row);
                          //todo ako aktualizovať tabulku po vymazani


//                           DefaultTableModel model = (DefaultTableModel) table1.getModel();
                          ((DefaultTableModel)table1.getModel()).removeRow(row);


                      } catch (SQLException ex) {
                          System.out.println("Zaznam sa nezmazal, nie je pripojenia databaza");;
                      }
                  }


              }
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

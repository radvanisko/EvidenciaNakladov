
//TODO editovanie tabulky  /enable /disable
//TODO  zadavanie datumu


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
import java.awt.print.PrinterException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class VydavkyGui {

    JPanel panelHlavny;
    private JTable table1;
    private JButton zmažVydavokButton;
    private JButton zadajVydavokButton;
    private JButton editujButton;
    private JButton vytlacPdfButton;
    private JPanel panelButtony;
    private JPanel panel3;
    private JLabel label;
    JPanel paneltable;
    private JButton tlacButton;
    boolean tableEditable;

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
        tableEditable=false;
        table1 = new JTable(data, columnNames){

            //  // Override the isCellEditable method to make all cells not editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return tableEditable;
        }
        };

        JTableHeader header = table1.getTableHeader();
        daDefaultTableModel.setColumnIdentifiers(columnNames);

        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // // Enable row selection
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

        //otvorenie databazy
        final Sluzby sluzby = new Sluzby();

//        Connection conn=sluzby.otvorDatabazu(); //MySQL
        Connection conn=sluzby.otvorH2(); // H2


        getLabel().setText("Databaza H2 je pripojena");

        ArrayList<Vydavok> vydavky = new ArrayList<Vydavok>();
        vydavky = sluzby.vyberVsetkyMySql(conn);  //naplnil som arraylist vydavky z dtb

        //naplnenie tabulky z arraylistu
        for (Vydavok zoznam : vydavky) {
            daDefaultTableModel.addRow(new Object[]{zoznam.getId(),zoznam.getPopisVydavku(), zoznam.getSuma(), zoznam.getDatum(), zoznam.getKategoria()});
        }


        zadajVydavokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                ZadajNovy dialog;
                try {
                    dialog = new ZadajNovy(VydavkyGui.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.pack();


                dialog.setLocationRelativeTo(null); // vycentrovanie okna
                dialog.setVisible(true);
//                System.exit(0);

            }
        });

        final Connection finalConn = conn;
                vytlacPdfButton.addActionListener(new ActionListener() {
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
                tableEditable=!tableEditable;
                if (tableEditable==true) JOptionPane.showMessageDialog (null, "Editovanie je povolené.. ", "Prepinač editovania", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog (null, "Editovanie je zakázané.. ", "Prepinač editovania", JOptionPane.INFORMATION_MESSAGE);

                table1 = new JTable(data, columnNames){

                    //  // Override the isCellEditable method to make all cells not editable
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return tableEditable;
                    }
                };

            }
        });
        zmažVydavokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id=0; int row=0;int column = 0;

                row = table1.getSelectedRow();
//                System.out.println("Nastavenie :  " + row + column + id);


              if ((row<0))
                {JOptionPane.showMessageDialog (null, "Zvoľ si prosím zaznam, ktorý chceš vymazať", "Vymaž záznam", JOptionPane.INFORMATION_MESSAGE);}
              else {
                  Object hodnota = table1.getModel().getValueAt(row, column);
                  if (hodnota instanceof Integer) id= (Integer) hodnota;


                  int input=JOptionPane.showConfirmDialog (null, " Naozaj chceš zmazať záznam s číslom :  " + id, "Vymaž záznam", JOptionPane.OK_CANCEL_OPTION);
                  System.out.println(input);

                  if (input==0) {
                      try {
                          sluzby.odstranVydavokMySql( id,finalConn);
                          row = table1.getSelectedRow();
                          System.out.println("rekord :  " +id + "\n" + "row v tabulke : " + row);

//                           DefaultTableModel model = (DefaultTableModel) table1.getModel();
                          ((DefaultTableModel)table1.getModel()).removeRow(row);  // zmaze riadok z tabulky


                      } catch (SQLException ex) {
                          System.out.println("Zaznam sa nezmazal, nie je pripojenia databaza");;
                      }
                  }


              }
            }
        });
        tlacButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    sluzby.vytlacMySql2Printer(finalConn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (DocumentException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (PrinterException ex) {
                    throw new RuntimeException(ex);
                }

//                JOptionPane.showMessageDialog (null, "Súbor bol  vytvorený", "Tlač zoznamu do pdf", JOptionPane.INFORMATION_MESSAGE);

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



//---------------------------------------------------------------------------

  /*  // vytvaranie prippojenia na Databazu

    Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/vydavky";
    String username = "root";
    String password = "password";

        if (conn == null) { // vytvorili sme tzv. singleton
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Databáza MySql je pripojená!");
                getLabel().setText("Databaza MySql je pripojena");
                }
//-----------------------------------------------------*/
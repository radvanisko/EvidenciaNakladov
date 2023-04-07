package sk.radvanisko.evidenciavydavkov.gui;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import sk.radvanisko.evidenciavydavkov.model.Sluzby;
import sk.radvanisko.evidenciavydavkov.model.Vydavok;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class ZadajNovy extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JFormattedTextField formattedTextField1;
    private JTextField textField4;

    private final VydavkyGui vydavkyGui;
    private Calendar currenttime;

    private void createUIComponents() {
        // TODO: place custom component creation code here

        System.out.println("trying to bring DatePicker alive!!!");
        UtilDateModel model = new UtilDateModel();
        model.setDate(2023,12,1);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
// Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

//todo tu mi to skape

//        datePanel.add(datePicker);
//        contentPane.add(datePanel);
    }


    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    public ZadajNovy(VydavkyGui vydavkyGui) throws SQLException {
        this.vydavkyGui = vydavkyGui;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // combobox define list


        ArrayList <String> kategorie =new ArrayList<String>();
        Sluzby sluzby=new Sluzby();
        Connection conn= sluzby.otvorH2();
        kategorie=sluzby.zoznamKategoriaH2(conn);

        for (String  i : kategorie) {  comboBox1.addItem(i);  }

//        createUIComponents();

        java.sql.Date datum=new java.sql.Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //tento format musi sediet s databazou, a modelom alebo vice versa
        String datumString=formatter.format(datum);
        formattedTextField1.setText(datumString);




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

//        System.out.println("bol stlačeny OK");
        Sluzby sluzby=new Sluzby();

        // vlozenie do objektu novy vydavok
        Vydavok novyvydavok=new Vydavok();
        novyvydavok.setPopisVydavku(textField1.getText());

        try {
            String hodnota = textField2.getText();
            hodnota=hodnota.replace(",",".");
            novyvydavok.setSuma(Double.parseDouble(hodnota));

//            novyvydavok.setSuma(Double.parseDouble(textField2.getText()));

        } catch (NumberFormatException e) {
            novyvydavok.setSuma(0);
        }

//      novyvydavok.setDatum(Date.valueOf(formattedTextField1.getText()));  //todo vyriešiť zadanie dátumu cez picker, alebo manualne

        String datumString=formattedTextField1.getText();
        System.out.println("DatumString   " +datumString);


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //tento format musi sediet s databazou, a modelom alebo vice versa


        Date  datum = null;
        try {
            datum = Date.valueOf(datumString);
        } catch (Exception e) {
            System.out.println(" zly format datumu , nastavil som dnesny");
            Calendar currenttime = Calendar.getInstance();
            datum = new Date((currenttime.getTime()).getTime());

        }


        datum= Date.valueOf(formatter.format(datum));

        System.out.println("Datum   " + datum);
        System.out.println("DatumString   " +datumString);


        formattedTextField1.setText(datumString);
        novyvydavok.setDatum(datum);
//        novyvydavok.setDatum(Date.valueOf(formattedTextField1.getText()));







        String typedText = ((JTextField)comboBox1.getEditor().getEditorComponent()).getText();
        novyvydavok.setKategoria(typedText);

//      Connection conn= sluzby.otvorDatabazu(); // MysQL
        Connection conn= sluzby.otvorH2(); // H2

//        sluzby.vlozVydavokMySql(conn,novyvydavok);  //vloz do databazy conn
        sluzby.vlozVydavokH2(conn,novyvydavok);
        JTable table1 = vydavkyGui.getTable1();

        int poslednyzaznam= sluzby.cisloposlednyZaznamH2(conn);
        novyvydavok.setId(poslednyzaznam);


        System.out.println(novyvydavok.getId()+novyvydavok.getPopisVydavku()+novyvydavok.getSuma()+novyvydavok.getKategoria());

        Object[] data = {novyvydavok.getId(),novyvydavok.getPopisVydavku(), novyvydavok.getSuma(), novyvydavok.getDatum(), novyvydavok.getKategoria()};
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addRow(data);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        System.out.println("bol stlaceny Cancel");
        dispose();
    }


}

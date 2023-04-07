package sk.radvanisko.evidenciavydavkov.lib;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.util.Properties;

public class JDatePicker extends JFrame {
        JDatePickerImpl datePicker;

        public JDatePicker() {


            SqlDateModel dateModel = new SqlDateModel();
            Properties properties = new Properties();
            properties.put("text.day", "Deň");
            properties.put("text.month", "Mesiac");
            properties.put("text,year", "Rok");


            JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);

            datePicker = new JDatePickerImpl(datePanel,null);

//            this.add(datePanel); // zobrazí picker panel
            this.add(datePicker);

            this.pack();
            this.setLocationRelativeTo(null); // vycentrovanie okna
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        }

        public static void main(String[] args) {
            new JDatePicker();

        }

    }
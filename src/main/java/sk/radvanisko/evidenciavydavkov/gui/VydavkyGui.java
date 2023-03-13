package sk.radvanisko.evidenciavydavkov.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public VydavkyGui(JTable table1) {
        this.table1 = table1;
    }


    public VydavkyGui() {

        zadajVýdavokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ZadajNovy dialog = new ZadajNovy();
                dialog.pack();
                dialog.setLocationRelativeTo(null); // vycentrovanie okna
                dialog.setVisible(true);
//                System.exit(0);

            }
        });

    }


    //konstruktory


    public VydavkyGui(JPanel panelHlavny) {
        this.panelHlavny = panelHlavny;
    }
}

    //gettery /settery


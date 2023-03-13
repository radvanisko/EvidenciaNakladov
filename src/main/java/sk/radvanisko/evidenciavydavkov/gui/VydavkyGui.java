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
    private JPanel paneltable;

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public VydavkyGui(JTable table1) {
        this.table1 = table1;
    }

    public VydavkyGui(JPanel panelHlavny) {
        this.panelHlavny = panelHlavny;
    }




    public VydavkyGui() {

           zadajVýdavokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


    //gettery /settery


    public JPanel getPaneltable() {
        return paneltable;
    }

    public void setPaneltable(JPanel paneltable) {
        this.paneltable = paneltable;
    }

    public JPanel getPanelHlavny() {
        return panelHlavny;
    }

    public void setPanelHlavny(JPanel panelHlavny) {
        this.panelHlavny = panelHlavny;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JButton getButton4() {
        return button4;
    }

    public void setButton4(JButton button4) {
        this.button4 = button4;
    }

    public JButton getZadajVýdavokButton() {
        return zadajVýdavokButton;
    }

    public void setZadajVýdavokButton(JButton zadajVýdavokButton) {
        this.zadajVýdavokButton = zadajVýdavokButton;
    }

    public JButton getButton1() {
        return button1;
    }

    public void setButton1(JButton button1) {
        this.button1 = button1;
    }

    public JButton getButton2() {
        return button2;
    }

    public void setButton2(JButton button2) {
        this.button2 = button2;
    }

    public JPanel getPanelButtony() {
        return panelButtony;
    }

    public void setPanelButtony(JPanel panelButtony) {
        this.panelButtony = panelButtony;
    }

    public JPanel getPanel3() {
        return panel3;
    }

    public void setPanel3(JPanel panel3) {
        this.panel3 = panel3;
    }
}

package view;

import model.Programare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorView extends JFrame {
    private JTable tableProgramari;
    private JButton confirmareButton;
    private JPanel mainPanel;

    public DoctorView() {
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setSize(this.mainPanel.getSize());
        this.setMinimumSize(this.mainPanel.getMinimumSize());
    }

    public void addConfirmareButtonListener(ActionListener actionListener) {
        confirmareButton.addActionListener(actionListener);
    }

    public int getTableProgramariSelectedRow() {
        return tableProgramari.getSelectedRow();
    }

    public Object getTableProgramariValueAt(int col) {
        return tableProgramari.getValueAt(tableProgramari.getSelectedRow(), col);
    }

    public void updateTableProgramari(List<Programare> programareList) {
        String[] header = {"id", "nume donator", "prenume donator", "grupa sanguina", "nume locatie", "data programare", "confirmare"};
        String[][] data = new String[programareList.size()][header.length];
        for (int i = 0; i < programareList.size(); i++) {
            data[i][0] = programareList.get(i).getId().toString();
            data[i][1] = programareList.get(i).getDonator().getNume();
            data[i][2] = programareList.get(i).getDonator().getPrenume();
            data[i][3] = String.valueOf(programareList.get(i).getDonator().getGrupaSanguina());
            data[i][4] = programareList.get(i).getLocatie().getNume();
            data[i][5] = programareList.get(i).getDataProgramarii().toString();
            data[i][6] = programareList.get(i).isConfirmare() ? "Confirmat" : "Neconfirmat";
        }
        TableModel tableModel = new DefaultTableModel(data, header);
        tableProgramari.setModel(tableModel);
        repaint();
    }
}

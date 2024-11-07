import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VaccineTableDisplay {
	public static void showTable(Map<String, Integer> provinceVaccineTotals) {
        JFrame frame = new JFrame("Vaccine Totals by Province");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        String[] columnNames = {"Province", "Total Vaccines"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model
        for (Map.Entry<String, Integer> entry : provinceVaccineTotals.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        
        frame.setVisible(true);
    }
}

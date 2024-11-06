import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Vaccines extends JFrame {
    private JTable table;

    public Vaccines() {
        setTitle("Vaccine Doses Administered");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(59, 71, 73));
        getContentPane().setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Vaccine Doses Administered");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Read and aggregate data by provinces for table display
        Map<String, Integer> provinceVaccineTotals = readAndAggregateData("Code/src/data/vaccine_administration_timeseries_prov.csv");

        // Prepare headers and data for the table
        String[] headers = {"Province", "Total Vaccines Administered"};
        String[][] rows = new String[provinceVaccineTotals.size()][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : provinceVaccineTotals.entrySet()) {
            rows[index][0] = entry.getKey();
            rows[index][1] = entry.getValue().toString();
            index++;
        }

        // Create a table model and JTable
        table = new JTable(rows, headers);
        table.setBackground(new Color(59, 71, 73));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(30);

        // Set table header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(143, 179, 175));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 20));

        // Center align the cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add the table to the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null); // center the window
        setVisible(true);
    }

    private static Map<String, Integer> readAndAggregateData(String filePath) {
        Map<String, Integer> provinceData = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 2 && !values[0].isEmpty() && !values[2].isEmpty()) {
                    String province = values[0].trim();
                    int avaccine;
                    try {
                        avaccine = Integer.parseInt(values[2].trim());
                    } catch (NumberFormatException e) {
                        continue; // Skip invalid number format
                    }

                    provinceData.put(province, provinceData.getOrDefault(province, 0) + avaccine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinceData;
    }
}

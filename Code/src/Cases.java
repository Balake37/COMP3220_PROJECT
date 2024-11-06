import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Cases extends JFrame {
    private JTable table;

    public Cases() {
        setTitle("COVID-19 Cases");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes current window only
        getContentPane().setBackground(new Color(59, 71, 73)); // set background color
        getContentPane().setLayout(new BorderLayout()); // using BorderLayout

        // Title label
        JLabel titleLabel = new JLabel("COVID-19 Cases");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Read and aggregate data by provinces for table display
        Map<String, Integer> provinceData = readAndAggregateData("Code/src/data/cases_hr.csv");

        // Prepare headers and data for the table
        String[] headers = {"Province", "Number of Cases"};
        String[][] rows = new String[provinceData.size()][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : provinceData.entrySet()) {
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
        Map<String, Integer> provinceData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 6) {
                    System.out.println("Skipping invalid line (not enough columns): " + line);
                    continue;
                }

                String province = values[1].replaceAll("\"", "").trim();
                String casesString = values[5].replaceAll("\"", "").trim();

                if (!casesString.isEmpty() && casesString.matches("\\d+")) {
                    int cases = Integer.parseInt(casesString);
                    provinceData.put(province, provinceData.getOrDefault(province, 0) + cases);
                } else {
                    System.out.println("Skipping invalid cases value: " + casesString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceData;
    }
}

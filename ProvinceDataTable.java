

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ProvinceDataTable {

    public static void main(String[] args) {
        // Create a JFrame to hold the table
        JFrame frame = new JFrame("Province Data Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Read and aggregate data by provinces
        Map<String, Integer> provinceData = readAndAggregateData("cases_hr.csv"); // Update with your file path

        // Check if the data is being aggregated correctly
        if (provinceData.isEmpty()) {
            System.out.println("No data found or failed to parse the CSV.");
        } else {
            System.out.println("Data successfully parsed:");
            provinceData.forEach((province, cases) -> System.out.println(province + ": " + cases));
        }

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
        DefaultTableModel model = new DefaultTableModel(rows, headers);
        JTable table = new JTable(model);

        // Add the table to a scroll pane and then to the frame
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to read and aggregate data by province from the CSV file
    public static Map<String, Integer> readAndAggregateData(String filePath) {
        Map<String, Integer> provinceData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < 6) { // Check if there are enough columns for column 6 data
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String province = values[1].replaceAll("\"", "").trim(); // Province abbreviation
                String casesString = values[5].replaceAll("\"", "").trim(); // Now using column 6

                System.out.println("Parsed Province: " + province + ", Cases: " + casesString); // Debugging line

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

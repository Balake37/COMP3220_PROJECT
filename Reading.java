package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reading {
    public static void main(String[] args) {
        String csvFile = "vaccine_administration_timeseries_prov.csv";
        String line;
        String csvSplitBy = ",";
        
        Map<String, Integer> provinceVaccineTotals = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header row if necessary
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                String province = values[0]; // Assuming "province" is the first column
                int avaccine = Integer.parseInt(values[2]); // Assuming "avaccine" is the 5th column (index 4)
                
                provinceVaccineTotals.put(province, provinceVaccineTotals.getOrDefault(province, 0) + avaccine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Pass the data to the JTable for display
        VaccineTableDisplay.showTable(provinceVaccineTotals);
    }
}

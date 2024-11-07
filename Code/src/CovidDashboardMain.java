import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Integrate all UI components and data classes into one main runnable program
public class CovidDashboardMain {
    public static void main(String[] args) {
        // Create and display the main frame
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static Image backgroundImage; //Initialize bg image

    // Method to create and display the main GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("COVID-19 Integrated Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        backgroundImage = new ImageIcon("Code/src/images/menu.png").getImage(); //Load bg image

        //Paint the background image
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(59, 71, 73, 0));

        // Title label
        JLabel titleLabel = new JLabel("COVID-19 Live Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createVerticalStrut(190));

        // Add buttons for different views
        addButton(menuPanel, "COVID-19 Cases", e -> new Cases());
        addButton(menuPanel, "Active Cases", e -> new Active());
        addButton(menuPanel, "Total Deaths", e -> new Deaths());
        addButton(menuPanel, "Total Recovered", e -> new Recovered());
        addButton(menuPanel, "Reported Cases", e -> new Reported());
        addButton(menuPanel, "Vaccine Administration Data", e -> new Vaccines());
        addButton(menuPanel, "Exit", e -> System.exit(0));

        // Add the menu panel to the main frame
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Helper method to add buttons to the menu panel
    private static void addButton(JPanel panel, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.addActionListener(listener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(143, 179, 175));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
        panel.add(button);
        panel.add(Box.createVerticalStrut(15));
    }

    // Method to display Vaccine Data Table
    private static void displayVaccineData() {
        String csvFile = "vaccine_administration_timeseries_prov.csv";
        String line;
        String csvSplitBy = ",";

        Map<String, Integer> provinceVaccineTotals = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header row if necessary
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                String province = values[0]; // Assuming "province" is the first column
                int avaccine = Integer.parseInt(values[2]); // Assuming "avaccine" is the third column (index 2)

                provinceVaccineTotals.put(province, provinceVaccineTotals.getOrDefault(province, 0) + avaccine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (provinceVaccineTotals.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data found or failed to parse the CSV.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        VaccineTableDisplay.showTable(provinceVaccineTotals);
    }
}

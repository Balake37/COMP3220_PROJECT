import javax.swing.*;
import java.awt.*;

public class Vaccines extends JFrame {
    public Vaccines() {
        setTitle("Vaccine Doses Administered");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes this frame ONLY

        getContentPane().setBackground(new Color(59, 71, 73));
        getContentPane().setLayout(new BorderLayout());

        //title label
        JLabel titleLabel = new JLabel("Vaccine Doses Administered");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        //add title to north (top) region
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        //TODO: add content here (ie. charts, tables, information from API)

        setLocationRelativeTo(null); //center the window
        setVisible(true);
    }
}

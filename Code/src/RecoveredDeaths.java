import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RecoveredDeaths extends JFrame {
    private BufferedImage recoveredImage, deathImage;
    public RecoveredDeaths() {
        setTitle(" Total Recovered/Deaths");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes current window only
        getContentPane().setBackground(new Color(59, 71, 73)); //set background colour
        getContentPane().setLayout(new BorderLayout()); //using BorderLayout

        //title label
        JLabel titleLabel = new JLabel("Total Recovered/Deaths");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        //create a panel to hold the images & labels
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(59, 71, 73)); //matches frame bg colour
        imagePanel.setLayout(new GridLayout(1, 2)); //creates 2 columns side-by-side

        //load images
        try {
            recoveredImage = ImageIO.read(new File("src/images/recovered.png"));
            deathImage = ImageIO.read(new File("src/images/deaths.png"));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        //create image labels
        JLabel recoveredLabel = createImageLabel(recoveredImage, "Total Recovered Cases");
        JLabel deathLabel = createImageLabel(deathImage, "Total Deaths");

        //add labels to panel
        imagePanel.add(recoveredLabel);
        imagePanel.add(deathLabel);

        //add panel to the frame
        getContentPane().add(imagePanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); //center the window
        setVisible(true);
    }

    //create a JLabel for images that is also clickable (leads to respective frames)
    private JLabel createImageLabel(BufferedImage image, String labelText) {
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //changes cursor to hover hand
        imageLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        imageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        imageLabel.setForeground(Color.WHITE);
        imageLabel.setText(labelText); //set text under the image

        imageLabel.setFont(new Font("Arial", Font.BOLD, 28));

        //add mouse listener for image click
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //open the respective frame (class) when clicked
                if (labelText.equals("Total Recovered Cases")) {
                    new Recovered(); //opens Recovered frame
                } else if (labelText.equals("Total Deaths")) {
                    new Deaths(); //opens Deaths frame
                }
            }
        });

        return imageLabel;
    }
}

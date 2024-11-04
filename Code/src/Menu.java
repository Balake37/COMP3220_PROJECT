import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Menu extends JPanel {
    private BufferedImage backgroundImage;
    public Menu(Main main) {
        //load the background image
        try {
            backgroundImage = ImageIO.read(new File("src/images/menu.png"));
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        setOpaque(false); //make panel non-opaque so that bg image is visible
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //title label
        JLabel titleLabel = new JLabel("COVID-19 Live DashBoard");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(15)); //space before title
        add(titleLabel);

        add(Box.createVerticalStrut(240)); //space between title and buttons

        //COVID-19 Cases button
        JButton button1 = new JButton("COVID-19 Cases");
        designButton(button1, main);
        add(button1);
        add(Box.createVerticalStrut(5));

        //Vaccines Administered button
        JButton button2 = new JButton("Vaccines Administered");
        designButton(button2, main);
        add(button2);
        add(Box.createVerticalStrut(5));

        //Total Recovered/Deaths button
        JButton button3 = new JButton("Total Recovered/Deaths");
        designButton(button3, main);
        add(button3);
        add(Box.createVerticalStrut(5));

        //Exit button
        JButton button4 = new JButton("Exit");
        designButton(button4, main);
        add(button4);

        add(Box.createVerticalGlue()); //space at the bottom (centers the buttons)
    }

    //design for each button
    private void designButton(JButton button, Main main) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        //button size
        button.setPreferredSize(new Dimension(180, 40));
        button.setMinimumSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setMargin(new Insets(0, 0, 0, 0));

        //button and text colour
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);

        //listener for when button is hovered
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(143, 179, 175)); //colour change for hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); //revert back to original colour (white)
            }
        });

        //action listener for each button when clicked
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (button.getText()) {
                    case "COVID-19 Cases":
                        new Cases(); //open Cases frame
                        break;
                    case "Vaccines Administered":
                        new Vaccines(); //open Vaccines frame
                        break;
                    case "Total Recovered/Deaths":
                        new RecoveredDeaths(); //open RecoveredDeaths frame
                        break;
                    case "Exit":
                        System.exit(0); //exit program
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
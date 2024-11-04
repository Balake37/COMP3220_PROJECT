import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        setTitle("COVID-19 Live DashBoard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 800);
        setLocationRelativeTo(null);

        //add menu panel for this frame
        add(new Menu(this));
        setVisible(true);
    }

    public static void main(String[] args) {
        //run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}

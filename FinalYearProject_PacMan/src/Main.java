import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        initializeUI();
    }

    private void initializeUI() {

        add(new Game());

        setTitle("Pacman");
        setSize( 15*24+15, 16*24+60); //blockshigh*blocksize
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main ex = new Main();
        ex.setVisible(true);

    }
}

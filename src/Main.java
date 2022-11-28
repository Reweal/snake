import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 380);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mainWindow = new Main();
    }
}
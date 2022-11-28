import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 320;

    private final int DOT_SIZE = 16;

    private final int ALL_DOTS = 400;

    private Image snake;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    private int dots;

    private Timer timer;
    private boolean inGame = true;
    private boolean right = true;
    private boolean left;
    private boolean up;
    private boolean down;


//    private final int x;
//    private final int y;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        createApple();
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(100, this);
        timer.start();
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon appleicon = new ImageIcon("src/apple.png");
        this.apple = appleicon.getImage();
        ImageIcon snakeicon = new ImageIcon("src/snake2.png");
        this.snake = snakeicon.getImage();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    private void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if( x[0] > SIZE) {
            inGame = false;
        }
        if( x[0] < 0 ) {
            inGame = false;
        }
        if( y[0] > SIZE) {
            inGame = false;
        }
        if( y[0] < 0) {
            inGame = false;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(snake, x[i], y[i], this);
            }
        } else {
            gameOver(g);
        }
//        if (!inGame) {
//            initGame();
//            repaint();
//            timer.restart();
//            inGame = true;
//        }
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down) {
                left = false;
                right = false;
                up = true;
            }
            if(key == KeyEvent.VK_DOWN && !up) {
                left = false;
                down = true;
                right = false;
            }
            if (key == KeyEvent.VK_SPACE) {
                if (inGame == true) {
                    timer.stop();
                    inGame = false;
                } else {
                    timer.start();
                    inGame = true;
                }
            }

        }
    }
    public void gameOver(Graphics g) {
//        g.setColor(Color.RED);
//        g.setFont(new Font("Ink Free", Font.BOLD, 22));
//        g.drawString("BAD", 125, SIZE/2);

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Game Over", 100, SIZE/2);
    }
}

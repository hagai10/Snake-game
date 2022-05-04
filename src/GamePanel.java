

        import java.awt.*;
        import java.awt.event.*;
        import javax.swing.*;
        import java.util.Random;

    public class GamePanel extends JPanel implements ActionListener {
        static final int GAMEPANELWIDTH = 1280;
        static final int GAMEPANELHEIGHT = 720;
        static final int PANELWIDTH = 1260;
        static final int PANELHEIGHT = 700;
        static final int UNIT_SIZE = 50;
        static final int GAME_UNITS = (PANELWIDTH * PANELHEIGHT) / UNIT_SIZE;
        static final int DELAY = 150;
        static final int x[] = new int[GAME_UNITS];
        static final int y[] = new int[GAME_UNITS];
        static int bodyParts = 6;
        static int applesEaten;
        static int appleX;
        static int appleY;
        static char direction = 'R';
        static boolean running = false;
        static Timer timer;
        Random random;

        public GamePanel() {
            random = new Random();
            this.setPreferredSize(new Dimension(GAMEPANELWIDTH, GAMEPANELHEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
            startGame();
        }

        public void startGame() {
            newApple();
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {

            if (running) {

                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.RED);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(Color.GREEN);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
                g.setColor(Color.gray);
                g.setFont(new Font("Kristen ITC", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + applesEaten, (GAMEPANELWIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
            } else {
                gameOver(g);
            }

        }

        public void newApple() {
            appleX = random.nextInt((int) (PANELWIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt((int) (PANELHEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

        public void move() {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                    break;
            }

        }

        public void checkApple() {
            if ((x[0] == appleX) && (y[0] == appleY)) {
                bodyParts++;
                applesEaten++;
                newApple();
            }
        }

        public void checkCollisions() {
            //checks if head collides with body
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                }
            }
            //check if head touches left border
            if (x[0] < 0) {
                running = false;
            }
            //check if head touches right border
            if (x[0] > GAMEPANELWIDTH) {
                running = false;
            }
            //check if head touches top border
            if (y[0] < 0) {
                running = false;
            }
            //check if head touches bottom border
            if (y[0] > GAMEPANELHEIGHT) {
                running = false;
            }

            if (!running) {
                timer.stop();
            }
        }

        public void gameOver(Graphics g) {
            g.setColor(Color.orange);
            g.setFont(new Font("Kristen ITC", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", (GAMEPANELWIDTH - metrics.stringWidth("Game Over")) / 2, GAMEPANELHEIGHT / 2 - 70);
            g.drawString("Your final score is: " + applesEaten, (GAMEPANELWIDTH - metrics.stringWidth("Your final score is: " + applesEaten)) / 2, GAMEPANELHEIGHT / 2);
            g.drawString("Press enter to try again", (GAMEPANELWIDTH - metrics.stringWidth("Press enter to try again")) / 2, GAMEPANELHEIGHT / 2 + 70);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (running) {
                move();
                checkApple();
                checkCollisions();
            }
            repaint();
        }

        public class MyKeyAdapter extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (GamePanel.direction != 'R') {
                            GamePanel.direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (GamePanel.direction != 'L') {
                            GamePanel.direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (GamePanel.direction != 'D') {
                            GamePanel.direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (GamePanel.direction != 'U') {
                            GamePanel.direction = 'D';
                        }
                        break;
                }
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    if(!running){
                        applesEaten=0;
                        bodyParts=6;
                        direction='R';
                        for(int i=0; i<x.length; i++)
                            x[i]=0;
                        for(int i=0; i<y.length; i++)
                            y[i]=0;
                        repaint();
                        startGame();

                    }

                }
            }

        }
    }


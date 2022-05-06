

        import java.awt.*;
        import java.awt.event.*;
        import javax.swing.*;
        import java.util.Random;

    public class GamePanel extends JPanel implements ActionListener {
        static final int GAMEPANELWIDTH = 1300;
        static final int GAMEPANELHEIGHT = 700;
        static final int UNIT_SIZE = 50;
        static final int GAME_UNITS = (GAMEPANELWIDTH * GAMEPANELHEIGHT) / UNIT_SIZE;
         int DELAY = 170;
        final int x[] = new int[GAME_UNITS];
        final int y[] = new int[GAME_UNITS];
        static char direction = 'R';
        int bodyParts;
        int applesEaten;
        int appleX;
        int appleY;
        boolean running = false;

        boolean welcome = false;
        JButton play;
        JButton exit;
        Timer timer;
        Random random;

        ImageIcon backgrond_welcome = new ImageIcon("1350welcome.jpg");
        ImageIcon backgrond_Game = new ImageIcon("background.jpg");
        ImageIcon backgrond_Gameover = new ImageIcon("1350.jpg");
        ImageIcon apple = new ImageIcon("apple.png");
        ImageIcon GoldApple = new ImageIcon("goldapple.png");

        public GamePanel() {
            random = new Random();
            this.setPreferredSize(new Dimension(GAMEPANELWIDTH, GAMEPANELHEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
        }
        public void startGame() {
            exit.setVisible(false);
            play.setVisible(false);
            welcome=false;
            running=true;
            applesEaten = 0;
            bodyParts = 6;
            direction = 'R';
            DELAY=150;
            for (int i = 0; i < x.length; i++)
                x[i] = 50;
            for (int i = 0; i < y.length; i++)
                y[i] = 50;
            welcome=true;
            newApple();
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
            repaint();
        }

        public void letsPlay (Graphics g) {
            play = new JButton("Play");
            play.setBounds(470, GAMEPANELHEIGHT / 2+100, 300, 50);
            this.add(play);
            play.addActionListener((event) -> {
                startGame();
            });
            exit = new JButton("Exit");
            play.setBounds(470, GAMEPANELHEIGHT / 2 - 100, 300, 50);
            this.add(exit);
            play.addActionListener((event) -> {
                System.exit(0);
            });
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            if (!welcome) {
                letsPlay(g);
                g.drawImage(backgrond_welcome.getImage(), 0, 0, null);
            }

            if (running) {
                g.drawImage(backgrond_Game.getImage(), 0, 0, null);
                g.setColor(new Color(139, 69, 2));
                g.fillRect(0, 0, 50, GAMEPANELHEIGHT);
                g.fillRect(0, 0, GAMEPANELWIDTH, 50);
                g.fillRect(GAMEPANELWIDTH - 50, 0, 50, GAMEPANELHEIGHT);
                g.fillRect(0, GAMEPANELHEIGHT - 50, GAMEPANELWIDTH, 50);

                g.setColor(Color.gray);
                for (int i = 0; i < GAMEPANELWIDTH; i++) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, GAMEPANELHEIGHT);

                }
                for (int i = 0; i < GAMEPANELHEIGHT; i++) {
                    g.drawLine(0, i * UNIT_SIZE, GAMEPANELWIDTH, i * UNIT_SIZE);

                }

                if (applesEaten % 5 == 0 && applesEaten != 0)
                    g.drawImage(GoldApple.getImage(), appleX, appleY, null);

                else
                    g.drawImage(apple.getImage(), appleX, appleY, null);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.RED);
                        g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(Color.GREEN);
                        g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
                g.setColor(Color.gray);
                g.setFont(new Font("Kristen ITC", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + applesEaten, (GAMEPANELWIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
                if ((applesEaten - 3) % 5 == 0 && applesEaten != 3) {
                    g.setColor(Color.gray);
                    g.setFont(new Font("Kristen ITC", Font.BOLD, 40));
                    g.drawString("Gold apple", (GAMEPANELWIDTH - metrics.stringWidth("Gold apple")) / 2, 40);
                }
            }
            if (!running && welcome) {
                gameOver(g);
            }

        }

        public void newApple() {
            appleX = random.nextInt((int) (GAMEPANELWIDTH / UNIT_SIZE) - 2) * UNIT_SIZE + UNIT_SIZE;
            appleY = random.nextInt((int) (GAMEPANELHEIGHT / UNIT_SIZE) - 2) * UNIT_SIZE + UNIT_SIZE;
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
                if (applesEaten % 5 == 0 && applesEaten != 0)
                    applesEaten = applesEaten + 3;
                else
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
            if (x[0] < 50) {
                running = false;
            }
            //check if head touches right border
            if (x[0] >= GAMEPANELWIDTH - 50) {
                running = false;
            }
            //check if head touches top border
            if (y[0] < 50) {
                running = false;
            }
            //check if head touches bottom border
            if (y[0] >= GAMEPANELHEIGHT - 50) {
                running = false;
            }

            if (!running) {
                timer.stop();
            }
        }

        public void gameOver(Graphics g) {
            g.drawImage(backgrond_Gameover.getImage(),0,0,null);
            g.setColor(Color.orange);
            g.setFont(new Font("Kristen ITC", Font.BOLD, 70));
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
                    if(!running)
                        startGame();

                }
            }

        }
    }


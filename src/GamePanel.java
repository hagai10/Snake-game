

        import java.awt.*;
        import java.awt.event.*;
        import javax.swing.*;
        import java.util.Random;

    public class GamePanel extends JPanel implements ActionListener {
        Apple apple = new Apple();
        SnakeBody snake = new SnakeBody();
        static final int PANELWIDTH = 1300;
        static final int PANELHEIGHT = 750;
        static final int UNITSIZE = 50;
        static int DELAY;
        int Score;
        boolean welcome = false;
        static boolean running = false;
        Timer timer;
        Random random;
        JButton play;
        JButton Exit;
        ImageIcon backgrond_welcome = new ImageIcon("1350welcome.jpg");
        ImageIcon backgrond_Game = new ImageIcon("background.jpg");

        GamePanel() {
            PlayerMovment player = new PlayerMovment(snake,this);
            this.setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            random = new Random();
        }
        public void StartGame() {
            Exit.setVisible(false);
            play.setVisible(false);
            DELAY = 175;
            Score = 0;
            snake.setBodyParts(6);
            snake.direction = 'R';
            for (int i = 0; i < snake.x.length; i++) {
                snake.x[i] = 50;
            }
            for (int i = 0; i < snake.y.length; i++) {
                snake.y[i] = 50;
            }
            welcome = true;
            newApple();
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
            repaint();
        }

        public void LetsPlay(Graphics g) {
            play = new JButton("PLAY");
            play.setBounds(PANELWIDTH/2-150,PANELHEIGHT / 2, 300, 50);
            this.add(play);
            play.addActionListener((event) -> {
                StartGame();
            });
            Exit = new JButton("Exit");
            Exit.setBounds(PANELWIDTH/2-150, (PANELHEIGHT / 2) + 100, 300, 50);
            this.add(Exit);
            Exit.addActionListener((event) -> {
                System.exit(0);

            });

        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            if (!welcome) {
                LetsPlay(g);
                g.drawImage(backgrond_welcome.getImage(), 0, 0, null);

            }

            if (running) {
                g.drawImage(backgrond_Game.getImage(), 0, 0, null);
                g.setColor(new Color(139, 69, 2));
                g.fillRect(0, 0, 50, 720);
                g.fillRect(0, 0, 1280, 50);
                g.fillRect(1250, 0, 50, 750);
                g.fillRect(0, 700, 1280, 50);

                g.setColor(Color.gray);
                for (int i = 0; i < PANELWIDTH; i++) {
                    g.drawLine(i * UNITSIZE, 0, i * UNITSIZE, PANELHEIGHT);

                }
                for (int i = 0; i < PANELHEIGHT; i++) {
                    g.drawLine(0, i * UNITSIZE, PANELWIDTH, i * UNITSIZE);

                }

                if (Score % 5 == 0 && Score != 0) {

                    g.drawImage(apple.GoldApple.getImage(), apple.getAppleX(), apple.getAppleY(), null);
                } else {

                    g.drawImage(apple.RedApple.getImage(), apple.getAppleX(), apple.getAppleY(), null);
                }

                for (int i = 0; i < snake.getBodyParts(); i++) {
                    if (i == 0) {
                        g.setColor(Color.blue);
                        g.fillOval(snake.x[i], snake.y[i], UNITSIZE, UNITSIZE);


                    } else {
                        g.setColor(new Color(51, 204, 255));
                        g.fillOval(snake.x[i], snake.y[i], UNITSIZE, UNITSIZE);
                    }
                }
                g.setColor(Color.GREEN);
                g.setFont(new Font("Kristen ITC", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + Score, (PANELWIDTH - metrics.stringWidth("Score: " + Score)) / 2, g.getFont().getSize());
                if ((Score - 3) % 5 == 0 && Score != 3) {
                    g.setColor(new Color(255, 215, 0));
                    g.setFont(new Font("Kristen ITC", Font.BOLD, 40));
                    g.drawString("Bonus!!", ((PANELWIDTH - metrics.stringWidth("Score: " + Score)) / 2) + 200, g.getFont().getSize());
                }
            }
            if (!running && welcome)
                gameOver(g);


        }

        public void newApple() {
            int XrandomLocationPossible= random.nextInt((int) (PANELWIDTH / UNITSIZE) - 2) * UNITSIZE + 50;
            int YrandomLocationPossible = random.nextInt((int) (PANELHEIGHT / UNITSIZE) - 2) * UNITSIZE + 50;

            apple.setAppleX(XrandomLocationPossible);
            apple.setAppleY(YrandomLocationPossible);

        }


        public void checkApple() {
            if ((snake.x[0] == apple.getAppleX()) && (snake.y[0] == apple.getAppleY())) {
                 snake.setBodyParts(snake.getBodyParts() + 1);
                if (Score % 5 == 0 && Score != 0)
                    Score += 3;
                else {
                    Score++;
                }
                newApple();
            }
        }

        public void checkCollisions() {

            for (int i = snake.getBodyParts(); i > 0; i--) {
                if ((snake.x[0] == snake.x[i]) && (snake.y[0] == snake.y[i])) {
                    running = false;
                }
            }
            if (snake.x[0] < 50)
                running = false;

            if (snake.x[0] >= PANELWIDTH- 50)
                running = false;

            if (snake.y[0] < 50)
                running = false;

            if (snake.y[0] >= PANELHEIGHT - 50)
                running = false;

            if (!running)
                timer.stop();
        }

        public void gameOver(Graphics g) {
            g.setColor(Color.blue);
            g.setFont(new Font("Kristen ITC", Font.BOLD, 70));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawImage(backgrond_Game.getImage(), 0, 0, null);
            g.drawString("Game Over", (PANELWIDTH - metrics.stringWidth("Game Over")) / 2, PANELHEIGHT / 2 - 70);
            g.drawString("Your final score is: " + Score, (PANELWIDTH - metrics.stringWidth("Your final score is: " + Score)) / 2, PANELHEIGHT / 2);
            g.drawString("Press enter to try again", (PANELWIDTH - metrics.stringWidth("Press enter to try again")) / 2,PANELHEIGHT / 2 + 70);


        }
        // פונקציית ההרצה הנורשת מ ActionEvent
        @Override
        public void actionPerformed(ActionEvent e) {

            if (running) {
                snake.move();
                checkApple();
                checkCollisions();
            }
            repaint();
        }

    }


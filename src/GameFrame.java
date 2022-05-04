
      import javax.swing.*;
      import java.awt.*;

    public class GameFrame extends JFrame{

        GameFrame(){
            this.add(new GamePanel());
            this.setTitle("Snake");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setSize(GamePanel.GAMEPANELWIDTH,GamePanel.GAMEPANELHEIGHT);
            this.setVisible(true);
            this.setLocationRelativeTo(null);

        }


        public static void main(String[] args) {

            new GameFrame();
        }
        }

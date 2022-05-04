
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
          /*  Font myDefaultFont = new Font ("Arial",Font.BOLD,20);
            JButton myButten = new JButton("Play nae");
            myButten.setBounds(0,0, 150 ,30);
            myButten.setFont(myDefaultFont);
            myButten.addActionListener((event)->{

                this.add(new GamePanel());
            });
            this.add(myButten);*/
        }


        public static void main(String[] args) {

            new GameFrame();
        }
        }

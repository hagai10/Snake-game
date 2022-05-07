import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class PlayerMovment extends KeyAdapter {
    private SnakeBody s;
    private GamePanel g;

    PlayerMovment(SnakeBody s, GamePanel g) {
        this.s = s;
        this.g = g;
        // הוספת האזנה למקלדת
        g.addKeyListener(this);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (s.direction != 'R') {
                    s.direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (s.direction != 'L') {
                    s.direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (s.direction != 'D') {
                    s.direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (s.direction != 'U') {
                    s.direction = 'D';
                }
                break;
        }
        // אתחול המשחק ע"י מקש Enter
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!GamePanel.running)
                g.StartGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!g.running) {
                g.repaint();
                g.StartGame();
            }

        }
    }
}
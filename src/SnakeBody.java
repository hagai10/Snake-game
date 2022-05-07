public class SnakeBody {
    static final int Snake_Max_Body = (GamePanel.PANELWIDTH * GamePanel.PANELHEIGHT) / GamePanel.UNITSIZE;

    final int x[] = new int[Snake_Max_Body];
    final int y[] = new int[Snake_Max_Body];
    private int bodyParts;
    char direction;
    private int UNIT_SIZE = GamePanel.UNITSIZE;

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }

    public void move() {

        for (int i = getBodyParts(); i > 0; i--) {
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
}
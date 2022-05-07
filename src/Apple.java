import javax.swing.*;
public class Apple {
    private int appleX;
    private int appleY;
    ImageIcon RedApple = new ImageIcon("apple.png");
    ImageIcon GoldApple = new ImageIcon("goldapple.png");

    // setters and getters
    public int getAppleX() {
        return appleX;
    }

    public void setAppleX(int appleX) {

        this.appleX = appleX;
    }

    public int getAppleY() {
        return appleY;
    }

    public void setAppleY(int appleY) {
        this.appleY = appleY;
    }
}

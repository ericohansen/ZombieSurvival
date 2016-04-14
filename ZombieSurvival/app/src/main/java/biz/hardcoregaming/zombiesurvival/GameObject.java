package biz.hardcoregaming.zombiesurvival;

import android.graphics.Rect;

/**
 * Created by HardcorFreddy on 2/15/2016.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;
<<<<<<< HEAD
    protected boolean isCollideX = false;
    protected boolean isCollideY = false;
    protected boolean isAlive = true;
    protected int health = 100;
=======
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
<<<<<<< HEAD

    public boolean isCollideY() {
        return isCollideY;
    }

    public void setIsCollideY(boolean isCollideY) {
        this.isCollideY = isCollideY;
    }

    public boolean isCollideX() {
        return isCollideX;
    }

    public void setIsCollideX(boolean isCollideX) {
        this.isCollideX = isCollideX;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
    }
=======
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
}

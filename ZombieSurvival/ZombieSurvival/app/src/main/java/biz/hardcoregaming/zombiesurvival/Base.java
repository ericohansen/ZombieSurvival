package biz.hardcoregaming.zombiesurvival;

/**
 * Created by ericohansen on 2/26/2016.
 */
public class Base {

    //constants
    private int x;
    private int y;
    private int health;
    private int level;
    private int width;
    private int height;

    public Base(int x, int y, int health, int level) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.level = level;
    }

    public Base(int x, int y, int health, int level, int width, int height) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.level = level;
        this.width = width;
        this.height = height;
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


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

package biz.hardcoregaming.zombiesurvival;

<<<<<<< HEAD
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ericohansen on 2/26/2016.
 */
public class Base extends GameObject {

    //constants
    private int health;
    private int level;
    private int width = 400;
    private int height = 400;
    private Paint paint = new Paint();
=======
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
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b

    public Base(int x, int y, int health, int level) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.level = level;
<<<<<<< HEAD
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        dx = 0;
        dy = 0;
=======
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
    }

    public Base(int x, int y, int health, int level, int width, int height) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.level = level;
        this.width = width;
        this.height = height;
<<<<<<< HEAD
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        dx = 0;
        dy = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public void update(){
        if(!isCollideX)x += dx;
        if(!isCollideY)y += dy;
    }

=======
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


>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

<<<<<<< HEAD
=======
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

>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

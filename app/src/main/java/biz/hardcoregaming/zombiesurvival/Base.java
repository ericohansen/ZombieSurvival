package biz.hardcoregaming.zombiesurvival;

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

    public Base(int x, int y, int health, int level) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.level = level;
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        dx = 0;
        dy = 0;
    }

    public Base(int x, int y, int health, int level, int width, int height) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.level = level;
        this.width = width;
        this.height = height;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

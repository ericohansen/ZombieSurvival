package biz.hardcoregaming.zombiesurvival;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ericohansen on 2/27/2016.
 */
public class Projectile extends GameObject {
    private int x = GamePanel.screenWidth/2, y = GamePanel.screenHeight/2, sx, sy;
    private int speed;
    private int damage;
    private float angle;
    private boolean isActive = true;
    private int width, height;

    public Projectile(int width, int height, int speed, int damage, float angle){
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
        this.angle = angle;
        setMoveAngleBools();
    }

    // sets the amount of x and y change based on angle of bullet/projectile
    private void setMoveAngleBools(){
        sx = (int)(speed*Math.cos(angle*(Math.PI/180)));
        sy = (int)(speed*Math.sin(angle*(Math.PI/180)));
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, 2, paint);
    }

    public void update(){
        if(isActive){
            x += sx;
            y += sy;
        }
        if(x > GamePanel.screenWidth || x < 0 || y > GamePanel.screenHeight || y < 0)isActive = false;
    }

    public void setActive(boolean active){
        isActive = active;
    }

    public boolean isActive(){
        return isActive;
    }

    public int getDamage(){
        return damage;
    }

}

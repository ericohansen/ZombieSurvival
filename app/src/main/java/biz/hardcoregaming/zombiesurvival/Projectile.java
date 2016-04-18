package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by ericohansen on 2/27/2016.
 */
public class Projectile extends GameObject {
    private int x = GamePanel.screenWidth/2, y = GamePanel.screenHeight/2, sx, sy;
    private int speed;
    private int damage;
    private float angle;
    private boolean isActive = false;
    private int width, height;
    private Matrix matrix = new Matrix();
    private Bitmap image;
    private int xOffset = 0, yOffset = 0;

    public Projectile(int width, int height, int speed, int damage, float angle){
        x = GamePanel.screenWidth/2;
        y = GamePanel.screenHeight/2;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
        this.angle = angle;
        isActive = true;
        setMoveAngleBools();
    }

    public Projectile(int width, int height, int damage, float angle, Bitmap res){
        this.width = width;
        this.height = height;
        this.damage = damage;
        this.angle = angle;

        x = (GamePanel.screenWidth/2) + xOffset;
        y = (GamePanel.screenHeight/2) + yOffset;

        isActive = true;

        image = res;
    }

    public Projectile(){
        x = (GamePanel.screenWidth/2);
        y = (GamePanel.screenHeight/2);
        this.width = 2;
        this.height = 2;
        isActive = false;
    }

    // sets the amount of x and y change based on angle of bullet/projectile
    private void setMoveAngleBools(){
        sx = (int)(speed*Math.cos(angle*(Math.PI/180)));
        sy = (int)(speed*Math.sin(angle*(Math.PI/180)));
    }

    public void draw(Canvas canvas){
        //Paint paint = new Paint();
        //paint.setColor(Color.WHITE);
       // paint.setStyle(Paint.Style.FILL);
        //canvas.drawCircle(x, y, 20, paint);
        matrix.reset();
        matrix.postRotate(angle, image.getWidth()/2, image.getHeight());
        matrix.postTranslate((GamePanel.screenWidth/2), (GamePanel.screenHeight/2));
        canvas.drawBitmap(image, matrix, null);
    }

    public void update(){
        if(isActive){
            //System.out.println("Bullet X: " + x + ": " + y);
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

    public int getX(){ return x; }

    public int getY(){ return y; }

}

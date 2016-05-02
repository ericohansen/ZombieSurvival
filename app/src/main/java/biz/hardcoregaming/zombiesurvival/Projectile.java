package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;

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
    private float[] pts = new float[4];

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

        x = (GamePanel.screenWidth/2)  + xOffset;
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
        matrix.postRotate(angle, width/3, height+(height/2));
        matrix.postTranslate(x-(width/3), y-(height+height/2));

        //System.out.println("X: " + x + " Y: " + y + " W: " + (x + width) + " H: " + (y + height));
        canvas.drawBitmap(image, matrix, null);

        matrix.reset();
        matrix.postRotate(angle-120, 0, 0);
        matrix.postTranslate(x, y);

        pts[0] = 0;//width+(width/3);
        pts[1] = 0;//height/2;
        pts[2] = GamePanel.screenWidth;
        pts[3] = GamePanel.screenHeight;

        matrix.mapPoints(pts);
        System.out.println(pts[0] + " : " + pts[1]);



        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        canvas.drawLine(pts[0], pts[1], pts[2], pts[3], p);
        //canvas.drawCircle((int)pts[0], (int)pts[1], 3, p);
        //canvas.drawRect(pts[0], pts[1], pts[2], pts[3], p);
        canvas.save();
        canvas.rotate(angle, x+(width/2), y+height);
        canvas.restore();
    }

    public float[] getRotRect(){

        matrix.reset();
        matrix.postRotate(angle-120, 0, 0);
        matrix.postTranslate(x, y);
        pts[0] = 0;//width+(width/3);
        pts[1] = 0;//height/2;
        pts[2] = GamePanel.screenWidth;
        pts[3] = GamePanel.screenHeight;
        matrix.mapPoints(pts);
        //System.out.println("Matrix: " + matrix + " Pts: " + pts[0] + " : "+ pts[1]);

        return pts;
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

package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

/**
 * Created by ericohansen on 2/17/2016.
 */
public class Enemy extends GameObject {
    //Player image information
    private Bitmap spritesheet;
    private Animation animation = new Animation();

    //position of player on screen vars
    private int x;
    private int y;

    private boolean isCollide = false;
    private boolean bgCollideX = false;
    private boolean bgCollideY = false;

    //angle that the enemy is moving towards vars
    private float angle;
    private Matrix matrix = new Matrix();

    //enemy attributes
    private int health;


    //init constructor
    public Enemy(Bitmap res, int width, int height, int numFrames) {
        spritesheet = res;
        int min = 50;
        int max = GamePanel.screenHeight - 50;

        Random r = new Random();
        int randY = r.nextInt(max - min + 1) + min;
        if(randY > GamePanel.screenHeight/2){
            x = 10;
        }else{
            x = GamePanel.screenWidth - 10;
        }
        y = randY;

        Bitmap[] image = new Bitmap[numFrames];

        //creates the sprite frames for animations
        for (int i = 0; i < numFrames; i++) {
            //System.out.println("W: " + i * width + " H: " + height);
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        //sets frames and delay between frames
        animation.setFrames(image);
        animation.setDelay(60);
    }

    //resets rotation matrix, rotates to the new angle, moves image to center of screen and draws it
    public void draw(Canvas canvas) {
        matrix.reset();
        matrix.postRotate(angle, animation.getImage().getWidth() / 2, animation.getImage().getHeight() / 2);
        matrix.postTranslate(x, y);
        canvas.drawBitmap(animation.getImage(), matrix, null);
        System.out.println("Enemy angle: " + angle);
    }

    //updates the sprite image and x y of player if changed
    public void update() {
        if (!isCollide) {
            animation.update();
            if(!isCollideX)
                x += dx;
            if(!isCollideY)
                y += dy;
        }
    }

    //allows public access to angle of player
    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isCollide() {
        return isCollide;
    }

    public void setIsCollide(boolean isCollide) {
        this.isCollide = isCollide;
    }

    public boolean isBgCollideY() {
        return bgCollideY;
    }

    public void setBgCollideY(boolean bgCollideY) {
        this.bgCollideY = bgCollideY;
    }

    public boolean isBgCollideX() {
        return bgCollideX;
    }

    public void setBgCollideX(boolean bgCollideX) {
        this.bgCollideX = bgCollideX;
    }
}

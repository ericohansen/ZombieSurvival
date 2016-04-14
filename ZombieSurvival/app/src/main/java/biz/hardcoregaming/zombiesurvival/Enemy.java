package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

<<<<<<< HEAD
import java.util.Random;

=======
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
/**
 * Created by ericohansen on 2/17/2016.
 */
public class Enemy extends GameObject {
    //Player image information
    private Bitmap spritesheet;
    private Animation animation = new Animation();

<<<<<<< HEAD
    //angle that the enemy is moving towards vars
    private float angle;
<<<<<<< HEAD
    private int speed = 3;
    private Matrix matrix = new Matrix();

    //init constructor
    public Enemy(Bitmap res, int width, int height, int numFrames, int health, int speed) {
        spritesheet = res;
        this.width = width;
        this.height = height;
        this.health = health;
        this.speed = speed;

        int min = -10;
        int maxH = GamePanel.screenHeight + 10;
        int maxW = GamePanel.screenWidth + 10;

        Random r = new Random();
        int side = r.nextInt(4)+1;

        if(side == 4) {
            this.y = r.nextInt(maxH - min) + min;
            this.x = GamePanel.screenWidth + 10;
        }else if(side == 3) {
            this.x = r.nextInt(maxW - min) + min;
            this.y = GamePanel.screenHeight + 10;
        }else if(side == 2){
            this.y = r.nextInt(maxH - min) + min;
            this.x = -10;
        }else{
            this.x = r.nextInt(maxW - min) + min;
            this.y = -10;
        }
=======
    private Matrix matrix = new Matrix();
=======
    //position of player on screen vars
    private int x;
    private int y;

    private boolean isCollide = false;

    //angle that the enemy is moving towards vars
    private float angle;
    private Matrix matrix = new Matrix();

    //enemy attributes
    private int health;

>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b

    //init constructor
    public Enemy(Bitmap res, int width, int height, int numFrames) {
        spritesheet = res;
<<<<<<< HEAD
        int min = 50;
        int max = GamePanel.screenHeight - 50;

        Random r = new Random();
        int randY = r.nextInt(max - min + 1) + min;
        if(randY > GamePanel.screenHeight/2){
            this.x = 10;
        }else{
            this.x = GamePanel.screenWidth - 10;
        }
        this.y = randY;
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
=======
        x = (GamePanel.screenWidth / 2) - (width / 2);
        y = (GamePanel.screenHeight / 2) - (height / 2);
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b

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
<<<<<<< HEAD
        //System.out.println("Enemy angle: " + angle);
=======
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
    }

    //updates the sprite image and x y of player if changed
    public void update() {
<<<<<<< HEAD
        if (isAlive) {
            animation.update();
<<<<<<< HEAD
            if(!isCollideX)x += Math.cos(angle)*speed + dx;
            if(!isCollideY)y += Math.sin(angle)*speed + dy;
=======
            if(!isCollideX)x += dx;
            if(!isCollideY)y += dy;
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
            if(health <= 0)isAlive = false;
=======
        if (!isCollide) {
            animation.update();
            x += dx;
            y += dy;
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
        }
    }

    //allows public access to angle of player
    public void setAngle(float angle) {
        this.angle = angle;
    }
<<<<<<< HEAD
=======

    public boolean isCollide() {
        return isCollide;
    }

    public void setIsCollide(boolean isCollide) {
        this.isCollide = isCollide;
    }
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
}

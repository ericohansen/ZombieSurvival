package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Player extends GameObject {

    //Player image information
    private Bitmap spritesheet;
    private Animation animation = new Animation();

<<<<<<< HEAD
    private int trueX;
    private int trueY;
=======
    //position of player on screen vars
    private int x;
    private int y;
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b

    private boolean isCollide = false;

    //angle that the player is moving towards vars
    private float angle;
    private Matrix matrix = new Matrix();

    //player attributes
    private int health;
    private int numWeapons;
    private int weapon;
    private boolean isSafe = false;


    //init constructor
    public Player(Bitmap res, int width, int height, int numFrames) {
        spritesheet = res;
<<<<<<< HEAD
        this.x = (GamePanel.screenWidth / 2) - (width / 2);
        this.y = (GamePanel.screenHeight / 2) - (height / 2);
        trueX = x;
        trueY = y;
=======
        x = (GamePanel.screenWidth / 2) - (width / 2);
        y = (GamePanel.screenHeight / 2) - (height / 2);

>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
        Bitmap[] image = new Bitmap[numFrames];

        //creates the sprite frames for animations
        for (int i = 0; i < numFrames; i++) {
            System.out.println("W: " + i * width + " H: " + height);
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
    }

    //updates the sprite image and x y of player if changed
    public void update() {
<<<<<<< HEAD
        if (!isCollideX)trueX += dx;
        if (!isCollideY)trueY += dy;
        animation.update();
=======
        if (!isCollide) {
            animation.update();
            x += dx;
            y += dy;
        }
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
    }

    //allows public access to angle of player
    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public boolean isCollide() {
        return isCollide;
    }

    public void setIsCollide(boolean isCollide) {
        this.isCollide = isCollide;
    }
<<<<<<< HEAD

    @Override
    public int getX(){return trueX;}

    @Override
    public int getY(){return trueY;}
=======
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
}
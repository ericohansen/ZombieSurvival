package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Player extends GameObject{

    //Player image information
    private Bitmap spritesheet;
    private Animation animation = new Animation();

    //position of player on screen vars
    private int x;
    private int y;

    //angle that the player is moving towards vars
    private float angle;
    private Matrix matrix = new Matrix();


    //init constructor
    public Player(Bitmap res, int w, int h, int numFrames) {
        spritesheet = res;
        x = (GamePanel.screenWidth/2) - (w/2);
        y = (GamePanel.screenHeight/2) - (h/2);
        int height = h;
        int width = w;

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
        matrix.postRotate(angle, animation.getImage().getWidth()/2, animation.getImage().getHeight()/2);
        matrix.postTranslate(x, y);
        canvas.drawBitmap(animation.getImage(), matrix, null);
    }

    //updates the sprite image and x y of player if changed
    public void update(){
        animation.update();
        x += dx;
        y += dy;
    }

    //allows public access to angle of player
    public void setAngle(float angle){
        this.angle = angle;
    }
}
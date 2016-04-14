package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
<<<<<<< HEAD
import android.graphics.Color;
import android.graphics.Paint;

public class Background extends GameObject {

    private int bgX, bgY, speedX, speedY;
    private Paint paint = new Paint();

    //constructor
    public Background(int x, int y) {
        bgX = x;
        bgY = y;
        speedX = 0;
        speedY = 0;
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bgX,bgY,bgX + 2000, bgY + 2000, paint);
=======

public class Background extends GameObject {

    private Bitmap image;
    private int bgX, bgY, speedX, speedY, bgWidth, bgHeight;
    private int origX, origY, origXWidth, origYHeight;

    private boolean isCollideX = false, isCollideY = false;

    //constructor
    public Background(Bitmap res, int x, int y) {
        image = res;
        bgX = x;
        bgY = y;
        origX = x;
        origY = y;
        origXWidth = x + 2000;
        origYHeight = y + 2000;
        speedX = 0;
        speedY = 0;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, bgX, bgY, null);
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
    }

    public void update() {
        if (!isCollideX)
            bgX += speedX;
        if (!isCollideY)
            bgY += speedY;
    }

    public int getBgX() {
        return bgX;
    }

    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    public int getBgY() {
        return bgY;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
<<<<<<< HEAD
=======

    public int getBgWidth() {
        return bgWidth;
    }

    public void setBgWidth(int bgWidth) {
        this.bgWidth = bgWidth;
    }

    public int getBgHeight() {
        return bgHeight;
    }

    public void setBgHeight(int bgHeight) {
        this.bgHeight = bgHeight;
    }

    public boolean isCollideX() {
        return isCollideX;
    }

    public void setIsCollideX(boolean isCollide) {
        this.isCollideX = isCollide;
    }

    public boolean isCollideY() {
        return isCollideY;
    }

    public void setIsCollideY(boolean isCollide) {
        this.isCollideY = isCollide;
    }

    public int getOrigX() {
        return origX;
    }

    public void setOrigX(int origX) {
        this.origX = origX;
    }

    public int getOrigY() {
        return origY;
    }

    public void setOrigY(int origY) {
        this.origY = origY;
    }

    public int getOrigXWidth() {
        return origXWidth;
    }

    public void setOrigXWidth(int origXWidth) {
        this.origXWidth = origXWidth;
    }

    public int getOrigYHeight() {
        return origYHeight;
    }

    public void setOrigYHeight(int origYHeight) {
        this.origYHeight = origYHeight;
    }
>>>>>>> 437587f7a302ce85f0ac8ae9d083e49127ddf46b
}

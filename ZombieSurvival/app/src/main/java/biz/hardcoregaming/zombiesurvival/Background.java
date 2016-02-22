package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
}

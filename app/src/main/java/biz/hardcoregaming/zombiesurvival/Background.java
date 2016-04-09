package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
}

package com.example.micha.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by micha on 1/11/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

private MainThread thread;

    private Droid mainGuy;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int y = 0;
    private int x = 0;
    private int deltaX = 1;
    private int deltaY = 1;

    public GamePanel(Context context)
   {
        super(context);

       // add the callback to the surfaceholder to intercept events (finger presses)
       getHolder().addCallback(this);

       thread = new MainThread(getHolder(), this);


       // this gets the actual height and width of the device accessing the
       // application
       WindowManager wm = (WindowManager) context
               .getSystemService(Context.WINDOW_SERVICE);
       Display display = wm.getDefaultDisplay();

       Point size = new Point();
       display.getSize(size);
       screenWidth = size.x;
       screenHeight = size.y;
       y = screenHeight/2;
       x = screenWidth/2;

       mainGuy = new Droid(BitmapFactory.decodeResource(getResources(),
               R.drawable.checker), screenWidth / 2, screenHeight - 300, 0);

       Speed myS = new Speed(100, 0);
       myS.setxDirection(1);
       myS.setyDirection(0);
       mainGuy.setSpeed(myS);

       // can handle events
       setFocusable(true);
   }



    public void draw(Canvas canvas) {
        super.draw(canvas);
        // this draws the main soccer ball onto the screen
        mainGuy.draw(canvas);

    }
        @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();

            }
            catch(InterruptedException e)
            {
                e.printStackTrace();

            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void update()
    {
       // I want it to bounce back and forth from left to right
        x+=deltaX;
        y+=deltaY;
        //y+=1;
        if(x == 0 || x == screenWidth)
        {
            deltaX *=-1;
        }

        if(y == 0 || y == screenHeight)
        {
            deltaY *=-1;
        }

        mainGuy.setY(y);
        mainGuy.setX(x);
    }
}

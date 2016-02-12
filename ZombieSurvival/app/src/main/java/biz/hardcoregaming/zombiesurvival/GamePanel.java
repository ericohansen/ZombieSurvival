package biz.hardcoregaming.zombiesurvival;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {


    private MainThread thread;

    //Screen information
    private int screenHeight = 0;
    private int screenWidth = 0;

    //Sensor information
    private SensorManager mSensorManager;
    private Sensor mSensor;

    //Background information
    private Background bg;
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 2000;

    public GamePanel(Context context)
   {
       super(context);

       // add the callback to the surfaceholder to intercept events (finger presses)
       getHolder().addCallback(this);

       thread = new MainThread(getHolder(), this);

       //Creates the sensor objects
       this.mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
       this.mSensor = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

       screenWidth = getWidth();
       screenHeight = getHeight();

       // can handle events
       setFocusable(true);
   }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bg.draw(canvas);
    }

        @Override
    public void surfaceCreated(SurfaceHolder holder) {

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background), 0, 0);
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
        bg.update();
        //mainGuy.setY(y);
        //mainGuy.setX(x);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

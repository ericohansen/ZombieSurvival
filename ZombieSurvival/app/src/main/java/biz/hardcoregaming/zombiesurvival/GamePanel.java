package biz.hardcoregaming.zombiesurvival;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
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
    public static int screenHeight = 0;
    public static int screenWidth = 0;

    //Sensor information
    private SensorManager mSensorManager;
    private Sensor mSensor;

    //Background information
    public static Background bg;
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 2000;

    //Player information
    private Player player;

    public GamePanel(Context context)
   {
       super(context);

       // add the callback to the surfaceholder to intercept events (finger presses)
       getHolder().addCallback(this);

       thread = new MainThread(getHolder(), this);

       //Creates the sensor objects
       this.mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
       this.mSensor = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       this.mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

       //Gets screen resolution
       DisplayMetrics displayMetrics = new DisplayMetrics();
       WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       wm.getDefaultDisplay().getMetrics(displayMetrics);
       screenWidth = displayMetrics.widthPixels;
       screenHeight = displayMetrics.heightPixels;

       // can handle events
       setFocusable(true);
   }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bg.draw(canvas);
        player.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player), 133, 200, 3);
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
        player.update();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //make changes to players x and y
            //System.out.println("Sensor X: " + x);
            //System.out.println("Sensor Y: " + y);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

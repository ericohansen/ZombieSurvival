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

    //Background information
    private Background bg;

    //Player information
    private Player player;

    public GamePanel(Context context) {
        super(context);

        // add the callback to the surfaceholder to intercept events (finger presses)
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //Gets screen resolution and sets it to a global variable
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        setFocusable(true);

        //Creates the sensor objects
        SensorManager mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Sensor listener
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bg.draw(canvas);
        player.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //creates the player and background objects
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playersquare), 200, 200, 3);
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
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("X: " + event.getRawX() + " Y: " + event.getRawY());

        //rotates player sprite based on user touch
        //takes the slope between two points and calculates the angle for player to rotate to
        float angle = (float)Math.toDegrees(Math.atan2((event.getY()- player.getY()),(event.getX()- player.getX())));
        System.out.println("ANGLE: " + angle);
        //sets the angle when finger tap
        player.setAngle(angle);

        return super.onTouchEvent(event);
    }

    public void update() {
        bg.update();
        if (bg.getSpeedX() != 0 || bg.getSpeedY() != 0)
            player.update();//stops the player sprite frames from transitioning while player not moving
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            //float z = event.values[2];

            //System.out.println("Sensor X: " + x);
            //System.out.println("Sensor Y: " + y);

            //checks if background object exists (was getting a null reference error without this check)
            if (bg != null) {
                if (y > 0.5) {
                    //move right
                    bg.setSpeedX(-20);
                    if (bg.getSpeedY() != 0)
                        bg.setSpeedX(-16);//slows movement speed if moving at angle
                } else if (y < -0.5) {
                    //move left
                    bg.setSpeedX(20);
                    if (bg.getSpeedY() != 0)
                        bg.setSpeedX(16);//slows movement speed if moving at angle
                } else {
                    bg.setSpeedX(0);
                }

                if (x > 3.5) {
                    //move down
                    bg.setSpeedY(-20);
                    if (bg.getSpeedX() != 0)
                        bg.setSpeedY(-16);//slows movement speed if moving at angle
                } else if (x < 3.0) {
                    //move up
                    bg.setSpeedY(20);
                    if (bg.getSpeedX() != 0)
                        bg.setSpeedY(16);//slows movement speed if moving at angle
                } else {
                    bg.setSpeedY(0);
                }

                //changes the direction player is moving towards based on direction background is moving
                if (bg.getSpeedX() < 0 && bg.getSpeedY() < 0) {
                    //down right
                    player.setAngle((float) 135);
                } else if (bg.getSpeedX() > 0 && bg.getSpeedY() > 0) {
                    //up left
                    player.setAngle((float) -45);
                } else if (bg.getSpeedX() > 0 && bg.getSpeedY() < 0) {
                    //down left
                    player.setAngle((float) -135);
                } else if (bg.getSpeedX() < 0 && bg.getSpeedY() > 0) {
                    //up right
                    player.setAngle((float) 45);
                } else {
                    if (bg.getSpeedX() > 0) {
                        player.setAngle((float) -90);
                    } else if (bg.getSpeedX() < 0) {
                        player.setAngle((float) 90);
                    } else if (bg.getSpeedY() < 0) {
                        player.setAngle((float) 180);
                    } else if (bg.getSpeedY() > 0) {
                        player.setAngle((float) 0);
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

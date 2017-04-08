package com.example.hoangp19.hoangp19_cannon_animation;

import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Paul on 4/2/17
 *
 * In this class, two moving targets are displayed, and the user can aim where they want the ball
 * to go by pressing anywhere on the screen. When the ball hits a target, the ball will bounce back
 * to show that it has hit the target. The app will also keep score as to how many times the player
 * hits the target. During the game, the player can shoot as many balls as they want at the same time.

 [8 points] Modify the targets so that they move around on the screen until they are hit by
 a cannonball.

 [10 points] Allow the user to have an arbitrary number of cannonballs in the air at once.

 [10 points] Instead of stopping when it hits the ground, the cannonball rolls along and/or
 bounces in a believable manner.

 [5 points] Have the targets create an animated explosion (or similar effect) when the
 cannonball hits them.

 */
public class DrawCannon extends android.animation.Animator implements Animator {
    //instance variables
    private int numTicks; //counts the number ticks
    private int numHits; //counts the number of hits on the target
    private ArrayList<CannonBall> balls; //an array list of all the balls currently on the screen
    private float cannonXPos; //normalized x-vector of the direction the cannon was fired last
    private float cannonYPos; //normalized y-vector of the direction the cannon was fired last
    private float target1XPos;
    private float target1YPos;
    private float target2XPos;
    private float target2YPos;
    private int xSize; //the x-size of the screen
    private int ySize; //the y-size of the screen
    private Paint black;
    private Paint gray;
    private Paint red;
    private Paint white;
    private Paint blue;
    private Paint brown;
    private Paint orange;
    private Path path; //path for cannon barrel

    public DrawCannon(CannonMainActivity activity) {
        //initialize placeholders and counter variables
        numTicks = 0;
        numHits = 0;
        cannonXPos = (float) (1 / Math.sqrt(2));
        cannonYPos = (float) (1 / Math.sqrt(2));
        balls = new ArrayList<>();

        path = new Path();
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(40);
        gray = new Paint();
        gray.setColor(0xff404040);
        gray.setStyle(Paint.Style.FILL);
        red = new Paint();
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);
        brown = new Paint();
        brown.setColor(0xff8B4513);
        brown.setStyle(Paint.Style.FILL);
        orange = new Paint();
        orange.setColor(0xffffaa00);
        orange.setStyle(Paint.Style.FILL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    public int interval() { return 20; }

    public int backgroundColor() {
        return Color.rgb(255, 255, 255);
    }

    public boolean doPause() { return false; }

    public boolean doQuit() { return false; }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void tick(Canvas canvas) {
        numTicks++;
        xSize = canvas.getWidth();
        ySize = canvas.getHeight();

        //aim the cannon where the user last pressed
        target1XPos = 0.6f * xSize;
        target1YPos = 0.4f * ySize + 0.2f * ySize * (float) Math.sin(2 * Math.toRadians(numTicks));
        target2XPos = 0.8f * xSize;
        target2YPos = 0.6f * ySize + 0.2f * ySize * (float) Math.sin(3 * Math.toRadians(numTicks));


        //drawing a wooden deck for the cannon
        canvas.drawRect(40, ySize - 80, 60, ySize, brown);
        canvas.drawRect(100, ySize - 80, 120, ySize, brown);
        canvas.drawRect(160, ySize - 80, 180, ySize, brown);
        canvas.drawRect(220, ySize - 80, 240, ySize, brown);
        canvas.drawRect(0, ySize - 100, 260, ySize - 80, brown);


        //draw the two moving targets in their current locations
        canvas.drawOval(target1XPos - 25, ySize - target1YPos - 100, target1XPos + 25, ySize - target1YPos + 100, blue);
        canvas.drawOval(target1XPos - 15, ySize - target1YPos - 60, target1XPos + 15, ySize - target1YPos + 60, white);
        canvas.drawOval(target1XPos - 5, ySize - target1YPos - 20, target1XPos + 5, ySize - target1YPos + 20, red);
        canvas.drawOval(target2XPos - 25, ySize - target2YPos - 100, target2XPos + 25, ySize - target2YPos + 100, blue);
        canvas.drawOval(target2XPos - 15, ySize - target2YPos - 60, target2XPos + 15, ySize - target2YPos + 60, white);
        canvas.drawOval(target2XPos - 5, ySize - target2YPos - 20, target2XPos + 5, ySize - target2YPos + 20, red);

        //update and draw all cannon balls
        for (int i = 0; i < balls.size(); i++) {
            CannonBall ball = balls.get(i);

            if (ball == null) { continue; }

            float ballXPos = ball.getXPos();
            float ballYPos = ball.getYPos();

            //if the cannon ball rolled off the screen, remove it from the array list
            if (ballXPos < -40 || ballXPos > xSize) {
                balls.remove(i);
                i--;
                continue;
                //if the cannonball hits target 1, score will be added by 1
            } else if (target1XPos - 35 < ballXPos && ballXPos < target1XPos + 15 &&
                    target1YPos - 120 < ballYPos && ballYPos < target1YPos + 80) {
                ball.hitTarget();
                canvas.drawPaint(orange); //overlay the screen with orange
                canvas.drawCircle(target1XPos, target1YPos, 1000, red);
                //wherever the target is, there
                //will be a big red circle to mimic an explosion when the target is hit
                numHits++;
                //if the cannonball hits target 2, score will be added by 1
            } else if (target2XPos - 35 < ballXPos && ballXPos < target2XPos + 15 &&
                    target2YPos - 120 < ballYPos && ballYPos < target2YPos + 80) {
                ball.hitTarget();
                canvas.drawPaint(orange); //the entire screen will flash orange
                canvas.drawCircle(target2XPos, target2YPos, 1000, red); //wherever the target is, there
                //will be a big red circle to mimic an explosion when the target is hit
                numHits++;
            }
            canvas.drawCircle(ballXPos + 20, ySize - ballYPos - 20, 20, black);
            ball.updateCannonBall(); //recalculate the ball's position and velocity
        }

        //draw the cannon barrel (on top of balls that are fired)
        canvas.drawArc(100, ySize - 140, 180, ySize - 60, 0, -180, true, gray);
        path.reset();
        path.moveTo(140 - 40 * cannonYPos, ySize - 100 - 40 * cannonXPos);
        path.rLineTo(100 * cannonXPos, -100 * cannonYPos);
        path.rLineTo(40 * cannonYPos, 40 * cannonXPos);
        path.rLineTo(-100 * cannonXPos, 100 * cannonYPos);
        path.close();
        canvas.drawPath(path, gray);

        //displaying number of times user hits bullseye
        canvas.drawText("BullsEye Hits: " + numHits, 30, 50, black);
    }

    public void onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //calculate the direction the user touched and then normalize the values
            cannonXPos = event.getX() - 120 - 20 * cannonYPos; //X location of user touch
            cannonYPos = ySize - event.getY(); //Y location of user touch
            double mag = Math.sqrt(cannonXPos * cannonXPos + cannonYPos * cannonYPos);
            //divide by the hypotenuse to normalize the values of cannonXPos and cannonYPos
            cannonXPos /= mag;
            cannonYPos /= mag;

            //create a new cannon ball, give it an initial position and velocity and put it in the array list
            balls.add(new CannonBall(120 - 20 * cannonYPos + 100 * cannonXPos, 80 + 20 * cannonXPos + 100 * cannonYPos, 50 * cannonXPos, 50 * cannonYPos));
        }
    }

    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public void setStartDelay(long l) { }

    @Override
    public android.animation.Animator setDuration(long l) {
        return null;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void setInterpolator(TimeInterpolator timeInterpolator) { }

    @Override
    public boolean isRunning() {
        return false;
    }


}




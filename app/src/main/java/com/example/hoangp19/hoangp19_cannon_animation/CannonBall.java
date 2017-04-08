package com.example.hoangp19.hoangp19_cannon_animation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Paul on 4/3/17
 *
 * In this class, the cannon balls are given functions when they a) hit a target or b) hit the ground.
 * When the a cannon ball hits the ground, they are able to bounce up and they will roll out off of
 * the screen. When a cannon ball hits either target 1 or 2, not only will the entire screen flash
 * red, but at the x and y position of the target, there will be a big red circle drawn to mimic
 * an explosion.
 */
public class CannonBall {
    //instance variables
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private Paint red;

    //class variables
    public static double acceleration=-1; //acceleration of this world (in pixels/tick^2)
    public static double xWindSpeed =0; //X wind speed (in pixels/tick)
    public static double yWindSpeed =0; //Y wind speed (in pixels/tick)

    public CannonBall(double initXPos, double initYPos, double initXVel, double initYVel) {
        red = new Paint();
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);

        this.xPos = initXPos;
        this.yPos = initYPos;
        this.xVel = initXVel;
        this.yVel = initYVel;
    }

    public float getXPos() {
        return (float)xPos;
    }

    public float getYPos() {
        return (float)yPos;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    //updating the ball's coordinates at every tick
    public void updateCannonBall() {
        xPos += xVel + xWindSpeed;
        yVel += acceleration;
        yPos += yVel + yWindSpeed;
        if(yPos<0) {
            yPos=0;
            hitGround();
        }
    }

    public void hitGround() {
        yVel *= -0.25; //decrease the ball's kinetic energy
        if(xVel<1 && xVel>=0)
            xVel=1;

        else if(xVel<0 && xVel>=-1)
            xVel=-1;
    }

    public void hitTarget() {
        xVel *= -0.1; //decrease the ball's kinetic energy
        yVel *= 0.6;

        if(xVel<0)
            xPos -= 40;
        else
            xPos += 40;
    }

}


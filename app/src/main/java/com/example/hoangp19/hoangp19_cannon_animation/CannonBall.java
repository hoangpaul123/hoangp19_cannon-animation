package com.example.hoangp19.hoangp19_cannon_animation;

/**
 * Created by Paul on 4/3/17
 */
public class CannonBall {
    //instance variables
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;

    //class variables
    public static double acceleration=-1; //acceleration of this world (in pixels/tick^2)
    public static double xWindSpeed =0; //X wind speed (in pixels/tick)
    public static double yWindSpeed =0; //Y wind speed (in pixels/tick)

    public CannonBall(double initXPos, double initYPos, double initXVel, double initYVel) {
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
        }
    }

}

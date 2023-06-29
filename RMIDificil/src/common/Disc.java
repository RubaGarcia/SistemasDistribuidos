package common;

import java.io.Serializable;

/**

 * Disc.java
 * 
 * Class that represents the game discs
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD
 * @version 2.0
 */

public class Disc implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
     * Represents the x-coordinate of the disc.
     */
    private int x;

    /**
     * Represents the y-coordinate of the disc.
     */
    private int y;

    /**
     * Represents the y-coordinate of where the dropping disc should stop dropping.
     */
    private int stopY;

    /**
     * Represents the velocity of the disc or speed in which the timer fires.
     */
    private int yVelocity;

    /**
     * Represents if disc is currently moving.
     */
    private boolean moving;

    /**
     * Initializes an instance of Disc with specified values.
     * @param x the x-coordinate of the disc
     * @param y the y-coordinate of the disc 
     * @param stopY the y-coordinate of where the dropping disc should stop dropping
     * @param vel the velocity of the disc or speed in which the timer fires
     */
    public Disc(int x, int y, int stopY, int vel) {
        this.x = x;
        this.y = y;
        this.stopY = stopY;
        this.yVelocity = vel;
        this.moving = false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setStopY(int stopY) {
        this.stopY = stopY;
    }

    public int getStopY() {
        return stopY;
    }

    public int getYVelocity(){
        return yVelocity;
    }

    public void setYVelocity(int vel) {
        this.yVelocity = vel;
    }

    public void startsMoving() {
        this.moving = true;
    }

    public void stopsMoving() {
        this.moving = false;
    }
    
    public boolean getMoving() {
        return moving;
    }

}

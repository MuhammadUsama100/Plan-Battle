package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Collisiondetection {
    public Vector2 position;
    long width, height;

    public Collisiondetection(Vector2 vector2, long width, long height) {
        position = new Vector2();
        this.height = height;
        this.width = width;
        this.position.x = vector2.x;
        this.position.y = vector2.y;
    }

    public void collisionMove(Vector2 vector2) {
        this.position.x = vector2.x;
        this.position.y = vector2.y;
    }

    public boolean Action(Collisiondetection collisiondetection) {
        if (position.x < collisiondetection.position.x + collisiondetection.width) {
            if (position.y < collisiondetection.position.y + collisiondetection.height) {
                if (position.x + width > collisiondetection.position.x) {
                    if (position.y + height > collisiondetection.position.y) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public static final float speed = 300f;
    Collisiondetection collisiondetection;
    Vector2 position;

    public boolean remove = false;

    public Bullet(Vector2 vector2) {

        position = new Vector2();
        this.position.x = vector2.x;
        this.position.y = vector2.y;
        collisiondetection = new Collisiondetection(position, 50, 60);
    }

    public void update(float delta, int indecation) {
        collisiondetection.collisionMove(position);
        position.x = position.x + (delta * speed) * (indecation);
        if (position.x > Gdx.graphics.getWidth())
            remove = true;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Collisiondetection getCollisiondetection() {
        return collisiondetection;
    }
}

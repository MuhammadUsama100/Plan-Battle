package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Rocket  {
    final float velocity   = 200.0f  ;
    float targetX ;
    float targetY ;
    Vector2 rocket_position ;
    Collisiondetection collisiondetection ;


    public Rocket(float targetX , float targetY  ) {
        this.targetX = targetX ;
        this.targetY  =  targetY   ;
        rocket_position =  new Vector2(0 ,0 ) ;
        collisiondetection =  new Collisiondetection(rocket_position , 40 , 20 ) ;
    }

    public void move (float delta ) {
        System.out.print("target: ");
        System.out.print(targetX);
        System.out.print("Enemy : ");

        System.out.print(rocket_position);


        if(rocket_position.x < targetX){
            rocket_position.x  =  rocket_position.x  + (delta *velocity)  ;
        }

        if (rocket_position.y > targetY) {
            rocket_position.y = rocket_position.y - (delta *velocity );
        }
        else if (rocket_position.y < targetY) {
            rocket_position.y =  rocket_position.y + (delta *velocity) ;
        }
        collisiondetection.collisionMove(rocket_position);
    }


    public Vector2 getRocket_position() {
        return rocket_position;
    }

    public void setRocket_position(Vector2 rocket_position) {
        this.rocket_position.x = rocket_position.x ;
        this.rocket_position.y =  rocket_position.y ;
    }

    public float getTargetX() {
        return targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }
    public  Collisiondetection  getCollisiondetection() {
        return   collisiondetection ;
    }
}

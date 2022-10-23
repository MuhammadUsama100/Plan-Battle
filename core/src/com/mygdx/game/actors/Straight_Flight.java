package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Bullet;
import com.mygdx.game.Collisiondetection;
import com.mygdx.game.utils.GameState;

import java.util.ArrayList;
import java.util.Random;

public class Straight_Flight extends Actor {
    Texture texture;
    Texture texture2;
    Vector2 position;
    boolean fire;
    float nextbullet;
    float accuracy;
    Random random;
    private final GameState gameState = GameState.getInstance();
    boolean distroy ;
    private long startTime = TimeUtils.millis();
    private final long width = Gdx.graphics.getWidth() / 7;
    private final long height = Gdx.graphics.getHeight() / 8;
    final float velocity = 1.2f;
    ArrayList<Bullet> bullets;
    PlayerPlane playerPlane;
    boolean selfdistruction;
    Collisiondetection collisiondetection;


    public Straight_Flight(AssetManager assetManager, PlayerPlane playerPlane) {
        texture = assetManager.get("enemy1.png", Texture.class);
        random = new Random();
        int y = random.nextInt(Gdx.graphics.getHeight());
        y++;
        texture2 = assetManager.get("bullet.png", Texture.class);
        position = new Vector2(Gdx.graphics.getWidth() + 20, y);
        bullets = new ArrayList<Bullet>();
        this.playerPlane = playerPlane;
        collisiondetection = new Collisiondetection(position, width, height);
        distroy =  false  ;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, position.x, position.y, width, height);

        for (Bullet bullet : bullets) {
            batch.draw(texture2, bullet.getPosition().x, bullet.getPosition().y + 20, 50, 60);
        }
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        position.x = position.x - velocity;
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();

        accuracy = playerPlane.getPlayer_position().y - position.x;
        if (accuracy > 0 && accuracy < 1 && TimeUtils.timeSinceMillis(startTime) > 100 + nextbullet) {
            nextbullet = TimeUtils.timeSinceMillis(startTime);
            fire = true;
            bullets.add(new Bullet(position));
        } else {
            fire = false;
        }
        for (Bullet bullet : bullets) {
            bullet.update(delta, -1);
            if (bullet.remove) {
                bulletToRemove.add(new Bullet(position));
            }

        }


        collisiondetection.collisionMove(position);
        for (Bullet bullet : bullets) {
            if (bullet.getCollisiondetection().Action(playerPlane.getCollisiondetection())) {
                bulletToRemove.add(bullet);
                gameState.decrementLife();
                Gdx.input.vibrate(200);
            }
        }
        bullets.removeAll(bulletToRemove);
        if (collisiondetection.Action(playerPlane.getCollisiondetection())) {
            selfdistruction = true;
        }



        for (Bullet  bullet : playerPlane.getBullets()) {
            if (bullet.getCollisiondetection().Action(collisiondetection)) {
                distroy =  true  ;
            }
        }


        super.act(delta);
    }

    @Override
    public boolean hasActions() {
        return selfdistruction;
    }

    @Override
    public boolean hasParent() {
        return distroy;
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public String getName() {
        return "Straight_Flight";
    }


    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Collisiondetection getCollisiondetection() {
        return collisiondetection;
    }

    public void setCollisiondetection(Collisiondetection collisiondetection) {
        this.collisiondetection = collisiondetection;
    }
}

package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Bullet;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Rocket;
import com.mygdx.game.controllers.PlayerController;
import com.mygdx.game.Collisiondetection;
import com.mygdx.game.utils.GameState;

import java.util.ArrayList;

public class PlayerPlane extends Actor {
    private Texture texture;
    private long startTime = TimeUtils.millis();
    ArrayList<Bullet> bullets ;
    private float nextbullet;
    private final long width = Gdx.graphics.getWidth() / 5;
    private final long height = Gdx.graphics.getHeight() / 6;
    private PlayerController playerController;
    private final GameState gameState = GameState.getInstance();
    private Collisiondetection collisiondetection;
    World world;
    Texture texture2 ;
    boolean targetDistroyed = false;
    boolean loading_rocket;
    private Rocket rocket;
    TextureAtlas textureAtlas;
    TextureRegion textureRegion;
    private BodyDef bodyDef;
    private Body body;
    private Vector2 player_position;
    private final float FORCE_FACTOR = 22f;
    Vector2 vector2;

    public Vector2 getPlayer_position() {
        return player_position;
    }

    Vector2 previous_force;


    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public PlayerPlane(AssetManager assetManager, World world, PlayerController playerController) {
        this.world = world;
        vector2 = new Vector2(Gdx.graphics.getWidth(), 0);
        texture = assetManager.get("rocket.png", Texture.class);
        textureAtlas = new TextureAtlas(Gdx.files.internal("PlayerJet/PLANE.txt"));

        this.playerController = playerController;
        player_position = new Vector2();
        player_position.x = 31;
        player_position.y = Gdx.graphics.getHeight()/2;
        previous_force = new Vector2();
        this.world = world;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(player_position.x + (this.getWidth() / 2), player_position.y + (this.getHeight() / 2)));
        this.body = world.createBody(bodyDef);
        collisiondetection = new Collisiondetection(player_position, width, height);
        preframe = new Vector2();
        loading_rocket = false;
        rocket = new Rocket(gameState.getMinimumEnemyX(), gameState.getMinimumEnemyY());
        bullets =  new ArrayList<Bullet>() ;
        texture2 =  assetManager.get("bullet1.png" , Texture.class) ;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(textureRegion, body.getPosition().x - (this.getWidth() / 2), body.getPosition().y - (this.getHeight() / 2), width, height);

        if (loading_rocket) {
            playerController.setRocket(false);
        }

        if (playerController.isRocket() || loading_rocket == true) {
            System.out.print("firing rocket : ");
            batch.draw(texture, rocket.getRocket_position().x, rocket.getRocket_position().y, 40, 20);
            loading_rocket = true;
        }

        super.draw(batch, parentAlpha);
        for (Bullet bullet : bullets) {
            batch.draw(texture2, bullet.getPosition().x, bullet.getPosition().y + 20, 50, 60);
        }
    }

    int frame = 1;
    Vector2 preframe;

    @Override
    public void act(float delta) {
        super.act(delta);

//        System.out.println(body.getPosition().x);

        collisiondetection.collisionMove(player_position);


        if (body.getPosition().y > Gdx.graphics.getHeight()  || body.getPosition().y < -10 || body.getPosition().x <  -10 || body.getPosition().x > Gdx.graphics.getWidth()) {
            gameState.setGameOver(true);
        }


        if (body.getPosition().x >= Gdx.graphics.getWidth() - 10 || player_position.x > Gdx.graphics.getWidth() - 10) {
            // body.applyForceToCenter(previous_force,true);
        } else if (body.getPosition().x <= -10 || player_position.x < -10) {
            // body.applyForceToCenter(previous_force,true);


        }


        body.applyForceToCenter(playerController.getMovement().x * FORCE_FACTOR, playerController.getMovement().y * FORCE_FACTOR, true);

        rotateBy((float) Math.atan(playerController.getMovement().y / playerController.getMovement().x));

        if (playerController.getMovement().x != 0) {
            previous_force.x = (-playerController.getMovement().x * FORCE_FACTOR);
            previous_force.y = (-playerController.getMovement().y * FORCE_FACTOR);
        }
        player_position.x = body.getPosition().x - (this.getWidth() / 2);
        player_position.y = body.getPosition().y - (this.getHeight() / 2);


        // simple animation :
        if (playerController.getMovement().y > 0) {
            frame = 2;
        } else if (playerController.getMovement().y < 0) {
            frame = 3;
        } else if (playerController.getMovement().x > 0 && playerController.getMovement().y == 0) {
            frame = 1;
        }


        // end simple animation :


        System.out.print("vector :");
        //  System.out.println(rocket.getRocket_position().x);

        textureRegion = textureAtlas.findRegion(String.valueOf(frame));
        //  rockets code :
        rocket.setTargetX(gameState.getMinimumEnemyX());
        rocket.setTargetY(gameState.getMinimumEnemyY());
        if (loading_rocket) {
            playerController.setRocket(false);
        }


        targetDistroyed = false;
        if (rocket.getRocket_position().x >= gameState.getMinimumEnemyX()) {
            loading_rocket = false;
            targetDistroyed = true;
            gameState.setMinimumEnemyX(Gdx.graphics.getWidth());
            gameState.setMinimumEnemyY(Gdx.graphics.getWidth());

            rocket.setRocket_position(player_position);
        }
        if (playerController.isRocket() == true) {
            rocket.setRocket_position(body.getPosition());
            targetDistroyed = false;

        }

        if (loading_rocket) {
            rocket.move(delta);
        }


        // firing :

        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
        System.out.print("Shoter  : ");
        System.out.print(playerController.isShot());
        if (playerController.isShot() && TimeUtils.timeSinceMillis(startTime) > 600 + nextbullet){
            nextbullet = TimeUtils.timeSinceMillis(startTime);
            bullets.add(new Bullet(player_position ));
        }

        for (Bullet bullet : bullets) {
            bullet.update(delta, 1);
            if (bullet.remove) {
                bulletToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletToRemove);

    }

    @Override
    public float getX() {
        return player_position.x;
    }

    @Override
    public float getY() {
        return player_position.y;
    }

    @Override
    public String getName() {
        return "PlayerPlane";
    }

    @Override
    public boolean getDebug() {
        return targetDistroyed;
    }

    public Collisiondetection getCollisiondetection() {
        return collisiondetection;
    }
}


package com.game.zombierunell.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yryskul on 8/7/2017.
 */
public class Heart {

    private Texture heart;
    private Rectangle bounds;
    private Vector2 position;
    private Animation heartAnimation;
    private Random random;
    private OrthographicCamera cam;
    private int HEART_WIDTH = 25, HEART_HEIGHT = 25;
    private Sound heart_s;

    public Heart(float x, float y, OrthographicCamera cam){
        heart = new Texture("objects/heart_beat.png");
        heartAnimation = new Animation(new TextureRegion(heart),5,0.5f);
        position = new Vector2(x, y);
        random = new Random();
        bounds = new Rectangle(position.x, position.y, HEART_WIDTH,HEART_WIDTH);
        this.cam = cam;
        heart_s = Gdx.audio.newSound(Gdx.files.internal("sounds/health_s.wav"));
    }

    public void update(float dt){
        heartAnimation.update(dt);
        if(cam.position.x-(cam.viewportWidth/2) > getPosition().x + HEART_WIDTH) {
            reposition();
        }
    }

    public TextureRegion getHeart() {
        return heartAnimation.getFrame();
    }

    public void setHeart(Texture zombie) {
        this.heart = zombie;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHEART_WIDTH() {
        return HEART_WIDTH;
    }

    public int getHEART_HEIGHT() {
        return HEART_HEIGHT;
    }

    public void reposition(){
        position.x = position.x + 1000;
        position.y = random.nextInt(200) + 40;
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Sound getHeart_s(){
        return heart_s;
    }

    public void dispose(){
        heart.dispose();
        heart_s.dispose();
    }
}

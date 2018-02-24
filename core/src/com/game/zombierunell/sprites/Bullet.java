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
 * Created by Yryskul on 8/17/2017.
 */
public class Bullet {
    private Texture bullet_t;
    private Rectangle bounds;
    private Vector2 position;
    private Random random;
    private OrthographicCamera cam;
    private int ITS_WIDTH = 30, ITS_HEIGHT = 30;
    private Sound time_s;
    private Hero hero;

    public Bullet(float x,OrthographicCamera cam, Hero hero){
        bullet_t = new Texture("objects/bullet_taken.png");
        position = new Vector2(x, 200);
        random = new Random();
        bounds = new Rectangle(position.x, position.y, ITS_WIDTH, ITS_HEIGHT);
        this.cam = cam;
        time_s = Gdx.audio.newSound(Gdx.files.internal("sounds/time_s.wav"));
        this.hero = hero;
    }

    public void update(){
        /*
        if(cam.position.x-(cam.viewportWidth/2) > getPosition().x + ITS_WIDTH
                && hero.getBullet_png().size < hero.getBullets_num()) {
            reposition();
        }
        */
    }

    public void setInvisible(){
        position.x = 4;
        bounds.setX(position.x);
    }

    public Vector2 getPosition() {
        return position;
    }


    public int getITS_WIDTH() {
        return ITS_WIDTH;
    }

    public int getITS_HEIGHT() {
        return ITS_HEIGHT;
    }

    public void dispose(){
        bullet_t.dispose();
        time_s.dispose();
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public Texture getBullet_t() {
        return bullet_t;
    }

    public void setBullet_t(Texture bullet_t) {
        this.bullet_t = bullet_t;
    }


    public Sound getTime_s(){
        return time_s;
    }

    public void reposition(){
        position.x = position.x + 1500;
        position.y = random.nextInt(200) + 40;
        bounds.setPosition(position.x, position.y);
    }
}

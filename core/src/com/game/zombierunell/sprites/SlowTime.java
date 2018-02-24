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
public class SlowTime {

    private Texture slow_sign;
    private Rectangle bounds;
    private Vector2 position;
    private Animation slow_animation;
    private Random random;
    private OrthographicCamera cam;
    private int ITS_WIDTH = 31, ITS_HEIGHT = 20;
    private Sound time_s;

    public SlowTime(float x, float y, OrthographicCamera cam){
        slow_sign = new Texture("objects/slow_time.png");
        slow_animation = new Animation(new TextureRegion(slow_sign),3,0.4f);
        position = new Vector2(x, y);
        random = new Random();
        bounds = new Rectangle(position.x, position.y, ITS_WIDTH-10, ITS_HEIGHT-10);
        this.cam = cam;
        time_s = Gdx.audio.newSound(Gdx.files.internal("sounds/time_s.wav"));
    }

    public void update(float dt){
        slow_animation.update(dt);
        if(cam.position.x-(cam.viewportWidth/2) > getPosition().x + ITS_WIDTH) {
            reposition();
        }
    }

    public TextureRegion getSlowSign() {
        return slow_animation.getFrame();
    }

    public void setSlowSign(Texture zombie) {
        this.slow_sign = zombie;
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
        slow_sign.dispose();
        time_s.dispose();
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public Sound getTime_s(){
        return time_s;
    }

    public void reposition(){
        position.x = position.x + 2000;
        position.y = random.nextInt(200) + 40;
        bounds.setPosition(position.x, position.y);
    }
}

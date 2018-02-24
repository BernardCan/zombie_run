package com.game.zombierunell.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Yryskul on 8/7/2017.
 */
public class Hero {

    private static final  int GRAVITY = -16;

    private int MOVEMENT = 180;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Animation heroAnimation;
    private Texture hero;
    private Texture hero_jump;
    private Texture hero_run;
    private Texture hero_dead, hero_sh;
    private boolean checkJump = false;
    private int HERO_WIDTH = 60, HERO_HEIGHT = 82;
    private int Y_OFFSET = 40;
    private float sprite_speed = 0.5f;
    private Array<Texture> lifeBar;
    //private Array<Texture> bullet_png;
    private Array<HeroBullet> bullets;
    private boolean isCollision;
    private int jumpCount = 0;
    private boolean exactJump = true;
    private int bullets_num = 5;
    private Sound shoot_s;
    private int bullet_pack = 0;

    public Hero(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        hero_run = new Texture("objects/hero_run.png");
        hero_jump = new Texture("objects/hero_jump.png");
        hero_dead = new Texture("objects/hero_dead.png");
        hero_sh = new Texture("objects/hero_shoot.png");
        hero = hero_run;
        heroAnimation = new Animation(new TextureRegion(hero),10,sprite_speed);
        bounds = new Rectangle(x, y, HERO_WIDTH-10, HERO_HEIGHT);
        lifeBar = new Array<Texture>();
        //bullet_png = new Array<Texture>();
        bullets = new Array<HeroBullet>();
        for(int i = 0; i < 10; i++)
            lifeBar.add(new Texture("objects/heart_bar.png"));
        //for(int i = 0; i < bullets_num-1; i++)
            //bullet_png.add(new Texture("objects/bullet_taken.png"));
        for(int i = 0; i < bullets_num; i++)
            bullets.add(new HeroBullet(this));
        shoot_s = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot_sound.ogg"));
    }

    public void update(float dt){
        heroAnimation.update(dt);
        setJumpToRun();
        velocity.add(0, GRAVITY);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y);
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
        if(lifeBar.size == 0)
            setExactJump(false);
        if(position.y + HERO_HEIGHT > 300) {
            velocity.y = -40;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setVelocityY(int y){
        this.velocity.y = y;
    }

    public TextureRegion getHero() {
        return heroAnimation.getFrame();
    }

    public void setJumpToRun(){
        if(position.y <= Y_OFFSET && checkJump){
            setHero(hero_run,10, sprite_speed);
            checkJump = false;
            jumpCount = 0;
        }
        if(isCollision) {
            setHero(hero_dead, 10, 0.8f);
            isCollision = false;
            checkJump = true;
        }
    }

    public void jump(){
        if(jumpCount < 2 && exactJump) {
            jumpCount++;
            if(jumpCount == 2)
                velocity.y = 330;
            else
                velocity.y = 400;
            position.add(0,1);
        }
        if(!checkJump) {
            setHero(hero_jump,2, 0.7f);
            checkJump = true;
        }
    }

    public void shoot(){
        //if(bullet_png.size > 0) {
        if(bullet_pack == bullets_num)
            bullet_pack = 0;
        bullets.get(bullets.size - bullet_pack-1).start(position);
        bullet_pack++;
        //bullet_png.removeIndex(bullet_png.size - 1);
        shoot_s.play();
        changeSprite();
        //}
    }

    public void changeSprite(){
        float delay = 0.2f; // seconds
        setHero(hero_sh,2, 0.5f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(checkJump)
                    setHero(hero_jump,2, 0.7f);
                else setHero(hero_run,10, sprite_speed);
            }
        }, delay);
    }


    public int getBullets_num() {
        return bullets_num;
    }

    public void setHero(Texture hero, int frameCount, float frameSec) {
        this.hero = hero;
        heroAnimation = new Animation(new TextureRegion(hero),frameCount,frameSec);
    }


    public Array<HeroBullet> getBullets() {
        return bullets;
    }

    public void setMOVEMENT(int MOVEMENT) {
        this.MOVEMENT = MOVEMENT;
    }
    public int getMOVEMENT() {
        return this.MOVEMENT;
    }
    public void setPositionY(float position) {
        this.position.y = position;
    }
    public void setPositionX(float position) {
        this.position.x = position;
    }
    public Rectangle getBounds(){
        return bounds;
    }

    public int getHERO_WIDTH() {
        return HERO_WIDTH;
    }

    public int getHERO_HEIGHT() {
        return HERO_HEIGHT;
    }

    public int getY_OFFSET() {
        return Y_OFFSET;
    }


    public void setSprite_speed(float sprite_speed) {
        this.sprite_speed = sprite_speed;
    }


    //public Array<Texture> getBullet_png() {
      //  return bullet_png;
    //}


    public void setY_OFFSET(int y_OFFSET) {
        Y_OFFSET = y_OFFSET;
    }

    public Array<Texture> getLifeBar(){
        return this.lifeBar;
    }

    public Texture getHero_dead() {
        return hero_dead;
    }
    public void  setExactJump(boolean a){
        this.exactJump = a;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
    }

    public void dispose(){
        hero_jump.dispose();
        hero_run.dispose();
        hero_dead.dispose();
        shoot_s.dispose();
        hero_sh.dispose();
        for(Texture texture : lifeBar){
            texture.dispose();
        }
        for(HeroBullet b : bullets){
            b.dispose();
        }
        //for(Texture p : bullet_png){
          //  p.dispose();
        //}
    }
}

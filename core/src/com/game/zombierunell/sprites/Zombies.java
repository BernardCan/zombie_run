package com.game.zombierunell.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yryskul on 8/6/2017.
 */
public class Zombies {

    private Texture zombie;
    private Rectangle bounds;
    private Vector2 position;
    private int ZOMBIE_WIDTH, ZOMBIE_HEIGHT, BOUNDS_H;
    private int SPEED;
    private int FRAME_COUNT;
    private float FRAME_TIME;

    private Animation zombieAnimation;
    private Random random;
    private OrthographicCamera cam;

    private ZombiesType zombiesType;

    private int value;
    private int p_value;

    public Zombies(float x, ZombiesType zombiesType, OrthographicCamera cam){
        zombie = pickZombieType(zombiesType);
        position = new Vector2(x, 40);
        bounds = new Rectangle(position.x, position.y, ZOMBIE_WIDTH-10, BOUNDS_H);
        zombieAnimation = new Animation(new TextureRegion(zombie),FRAME_COUNT,FRAME_TIME);
        this.cam = cam;
        random = new Random();
        this.zombiesType = zombiesType;
        p_value = value;
    }

    public void update(float dt){
        zombieAnimation.update(dt);
        position.add(-(SPEED * dt), 0);
        bounds.setPosition(position.x+10, position.y);
        if(cam.position.x-(cam.viewportWidth/2) > getPosition().x + ZOMBIE_WIDTH) {
            reposition();
        }
    }

    private Texture pickZombieType(ZombiesType zombiesType){
        switch (zombiesType){
            case BIG_ZOMBIE:
                setSize(68, 106);
                setFeatures(10, 0.5f, 50);
                value = 1;
                BOUNDS_H = 90;
                return (new Texture("objects/big_zombie.png"));
            case SMALL_ZOMBIE:
                setSize(56,80);
                setFeatures(10, 0.5f, 65);
                BOUNDS_H = 66;
                value = 1;
                return (new Texture("objects/small_zombie.png"));
            case WHEEL_ZOMBIE:
                setSize(107,79);
                setFeatures(5, 0.5f, 90);
                BOUNDS_H = 60;
                value = 1;
                return (new Texture("objects/wheel_zombie.png"));
            case CAR_ZOMBIE:
                setSize(125,37);
                setFeatures(7, 0.5f, 115);
                BOUNDS_H = 35;
                value = 1;
                return (new Texture("objects/car_zombie.png"));
            default:
                setSize(33,46);
                setFeatures(10, 0.5f, 65);
                BOUNDS_H = 55;
                value = 1;
                return (new Texture("objects/small_zombie.png"));
        }
    }

    private void setSize(int width, int height){
        setZOMBIE_WIDTH(width);
        setZOMBIE_HEIGHT(height);
    }

    public void reposition(){
        position.x = position.x + random.nextInt(2000) + 1500;
        bounds.setPosition(position.x+10, position.y);
    }


    public int getP_value() {
        return p_value;
    }

    private void setFeatures(int FRAME_COUNT, float FRAME_TIME, int SPEED){
        setFRAME_COUNT(FRAME_COUNT);
        setFRAME_TIME(FRAME_TIME);
        setSPEED(SPEED);
    }

    public void setFRAME_COUNT(int FRAME_COUNT) {
        this.FRAME_COUNT = FRAME_COUNT;
    }

    public void setFRAME_TIME(float FRAME_TIME) {
        this.FRAME_TIME = FRAME_TIME;
    }


    public void setZombie(Texture zombie) {
        this.zombie = zombie;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getZombie() {
        return zombieAnimation.getFrame();
    }

    public int getZOMBIE_WIDTH() {
        return ZOMBIE_WIDTH;
    }

    public void setZOMBIE_WIDTH(int ZOMBIE_WIDTH) {
        this.ZOMBIE_WIDTH = ZOMBIE_WIDTH;
    }

    public int getZOMBIE_HEIGHT() {
        return ZOMBIE_HEIGHT;
    }

    public void setZOMBIE_HEIGHT(int ZOMBIE_HEIGHT) {
        this.ZOMBIE_HEIGHT = ZOMBIE_HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public ZombiesType getZombiesType() {
        return zombiesType;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void dispose(){
        zombie.dispose();;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }

}

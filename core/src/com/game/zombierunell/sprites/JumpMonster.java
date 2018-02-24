package com.game.zombierunell.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yryskul on 8/18/2017.
 */
public class JumpMonster {
    private Texture jumpMon;
    private Rectangle bounds;
    private Vector2 position;
    private Vector2 velocity;
    private int MONST_WIDTH, MONST_HEIGHT, BOUNDS_H;
    private int SPEED;
    private int FRAME_COUNT;
    private float FRAME_TIME;
    private static final  int GRAVITY = -8;
    private int Y_OFFSET = 40;

    private Animation monstAnimation;
    private Random random;
    private OrthographicCamera cam;

    private int monstType;

    private int value;
    private int p_value;

    public JumpMonster(float x, int monstType, OrthographicCamera cam){
        jumpMon = pickZombieType(monstType);
        position = new Vector2(x, 40);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(position.x, position.y, MONST_WIDTH, BOUNDS_H);
        monstAnimation = new Animation(new TextureRegion(jumpMon),FRAME_COUNT,FRAME_TIME);
        this.cam = cam;
        random = new Random();
        this.monstType = monstType;
        p_value = value;
    }

    public void update(float dt){
        monstAnimation.update(dt);
        velocity.add(0, GRAVITY);
        velocity.scl(dt);
        position.add(-(SPEED * dt), velocity.y);
        velocity.scl(1/dt);
        bounds.setPosition(position.x+10, position.y);
        if(cam.position.x-(cam.viewportWidth/2) > getPosition().x + MONST_WIDTH) {
            reposition();
        }
        if(position.y < Y_OFFSET)
            jump();
    }

    private Texture pickZombieType(int monstType){
        switch (monstType){
            case 1:
                setSize(60, 65);
                setFeatures(9, 0.8f, 30);
                value = 1;
                BOUNDS_H = 65;
                return (new Texture("objects/ninja_j.png"));
            default:
                setSize(60, 65);
                setFeatures(9, 0.8f, 50);
                value = 1;
                BOUNDS_H = 65;
                return (new Texture("objects/ninja_j.png"));
        }
    }

    private void setSize(int width, int height){
        setMONST_WIDTH(width);
        setMONST_HEIGHT(height);
    }

    public void reposition(){
        position.x = position.x + random.nextInt(1500) + 1000;
        bounds.setPosition(position.x+10, position.y);
    }

    public void jump(){
        velocity.y = 380;
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


    public void setJumpMon(Texture jumpMon) {
        this.jumpMon = jumpMon;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getJumpMon() {
        return monstAnimation.getFrame();
    }

    public int getMONST_WIDTH() {
        return MONST_WIDTH;
    }

    public void setMONST_WIDTH(int MONST_WIDTH) {
        this.MONST_WIDTH = MONST_WIDTH;
    }

    public int getMONST_HEIGHT() {
        return MONST_HEIGHT;
    }

    public void setMONST_HEIGHT(int MONST_HEIGHT) {
        this.MONST_HEIGHT = MONST_HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getMonstType() {
        return monstType;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void dispose(){
        jumpMon.dispose();;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }

}

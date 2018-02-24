package com.game.zombierunell.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yryskul on 8/17/2017.
 */
public class HeroBullet {

    private Texture bullet;
    private Rectangle bounds;
    private Vector2 position;
    private int BULLET_WIDTH = 20, BULLET_HEIGHT=10;
    private int SPEED = 550;
    private int distance = 200;
    private Hero hero;

    private boolean isMoving = false;

    public HeroBullet(Hero hero){
        bullet = new Texture("objects/bullet.png");
        position = new Vector2(0, 0);
        bounds = new Rectangle(position.x, position.y, BULLET_WIDTH, BULLET_HEIGHT);
        this.hero = hero;
    }

    public void update(float dt){
        if(isMoving) {
            position.add(+(SPEED * dt), 0);
            bounds.setPosition(position.x, position.y);
            if (position.x > hero.getPosition().x + distance) {
                setMoving(false);
                setInvisible();
            }
        }
    }

    public void setInvisible(){
        position.x = -10;
        bounds.setX(position.x);
    }

    private void setSize(int width, int height){
        setBULLET_WIDTH(width);
        setBULLET_HEIGHT(height);
    }

    public void start(Vector2 pos){
        setMoving(true);
        this.position.x = pos.x+10;
        this.position.y = pos.y + hero.getHERO_HEIGHT()/2-10;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Texture getBullet() {
        return bullet;
    }
    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getDistance() {
        return distance;
    }
    public void setBullet(Texture bullet) {
        this.bullet = bullet;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getBULLET_WIDTH() {
        return BULLET_WIDTH;
    }

    public void setBULLET_WIDTH(int BULLET_WIDTH) {
        this.BULLET_WIDTH = BULLET_WIDTH;
    }

    public int getBULLET_HEIGHT() {
        return BULLET_HEIGHT;
    }

    public void setBULLET_HEIGHT(int BULLET_HEIGHT) {
        this.BULLET_HEIGHT = BULLET_HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }



    public void dispose(){
        bullet.dispose();;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }
}

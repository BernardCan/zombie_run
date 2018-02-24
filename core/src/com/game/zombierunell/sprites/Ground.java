package com.game.zombierunell.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yryskul on 8/9/2017.
 */
public class Ground {

    private Texture ground1, ground2;
    private Rectangle bounds;
    private Vector2 groundPos1,groundPos2;
    private int GROUND_WIDTH, GROUND_HEIGHT;
    private OrthographicCamera cam;
    private int distance = 0;
    private String string_d = "0";
    private boolean isString = false;

    public Ground(OrthographicCamera cam){
        ground1 = new Texture("objects/ground_1.png");
        ground2 = new Texture("objects/ground_2.png");
        this.cam = cam;
        groundPos1 = new Vector2(-200,0);
        groundPos2 = new Vector2(groundPos1.x + ground1.getWidth(),0);
        bounds = new Rectangle(groundPos1.x, groundPos1.y, GROUND_WIDTH, GROUND_HEIGHT);
    }

    public void update(){
        if(cam.position.x - (cam.viewportWidth/2)> groundPos1.x + ground1.getWidth()) {
            groundPos1.x = groundPos1.x + ground1.getWidth() * 2;
            string_d = Integer.toString(distance++);
            isString = true;
        }
        if(cam.position.x - (cam.viewportWidth/2)> groundPos2.x + ground1.getWidth()) {
            groundPos2.x = groundPos1.x + ground1.getWidth();
            string_d = Integer.toString(distance++);
            isString = true;
        }
    }

    private void setSize(int width, int height){
        setGROUND_WIDTH(width);
        setGROUND_HEIGHT(height);
    }

    public int getGROUND_WIDTH() {
        return GROUND_WIDTH;
    }

    public void setGROUND_WIDTH(int GROUND_WIDTH) {
        this.GROUND_WIDTH = GROUND_WIDTH;
    }

    public int getGROUND_HEIGHT() {
        return GROUND_HEIGHT;
    }

    public void setGROUND_HEIGHT(int GROUND_HEIGHT) {
        this.GROUND_HEIGHT = GROUND_HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isString() {
        return isString;
    }

    public void setString(boolean string) {
        isString = string;
    }

    public Texture getGround1() {
        return ground1;
    }
    public Texture getGround2() {
        return ground2;
    }

    public Vector2 getGroundPos1() {
        return groundPos1;
    }

    public Vector2 getGroundPos2() {
        return groundPos2;
    }

    public String getString_d(){
        return this.string_d;
    }

    public int getDistance(){
        return distance;
    }


    public void dispose(){
        ground1.dispose();
        ground2.dispose();
    }
}

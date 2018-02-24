package com.game.zombierunell.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yryskul on 8/5/2017.
 */
public class Blocks {
    private Texture block;
    private Rectangle bounds;
    private Vector2 position;
    private int BLOCK_WIDTH, BLOCK_HEIGHT;

    private Random random;
    private OrthographicCamera cam;

    private int value;
    private int p_value;

    public Blocks(float x, BlocksType blocksType, OrthographicCamera cam){
        block = pickBlockType(blocksType);
        position = new Vector2(x, 40);
        bounds = new Rectangle(position.x, position.y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.cam = cam;
        random = new Random();
        p_value = value;
    }

    public void update(){
        if(cam.position.x-(cam.viewportWidth/2) > position.x + BLOCK_WIDTH) {
            reposition();
        }
    }

    public void reposition(){
        position.x = position.x + random.nextInt(3000) + Gdx.graphics.getWidth();
        bounds.setPosition(position.x, position.y);
    }

    private Texture pickBlockType(BlocksType blocksType){
        switch (blocksType){
            case BIG_BLOCK:
                setSize(50,100);
                value = 1;
                return (new Texture("objects/big_block.png"));
            case SMALL_BLOCK:
                setSize(50,50);
                value = 1;
                return (new Texture("objects/small_block.png"));
            case HUGE_BLOCK:
                setSize(100,100);
                value = 1;
                return (new Texture("objects/huge_block.png"));
            default:
                setSize(30,30);
                return (new Texture("objects/small_block.png"));
        }
    }

    public void setBlock(Texture zombie) {
        this.block = zombie;
    }

    private void setSize(int width, int height){
        setBLOCK_WIDTH(width);
        setBLOCK_HEIGHT(height);
    }
    public int getP_value() {
        return p_value;
    }
    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getBLOCK_WIDTH() {
        return BLOCK_WIDTH;
    }

    public void setBLOCK_WIDTH(int BLOCK_WIDTH) {
        this.BLOCK_WIDTH = BLOCK_WIDTH;
    }

    public int getBLOCK_HEIGHT() {
        return BLOCK_HEIGHT;
    }

    public void setBLOCK_HEIGHT(int BLOCK_HEIGHT) {
        this.BLOCK_HEIGHT = BLOCK_HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getBlock() {
        return block;
    }

    public void dispose(){
        block.dispose();;
    }

}

package com.game.zombierunell.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Yryskul on 1/26/2017.
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTiem;
    private int frameCount;
    private int frame;

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameHeight = region.getRegionHeight()/frameCount;
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, 0, i * frameHeight,region.getRegionWidth(), frameHeight));

        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime/ frameCount;
        frame = 0;
    }
    public void update(float dt){
        currentFrameTiem += dt;
        if(currentFrameTiem > maxFrameTime){
            frame++;
            currentFrameTiem = 0;
        }
        if(frame >= frameCount)
            frame = 0;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}

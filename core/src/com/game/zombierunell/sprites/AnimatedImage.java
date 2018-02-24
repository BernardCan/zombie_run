package com.game.zombierunell.sprites;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Yryskul on 8/14/2017.
 */
public class AnimatedImage extends Image
{
    protected Animation animation = null;

    public AnimatedImage(Animation animation) {
        super(animation.getFrame());
        this.animation = animation;
    }

    @Override
    public void act(float delta)
    {
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getFrame());
        super.act(delta);
    }
}
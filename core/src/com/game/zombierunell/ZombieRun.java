package com.game.zombierunell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.zombierunell.states.GameStateManager;

public class ZombieRun extends ApplicationAdapter {
	public static final int WIDTH = 896;
	public static final int HEIGHT = 537;

	public static final String TITLE = "Hero";
	private com.game.zombierunell.states.GameStateManager gsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new com.game.zombierunell.states.GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new com.game.zombierunell.states.MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	public GameStateManager getGsm(){
		return this.gsm;
	}



	public void jump() {
		com.game.zombierunell.states.PlayState.jump();
	}
}
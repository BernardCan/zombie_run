package com.game.zombierunell.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.zombierunell.sprites.AnimatedImage;
import com.game.zombierunell.sprites.Animation;

/**
 * Created by Yryskul on 1/26/2017.
 */
public class MenuState extends State {
    private Texture bg, like, music_on, play, play_down, like_down, restart_u, restart_d;
    private Texture music_off, hero_idle, zombie_run, b_zombie_run;
    private Stage stage;
    private BitmapFont small_fnt, big_fnt;
    private int score, h_width, h_height;
    private Music menu_m;
    private Preferences prefs;
    private Animation heroAnimation, zombieAnimation, b_zombieAnimation;

    public MenuState(com.game.zombierunell.states.GameStateManager gsm) {
        super(gsm);
        bg = new Texture("menus/back.png");
        like = new Texture("menus/like_btn.png");
        music_on = new Texture("menus/music_on_btn.png");
        play = new Texture("menus/play_btn.png");
        music_off = new Texture("menus/music_off_btn.png");
        hero_idle = new Texture("objects/hero_run.png");
        zombie_run = new Texture("objects/small_zombie.png");
        play_down = new Texture("menus/play_down.png");
        like_down = new Texture("menus/like_down.png");
        b_zombie_run = new Texture("objects/big_zombie.png");
        restart_u = new Texture("menus/restart_up.png");
        restart_d = new Texture("menus/restart_down.png");
        small_fnt = new BitmapFont(Gdx.files.internal("menus/text_font.fnt"));
        big_fnt = new BitmapFont(Gdx.files.internal("menus/zombie_font.fnt"));

        h_width = Gdx.graphics.getWidth()/7;
        h_height = Gdx.graphics.getHeight()/3;

        big_fnt.getData().setScale((float)h_width/150);
        small_fnt.getData().setScale((float)h_width/130);

        heroAnimation = new Animation(new TextureRegion(hero_idle),10,0.7f);
        zombieAnimation = new Animation(new TextureRegion(zombie_run),10,0.7f);
        b_zombieAnimation = new Animation(new TextureRegion(b_zombie_run),10,0.8f);

        prefs = Gdx.app.getPreferences("My Preferences");
        score = prefs.getInteger("highscore", 0);
        cam.setToOrtho(false, bg.getWidth(), bg.getHeight());

        menu_m = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_music.ogg"));
        menu_m.setLooping(true);
        if(prefs.getInteger("checkMusic", 1) == 1)
            menu_m.play();
        Gdx.input.setCatchBackKey(false);

        displayButtons();
    }

    private void displayButtons(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        int width = Gdx.graphics.getWidth() / 15-20;
        int height = Gdx.graphics.getHeight() / 8;

        Image backGr = new Image(bg);
        backGr.setWidth(stage.getWidth());
        backGr.setHeight(stage.getHeight());
        stage.addActor(backGr);

        final TextureRegionDrawable like_u = new TextureRegionDrawable(new TextureRegion(like));
        final TextureRegionDrawable like_d = new TextureRegionDrawable(new TextureRegion(like_down));

        ImageButton like_btn = new ImageButton(like_u, like_d);
        like_btn.setPosition(width,height*4);
        int btn_size = Gdx.graphics.getWidth()/6;
        like_btn.setHeight(btn_size);
        like_btn.setWidth(btn_size);
        like_btn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("like");
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.game.zombierunell");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(like_btn);

        final boolean[] isMusic = {true};
        final TextureRegionDrawable music_on_dr = new TextureRegionDrawable(new TextureRegion(music_on));
        final TextureRegionDrawable music_off_dr = new TextureRegionDrawable(new TextureRegion(music_off));

        ImageButton music_btn;
        if (prefs.getInteger("checkMusic", 1) == 1) {
            music_btn = new ImageButton(music_on_dr, music_on_dr, music_off_dr);
            isMusic[0] = true;
        }
        else {
            music_btn = new ImageButton(music_off_dr, music_on_dr, music_on_dr);
            isMusic[0] = false;
        }
        music_btn.setPosition(width,height);
        music_btn.setHeight(btn_size);
        music_btn.setWidth(btn_size);
        music_btn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(isMusic[0]){
                    System.out.println("music is set to false"); isMusic[0] = false;
                    menu_m.stop();
                    prefs.putInteger("checkMusic", 0);
                    prefs.flush();
                }
                else {System.out.println("music is set to true"); isMusic[0] = true;
                    menu_m.play();
                    prefs.putInteger("checkMusic", 1);
                    prefs.flush();
                }

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(music_btn);

        width = Gdx.graphics.getWidth()/2- btn_size /2;
        height = Gdx.graphics.getHeight()/2- btn_size /2-20;

        final TextureRegionDrawable play_u = new TextureRegionDrawable(new TextureRegion(play));
        final TextureRegionDrawable play_d = new TextureRegionDrawable(new TextureRegion(play_down));
        final TextureRegionDrawable res_u = new TextureRegionDrawable(new TextureRegion(restart_u));
        final TextureRegionDrawable res_d = new TextureRegionDrawable(new TextureRegion(restart_d));
        ImageButton play_btn;
        if(prefs.getInteger("endgame",0) == 0 ) {
            play_btn = new ImageButton(play_u, play_d);
            prefs.putInteger("endgame", 0);
            prefs.flush();
        }
        else {
            play_btn = new ImageButton(res_u, res_d);
            prefs.putInteger("endgame", 0);
            prefs.flush();
        }
        play_btn.setPosition(width,height);
        play_btn.setHeight(btn_size);
        play_btn.setWidth(btn_size);
        play_btn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("play");
                menu_m.stop();
                gsm.set(new PlayState(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(play_btn);

        Label title = new Label("ZOMBIE RUN",new Label.LabelStyle(big_fnt, Color.WHITE));
        title.setPosition(Gdx.graphics.getWidth()/2-title.getWidth()/2,Gdx.graphics.getHeight()/2 + btn_size /2);
        stage.addActor(title);

        Label touch_score = new Label("Highscore: " + score,new Label.LabelStyle(small_fnt, Color.WHITE));
        touch_score.setPosition(Gdx.graphics.getWidth()/2 - touch_score.getWidth()/2,Gdx.graphics.getHeight()-80);
        stage.addActor(touch_score);

        Label curr_score = new Label("Last score: " + prefs.getInteger("curr_score",0),new Label.LabelStyle(small_fnt, Color.WHITE));
        curr_score.setPosition(Gdx.graphics.getWidth()/2 - curr_score.getWidth()/2,40);
        stage.addActor(curr_score);

        AnimatedImage h_animatedImage = new AnimatedImage(heroAnimation);
        h_animatedImage.setPosition(Gdx.graphics.getWidth()/2 - btn_size /2-h_width-20,
                Gdx.graphics.getHeight()/2- btn_size);
        h_animatedImage.setWidth(h_width);
        h_animatedImage.setHeight(h_height);
        stage.addActor(h_animatedImage);

        AnimatedImage z_animatedImage = new AnimatedImage(zombieAnimation);
        z_animatedImage.setPosition(Gdx.graphics.getWidth()/2 + btn_size /2+20,
                Gdx.graphics.getHeight()/2- btn_size);
        z_animatedImage.setWidth(h_width);
        z_animatedImage.setHeight(h_height);
        stage.addActor(z_animatedImage);

        AnimatedImage b_z_animatedImage = new AnimatedImage(b_zombieAnimation);
        b_z_animatedImage.setPosition(Gdx.graphics.getWidth()/2 + btn_size /2+h_width,
                Gdx.graphics.getHeight()/2- btn_size);
        b_z_animatedImage.setWidth(2*h_width);
        b_z_animatedImage.setHeight(2*h_height);
        stage.addActor(b_z_animatedImage);
    }

    @Override
    public void handleInput() {
        //if(Gdx.input.justTouched()){
            //gsm.set(new PlayState(gsm));
    }

    @Override
    protected void update(float dt) {
        handleInput();
        heroAnimation.update(dt);
        zombieAnimation.update(dt);
        b_zombieAnimation.update(dt);
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        stage.act();
        stage.draw();
        sb.begin();
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        like.dispose();
        music_on.dispose();
        play.dispose();
        music_off.dispose();
        hero_idle.dispose();
        zombie_run.dispose();
        play_down.dispose();
        like_down.dispose();
        small_fnt.dispose();
        big_fnt.dispose();
        menu_m.dispose();
        b_zombie_run.dispose();
        restart_u.dispose();
        restart_d.dispose();
        System.out.println("Menu state disposed");
    }
}

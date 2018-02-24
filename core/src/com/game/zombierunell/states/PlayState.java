package com.game.zombierunell.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.zombierunell.ZombieRun;
import com.game.zombierunell.sprites.Blocks;
import com.game.zombierunell.sprites.BlocksType;
import com.game.zombierunell.control.ControlCollision;
import com.game.zombierunell.sprites.Bullet;
import com.game.zombierunell.sprites.Ground;
import com.game.zombierunell.sprites.JumpMonster;
import com.game.zombierunell.sprites.SlowTime;
import com.game.zombierunell.sprites.Zombies;

/**
 * Created by Yryskul on 1/26/2017.
 */
public class PlayState extends State {
    private static com.game.zombierunell.sprites.Hero hero;
    private com.game.zombierunell.sprites.Heart heart;
    private SlowTime slowTime;
    private Ground ground;
    private Bullet bullet;
    private Texture bg, sh_btn, jp_btn;
    private Array<Zombies> zombies;
    private Array<Blocks> blocks;
    private JumpMonster jumpMonster;
    private ControlCollision controlCollision;
    private boolean check_speed = true;
    private Music music_bg;
    private Stage stage;
    private Label title;
    private BitmapFont text_font;
    private Preferences prefs;
    private Rectangle left, right;
    private Timer.Task task;

    public PlayState(com.game.zombierunell.states.GameStateManager gsm) {
        super(gsm);
        bg = new Texture("objects/background.png");
        sh_btn = new Texture("objects/shoot_btn.png");
        jp_btn = new Texture("objects/jump_btn.png");
        hero = new com.game.zombierunell.sprites.Hero(0, 40);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        zombies = new Array<Zombies>();
        zombies.add(new Zombies(1000, com.game.zombierunell.sprites.ZombiesType.SMALL_ZOMBIE, cam));
        zombies.add(new Zombies(2000, com.game.zombierunell.sprites.ZombiesType.WHEEL_ZOMBIE, cam));
        zombies.add(new Zombies(3000, com.game.zombierunell.sprites.ZombiesType.BIG_ZOMBIE, cam));
        zombies.add(new Zombies(4000, com.game.zombierunell.sprites.ZombiesType.CAR_ZOMBIE, cam));

        blocks = new Array<Blocks>();
        blocks.add(new Blocks(-200, BlocksType.BIG_BLOCK, cam));
        blocks.add(new Blocks(-200, BlocksType.SMALL_BLOCK, cam));
        blocks.add(new Blocks(-200, BlocksType.HUGE_BLOCK, cam));

        jumpMonster = new JumpMonster(1350, 1,cam);

        heart = new com.game.zombierunell.sprites.Heart(1300, 100, cam);
        slowTime = new SlowTime(3000, 100, cam);
        //bullet = new Bullet(500,cam, hero);
        controlCollision = new ControlCollision(hero, zombies, blocks, heart, slowTime, bullet, jumpMonster);
        ground = new Ground(cam);

        cam.setToOrtho(false, ZombieRun.WIDTH / 2, ZombieRun.HEIGHT / 2);

        prefs = Gdx.app.getPreferences("My Preferences");

        music_bg = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.ogg"));
        music_bg.setLooping(true);
        if(prefs.getInteger("checkMusic", 1) == 1)
            music_bg.play();

        text_font = new BitmapFont(Gdx.files.internal("menus/zombie_font.fnt"));

        left = new Rectangle(0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
        right = new Rectangle(Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());

        title = new Label(ground.getString_d(),new Label.LabelStyle(text_font, Color.WHITE));
        title.setPosition(Gdx.graphics.getWidth()-100,Gdx.graphics.getHeight()-100);
        stage.addActor(title);
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            if(is_jump_sh(left)) {
                runTimer();
                hero.shoot();
            }
            if(is_jump_sh(right))
                hero.jump();
        }
    }

    private boolean is_jump_sh(Rectangle obj) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);

        return obj.contains(touchPos.x, touchPos.y);
    }

    private void runTimer(){
        //if(hero.getBullet_png().size > 0) {
        System.out.println("Timer runs");
        float delay = 0.5f; // seconds
        if (task != null) {
            task.cancel();
            System.out.println("Timer is canceled");
        }
        controlCollision.setRunBullet(true);
        Timer.schedule(task = new Timer.Task() {
            @Override
            public void run() {
                controlCollision.setRunBullet(false);
                System.out.println("RunBullet is set to false");
            }
        }, delay);
        //}
    }


    @Override
    protected void update(float dt) {
        //this is for ending the game
        if (hero.getPosition().y < 39) {
            if (hero.getPosition().y < -100) {
                int score = prefs.getInteger("highscore", 0);
                if(ground.getDistance() > score) {
                    prefs.putInteger("highscore", ground.getDistance());
                    prefs.putInteger("curr_score", ground.getDistance());
                }
                else prefs.putInteger("curr_score", ground.getDistance());
                prefs.putInteger("endgame", 1);
                prefs.flush();
                com.game.zombierunell.states.MenuState menu = new com.game.zombierunell.states.MenuState(gsm);
                gsm.set(menu);
            }
        }
        //followings are update methods
        handleInput();
        hero.update(dt);
        for (int i = 0; i < zombies.size; i++)
            zombies.get(i).update(dt);
        for (int i = 0; i < blocks.size; i++)
            blocks.get(i).update();
        heart.update(dt);
        slowTime.update(dt);
        //bullet.update();
        for (int i = 0; i < hero.getBullets_num(); i++){
            if(hero.getBullets().get(i).isMoving())
                hero.getBullets().get(i).update(dt);
        }
        jumpMonster.update(dt);
        controlCollision.update();
        ground.update();
        setLevelSpeed();
        left.setPosition(cam.position.x-Gdx.graphics.getWidth()/2,0);
        right.setPosition(cam.position.x,0);
        //following is for camera's position
        cam.position.x = hero.getPosition().x + 165;

        if(ground.isString()){
            title.setText(ground.getString_d());
            ground.setString(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            com.game.zombierunell.states.MenuState menu = new com.game.zombierunell.states.MenuState(gsm);
            gsm.set(menu);

        }

        /*
        if(valiant.getPosition().y <= GROUND_Y_OFFSET +15 && score > 1 && ht < 3 ) {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");
            int a = prefs.getInteger("score", 0);
            prefs.putInteger("heart",ht);
            if(score > a)
                prefs.putInteger("score", score);
            prefs.flush();
            MenuState menu = new MenuState(gsm);
            menu.setScore(score);
            gsm.set(menu);
            menu2 = false;
        }
        */
        cam.update();
    }


    @Override
    protected void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2) - 20, 0, ZombieRun.WIDTH / 2 + 40, ZombieRun.HEIGHT - 50);
        sb.draw(ground.getGround1(), ground.getGroundPos1().x, ground.getGroundPos1().y);
        sb.draw(ground.getGround2(), ground.getGroundPos2().x, ground.getGroundPos2().y);
        sb.draw(hero.getHero(), hero.getPosition().x, hero.getPosition().y, hero.getHERO_WIDTH(), hero.getHERO_HEIGHT());

        for (int i = 0; i < zombies.size; i++)
            sb.draw(zombies.get(i).getZombie(), zombies.get(i).getPosition().x, zombies.get(i).getPosition().y,
                    zombies.get(i).getZOMBIE_WIDTH(), zombies.get(i).getZOMBIE_HEIGHT());
        for (int i = 0; i < blocks.size; i++)
            sb.draw(blocks.get(i).getBlock(), blocks.get(i).getPosition().x, blocks.get(i).getPosition().y,
                    blocks.get(i).getBLOCK_WIDTH(), blocks.get(i).getBLOCK_HEIGHT());
        for (int i = 0; i < hero.getLifeBar().size; i++)
            sb.draw(hero.getLifeBar().get(i), (cam.position.x - cam.viewportWidth / 2 + 10) + i * 15, cam.viewportHeight-20, 13, 13);
        //for (int i = 0; i < hero.getBullet_png().size; i++)
            //sb.draw(hero.getBullet_png().get(i), (cam.position.x - cam.viewportWidth / 2 + 10) + i * 15, cam.viewportHeight-35, 13, 13);
        sb.draw(heart.getHeart(), heart.getPosition().x, heart.getPosition().y, heart.getHEART_WIDTH(), heart.getHEART_HEIGHT());
        sb.draw(slowTime.getSlowSign(), slowTime.getPosition().x, slowTime.getPosition().y, slowTime.getITS_WIDTH(), slowTime.getITS_HEIGHT());
        //sb.draw(bullet.getBullet_t(), bullet.getPosition().x, bullet.getPosition().y, bullet.getITS_WIDTH(), bullet.getITS_HEIGHT());
        sb.draw(jumpMonster.getJumpMon(),jumpMonster.getPosition().x,jumpMonster.getPosition().y,jumpMonster.getMONST_WIDTH(),jumpMonster.getMONST_HEIGHT());
        if(ground.getDistance()<5){
            sb.draw(sh_btn, cam.position.x-cam.viewportWidth/2+20, 10, 50,50);
            sb.draw(jp_btn, cam.position.x+cam.viewportWidth/2-70, 10, 50, 50);
        }

        for (int i = 0; i < hero.getBullets_num(); i++){
            if(hero.getBullets().get(i).isMoving()) {
                sb.draw(hero.getBullets().get(i).getBullet(), hero.getBullets().get(i).getPosition().x, hero.getBullets().get(i).getPosition().y,
                        hero.getBullets().get(i).getBULLET_WIDTH(), hero.getBullets().get(i).getBULLET_HEIGHT());
            }
        }

        sb.end();
        stage.act();
        stage.draw();
    }

    public void setLevelSpeed() {
        if (ground.getDistance() == 5 && check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = false;
        } else if (ground.getDistance() == 15 && !check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            hero.setSprite_speed(0.4f);
            check_speed = true;
        } else if (ground.getDistance() == 25 && check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = false;
        } else if (ground.getDistance() == 35 && !check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = true;
        } else if (ground.getDistance() == 45 && check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = false;
        } else if (ground.getDistance() == 55 && !check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = true;
            hero.setSprite_speed(0.3f);
        } else if (ground.getDistance() == 75 && check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = false;
        } else if (ground.getDistance() == 90 && !check_speed) {
            hero.setMOVEMENT(hero.getMOVEMENT() + 30);
            check_speed = true;
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        hero.dispose();
        music_bg.dispose();
        ground.dispose();
        heart.dispose();
        slowTime.dispose();
        controlCollision.dispose();
        text_font.dispose();
        //bullet.dispose();
        jp_btn.dispose();
        sh_btn.dispose();
        jumpMonster.dispose();
        for (Zombies zombie : zombies) {
            zombie.dispose();
        }
        for (Blocks block : blocks) {
            block.dispose();
        }

        System.out.println("Plays state disposed");
    }

    public void setBg(Texture bg) {
        this.bg = bg;
    }

    public static void jump() {
        hero.jump();
    }
}

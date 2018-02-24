package com.game.zombierunell.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.game.zombierunell.sprites.JumpMonster;

/**
 * Created by Yryskul on 8/10/2017.
 */
public class ControlCollision {
    private com.game.zombierunell.sprites.Hero hero;
    private com.game.zombierunell.sprites.Heart heart;
    private com.game.zombierunell.sprites.SlowTime slowTime;
    private com.game.zombierunell.sprites.Bullet bullet;
    private Array<com.game.zombierunell.sprites.Zombies> zombies;
    private Array<com.game.zombierunell.sprites.Blocks> blocks;
    private int ground_y = 40;
    private Sound injury_s;
    private Preferences prefs;
    private boolean runBullet = false;
    private JumpMonster jumpMonster;

    public ControlCollision(com.game.zombierunell.sprites.Hero hero, Array<com.game.zombierunell.sprites.Zombies> zombies, Array<com.game.zombierunell.sprites.Blocks> blocks,
                            com.game.zombierunell.sprites.Heart heart, com.game.zombierunell.sprites.SlowTime slowTime,
                            com.game.zombierunell.sprites.Bullet bullet, com.game.zombierunell.sprites.JumpMonster jumpMonster){
        this.hero = hero;
        this.zombies = zombies;
        this.blocks = blocks;
        this.heart = heart;
        this.slowTime = slowTime;
        this.bullet = bullet;
        this.jumpMonster = jumpMonster;
        this.injury_s = Gdx.audio.newSound(Gdx.files.internal("sounds/injury_s.wav"));
        prefs = Gdx.app.getPreferences("My Preferences");

    }

    public void update(){
        //this for staying on the ground and blocks
        if(hero.getPosition().y <= hero.getY_OFFSET() && hero.getLifeBar().size != 0)
            hero.setPositionY(hero.getY_OFFSET());
        //this for staying on the blocks
        for(int i = 0; i < blocks.size; i++){
            if(checkOnBlock(blocks.get(i), hero)&& hero.getLifeBar().size != 0) {
                hero.setY_OFFSET(ground_y + blocks.get(i).getBLOCK_HEIGHT());
                hero.setVelocityY(1);
                break;
            }
            else hero.setY_OFFSET(ground_y);
        }
        //this for checking the collision between hero and blocks
        for(int i = 0; i < blocks.size; i++){
            if(checkLeftBlock(blocks.get(i), hero)&& hero.getLifeBar().size != 0){
                hero.getLifeBar().removeIndex(hero.getLifeBar().size-1);
                blocks.get(i).reposition();
                hero.setVelocityY(200);
                hero.setCollision(true);
                if(prefs.getInteger("checkMusic", 1) == 1)
                    injury_s.play();
                break;
            }
        }
        //this for checking the collusion between the hero and heart
        if(heart.getBounds().overlaps(hero.getBounds()) && hero.getLifeBar().size != 0){
            if(hero.getLifeBar().size < 25) {
                heart.reposition();
                hero.getLifeBar().add(new Texture("objects/heart_bar.png"));
                if(prefs.getInteger("checkMusic", 1) == 1 )
                    heart.getHeart_s().play();

            }
        }
        //this for checking the collusion between the hero and slowTime
        if(hero.getBounds().overlaps(slowTime.getBounds())){
            if(hero.getMOVEMENT() > 80 && hero.getMOVEMENT()< 300){
                slowTime.reposition();
                if(prefs.getInteger("checkMusic", 1) == 1 )
                    slowTime.getTime_s().play();


                hero.setMOVEMENT(hero.getMOVEMENT()-3);

                /*if (hero.getMOVEMENT() > 130 && hero.getMOVEMENT() < 160)
                    hero.setSprite_speed(0.5f);
                else if (hero.getMOVEMENT() > 160 && hero.getMOVEMENT() < 190)
                    hero.setSprite_speed(0.4f);
                else if (hero.getMOVEMENT() > 190 && hero.getMOVEMENT() < 220)
                    hero.setSprite_speed(0.4f);
                else if (hero.getMOVEMENT() > 220 && hero.getMOVEMENT() < 250)
                    hero.setSprite_speed(0.3f);
                else if (hero.getMOVEMENT() > 250 && hero.getMOVEMENT() < 280)
                    hero.setSprite_speed(0.3f);
                else if (hero.getMOVEMENT() > 280 && hero.getMOVEMENT() < 310)
                    hero.setSprite_speed(0.3f);
                    */
            }
        }
        //this for checking the collusion between the hero and bullet
        /*
        if(bullet.getBounds().overlaps(hero.getBounds()) && hero.getLifeBar().size != 0){
            if(hero.getBullet_png().size < hero.getBullets_num()) {
                hero.getBullet_png().add(new Texture("objects/bullet_taken.png"));
                if(hero.getBullet_png().size < hero.getBullets_num())
                    bullet.reposition();
                else if(hero.getBullet_png().size == hero.getBullets_num())
                    bullet.setInvisible();
                if(prefs.getInteger("checkMusic", 1) == 1 )
                    bullet.getTime_s().play();

            }
        }
        */
        //this for checking the collusion between hero and Zombies
        for(int i = 0; i < zombies.size; i++){
            if(zombies.get(i).getBounds().overlaps(hero.getBounds())&& hero.getLifeBar().size != 0){
                hero.getLifeBar().removeIndex(hero.getLifeBar().size - 1);
                zombies.get(i).reposition();
                hero.setVelocityY(200);
                hero.setCollision(true);
                if(prefs.getInteger("checkMusic", 1) == 1 )
                    injury_s.play();
                break;
            }
        }
        //this for checking the collusion between the hero and jumpMonster
        if (jumpMonster.getBounds().overlaps(hero.getBounds()) && hero.getLifeBar().size != 0) {
            jumpMonster.reposition();
            hero.getLifeBar().removeIndex(hero.getLifeBar().size-1);
            hero.setVelocityY(200);
            hero.setCollision(true);
            if(prefs.getInteger("checkMusic", 1) == 1 )
                injury_s.play();
        }
        //this for checking the collusion between bullets and Zombies
        if (runBullet)
            for (int i = 0; i < hero.getBullets_num(); i++) {
                for (int j = 0; j < zombies.size; j++) {
                    if (zombies.get(j).getBounds().overlaps(hero.getBullets().get(i).getBounds())) {
                        hero.getBullets().get(i).setMoving(false);
                        hero.getBullets().get(i).setInvisible();
                        zombies.get(j).setValue(zombies.get(j).getValue() - 1);
                        System.out.println(zombies.get(j).getValue());
                        if (zombies.get(j).getValue() == 0) {
                            zombies.get(j).reposition();
                            zombies.get(j).setValue(zombies.get(j).getP_value());
                        }
                        break;
                    }


                }
                //this for checking the collusion between bullets and Blocks
                for (int j = 0; j < blocks.size; j++) {
                    if (blocks.get(j).getBounds().overlaps(hero.getBullets().get(i).getBounds())) {
                        hero.getBullets().get(i).setMoving(false);
                        hero.getBullets().get(i).setInvisible();
                        /*
                        blocks.get(j).setValue(blocks.get(j).getValue() - 1);
                        System.out.println(blocks.get(j).getValue());
                        if (blocks.get(j).getValue() == 0) {
                            blocks.get(j).reposition();
                            blocks.get(j).setValue(blocks.get(j).getP_value());
                        }
                        */
                        break;
                    }


                }
                //this for collision between bullets and ninja
                if (jumpMonster.getBounds().overlaps(hero.getBullets().get(i).getBounds())) {
                    hero.getBullets().get(i).setMoving(false);
                    hero.getBullets().get(i).setInvisible();
                    jumpMonster.setValue(jumpMonster.getValue() - 1);
                    System.out.println(jumpMonster.getValue());
                    if (jumpMonster.getValue() == 0) {
                        jumpMonster.reposition();
                        jumpMonster.setValue(jumpMonster.getP_value());
                    }
                }
            }



    }

    public boolean checkLeftBlock(com.game.zombierunell.sprites.Blocks block, com.game.zombierunell.sprites.Hero hero){
        return ((hero.getBounds().getX() + hero.getHERO_WIDTH()-2 >= block.getBounds().getX()) &&
                (hero.getBounds().getX() + hero.getHERO_WIDTH() <= block.getBounds().getX() + block.getBLOCK_WIDTH())
                        && (hero.getBounds().getY() <= ground_y + block.getBLOCK_HEIGHT()-20));
    }

    public boolean checkOnBlock(com.game.zombierunell.sprites.Blocks block, com.game.zombierunell.sprites.Hero hero){
        return ((hero.getBounds().getY() <= ground_y + block.getBLOCK_HEIGHT()) &&
                (hero.getBounds().getX() + hero.getHERO_WIDTH()-5 >= block.getBounds().getX()
                        && hero.getBounds().getX() <= block.getBounds().getX()+ block.getBLOCK_WIDTH()-ground_y/2));
    }


    public boolean isRunBullet() {
        return runBullet;
    }

    public void setRunBullet(boolean runBullet) {
        this.runBullet = runBullet;
    }


    public void dispose(){
        injury_s.dispose();
    }
}

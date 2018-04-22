/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ld41.entities;

import engine.Popup;
import engine.Shop;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Number6406
 */
public class EnnemyEntity {
    
    SpriteSheet sprite;
    Animation animation;
    Color color;
    private int max_lp, lp;
    public int level;
    private Attributes attributes;
    
    public void init(int level) {
        
        try {
            Random r = new Random();
            
            this.level = level;
            sprite = new SpriteSheet("assets/ennemy-animation.png", 200, 200);
            sprite.setFilter(Image.FILTER_LINEAR);
            animation = new Animation(sprite, 200);
            animation.setAutoUpdate(true);
            color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), r.nextInt(100)+100);
            
            attributes = new Attributes();
            for (Attributes.AttributeList index : Attributes.AttributeList.values()) {
                attributes.set(index, r.nextInt(level/5+1));
            }
            max_lp = lp = (level+1)*10 + r.nextInt((level+1)*10);
            
        } catch (SlickException ex) {
            Logger.getLogger(EnnemyEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        g.setColor(color);
        g.drawAnimation(animation, 1300, 800, color);
        g.setColor(new Color(0,0,0,100));
        g.fillRect(1300, 700, 200, 50);
        g.setColor(Color.white);
        g.drawString("Slime Lv." + level, 1320, 715);
        
        if(max_lp>0) {
            g.setColor(Color.red);
            g.fillRect(1300, 750, lp*200/max_lp, 20);
        }
        
        g.setColor(Color.white);
        g.drawString(lp+"/"+max_lp, 1305, 750);
        
    }
    
    public boolean hit(PlayerEntity player, Shop shop, LinkedList<Popup> list) {
        int dmg = player.level+player.attributes.get(Attributes.AttributeList.STRENGTH)*2;
        dmg += Math.sqrt(shop.upgrade)*10;
        boolean killed = false;
        lp-=dmg;
        list.add(new Popup("-"+dmg+" PV", Color.orange));
        if(lp<=0) {
            killed = true;
        }
        return killed;
    }
    
}

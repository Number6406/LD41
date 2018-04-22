package ld41.entities;

import engine.Button;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Number6406
 */
public class PlayerEntity {
    
    private Animation animation;
    private SpriteSheet sprite;
    public Image coin_image;
    
    public Sound music_lv_up;
    
    // AESTHETICS DATA
    private Color color;
    
    // STATISTICS DATA
    public Attributes attributes;
    Map<Attributes.AttributeList, Button> buttons;
    int data_pos_x = 50, data_pos_y = 500;
    
    public int xp = 0,
                level = 0,
                aptitude = 10,
                gold = 50,
                lifepoints = 100,
                manapoints = 100;
    
    
    private int computeNeededXP(int level) {
        return (int)(level*10.0*Math.exp(level+1));
    }
    
    private void evolve() {
        int next_level = level+1;
        if(xp >= computeNeededXP(next_level)) {
            level++;
            aptitude++;
            xp -= computeNeededXP(next_level);
        }
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
        music_lv_up = new Sound("assets/lv_up.wav");
        
        sprite = new SpriteSheet("assets/player-animation.png", 500, 500);
        sprite.setFilter(Image.FILTER_LINEAR);
        animation = new Animation(sprite, 200);
        animation.setAutoUpdate(true);
        
        coin_image = new Image("assets/coin.png");
        
        attributes = new Attributes();
        buttons = new HashMap<Attributes.AttributeList, Button>();
        int incr = data_pos_y;
        for (Attributes.AttributeList attribute : Attributes.AttributeList.values()) {
            buttons.put(attribute, new Button(new Vector2f(data_pos_x, incr), 50, 45, "+1"));
            incr += 50;
        }
        
        Random r = new Random();
        
        int red = r.nextInt(190)+20;
        
        color = new Color(
            red, 
            red + r.nextInt(25)+-50, 
            red + r.nextInt(25)+-50
        );
        
    }

    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        g.drawAnimation(animation, data_pos_x+300, data_pos_y, color);
        
        g.setColor(Color.darkGray);
        g.fillRect(50, 200, gc.getScreenWidth() - 100, 30);
        
        g.setColor(Color.orange);
        g.fillRect(50, 200, (gc.getScreenWidth()-100)*xp/computeNeededXP(level+1), 30);
        
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(data_pos_x-20, data_pos_y-20, 380, attributes.dataset.size()*50+30);
        
        g.setColor(Color.white);
        g.drawString("LEVEL " + level, 50, 180);
        g.drawString("XP : " + xp + "/" + computeNeededXP(level+1), 55, 205);
        
        coin_image.draw(50, 245);
        g.drawString("GOLD : "+gold, 100, 250);
        
        if(aptitude>0) {
            g.drawString("Available AP : " + aptitude, data_pos_x, data_pos_y - 60);
        }
        int incr = data_pos_y + 10;
        for(Attributes.AttributeList attribute : Attributes.AttributeList.values()) {
            g.drawString(attribute.toString() + " : " + attributes.get(attribute), data_pos_x + 100, incr);
            if(aptitude>0) {
                buttons.get(attribute).render(gc, sbg, g);
            }
            incr += 50;
        }
        
    }
    
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta, float scale) throws SlickException {
        
        Input input = gc.getInput();   
        
        evolve();
        for(Attributes.AttributeList attribute : Attributes.AttributeList.values()) {
            if(buttons.get(attribute).isFocused(new Vector2f(input.getMouseX()/scale, input.getMouseY()/scale)) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && aptitude>0) {
                attributes.increment(attribute);
                music_lv_up.play();
                aptitude-=1;
            }
        }
    }
    
}

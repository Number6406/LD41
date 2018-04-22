package engine;

import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import ld41.entities.Attributes;
import ld41.entities.EnnemyEntity;
import ld41.entities.PlayerEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Number6406
 */
public class GameState extends BasicGameState {

    public Music music_bg;
    public Sound music_coin, music_hit;
    PlayerEntity player;
    EnnemyEntity ennemy;
    Shop shop;
    
    Image game_logo;
    Image cursor_hit;
    LinkedList<Popup> popups;

    Font font;
    UnicodeFont uniFont;
    public TrueTypeFont ttf;
    public float scale_x, scale_y;
    
    int update_timer, max_timer = 80;
    Locations.List currentLocation = Locations.List.GRASSLAND;
    
    public int hit_timer = 0, hit_max_timer = 1000;
    Random r = new Random();

    @Override
    public int getID() {
        return LD41.GAME_STATE_GAME;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("assets/Minecraft.ttf")).deriveFont(Font.PLAIN, 24.f);
            uniFont = new org.newdawn.slick.UnicodeFont(font);
            uniFont.addAsciiGlyphs();
            uniFont.addGlyphs(400, 600);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            uniFont.loadGlyphs();
            
            cursor_hit = new Image("assets/cursor-fight.png");
            gc.setMouseCursor(cursor_hit, 1, 1);
            game_logo = new Image("assets/game-logo.png");
            
            music_bg = new Music("assets/bg.wav");
            music_bg.loop();
            music_coin = new Sound("assets/coin.wav");
            music_hit = new Sound("assets/hit.wav");
            
        } catch (Exception ex) {
            Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        popups = new LinkedList<Popup>();
        
        player = new PlayerEntity();
        player.init(gc, sbg);
        
        ennemy = new EnnemyEntity();
        ennemy.init(player.level);
        
        shop = new Shop();
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        g.setColor(Color.white);
        g.setFont(uniFont);
        g.scale(scale_x, scale_y);
        
        g.drawImage(Locations.getInstance().list.get(currentLocation).background, 0, 0);
        
        g.drawImage(game_logo, 0, 0);
        
        Input input = gc.getInput();
        
        //g.drawString(input.getMouseX() + " " + input.getMouseY(), 50, 50);
        
        player.render(gc, sbg, g);
        if(currentLocation.equals(Locations.List.GRASSLAND)) {
            ennemy.render(gc, sbg, g);
        }
        
        if(currentLocation.equals(Locations.List.SHOP)) {
            g.setColor(new Color(0,0,0,100));
            g.fillRect(1050, 600, 400, 200);
            if(shop.cost()>player.gold) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.white);
                if(input.getMouseX()/scale_x>=1050 && input.getMouseY()/scale_y >= 600 && input.getMouseX()/scale_x <= 1450 && input.getMouseY()/scale_y <= 800) {
                    g.setColor(Color.yellow);
                }
            }
            g.drawString("BUY UPGRADE", 1100, 620);
            player.coin_image.draw(1170, 675);
            g.drawString("Cost :      " + shop.cost(), 1100, 680);
        }
        
        for (Map.Entry<Locations.List, Location> entry : Locations.getInstance().list.entrySet()) {
            Locations.List key = entry.getKey();
            Location location = entry.getValue();
            
            location.zone_name.draw(location.x, location.y);
            g.setColor(Color.black);
            g.drawString(key.toString(), location.x+20, location.y+28);
            g.setColor(Color.white);
            if(location.isHovered((int)(input.getMouseX()/scale_x), (int)(input.getMouseY()/scale_y))) {
                g.setColor(new Color(200,200,200));
            }
            g.drawString(key.toString(), location.x+20, location.y+25);
        }
        
        for (Popup popup : popups) {
            popup.render(gc, sbg, g);
        }
        
        g.setColor(Color.white);
        if(player.attributes.getValue() < 11) {
            g.drawString("Hey mate, try upgrading your aptitudes, then hitting that *dangerous* Slime, and buyin' some stuff :|", 600, 300);
        } else if(player.attributes.getValue() < 15) {
            g.drawString("Good. Now, keep going...", 600, 300);
        } else if(player.attributes.getValue() < 18) {
            g.drawString("Yep, that's all !", 600, 300);
        } else if(player.attributes.getValue() < 20) {
            g.drawString("I swear adventurer, there's nothing more to see.", 600, 300);
        } else if(player.attributes.getValue() < 22) {
            g.drawString("Boring, uh ?", 600, 300);
        } else if(player.attributes.getValue() < 25) {
            g.drawString("Okay, stop this game now, please. It's for you !", 600, 300);
        } else if(player.attributes.getValue() < 28) {
            g.drawString("You're still here ? OMG, get a life >.<", 600, 300);
        } else if(player.attributes.getValue() < 30) {
            g.drawString("Alright, you won, I'm leaving.", 600, 300);
        } else if(player.attributes.getValue() < 32) {
            g.drawString("", 600, 300);
        } else {
            g.drawString("Well, if you went that far, you should be really willful. Good job.", 600, 300);
        }
        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        Input input = gc.getInput();
        
        scale_x = (float)gc.getWidth()/1920;
        scale_y = (float)gc.getHeight()/1080;
        
        
        if(currentLocation.equals(Locations.List.GRASSLAND)) {
            hit_timer += delta+player.attributes.get(Attributes.AttributeList.DEXTERITY)+r.nextInt(player.attributes.get(Attributes.AttributeList.LUCK)+1);
            if(hit_timer >= hit_max_timer) {
                if(ennemy.hit(player, shop, popups)) {
                    int gold_get = (ennemy.level+1)*r.nextInt(10)+ennemy.level*10;
                    player.gold+=gold_get;
                    player.xp+=ennemy.level*5+r.nextInt(ennemy.level*12+5);
                    ennemy.init(player.level+r.nextInt(5));
                    
                    popups.add(new Popup("KILL!", Color.red));
                    popups.add(new Popup("+"+gold_get+" GOLD", Color.yellow));
                    music_coin.play();
                }
                hit_timer%=hit_max_timer;
            }
            if(input.getMouseX()/scale_x>=1300 && input.getMouseX()/scale_x<=1500 && input.getMouseY()/scale_x>=800 && input.getMouseY()/scale_x<=1000) {
                
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    music_hit.play();
                    if(ennemy.hit(player, shop, popups)) {
                        int gold_get = (ennemy.level+1)*r.nextInt(10)+ennemy.level*10;
                        player.gold+=gold_get;
                        player.xp+=ennemy.level*5+r.nextInt(ennemy.level*12+5);
                        ennemy.init(player.level+r.nextInt(5));
                    
                        popups.add(new Popup("KILL!", Color.red));
                        popups.add(new Popup("+"+gold_get+" GOLD", Color.yellow));
                        music_coin.play();
                    }
                }
            }
        }
        
        if(currentLocation.equals(Locations.List.SHOP)) {
            if(player.gold>=shop.cost()) {
                if(input.getMouseX()/scale_x>=1050 && input.getMouseY()/scale_y >= 600 && input.getMouseX()/scale_x <= 1450 && input.getMouseY()/scale_y <= 800) {
                    if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        music_coin.play();
                        player.gold-=shop.upgrade(player.gold);
                    }
                }
            }
        }
        
        Iterator<Popup> iter = popups.iterator();
        while (iter.hasNext()) {
            Popup popup = iter.next();
            popup.update(delta);
            if(popup.timer <= 0) {
                iter.remove();
            }
        }
        
        update_timer+=delta;
        if(update_timer > max_timer) {
            player.update(gc, sbg, delta, scale_x);
            
            for (Map.Entry<Locations.List, Location> entry : Locations.getInstance().list.entrySet()) {
                Locations.List key = entry.getKey();
                Location location = entry.getValue();
                if(location.isHovered((int)(input.getMouseX()/scale_x), (int)(input.getMouseY()/scale_y))) {
                    if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        currentLocation = key;
                    }
                }
            }
            
            update_timer%=max_timer;
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Number6406
 */
public class Popup {
    
    private Random r = new Random();
    public int x, y, timer;
    String msg;
    Color color;
    
    public Popup(String msg, Color color) {
        x = 1200 + r.nextInt(400);
        y = 600 + r.nextInt(200);
        timer = 500;
        this.msg = msg;
        this.color = color;
    }
    
    public void update(int delta) {
        timer -= delta;
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        g.setColor(color);
        g.drawString(msg, x, y);
        
    }
    
}

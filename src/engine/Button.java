/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Number6406
 */
public class Button {
    
    private Vector2f position;
    private int width, height;
    private boolean active = true;
    private Color color, background, background_hover;
    private String text;
    private boolean focus;
    
    public Button(Vector2f position, int width, int height, String text) {
        this.background = Color.darkGray;
        this.background_hover = Color.gray;
        this.color = Color.white;
        this.position = position;
        this.width = width;
        this.height = height;
        this.text = text;
    }
    
    public boolean isFocused(Vector2f mouse_pos) {
        return focus = (active && mouse_pos.x >= position.x && mouse_pos.y >= position.y && mouse_pos.x <= position.x + width && mouse_pos.y <= position.y + height);
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        g.setColor(background);
        if(focus) {
            g.setColor(background_hover);
        }
        g.fillRect(position.x, position.y, width, height);
        g.setColor(color);
        g.drawString(text, position.x + 10 , position.y+10);

    }
    
    
}

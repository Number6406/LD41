/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Image;

/**
 *
 * @author Number6406
 */
public class Location {
    
    public Image background;
    public Image zone_name;
    public int x, y;
    
    public Location(Image background, Image zone_name) {
        this.background = background;
        this.zone_name = zone_name;
    }
    
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean isHovered(int mouse_x, int mouse_y) {
        return mouse_x >= x && mouse_y >= y && mouse_x <= x+zone_name.getWidth() && mouse_y <= y+zone_name.getHeight();
    }
    
}

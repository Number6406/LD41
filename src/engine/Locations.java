/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Number6406
 */

public class Locations {
    
    private static Locations INSTANCE = new Locations();
    
    public enum List{
        GRASSLAND,
        SHOP
    }
    
    public Map<List, Location> list;
    
    public static Locations getInstance() {
        return INSTANCE;
    }
    
    private Locations() {
        
        try {
            
            list = new HashMap<List, Location>();
            
            Location grassland = new Location(new Image("assets/zone-background-grassland.png"), new Image("assets/zone-name-grassland.png"));
            grassland.setPos(1000, 0);
            list.put(Locations.List.GRASSLAND, grassland);
        
            Location shop = new Location(new Image("assets/zone-background-shop.png"), new Image("assets/zone-name-shop.png"));
            shop.setPos(1250, 0);
            list.put(Locations.List.SHOP, shop);
        
            
        } catch (SlickException ex) {
            Logger.getLogger(Locations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Random;

/**
 *
 * @author Number6406
 */
public class Shop {
    
    public int upgrade = 0;
    int current_cost_upgrade = -1;
    int cost = 0;
    Random r = new Random();
    
    public int cost() {
        if (current_cost_upgrade <= upgrade) {
            cost = (int) (Math.exp(upgrade+1)*50 + r.nextInt((upgrade+1)*10));
            current_cost_upgrade++;
        }
        return cost;
    }
    
    public int upgrade(int gold) {
        int cost = cost();
        if(gold >= cost) {
            gold-=cost;
            upgrade++;
            return cost;
        }
        return 0;
    }
    
}

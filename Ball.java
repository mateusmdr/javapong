package javapong;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class Ball {
    public double x;
    public double y;
    public int width,height;
    private double dx,dy;

    public Ball(int frameWidth,int frameHeight,int width,int height) {
        this.width = width;
        this.height = height;
        this.x = (frameWidth-width)/2.;
        this.y = (frameHeight-height)/2.;

        dx = new Random().nextGaussian() *0.3;
        dy = Math.max(new Random().nextGaussian(),0.5);
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int)x,(int)y,width,height);
    }

    public void update(int frameWidth){
        x += dx;
        y += dy;

        if(x<=0 && dx<0){
            x=0;
            dx *= -1;
        }else if(x>=frameWidth - width && dx>0){
            x=frameWidth-width;
            dx*= -1;
        }

        boolean isInsidePlayerX = (x+width/2>= Game.player.x && x+width/2 <= Game.player.x + Game.player.width);

        boolean isInsideEnemyX = (x+width/2>= Game.enemy.x && x+width/2 <= Game.enemy.x + Game.enemy.width);

        boolean isInsidePlayableArea = (y+height/2 >= Game.enemy.y + Game.enemy.height && y+height/2 <= Game.player.y);

        if(isInsidePlayableArea){
            if(y+dy <= Game.enemy.y + Game.enemy.height && isInsideEnemyX){
                dy*= -1;
                y = Game.enemy.y + Game.enemy.height;
                dy += 0.2;
                if(dx>0){
                    dx+= 0.1;
                }else {
                    dx-=0.1;
                }
            }else if (y+dy >= Game.player.y - height && isInsidePlayerX) {
                dy*= -1;
                y = Game.player.y - height;
                dy -= 0.2;
                if(dx>0){
                    dx+= 0.1;
                }else {
                    dx-=0.1;
                }
            }
        }
    }
}

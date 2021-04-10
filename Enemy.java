package javapong;

import java.awt.Graphics;
import java.awt.Color;

public class Enemy {
    public double x,y;
    public int width,height;

    public Enemy(int frameWidth,int width,int height) {
        this.width = width;
        this.height = height;
        this.x = (frameWidth-width)/2.;
        this.y = 20.- height;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x,(int)y,width,height);
    }

    public void update(int frameWidth){
        double refx = Game.ball.x -(width-Game.ball.width)/2.;

        if(Math.abs(x-refx)<=1){
            x= refx;
        }else if(x-refx >0){
            x-= 1;
        }else {
            x+= 1;
        }

        if(x<=0){
            x=0;
        }
        else if(x>=frameWidth - width){
            x= frameWidth - width;
        }
    }
}

package javapong;

import java.awt.Graphics;
import java.awt.Color;

public class Player {

    public int x,y;
    public int width,height;
    public boolean isMovingLeft = false;
    public boolean isMovingRight = false;

    public Player(int frameWidth,int frameHeight,int width,int height) {
        this.width = width;
        this.height = height;
        this.x = (frameWidth-width)/2;
        this.y = frameHeight-height - 20;
    }
    
    public void render(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(x,y,width,height);
    }

    public void update(int frameWidth){
        if(isMovingLeft ^ isMovingRight){
            if(isMovingLeft){
                if(x<=0)
                    x=0;
                else 
                    x-= 2;
            }else {
                if(x>=frameWidth - width)
                    x=frameWidth - width;
                else 
                    x+= 2;
            }
        }
    }
}

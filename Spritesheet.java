package javapong;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

public class Spritesheet {

    private BufferedImage sheet;
    private static final String URL = "/opt/lampp/htdocs/javagame/assets/";

    public Spritesheet(String fileName) {
        try {
            System.out.println(URL + fileName);
            sheet = ImageIO.read(new File(URL + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public BufferedImage getBufferedImage(){
        return sheet;
    }

    public int getWidth(){
        return sheet.getWidth();
    }

    public int getHeight(){
        return sheet.getHeight();
    }

    public BufferedImage getSprite(int x, int y, int width, int height){
        return sheet.getSubimage(x,y,width,height);
    }
}

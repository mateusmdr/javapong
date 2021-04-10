package javapong;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Game extends Canvas implements Runnable, KeyListener {


    private static final long serialVersionUID = 1L;

    private JFrame frame;
    private double maxFPS;
    private boolean isRunning;
    private static Thread thread;

    private static final int WIDTH = 320;
    private static final int HEIGHT = 180;
    private static final int SCALE = 5;

    private BufferedImage bg = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

    public static Player player;
    public static Enemy enemy;
    public static Ball ball;

    private Spritesheet sheet;

    public Game(String title, int maxFPS) {

        frame = new JFrame(title);
        this.maxFPS = maxFPS;

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        addKeyListener(this);

        player = new Player(WIDTH,HEIGHT,30,4);
        enemy = new Enemy(WIDTH,30,4);
        ball = new Ball(WIDTH,HEIGHT,5,5);

        sheet = new Spritesheet("logo.png");

        start();

    }

    public static void main(String[] args){
        new Game("Titulo",60);
    }

    private synchronized void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }

    private void update() {
        player.update(bg.getWidth());
        enemy.update(bg.getWidth());
        ball.update(bg.getWidth());

        if(ball.y > Game.HEIGHT) {            
            System.out.println("O Inimigo marcou um ponto");
            Game.ball = new Ball(WIDTH,HEIGHT,5,5);
            player = new Player(WIDTH,HEIGHT,30,4);
            enemy = new Enemy(WIDTH,30,4);
        }else if (ball.y < 0) {
            System.out.println("Você marcou um ponto");
            Game.ball = new Ball(WIDTH,HEIGHT,5,5);
            player = new Player(WIDTH,HEIGHT,30,4);
            enemy = new Enemy(WIDTH,30,4);
        }
    }

    private void render() {

        BufferStrategy bf = this.getBufferStrategy();
        if(bf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bg.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        /*Renderização de entidades*/

        player.render(g);
        enemy.render(g);
        ball.render(g);

        // g.setColor(Color.BLACK);
        // int[] xpoints = {30,200,100,100};
        // int[] ypoints = {500,10,20,200};
        // g.fillPolygon(xpoints,ypoints,4);
        // g.setColor(new Color(0,0,255));

        // g.setFont(new Font("Times",Font.BOLD,16));
        // g.drawString("Eai qual vai ser?",100,100);

        // g.fillOval(0,0,100,80);

        // g.setColor(Color.BLACK);
        // g.drawLine(0,0,500,500);
        // g2.rotate(Math.toRadians(45),0,0);
        // g.drawImage(sheet.getSprite(0,0,sheet.getBufferedImage().getWidth(),sheet.getBufferedImage().getHeight()),0,0,null);

        /***/

        g = bf.getDrawGraphics();
        g.drawImage(bg,0,0,WIDTH * SCALE,HEIGHT * SCALE,null);

        g.dispose();
        bf.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double period = Math.pow(10,9) / maxFPS;
        double delta = 0;

        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / period;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                frames ++;
                delta --;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }

        stop();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.isMovingLeft = true;
            break;
            case KeyEvent.VK_D:
            player.isMovingRight = true;
            break;
            default:
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.isMovingLeft = false;
            break;
            case KeyEvent.VK_D:
                player.isMovingRight = false;
            break;
            default:
            System.out.println("aaa");
        }
    }

    public void keyTyped(KeyEvent e) {
        //qqqq
    }
    
}

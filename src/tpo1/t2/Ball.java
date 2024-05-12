package tpo1.t2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Ball {
    private Component canvas;
    private static final int XSIZE = 15;
    private static final int YSIZE = 15;
    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;
    private BallThread thread;


    public Ball(Component c){
        this.canvas = c;

        if(Math.random()<0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }else{
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public void draw (Graphics2D g2){
        g2.setColor(Color.darkGray);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        if (this.canvas instanceof BallCanvas) {
            BallCanvas ballCanvas = (BallCanvas) this.canvas;
            if (ballCanvas.getPit().contains(x, y)) {
                ballCanvas.removeBall(this);
                this.thread.stopRunning();
                BounceFrame.incrementPitCounter();
            }
        }
        this.canvas.repaint();
    }

    public void setThread (BallThread t){
        this.thread = t;
    }
}
package tpo1.t4;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    private static int ballsInPitCount = 0;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private static final int NUM_BALLS = 15;
    private static final JLabel ballsInPitLabel = new JLabel("Balls in pit: " + ballsInPitCount);
    private static BallThread prevThread = null;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.lightGray);
        ballsInPitLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(ballsInPitLabel);
        content.add(topPanel, BorderLayout.NORTH);

        JPanel pitPanel = new JPanel();
        pitPanel.setLayout(new BorderLayout());

        pitPanel.add(this.canvas, BorderLayout.CENTER);
        content.add(pitPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(e -> {
            for (int i = 0; i < NUM_BALLS; i++) {
                Ball b = new Ball(canvas);
                canvas.add(b);

                BallThread thread;
                if (prevThread == null) {
                    thread = new BallThread(b, null);
                } else {
                    thread = new BallThread(b, prevThread);

                }
                thread.start();
                b.setThread(thread);
                prevThread = thread;
                System.out.println("Thread name = " + thread.getName());
                canvas.repaint();
            }
        });

        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static synchronized void incrementPitCounter() {
        ballsInPitCount++;
        ballsInPitLabel.setText("Balls in pit: " + ballsInPitCount);
    }
}
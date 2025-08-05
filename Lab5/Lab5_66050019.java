package Lab5;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lab5_66050019 extends JPanel implements Runnable{

    double circleMove = 0;
    double squareRotate = 0;
    double squareMove = 0;
    boolean isMoveRight = true;
    

    public static void main(String[] args) {
        Lab5_66050019 m = new Lab5_66050019();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        (new Thread(m)).start();
        
    }
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        g2.setColor(Color.BLACK);
        g2.translate(circleMove, 0);
        g2.drawOval(0, 0, 100, 100);

        // Rectangle
        // g2.translate(-circleMove, 0);
        // g2.rotate(squareRotate, 300,300);
        // g2.drawRect(200, 200, 200, 200);

        // Rectangle bottomleft
        Graphics2D g3 = (Graphics2D) g.create();
        g3.setColor(Color.BLACK);
        g3.translate(0, squareMove);
        g3.drawRect(0, 510, 50, 50);
        
    }
    public void run() {
        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;
        double startTime = System.currentTimeMillis();

        while (true){
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            double time = currentTime - startTime;
            System.out.println("time: " + time);
            
            //TODO: update logics
            if (time >= 3000){
                squareMove -= 100.0 * elapsedTime / 1000.0;
            }
            if (isMoveRight){
                if (circleMove < 500){
                    circleMove += 100.0 * elapsedTime / 1000.0;
                }
                else {
                    circleMove = 500;
                    isMoveRight = false;
                }
            }
            else{
                if (circleMove > 0){
                    circleMove -= 100.0 * elapsedTime / 1000.0;
                }
                else{
                    circleMove = 0;
                    isMoveRight = true;
                }
            }
            
            

            
            //Display
            repaint();
        }
    }
    
}

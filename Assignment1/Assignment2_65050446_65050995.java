import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Queue;

public class Assignment2_65050446_65050995 extends JPanel implements Runnable {
    public static void main(String[] args) {
        // ImageIcon originalIcon = new ImageIcon(new ImageIcon(
        // "C:\\Users\\attap\\Downloads\\pngtree-baby-illustration-holding-a-baby-bottle-image_1457394.jpg")
        // .getImage().getScaledInstance(600, 600, 0));
        // Image originalImage = originalIcon.getImage();
        // Image translucentImage = makeImageTranslucent(originalImage, 0.1f);
        // ImageIcon translucentIcon = new ImageIcon(translucentImage);
        // JLabel label = new JLabel(translucentIcon);
        // JLabel bg = new JLabel(originalIcon);
        // label.setLocation(50, 50);
        // label.setBounds(0, 230, 600, 600);

        Assignment2_65050446_65050995 m = new Assignment2_65050446_65050995();

        JFrame f = new JFrame();
        // m.add(label);
        // f.getContentPane().add(label);
        f.add(m);
        f.setTitle("Assignment2_65050446_65050995");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        (new Thread(m)).start();
    }

    int o = 0;
    int xflower = -20;
    int yflower = 0;
    int sxflower = 1;
    int syflower = 1;
    double xtflower = 0.0;
    double ytflower = 0.0;

    int xtailsun = 0;
    int sxtailsun = 1;
    double xttailsun = -480.0;

    int xgrass = 0;
    int sxgrass = 1;
    double xtgrass = 0.0;

    double sunToSperm = 0.0;
    int xcloud1 = 0;
    int xcloud2 = 0;
    int xscloud1 = 2;
    int xscloud2 = 1;

    double posFace1 = 0;
    double posFace2 = 0;

    public void paintComponent(Graphics g) {
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        Color sunColor1 = new Color(0xfb8c00);
        Color sunColor2 = new Color(0xef6c00);
        if (sunMove > -330) {
            g2.translate(0, bgSpeed);
            // -----------------background----------------------
            // background(g2, new Color(0x004ddb), new Color(0xc1eafa));


            g2.translate(0, -bgSpeed);
            cloud1(g2, xcloud1, yflower);
            cloud2(g2, xcloud2, yflower);
            g2.setColor(Color.BLACK);
            g2.setColor(sunColor1);
            midpointEllipse(g2, 300, 450 + (int) sunMove, 120, 120, 1);
            floodFillGradient(buffer, 300, 440 + (int) sunMove, 300, 400 + (int) sunMove, sunColor1, sunColor2,
                    sunColor1);
            // faceBabySpeed(g2, buffer);

            // ---------------หน้าเด็ก---------------------
            // faceBaby(g2, buffer, (int) sunMove);

            // System.out.println(sunMove);
            posFace1 = 440 + (int) sunMove;
            posFace2 = 400 + (int) sunMove;
            home(g2,buffer);
            mountain(g2, buffer);

        } else if (sunMove <= -330) {
            g2.setColor(new Color(0xc1eafa));
            g2.fillRect(0, 0, 600, 600);
            cloud1(g2, xcloud1, yflower);
            cloud2(g2, xcloud2, yflower);
            home(g2,buffer);
            mountain(g2, buffer);
            g2.translate(0, -tailMove);

            // --------วาดเส้นหางอสุจิ-------------
            // tailSun(g2, xtailsun);


            g2.translate(0, tailMove);
            // System.out.println(tailMove);
            if (tailMove <= -295) {

                // -----------เติมสีหาง-------------
                // floodFillTail(buffer);
                // plot(g2, 294, 240, 4);

            }
            g2.setColor(Color.BLACK);

            if (Math.abs(xtailsun) > 50) {
                sxtailsun = -sxtailsun;
                xtailsun += sxtailsun;
            }
            if (flowerMove < xttailsun) {
                xtailsun += sxtailsun;
                xttailsun -= 3;
            }
            if (tailMove <= -490) {
                if (o < 40) {
                    g2.setColor(sunColor1);
                    midpointEllipse(g2, 300, 120, 120 - o, 120, 1);
                    floodFillGradient(buffer, 300, (int) posFace1, 300, (int) posFace2, sunColor1, sunColor2,
                            sunColor1);
                    // System.out.println(440+(int)posFace);
                    o++;

                } else {
                    // วาดตัวใบหน้า
                    g2.setColor(sunColor1);
                    midpointEllipse(g2, 300, 120, 120 - o, 120, 1);
                    floodFillGradient(buffer, 300, (int) posFace1, 300, (int) posFace2, sunColor1, sunColor2,
                            sunColor1);

                    // faceSun(g2, buffer);
                }
            } else {
                g2.setColor(sunColor1);
                midpointCircle(g2, 300, 120, 120, 1);
                floodFillGradient(buffer, 300, (int) posFace1, 300, (int) posFace2, sunColor1, sunColor2, sunColor1);
                g2.setColor(Color.BLACK);

                // ----------หน้าเด็ก------------
                // faceBaby(g2, buffer, -330);

                g2.setColor(Color.BLACK);
            }
        }

        // faceBabySpeed(g2, buffer);
        Color colorFlower[] = {new Color(0xfb7633),new Color(0xfb9abf),new Color(0xc9a2db),new Color(0x6ec5de),new Color(0xc184ff)};
        g2.setColor(Color.BLACK);
        // -----------------------------ดอกไม้ 4 ดอก------------------------------------
        flower(g2, 20, 20, xflower, yflower, colorFlower[0], colorFlower[0], buffer);
        flower(g2, 400, 60, xflower, yflower, colorFlower[1], colorFlower[1], buffer);
        flower(g2, 180, 80, xflower, yflower, colorFlower[4], colorFlower[4], buffer);
        flower(g2, 500, 120, xflower, yflower, colorFlower[3], colorFlower[3], buffer);

        // ----------------------------หญ้า 4 ---------------------------------------------
        grass(g2, 0, 0, xgrass, buffer);
        grass(g2, 300, -30, xgrass, buffer);
        grass(g2, 380, 50, xgrass, buffer);
        grass(g2, 100, 60, xgrass, buffer);
        grass(g2, 500, -60, xgrass, buffer);
        if (Math.abs(xflower) > 20) {
            sxflower = -sxflower;
            xflower += sxflower;
        }
        if (flowerMove < xtflower) {
            xflower += sxflower;
            xtflower -= 5.0;
        }
        if (Math.abs(yflower) > 5) {
            syflower = -syflower;
            yflower += syflower;
        }
        if (flowerMove < ytflower) {
            yflower += syflower;
            ytflower -= 5.0;
        }

        if (Math.abs(xgrass) > 10) {
            sxgrass = -sxgrass;
            xgrass += sxgrass;
        }
        if (flowerMove < xtgrass) {
            xgrass += sxgrass;
            xtgrass -= 1.0;
        }

        if (xcloud1 <= -69 || xcloud1 >= 259) {
            xscloud1 = -xscloud1;
        }
        if (xcloud2 <= -300 || xcloud2 >= 29) {
            xscloud2 = -xscloud2;
        }
        xcloud1 += xscloud1;
        xcloud2 += xscloud2;

        
        
        g.drawImage(buffer, 0, 0, null);

    }

    Color home1_1 = new Color(0xef6c00); //สีอาคาร
    Color home1_2 = new Color(0xef6c90); //สีอาคาร
    Color home2 = new Color(0xa02c00); //สีขอบอาคาร
    Color home3_1 = new Color(0xFB6F92); //หลังคา
    Color home3_2 = new Color(0xe01c00); //หลังคา
    Color home4 = new Color(0xa02c00); //ขอบหลังคา
    Color home5 = new Color(0xfce2d5); //หน้าต่าง
    
    // ---------------------------------------หอคอย---------------------------------------------
    void home(Graphics2D g,BufferedImage buffer){
        // g.setColor(home2);
        // bresenhamLine(g, 30, 80, 350, 350, 2);
        // bresenhamLine(g, 80, 80, 200, 350, 2);
        // bresenhamLine(g, 30, 30, 200, 350, 2);
        // bresenhamLine(g, 20, 90, 200, 200, 2);
        // floodFillGradient(buffer, 55, 221, 55, 329, home1_1, home1_2, home2);

        
        // g.setColor(home5);        
        // bresenhamLine(g, 50, 70, 250, 250, 1);
        // bezierCurve(g, 50, 250, 50, 210, 70, 210, 71, 250, 1);
        // FloodFill2(buffer, 60, 240, home5, home5);
        
        // g.setColor(home4);
        // bresenhamLine(g, 20, 55, 200, 150, 2);
        // bresenhamLine(g, 90, 55, 200, 150, 2);
        // bresenhamLine(g, 20, 90, 200, 200, 2);
        // floodFillGradient(buffer, 55, 160, 55, 195, home3_1, home3_2, home4);
        
        // g.setColor(home2);
        // bresenhamLine(g, 50, 70, 250, 250, 1);
        // bezierCurve(g, 50, 250, 50, 210, 70, 210, 71, 250, 1);
        // bresenhamLine(g, 30, 50, 260, 260, 1);
        // bresenhamLine(g, 60, 80, 290, 290, 1);
        // bresenhamLine(g, 30, 50, 310, 310, 1);
        //plot(g, 60, 240, 3);
    } 

    double sunMove = 0;
    double velocityX = 100.0;
    double currentTime = 0; // เวลาล่าสุด
    double elapsedTime = 0; // เวลา
    double lastTime = 0; // เวลาเริ่มโปรแกรม

    // @Override
    public void run() {
        lastTime = System.currentTimeMillis();

        while (true) {
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;
            flowerMove();
            sunMove();
            if (sunMove <= -480) {
                tailMove();
            }
            repaint();
        }
    }

    Color colorTailSun = new Color(0xffec00);

    // void tailSun(Graphics2D g2, int xr) {
    //     g2.setColor(colorTailSun);
    //     bezierCurve(g2, 285, -255, 285, -160, 285 + xr, -70, 290 - xr, 30, 1);
    //     bezierCurve(g2, 305, -250, 295, -160, 295 + xr, -70, 290 - xr, 30, 1);
    //     bresenhamLine(g2, 285, 305, -255, -250, 1);
    //     // bezierCurve(g2, 280, -255, 400, -160, 200, -70 , 290 -xr, 30, 1);
    //     // bezierCurve(g2, 300, -250, 410, -160, 210, -70, 290 -xr, 30, 1);

    // }

    // เติมสีหาง
    // void floodFillTail(BufferedImage buffer) {
    //     FloodFill2(buffer, 298, 239, colorTailSun, colorTailSun);

    // }

    double tailMove = 0;

    void tailMove() {
        if (tailMove > -490) {
            tailMove -= velocityX * elapsedTime / 1000.0;
        }
    }

    Color currentColor1 = new Color(0xf47b00);
    Color currentColor2 = new Color(0xf47b00);
    Color y2b1 = new Color(0xffbb00);
    Color y2b2 = new Color(0xffbb00);

    double faceFast = -1000.0;

    // -----------------------------------------------------โครงใบหน้า---------------------------------------------------------
    // void faceStart() {
    //     Color blackColor = Color.BLACK;
    //     if (flowerMove < faceFast && !currentColor1.equals(blackColor)) {
    //         int newRed = Math.max(0, currentColor1.getRed() - 1);
    //         int newGreen = Math.max(0, currentColor1.getGreen() - 1);
    //         int newBlue = Math.max(0, currentColor1.getBlue() - 1);
    //         currentColor1 = new Color(newRed, newGreen, newBlue);
    //         faceFast -= 2;
    //         y2b1 = currentColor1;

    //         int newRed2 = Math.max(0, currentColor2.getRed() - 1);
    //         int newGreen2 = Math.max(0, currentColor2.getGreen() - 1);
    //         int newBlue2 = Math.max(0, currentColor2.getBlue() - 1);
    //         currentColor2 = new Color(newRed2, newGreen2, newBlue2);
    //         y2b2 = currentColor2;
    //     } else if (flowerMove >= faceFast) {
    //         currentColor1 = blackColor; // Set color to black
    //         y2b1 = currentColor1;
    //         y2b2 = currentColor2;
    //     }
    // }

    // -----------------------------------------วาดตัวใบหน้า------------------------------------------------------------------

    // void faceSun(Graphics2D g2, BufferedImage buffer) {
    //     // faceStart();
    //     int y_offset = 0; // กำหนดค่า offset ที่ต้องการ
    //     g2.setColor(y2b1);
    //     // คิ้วซ้าย
    //     bezierCurve(g2, 240, 88 + y_offset, 246, 65 + y_offset, 285, 85 + y_offset, 290, 75 + y_offset, 1);
    //     bezierCurve(g2, 240, 88 + y_offset, 241, 65 + 8 + y_offset, 285, 85 + 8 + y_offset, 290, 75 + y_offset, 1);

    //     bezierCurve(g2, 288, 83 + y_offset, 293, 77 + y_offset, 293, 76 + y_offset, 294, 75 + y_offset, 1);

    //     // คิ้วขวา
    //     bezierCurve(g2, 310, 75 + y_offset, 330, 81 + y_offset, 340, 81 + y_offset, 358, 80 + y_offset, 1);
    //     bezierCurve(g2, 310, 75 + y_offset, 320, 81 + 9 + y_offset, 340, 81 + 9 + y_offset, 360, 80 + 9 + y_offset, 1);
    //     bresenhamLine(g2, 358, 355 + y_offset, 80 + y_offset, 84 + y_offset, 1);
    //     bresenhamLine(g2, 355, 360 + y_offset, 84 + y_offset, 80 + 9 + y_offset, 1);

    //     // ตาซ้าย
    //     bresenhamLine(g2, 250, 280 + y_offset, 91 + y_offset, 90 + y_offset, 1);
    //     bresenhamLine(g2, 253, 280 + y_offset, 95 + y_offset, 92 + y_offset, 1);
    //     plot(g2, 280, 91 + y_offset, 1);
    //     bresenhamLine(g2, 250, 253 + y_offset, 91 + y_offset, 95 + y_offset, 1);

    //     bezierCurve(g2, 278, 87 + y_offset, 288, 90 + y_offset, 290, 93 + y_offset, 270, 100 + y_offset, 1);

    //     // ตาขวา
    //     bezierCurve(g2, 317, 94 + y_offset, 330, 94 + y_offset, 340, 93 + y_offset, 350, 100 + y_offset, 1);
    //     bezierCurve(g2, 317, 94 + y_offset, 330, 94 + 6 + y_offset, 340, 93 + 6 + y_offset, 350, 100 + y_offset, 1);

    //     bezierCurve(g2, 307, 75 + y_offset, 310, 80 + y_offset, 312, 85 + y_offset, 314, 87 + y_offset, 1);

    //     bresenhamLine(g2, 314, 307 + y_offset, 87 + y_offset, 90 + y_offset, 1);

    //     // จมูก
    //     bresenhamLine(g2, 290, 290 + y_offset, 95 + y_offset, 115 + y_offset, 1);
    //     bezierCurve(g2, 288, 118 + y_offset, 290, 130 + y_offset, 300, 130 + y_offset, 304, 122 + y_offset, 1);
    //     bezierCurve(g2, 288, 118 + y_offset, 275, 130 + y_offset, 286, 133 + y_offset, 290, 130 + y_offset, 1);
    //     bezierCurve(g2, 290, 130 + y_offset, 292, 140 + y_offset, 295, 130 + y_offset, 298, 131 + y_offset, 1);
    //     bezierCurve(g2, 304, 122 + y_offset, 310, 140 + y_offset, 309, 130 + y_offset, 298, 131 + y_offset, 1);

    //     bezierCurve(g2, 290, 95 + y_offset, 280, 100 + y_offset, 285, 106 + y_offset, 290, 113 + y_offset, 1);

    //     g2.setColor(y2b2);
    //     // ปาก
    //     bresenhamLine(g2, 308, 285 + y_offset, 144 + y_offset, 144 + y_offset, 1);
    //     bezierCurve(g2, 315, 144 + 8 + y_offset, 310, 148 + y_offset, 280, 148 + y_offset, 275, 144 + 8 + y_offset, 1);
    //     bresenhamLine(g2, 308, 315 + y_offset, 144 + y_offset, 144 + 8 + y_offset, 1);
    //     bresenhamLine(g2, 285, 275 + y_offset, 144 + y_offset, 144 + 8 + y_offset, 1);

    //     // คาง
    //     bezierCurve(g2, 313, 166 + y_offset, 300, 177 + y_offset, 290, 173 + y_offset, 286, 173 + y_offset, 1);
    //     bezierCurve(g2, 313, 166 + y_offset, 300, 167 + y_offset, 290, 169 + y_offset, 280, 165 + y_offset, 1);
    //     bezierCurve(g2, 286, 173 + y_offset, 284, 167 + y_offset, 285, 169 + y_offset, 280, 165 + y_offset, 1);

    //     Color cFace = y2b1;
    //     FloodFill2(buffer, 255, 78 + y_offset, cFace, cFace);
    //     FloodFill2(buffer, 320, 78 + y_offset, cFace, cFace);
    //     FloodFill2(buffer, 325, 95 + y_offset, cFace, cFace);
    //     FloodFill2(buffer, 255, 92 + y_offset, cFace, cFace);
    //     FloodFill2(buffer, 285, 100 + y_offset, cFace, cFace);
    //     FloodFill2(buffer, 285, 122 + y_offset, cFace, cFace);
    //     FloodFill2(buffer, 285, 145 + y_offset, y2b2, y2b2);
    //     FloodFill2(buffer, 285, 168 + y_offset, y2b2, y2b2);

    // }

    void sunMove() {
        if (sunMove > -480) {
            sunMove -= velocityX * elapsedTime / 1000.0;
            bgSpeed();
        }
    }

    double flowerMove = 0.0;

    void flowerMove() {
        flowerMove -= velocityX * elapsedTime / 1000.0;
    }

    // --------------------------------วาด background----------------------------------------
    // void background(Graphics2D g2, Color darkColor, Color lightColor) {
    //     GradientPaint GP1 = new GradientPaint(0, 0, darkColor, 300, 300, lightColor);
    //     g2.setPaint(GP1);
    //     g2.fillRect(0, 0, 300, 2000);

    //     GradientPaint GP2 = new GradientPaint(600, 0, darkColor, 300, 300, lightColor);
    //     g2.setPaint(GP2);
    //     g2.fillRect(300, 0, 300, 2000);
    // }

    public void rotateBezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size,
            double angle, int centerX, int centerY) {
        double radians = Math.toRadians(angle);
        for (int i = 0; i <= 1000; i++) {
            double t = i / 1000.0;

            // Calculate rotated coordinates
            int x = (int) ((Math.pow((1 - t), 3) * x1
                    + 3 * t * Math.pow((1 - t), 2) * x2
                    + 3 * Math.pow((t), 2) * (1 - t) * x3 + Math.pow(t, 3) * x4));

            int y = (int) ((Math.pow((1 - t), 3) * y1
                    + 3 * t * Math.pow((1 - t), 2) * y2
                    + 3 * Math.pow((t), 2) * (1 - t) * y3 + Math.pow(t, 3) * y4));

            // Translate to origin, rotate, and translate back
            int translatedX = x - centerX;
            int translatedY = y - centerY;
            int rotatedX = (int) (translatedX * Math.cos(radians) - translatedY * Math.sin(radians));
            int rotatedY = (int) (translatedX * Math.sin(radians) + translatedY * Math.cos(radians));
            int finalX = rotatedX + centerX;
            int finalY = rotatedY + centerY;

            // Plot the point
            plot(g, finalX, finalY, size);
        }
    }

    Color flower1 = new Color(0x836953); // สีก้าน
    Color flower2 = new Color(0xFCCF55); // สีแกน
    Color flower3 = new Color(0xCC9B71); // สีขอบแกน
    Color flower4 = new Color(0x92CA68); // สีใบไม้
    Color flower5 = new Color(0x6BA08A); // สีขอบใบไม้

    void flower(Graphics2D g2, int x, int y, int xr, int yr, Color cl1, Color cl2, BufferedImage m) {
        // x คือตำแหน่งจริง xr คือตำแหน่งสำหรับขยับ
        g2.setColor(cl1);
        // bezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr
        // + x, 370 + yr + y, 35 + xr + x,355 + yr + y, 1);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 45, 40 + xr + x, 350 + yr + y);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 135, 40 + xr + x, 350 + yr + y);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 225, 40 + xr + x, 350 + yr + y);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 315, 40 + xr + x, 350 + yr + y);
        FloodFill2(m, 30 + x + xr, 335 + y + yr, cl1, cl1);
        g2.setColor(cl2);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 45, 40 + xr + x, 350 + yr + y);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 135, 40 + xr + x, 350 + yr + y);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 225, 40 + xr + x, 350 + yr + y);
        rotateBezierCurve(g2, 35 + xr + x, 345 + yr + y, 10 + xr + x, 330 + yr + y, 10 + xr + x, 370 + yr + y,
                35 + xr + x, 355 + yr + y, 1, 315, 40 + xr + x, 350 + yr + y);
        g2.setColor(flower1);
        bezierCurve(g2, 40 + xr + x, 360 + yr + y, 38 + xr + x, 370 + yr + y, 35 + xr / 3 + x, 390 + yr / 3 + y, 40 + x,
                400 + y, 2);
        // bresenhamLine(g2, 40 + xr + x, 40 + x, 360 + yr + y, 410 + y, 2);
        g2.setColor(flower4);
        bezierCurve(g2, 40 + x, 400 + y, 45 + x, 390 + yr + y, 50 + x, 390 + yr + y, 60 + x, 395 + yr + y, 1);
        bezierCurve(g2, 40 + x, 400 + y, 45 + x, 400 + yr + y, 50 + x, 400 + yr + y, 60 + x, 395 + yr + y, 1);
        bezierCurve(g2, 40 + x, 400 + y, 35 + x, 390 + yr + y, 30 + x, 390 + yr + y, 20 + x, 395 + yr + y, 1); // ใบ
        bezierCurve(g2, 40 + x, 400 + y, 35 + x, 400 + yr + y, 30 + x, 400 + yr + y, 20 + x, 395 + yr + y, 1);
        FloodFill2(m, 30 + x, 395 + y + yr, flower4, flower4);
        FloodFill2(m, 50 + x, 395 + y + yr, flower4, flower4);
        g2.setColor(flower5);
        bezierCurve(g2, 40 + x, 400 + y, 45 + x, 390 + yr + y, 50 + x, 390 + yr + y, 60 + x, 395 + yr + y, 1);
        bezierCurve(g2, 40 + x, 400 + y, 45 + x, 400 + yr + y, 50 + x, 400 + yr + y, 60 + x, 395 + yr + y, 1);
        bezierCurve(g2, 40 + x, 400 + y, 35 + x, 390 + yr + y, 30 + x, 390 + yr + y, 20 + x, 395 + yr + y, 1); // ใบ
        bezierCurve(g2, 40 + x, 400 + y, 35 + x, 400 + yr + y, 30 + x, 400 + yr + y, 20 + x, 395 + yr + y, 1);
        g2.setColor(flower2);
        midpointCircle(g2, 40 + xr + x, 350 + yr + y, 8, 1);
        FloodFill2(m, 41 + x + xr, 351 + y + yr, flower2, flower2);
        g2.setColor(flower3);
        midpointCircle(g2, 40 + xr + x, 350 + yr + y, 8, 1);
    }

    Color grass1 = new Color(0xADD88D); // สีใบ
    Color grass2 = new Color(0x228B22); // สีขอบใบ

    private void grass(Graphics2D g2, int x, int y, int xr, BufferedImage buffer) {
        g2.setColor(grass1);
        bezierCurve(g2, 38 + x, 500 + y, 34 + x, 490 + y, 30 + x, 485 + y, 23 + x, 485 - xr / 2 + y, 1);
        bezierCurve(g2, 45 + x, 498 + y, 41 + x, 485 + y, 29 + x, 485 + y, 23 + x, 485 - xr / 2 + y, 1);
        bezierCurve(g2, 45 + x, 498 + y, 41 + x, 475 + y, 29 + x, 470 + y, 28 + x + xr / 2, 468 - xr / 2 + y, 1);
        bezierCurve(g2, 48 + x, 487 + y, 41 + x, 475 + y, 29 + x, 470 + y, 28 + x + xr / 2, 468 - xr / 2 + y, 1);
        bezierCurve(g2, 48 + x, 487 + y, 50 + x, 475 + y, 50 + x, 470 + y, 55 + x + xr, 463 + y, 1);
        bezierCurve(g2, 52 + x, 489 + y, 52 + x, 475 + y, 53 + x, 470 + y, 55 + x + xr, 463 + y, 1);
        bezierCurve(g2, 49 + x, 498 + y, 52 + x, 490 + y, 53 + x, 481 + y, 74 + x + xr / 2, 481 + xr / 2 + y, 1);
        bezierCurve(g2, 55 + x, 498 + y, 57 + x, 495 + y, 58 + x, 489 + y, 74 + x + xr / 2, 481 + xr / 2 + y, 1);
        bezierCurve(g2, 55 + x, 498 + y, 57 + x, 498 + y, 58 + x, 495 + y, 70 + x, 491 + xr / 2 + y, 1);
        bezierCurve(g2, 62 + x, 500 + y, 62 + x, 500 + y, 62 + x, 498 + y, 70 + x, 491 + xr / 2 + y, 1);
        bresenhamLine(g2, 38 + x, 62 + x, 500 + y, 500 + y, 1);
        FloodFill2(buffer, 40 + x, 497 + y, grass1, grass1);
        plot(g2, 40, 497, 2);
        g2.setColor(grass2);
        bezierCurve(g2, 38 + x, 500 + y, 34 + x, 490 + y, 30 + x, 485 + y, 23 + x, 485 - xr / 2 + y, 1);
        // bezierCurve(g2, 45 + x, 498 + y, 41 + x, 485 + y, 29 + x, 485 + y, 23 + x, 485 - xr / 2 + y, 1);
        // bezierCurve(g2, 45 + x, 498 + y, 41 + x, 475 + y, 29 + x, 470 + y, 28 + x + xr / 2, 468 - xr / 2 + y, 1);
        // bezierCurve(g2, 48 + x, 487 + y, 41 + x, 475 + y, 29 + x, 470 + y, 28 + x + xr / 2, 468 - xr / 2 + y, 1);
        // bezierCurve(g2, 48 + x, 487 + y, 50 + x, 475 + y, 50 + x, 470 + y, 55 + x + xr, 463 + y, 1);
        // bezierCurve(g2, 52 + x, 489 + y, 52 + x, 475 + y, 53 + x, 470 + y, 55 + x + xr, 463 + y, 1);
        // bezierCurve(g2, 49 + x, 498 + y, 52 + x, 490 + y, 53 + x, 481 + y, 74 + x + xr / 2, 481 + xr / 2 + y, 1);
        // bezierCurve(g2, 55 + x, 498 + y, 57 + x, 495 + y, 58 + x, 489 + y, 74 + x + xr / 2, 481 + xr / 2 + y, 1);
        // bezierCurve(g2, 55 + x, 498 + y, 57 + x, 498 + y, 58 + x, 495 + y, 70 + x, 491 + xr / 2 + y, 1);
        // bezierCurve(g2, 62 + x, 500 + y, 62 + x, 500 + y, 62 + x, 498 + y, 70 + x, 491 + xr / 2 + y, 1);
        // bresenhamLine(g2, 38+x, 62+x, 500+y, 500+y, 1);
    }

    // void faceBabySpeed(Graphics2D g, BufferedImage buffer) {
    // // y += 10; // เพิ่มค่า y ทั้งหมดที่ถูกใช้ในการวาด
    // // System.out.println(flowerMove);
    // // if (faceBabySpeed > flowerMove && sunMove >= -330) {
    // // faceBabySpeed -= 5;
    // // faceBaby(g, buffer);
    // // } else {
    // // faceBaby(g, buffer);
    // // }
    // faceBabySpeed = (int)sunMove;
    // faceBaby(g, buffer);
    // }

    // void faceBaby(Graphics2D g, BufferedImage buffer, int faceBabySpeed) {
    //     Color p = new Color(0xFFC2D1);
    //     g.setColor(p);
    //     midpointEllipse(g, 230, 460 + faceBabySpeed, 15, 10, 1);
    //     midpointEllipse(g, 370, 470 + faceBabySpeed, 15, 10, 1);
    //     FloodFill2(buffer, 233, 465 + faceBabySpeed, p, p);
    //     FloodFill2(buffer, 373, 465 + faceBabySpeed, p, p);
    //     Color r = new Color(0xFF6962);
    //     g.setColor(r);
    //     bezierCurve(g, 260, 480 + faceBabySpeed, 270, 530 + faceBabySpeed, 320, 520 + faceBabySpeed, 330,
    //             490 + faceBabySpeed, 1);
    //     bresenhamLine(g, 260, 330, 480 + faceBabySpeed, 490 + faceBabySpeed, 1);
    //     FloodFill2(buffer, 275, 490 + faceBabySpeed, r, r);

    //     g.setColor(p);
    //     bezierCurve(g, 270, 501 + faceBabySpeed, 280, 490 + faceBabySpeed, 300, 490 + faceBabySpeed, 310,
    //             511 + faceBabySpeed, 1);
    //     bezierCurve(g, 260, 480 + faceBabySpeed, 270, 530 + faceBabySpeed, 320, 520 + faceBabySpeed, 330,
    //             490 + faceBabySpeed, 1);
    //     FloodFill2(buffer, 275, 500 + faceBabySpeed, p, p);
    //     g.setColor(Color.BLACK);
    //     drawCircle(g, 260, 430 + faceBabySpeed, 15);
    //     drawCircle(g, 350, 440 + faceBabySpeed, 15);

    //     g.setColor(Color.WHITE);
    //     drawCircle(g, 255, 425 + faceBabySpeed, 5);
    //     drawCircle(g, 345, 435 + faceBabySpeed, 5);

    // }

    double bgSpeed = 0.0;

    void bgSpeed() {
        if (sunMove > -480) {
            bgSpeed -= velocityX * elapsedTime / 500.0;
        }
    }

    Color cloud1 = Color.WHITE;

    void cloud1(Graphics2D g, int x, int y) {
        g.setColor(cloud1);
        drawCircle(g, 100 + x, 90 + y, 30);
        drawCircle(g, 170 + x, 70 + y, 60);
        drawCircle(g, 250 + x, 80 + y, 50);
        drawCircle(g, 310 + x, 90 + y, 30);
    }

    void cloud2(Graphics2D g, int x, int y) {
        g.setColor(cloud1);
        drawCircle(g, 400 + x, 205 + y, 50);
        drawCircle(g, 470 + x, 195 + y, 60);
        drawCircle(g, 540 + x, 215 + y, 30);
    }

    Color mountain2 = new Color(0xd1e43c);
    Color mountain1 = new Color(0x028910);

    void mountain(Graphics2D g2, BufferedImage buffer) {
        g2.setColor(mountain1);
        bezierCurve(g2, 0, 330, 100, 360, 150, 260, 390, 430, 2);
        bezierCurve(g2, 400, 390, 520, 370, 560, 360, 600, 360, 2);
        bezierCurve(g2, 300, 370, 320, 360, 470, 360, 490, 375, 2);
        // bezierCurve(g2, 0, 330, 280, 280, 470, 280, 600, 360, 2);
        bresenhamLine(g2, 0, 0, 330, 600, 5);
        bresenhamLine(g2, 0, 600, 600, 600, 5);
        bresenhamLine(g2, 600, 600, 360, 600, 5);
        // FloodFill2(buffer, 5, 340, grass1,grass1);
        floodFillGradient(buffer, 300, 599, 5, 330, mountain1, mountain2, mountain1);
        g2.setColor(mountain2);
        bezierCurve(g2, 0, 330, 100, 360, 150, 260, 390, 430, 2);
        bezierCurve(g2, 400, 390, 520, 370, 560, 360, 600, 360, 2);
        bezierCurve(g2, 300, 370, 320, 360, 470, 360, 490, 375, 2);
        // bezierCurve(g2, 0, 330, 280, 280, 470, 280, 600, 360, 2);

        g2.setColor(Color.BLACK);

    }

    private void drawCircle(Graphics2D g, int centerX, int centerY, int radius) {
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= radius * radius) {
                    plot(g, x, y, 1);
                }
            }
        }
    }

    public void midpointEllipse(Graphics g, int xc, int yc, int a, int b, int size) {
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;

        // region 1
        int x = 0;
        int y = b;
        int D = Math.round(b2 - a2 * b + a2 / 4);
        int Dx = 0, Dy = twoA2 * y;
        while (Dx <= Dy) {
            plot(g, x + xc, y + yc, size);
            plot(g, -x + xc, y + yc, size);
            plot(g, x + xc, -y + yc, size);
            plot(g, -x + xc, -y + yc, size);
            x++;
            Dx += twoB2;
            D += Dx + b2;
            if (D >= 0) {
                y--;
                Dy -= twoA2;
                D -= Dy;
            }
        }
        // region 2
        x = a;
        y = 0;
        D = Math.round(a2 - b2 * a + b2 / 4);
        Dx = twoB2 * x;
        Dy = 0;
        while (Dx >= Dy) {
            plot(g, x + xc, y + yc, size);
            plot(g, -x + xc, y + yc, size);
            plot(g, x + xc, -y + yc, size);
            plot(g, -x + xc, -y + yc, size);
            y++;
            Dy += twoA2;
            D += Dy + a2;
            if (D >= 0) {
                x--;
                Dx -= twoB2;
                D -= Dx;
            }
        }

    }

    public void midpointCircle(Graphics g, int xc, int yc, int r, int size) {
        int x = 0;
        int y = r;
        int Dx = 2 * x;
        int Dy = 2 * y;
        int D = 1 - r;
        while (x <= y) {
            // plot
            plot(g, x + xc, y + yc, size);
            plot(g, -x + xc, y + yc, size);
            plot(g, x + xc, -y + yc, size);
            plot(g, -x + xc, -y + yc, size);
            plot(g, y + xc, x + yc, size);
            plot(g, -y + xc, x + yc, size);
            plot(g, y + xc, -x + yc, size);
            plot(g, -y + xc, -x + yc, size);
            x++;
            Dx = Dx + 2;
            D = D + Dx + 1;
            if (D >= 0) {
                y -= 1;
                Dy -= 2;
                D -= Dy;
            }
        }
    }

    public void drawCircle(Graphics g, int centerX, int centerY, int radius) {
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= radius * radius) {
                    plot(g, x, y, 1);
                }
            }
        }
    }

    private void plot(Graphics g, double x, double y, int size) {
        g.fillRect((int) x, (int) y, size, size);
    }

    public void bresenhamLine(Graphics g, int x1, int x2, int y1, int y2, int size) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSwap = false;

        if (dy > dx) {
            double tmp = dy;
            dy = dx;
            dx = tmp;
            isSwap = true;
        }

        Double D = 2 * dy - dx;

        double x = x1;
        double y = y1;

        for (int i = 1; i <= dx; i++) {
            plot(g, x, y, size);

            if (D >= 0) {
                if (isSwap)
                    x += sx;
                else
                    y += sy;

                D -= 2 * dx;
            }

            if (isSwap)
                y += sy;
            else
                x += sx;

            D += 2 * dy;
        }
    }

    public void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size) {
        for (int i = 0; i <= 1000; i++) {
            double t = i / 1000.0;
            int x = (int) (Math.pow((1 - t), 3) * x1
                    + 3 * t * Math.pow((1 - t), 2) * x2
                    + 3 * Math.pow((t), 2) * (1 - t) * x3 + Math.pow(t, 3) * x4);

            int y = (int) (Math.pow((1 - t), 3) * y1
                    + 3 * t * Math.pow((1 - t), 2) * y2
                    + 3 * Math.pow((t), 2) * (1 - t) * y3 + Math.pow(t, 3) * y4);

            plot(g, x, y, size);
        }
    }

    public BufferedImage FloodFill(BufferedImage m, int x, int y, Color targetColour, Color replacementColor) {
        Graphics2D g2 = m.createGraphics();
        Queue<Point> q = new LinkedList<>();

        if (m.getRGB(x, y) == targetColour.getRGB()) {
            g2.setColor(replacementColor);
            plot(g2, x, y, 1);
            q.add(new Point(x, y));
        }

        while (!q.isEmpty()) {
            Point p = q.poll();

            // s
            if (p.y < 600 && m.getRGB(p.x, p.y + 1) == targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }
            // n
            if (p.y > 0 && m.getRGB(p.x, p.y - 1) == targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }
            // e
            if (p.x < 600 && m.getRGB(p.x + 1, p.y) == targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }
            // w
            if (p.x > 0 && m.getRGB(p.x - 1, p.y) == targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }
        return m;
    }

    public BufferedImage FloodFill2(BufferedImage m, int x, int y, Color targetColour, Color replacementColor) {
        Graphics2D g2 = m.createGraphics();
        Queue<Point> q = new LinkedList<>();

        if (m.getRGB(x, y) != targetColour.getRGB()) {
            g2.setColor(replacementColor);
            plot(g2, x, y, 1);
            q.add(new Point(x, y));
        }

        while (!q.isEmpty()) {
            Point p = q.poll();

            // s
            if (p.y < 600 && m.getRGB(p.x, p.y + 1) != targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }
            // n
            if (p.y > 0 && m.getRGB(p.x, p.y - 1) != targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }
            // e
            if (p.x < 600 && m.getRGB(p.x + 1, p.y) != targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }
            // w
            if (p.x > 0 && m.getRGB(p.x - 1, p.y) != targetColour.getRGB()) {
                g2.setColor(replacementColor);
                plot(g2, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }
        return m;
    }

    public BufferedImage floodFillGradient(BufferedImage m, int xStart, int yStart, int xEnd, int yEnd,
            Color startColor, Color endColor, Color target) {
        Graphics2D g2 = m.createGraphics();
        Queue<Point> q = new LinkedList<>();

        float ratio = (float) (yStart - yStart) / (yEnd - yStart - 1);

        Color lineColor = interpolateColor(startColor, endColor, ratio);
        Color lineColor1 = interpolateColor(startColor, endColor, ratio);
        Color lineColor2 = interpolateColor(startColor, endColor, ratio);

        if (m.getRGB(xStart, yStart) != target.getRGB()) {
            g2.setColor(lineColor);
            plot(g2, xStart, yStart, 1);
            q.add(new Point(xStart, yStart));
        }

        while (!q.isEmpty()) {
            Point p = q.poll();

            ratio = (float) (p.y - yStart) / (yEnd - yStart - 1);
            lineColor = interpolateColor(startColor, endColor, ratio);

            // y-1
            ratio = (float) (p.y - yStart - 1) / (yEnd - yStart - 1);
            lineColor1 = interpolateColor(startColor, endColor, ratio);

            // y+1
            ratio = (float) (p.y - yStart + 1) / (yEnd - yStart - 1);
            lineColor2 = interpolateColor(startColor, endColor, ratio);

            // s
            if (p.y + 1 < 600 && (m.getRGB(p.x, p.y + 1) != target.getRGB())) {
                if (m.getRGB(p.x, p.y + 1) != lineColor2.getRGB()) {
                    g2.setColor(lineColor2);
                    plot(g2, p.x, p.y + 1, 1);
                    q.add(new Point(p.x, p.y + 1));
                }
            }
            // n
            if (p.y - 1 > 0 && (m.getRGB(p.x, p.y - 1) != target.getRGB())) {
                if (m.getRGB(p.x, p.y - 1) != lineColor1.getRGB()) {
                    g2.setColor(lineColor1);
                    plot(g2, p.x, p.y - 1, 1);
                    q.add(new Point(p.x, p.y - 1));
                }
            }
            // e
            if (p.x + 1 < 600 && (m.getRGB(p.x + 1, p.y) != target.getRGB())) {
                if (m.getRGB(p.x + 1, p.y) != lineColor.getRGB()) {
                    g2.setColor(lineColor);
                    plot(g2, p.x + 1, p.y, 1);
                    q.add(new Point(p.x + 1, p.y));
                }
            }
            // w
            if (p.x - 1 > 0 && (m.getRGB(p.x - 1, p.y) != target.getRGB())) {
                if (m.getRGB(p.x - 1, p.y) != lineColor.getRGB()) {
                    g2.setColor(lineColor);
                    plot(g2, p.x - 1, p.y, 1);
                    q.add(new Point(p.x - 1, p.y));
                }
            }

        }
        return m;
    }

    private Color interpolateColor(Color startColor, Color endColor, float ratio) {
        int red = Math.max(0,
                Math.min(255, (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio)));
        int green = Math.max(0,
                Math.min(255, (int) (startColor.getGreen() * (1 - ratio)
                        + endColor.getGreen() * ratio)));
        int blue = Math.max(0,
                Math.min(255, (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio)));

        return new Color(red, green, blue);
    }

    public void line(Graphics g, int x1, int y1, int x2, int y2, int size) {
        // y2+=1;
        // System.out.println(x2+=1);
        if (x1 != x2) {
            for (int i = 0; i < size; i++) {
                bresenhamLine(g, x1, x2, y1 + i, y2 + i, 1);
            }
        } else {
            for (int i = 0; i < size; i++) {
                bresenhamLine(g, x1 + i, x2 + i, y1, y2, 1);
            }
        }
    }

    private static Image makeImageTranslucent(Image image, float transparency) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // สร้าง Graphics2D จาก BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();

        // ตั้งค่าความทึบของรูปภาพ
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

        // วาดรูปภาพลงใน BufferedImage
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }
}
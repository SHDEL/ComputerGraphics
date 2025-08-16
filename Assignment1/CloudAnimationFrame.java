import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CloudAnimationFrame extends JFrame {

    public CloudAnimationFrame() {
        setTitle("Sky to Ground");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        SkyPanel skyPanel = new SkyPanel();
        add(skyPanel);

        Timer timer = new Timer(30, e -> {
            skyPanel.updateScene();
            skyPanel.repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CloudAnimationFrame frame = new CloudAnimationFrame();
            frame.setVisible(true);
        });
    }

    class SkyPanel extends JPanel {
        private List<Cloud> clouds;
        private int cameraY = 0;
        private int cameraSpeed = 1;
        private int skyHeight = 800;
        private int groundHeight = 150;

        public SkyPanel() {
            clouds = new ArrayList<>();
            // Upper clouds
            clouds.add(new Cloud(50, 100, 120, 60, 1));
            clouds.add(new Cloud(300, 200, 150, 80, 2));
            clouds.add(new Cloud(400, 50, 100, 50, 1));
            clouds.add(new Cloud(150, 150, 80, 40, 3));
            clouds.add(new Cloud(500, 70, 130, 60, 2));

            // Lower clouds near ground (adjustable)
            clouds.add(new Cloud(100, 400, 120, 60, 1));
            clouds.add(new Cloud(350, 450, 140, 70, 2));

            // ==== YOU CAN ADD MORE CLOUDS OR DECORATIONS HERE ====
            // Example: clouds.add(new Cloud(x, y, width, height, speed));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int panelHeight = getHeight();

            // ==== BACKGROUND SKY ====
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, -cameraY, getWidth(), skyHeight);

            // ==== CLOUDS ====
            g.setColor(Color.WHITE);
            for (Cloud cloud : clouds) {
                g.fillOval(cloud.x, cloud.y - cameraY, cloud.width, cloud.height);
            }

            // ==== ADD DECORATIONS ABOVE CLOUDS ====
            // Example: draw sun
            // g.setColor(Color.YELLOW);
            // g.fillOval(500, 50 - cameraY, 80, 80);
            // Example: draw birds
            // g.setColor(Color.BLACK);
            // g.drawLine(200, 120 - cameraY, 220, 120 - cameraY);
            // g.drawLine(220, 120 - cameraY, 230, 110 - cameraY);

            // ==== GROUND ====
            g.setColor(new Color(34, 139, 34));
            int groundY = skyHeight; 
            g.fillRect(0, groundY - cameraY, getWidth(), groundHeight);
            // ==== PIXEL SCENE (castle, mountains, grass) ====
            PixelArt.drawScene(g, groundY, cameraY);
            // ==== ADD DECORATIONS ON GROUND ====
            // Example: g.setColor(Color.RED); g.fillRect(50, groundY - cameraY + 50, 30, 30); // small house
            // Example: flowers, trees, rocks, etc.
        }

        public void updateScene() {
            // Move clouds horizontally and cycle
            for (Cloud cloud : clouds) {
                cloud.x += cloud.speed;
                if (cloud.x > getWidth()) {
                    cloud.x = -cloud.width; // cycle back to left
                }
            }

            // Move camera down until ground reaches bottom
            int totalSceneHeight = skyHeight + groundHeight;
            int panelHeight = getHeight();
            int maxCameraY = totalSceneHeight - panelHeight;

            if (cameraY < maxCameraY) {
                cameraY += cameraSpeed;
                if (cameraY > maxCameraY) cameraY = maxCameraY;
            }
        }
    }

    class Cloud {
        int x, y, width, height, speed;

        public Cloud(int x, int y, int width, int height, int speed) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.speed = speed;
        }
    }
}

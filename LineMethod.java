public class LineMethod {

    public static void main(String[] args) {
        
    }
    public static void naiveLine(int x1, int y1, int x2, int y2){
        int dx = x2 - x1;
        int dy = y2 - y1;
        int b = y1 - (dy / dx) * x1;

        for (int x = x1; x < x2 ; x++){
            int y = Math.round((dy / dx) * x) + b;
            // method plot
            
        }
    }
    public static void DDALine(int x1, int y1, int x2, int y2){
        int dx = x2 - x1;
        int dy = y2 - y1;
        int y = y1;
        int m = dy / dx;

        if (m <= 1 && m >= 0){
            for (int x = x1; x < x2; x++){
                y = y + m;
                // plot method

            }
        }
        else if (m <= -1){
            for (int x = x2; x < x1; x++){
                y = y + m;
                // plot method
            }
        }
        else if (m > 1){
            int x;
            for (y = y1; y < y2; y++){
                x = y + 1 / m;
                // plot method
            }
        }
        else{
            int x;
            for (y = y2; y < y1; y++){
                x = y + 1 / m;
                //plot method
            }
        }
    
    }
    public static void BresenhamLine(int x1, int y1, int x2, int y2){
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        boolean isSwap = false;

        // swap dx and dy if slope > 1
        if (dy > dx) {
            int temp = dx;
            dx = dy;
            dy = temp;
            isSwap = true;
        }

        int D = 2 * dy - dx;
        int x = x1;
        int y = y1;

        for (int i = 1; i <= dx; i++) {
            // plot(x, y); // draw the pixel

            if (D >= 0) {
                if (isSwap) {
                    x += sx;
                } else {
                    y += sy;
                }
                D -= 2 * dx;
            }

            if (isSwap) {
                y += sy;
            } else {
                x += sx;
            }

            D += 2 * dy;
        }
    }
    
}
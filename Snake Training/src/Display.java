import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display {
    private static Frame frame;
    private static Canvas canvas;

    private static int canvasWidth = 200;
    private static int canvasHeight = 200;

    private static int gridWidth = 10;
    private static int gridHeight = 10;

    private static void getBestSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        boolean done = false;
        while(!done){
            canvasWidth += gridWidth;
            canvasHeight += gridHeight;
        }
    }

    public static void init() {
        frame = new Frame();
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        frame.add(canvas);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                frame.setVisible(false);
            }
        });
        frame.setVisible(true);
    }
}

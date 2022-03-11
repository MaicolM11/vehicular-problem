package vehicular.problem.views;

import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel implements Runnable {
    
    private static final int REFRESH_TIME = 1000;

    private BoardView vehicles;
    private JFrame frame;
    
    public Window(BoardView vehicles) {
        this.frame = new JFrame();
        this.vehicles = vehicles;
        this.frame.add(this);
        this.frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.frame.setVisible(true);
    }

    @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		vehicles.paintVehicles(g);
	}

    @Override
    public void run() {
        while(true){
            this.repaint();
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }    

}
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SatelliteSimulation extends JPanel implements ActionListener {
    private Timer timer;
    private int angle = 0; 
    private int planetX = 300;
    private int planetY = 200;
    private int planetRadius = 50; 
    private int orbitA = 200; 
    private int orbitB = 100; 

    public SatelliteSimulation() {
        timer = new Timer(40, this); 
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.YELLOW);
        g.fillOval(planetX - planetRadius, planetY - planetRadius, planetRadius * 2, planetRadius * 2);

        double rad = Math.toRadians(angle);
        int satelliteX = (int) (planetX + orbitA * Math.cos(rad));
        int satelliteY = (int) (planetY + orbitB * Math.sin(rad));

        if (satelliteX < planetX) {
            g.setColor(Color.PINK);
            g.fillOval(satelliteX - 10, satelliteY - 10, 20, 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += 2; 
        if (angle >= 360) {
            angle = 0; 
        }
        repaint(); 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Satellite Simulation");
        SatelliteSimulation simulation = new SatelliteSimulation();
        frame.add(simulation);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

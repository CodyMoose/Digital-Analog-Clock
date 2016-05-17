package digitalanalogclock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import javax.swing.JPanel;

public class ClockPanel extends JPanel implements KeyListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 8632849499412146643L;
    public LocalDateTime       now                = LocalDateTime.now();
    public double              hour               = now.getHour();
    public double              hourAngle          = 45;
    public double              minute             = now.getMinute();
    public double              minuteAngle        = 0;
    public double              second             = now.getSecond();
    public double              secondAngle        = 0;
    public static final double HOUR_HAND_LENGTH   = 0.40;
    public static final double MINUTE_HAND_LENGTH = 0.72;
    public static final double SECOND_HAND_LENGTH = 0.72;
    public static final double MAJOR_TICK_LENGTH  = 0.1;
    public static final byte   MAJOR_TICK_AMOUNT  = 12;
    public static final double MINOR_TICK_LENGTH  = 0.05;
    public static final byte   MINOR_TICK_AMOUNT  = 60;
    
    public ClockPanel(){
        addKeyListener(this);
        setSize(300, 300);
        setBackground(new Color(255, 255, 255));
        setVisible(true);
        setLocation(0, 0);
        repaint();
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        now = LocalDateTime.now();
        hour = now.getHour() + ((double) now.getMinute() / 60.) + ((double) now.getSecond() / 3600.)
                        + ((double) now.getNano() / (3600. * 1000000000.));
        hourAngle = 30. * hour;
        minute = now.getMinute() + ((double) now.getSecond() / 60.) + ((double) now.getNano() / 60000000000.);
        minuteAngle = 6. * minute;
        second = now.getSecond() + ((double) now.getNano() / 1000000000.);
        secondAngle = 6. * second;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setPaint(Color.WHITE);
        g2.fillOval((int) (getWidth() * 0.05), (int) (getHeight() * 0.05), (int) (getWidth() * 0.9),
                        (int) (getHeight() * 0.9));
        g2.setStroke(new BasicStroke(5));
        g2.setPaint(Color.BLACK);
        g2.drawOval((int) (getWidth() * 0.05), (int) (getHeight() * 0.05), (int) (getWidth() * 0.9),
                        (int) (getHeight() * 0.9));
        // Hours line
        g2.drawLine(getWidth() / 2, getHeight() / 2,
                        (int) ((getWidth() / 2) - (HOUR_HAND_LENGTH * getWidth() / 2
                                        * Math.cos(Math.toRadians(90 + hourAngle)))),
                        (int) ((getHeight() / 2) - (HOUR_HAND_LENGTH * getHeight() / 2
                                        * Math.sin(Math.toRadians(90 + hourAngle)))));
        // Minutes line
        g2.drawLine(getWidth() / 2, getHeight() / 2,
                        (int) ((getWidth() / 2) - (MINUTE_HAND_LENGTH * getWidth() / 2
                                        * Math.cos(Math.toRadians(90 + minuteAngle)))),
                        (int) ((getHeight() / 2) - (MINUTE_HAND_LENGTH * getHeight() / 2
                                        * Math.sin(Math.toRadians(90 + minuteAngle)))));
        g2.setPaint(Color.RED);
        // Seconds line
        g2.drawLine(getWidth() / 2, getHeight() / 2,
                        (int) ((getWidth() / 2) - (SECOND_HAND_LENGTH * getWidth() / 2
                                        * Math.cos(Math.toRadians(90 + secondAngle)))),
                        (int) ((getHeight() / 2) - (SECOND_HAND_LENGTH * getHeight() / 2
                                        * Math.sin(Math.toRadians(90 + secondAngle)))));
        g2.setStroke(new BasicStroke(4));
        g2.setPaint(Color.BLACK);
        double majorTickAngle = 360. / (double) MAJOR_TICK_AMOUNT;
        for (int i = 0; i < MAJOR_TICK_AMOUNT; i++)
            g2.drawLine((int) ((getWidth() / 2)
                            + (0.9 * getWidth() / 2 * Math.cos(Math.toRadians(90 + majorTickAngle * i)))),
                            (int) ((getHeight() / 2) + (0.9 * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + majorTickAngle * i)))),
                            (int) ((getWidth() / 2) + ((0.9 - MAJOR_TICK_LENGTH) * getWidth() / 2
                                            * Math.cos(Math.toRadians(90 + majorTickAngle * i)))),
                            (int) ((getHeight() / 2) + ((0.9 - MAJOR_TICK_LENGTH) * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + majorTickAngle * i)))));
        g2.setStroke(new BasicStroke(2));
        double minorTickAngle = 360. / (double) MINOR_TICK_AMOUNT;
        for (int i = 0; i < MINOR_TICK_AMOUNT; i++)
            g2.drawLine((int) ((getWidth() / 2)
                            + (0.9 * getWidth() / 2 * Math.cos(Math.toRadians(90 + minorTickAngle * i)))),
                            (int) ((getHeight() / 2) + (0.9 * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + minorTickAngle * i)))),
                            (int) ((getWidth() / 2) + ((0.9 - MINOR_TICK_LENGTH) * getWidth() / 2
                                            * Math.cos(Math.toRadians(90 + minorTickAngle * i)))),
                            (int) ((getHeight() / 2) + ((0.9 - MINOR_TICK_LENGTH) * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + minorTickAngle * i)))));
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }
    
}

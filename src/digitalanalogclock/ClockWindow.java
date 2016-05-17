package digitalanalogclock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.time.LocalDateTime;
import javax.swing.JFrame;

public class ClockWindow extends JFrame implements KeyListener
{
    /**
     * 
     */
    private static final long      serialVersionUID   = 129105896982702963L;
    public LocalDateTime           now                = LocalDateTime.now();
    public double                  hour               = now.getHour();
    public double                  hourAngle          = 45;
    public double                  minute             = now.getMinute();
    public double                  minuteAngle        = 0;
    public double                  second             = now.getSecond();
    public double                  secondAngle        = 0;
    public static final double     HOUR_HAND_LENGTH   = 0.40;
    public static final double     HOUR_HAND_STROKE   = 5;
    public static final double     MINUTE_HAND_LENGTH = 0.72;
    public static final double     MINUTE_HAND_STROKE = 5;
    public static final double     SECOND_HAND_LENGTH = 0.72;
    public static final double     SECOND_HAND_STROKE = 5;
    public static final double     MAJOR_TICK_START   = 0.9;
    public static final double     MAJOR_TICK_LENGTH  = 0.1;
    public static final byte       MAJOR_TICK_AMOUNT  = 12;
    public static final double     MAJOR_TICK_STROKE  = 4;
    public static final double     MINOR_TICK_START   = 0.9;
    public static final double     MINOR_TICK_LENGTH  = 0.05;
    public static final byte       MINOR_TICK_AMOUNT  = 60;
    public static final double     MINOR_TICK_STROKE  = 2;
    public static Line2D.Double    hourLine           = new Line2D.Double(0, 0, 0, 0);
    public static Ellipse2D.Double hourLineCenter     = new Ellipse2D.Double(0, 0, 0, 0);
    public static Ellipse2D.Double hourLineOutside    = new Ellipse2D.Double(0, 0, 0, 0);
    public static Line2D.Double    minuteLine         = new Line2D.Double(0, 0, 0, 0);
    public static Ellipse2D.Double minuteLineCenter   = new Ellipse2D.Double(0, 0, 0, 0);
    public static Ellipse2D.Double minuteLineOutside  = new Ellipse2D.Double(0, 0, 0, 0);
    public static Line2D.Double    secondLine         = new Line2D.Double(0, 0, 0, 0);
    public static Ellipse2D.Double secondLineCenter   = new Ellipse2D.Double(0, 0, 0, 0);
    public static Ellipse2D.Double secondLineOutside  = new Ellipse2D.Double(0, 0, 0, 0);
    public ClockPanel              clock              = new ClockPanel();
    public static boolean          twfrHour             = false;

    public ClockWindow()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addKeyListener(this);
        setAlwaysOnTop(true);
        setSize(300, 300);
        setUndecorated(true);
        setResizable(false);
        setBackground(new Color(0, 0, 0, 0));
        setVisible(true);
        setLocation(0, 0);
        setLocation((int) (screenSize.getWidth() - getWidth()), 0);
        for(int i = 0; i < GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames().length; i++){
            System.out.println((i + 1) + ". " + GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[i]);
        }
        // add(clock);
        repaint();
        // clock.repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        // *
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

        g2.setStroke(new BasicStroke((float) MAJOR_TICK_STROKE));
        g2.setPaint(Color.BLACK);
        double majorTickAngle = 360. / (double) MAJOR_TICK_AMOUNT;
        for (int i = 0; i < MAJOR_TICK_AMOUNT; i++)
            g2.drawLine((int) ((getWidth() / 2)
                            + (MAJOR_TICK_START * getWidth() / 2 * Math.cos(Math.toRadians(90 + majorTickAngle * i)))),
                            (int) ((getHeight() / 2) + (MAJOR_TICK_START * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + majorTickAngle * i)))),
                            (int) ((getWidth() / 2) + ((MAJOR_TICK_START - MAJOR_TICK_LENGTH) * getWidth() / 2
                                            * Math.cos(Math.toRadians(90 + majorTickAngle * i)))),
                            (int) ((getHeight() / 2) + ((MAJOR_TICK_START - MAJOR_TICK_LENGTH) * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + majorTickAngle * i)))));
        g2.setStroke(new BasicStroke((float) MINOR_TICK_STROKE));
        double minorTickAngle = 360. / (double) MINOR_TICK_AMOUNT;
        for (int i = 0; i < MINOR_TICK_AMOUNT; i++)
            g2.drawLine((int) ((getWidth() / 2)
                            + (MINOR_TICK_START * getWidth() / 2 * Math.cos(Math.toRadians(90 + minorTickAngle * i)))),
                            (int) ((getHeight() / 2) + (MINOR_TICK_START * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + minorTickAngle * i)))),
                            (int) ((getWidth() / 2) + ((MINOR_TICK_START - MINOR_TICK_LENGTH) * getWidth() / 2
                                            * Math.cos(Math.toRadians(90 + minorTickAngle * i)))),
                            (int) ((getHeight() / 2) + ((MINOR_TICK_START - MINOR_TICK_LENGTH) * getHeight() / 2
                                            * Math.sin(Math.toRadians(90 + minorTickAngle * i)))));
        
        g2.setStroke(new BasicStroke(5));
        g2.setPaint(Color.BLACK);
        g2.drawOval((int) (getWidth() * 0.05), (int) (getHeight() * 0.05), (int) (getWidth() * 0.9),
                        (int) (getHeight() * 0.9));
        // Hours line
        g2.setStroke(new BasicStroke((float) HOUR_HAND_STROKE));
        hourLine = new Line2D.Double(getWidth() / 2, getHeight() / 2,
                        (int) ((getWidth() / 2) - (HOUR_HAND_LENGTH * getWidth() / 2
                                        * Math.cos(Math.toRadians(90 + hourAngle)))),
                        (int) ((getHeight() / 2) - (HOUR_HAND_LENGTH * getHeight() / 2
                                        * Math.sin(Math.toRadians(90 + hourAngle)))));
        g2.draw(hourLine);
        /*-
        hourLineCenter = new Ellipse2D.Double((getWidth() / 2) - HOUR_HAND_STROKE / 2,
                        (getHeight() / 2) - HOUR_HAND_STROKE / 2, HOUR_HAND_STROKE / 4, HOUR_HAND_STROKE / 4);
        g2.draw(hourLineCenter);
        hourLineOutside = new Ellipse2D.Double(hourLine.getX2() - HOUR_HAND_STROKE / 2,
                        hourLine.getY2() - HOUR_HAND_STROKE / 2, HOUR_HAND_STROKE / 4, HOUR_HAND_STROKE / 4);
        g2.draw(hourLineOutside);
        //- */
        // Minutes line
        minuteLine = new Line2D.Double(getWidth() / 2, getHeight() / 2,
                        (int) ((getWidth() / 2) - (MINUTE_HAND_LENGTH * getWidth() / 2
                                        * Math.cos(Math.toRadians(90 + minuteAngle)))),
                        (int) ((getHeight() / 2) - (MINUTE_HAND_LENGTH * getHeight() / 2
                                        * Math.sin(Math.toRadians(90 + minuteAngle)))));
        g2.draw(minuteLine);
        // Seconds line
        g2.setStroke(new BasicStroke((float) SECOND_HAND_STROKE));
        g2.setPaint(Color.RED);
        secondLine = new Line2D.Double(getWidth() / 2, getHeight() / 2,
                        (int) ((getWidth() / 2) - (SECOND_HAND_LENGTH * getWidth() / 2
                                        * Math.cos(Math.toRadians(90 + secondAngle)))),
                        (int) ((getHeight() / 2) - (SECOND_HAND_LENGTH * getHeight() / 2
                                        * Math.sin(Math.toRadians(90 + secondAngle)))));
        g2.draw(secondLine);
        /*-
        secondLineCenter = new Ellipse2D.Double((getWidth() / 2) - SECOND_HAND_STROKE / 2,
                        (getHeight() / 2) - SECOND_HAND_STROKE / 2, SECOND_HAND_STROKE / 2, SECOND_HAND_STROKE / 2);
        g2.fill(secondLineCenter);
        secondLineOutside = new Ellipse2D.Double(secondLine.getX2() - SECOND_HAND_STROKE / 2,
                        secondLine.getY2() - SECOND_HAND_STROKE / 2, SECOND_HAND_STROKE / 2, SECOND_HAND_STROKE / 2);
        //- */
        g2.fill(secondLineOutside);
        g2.setPaint(Color.WHITE);
        g2.setFont(new Font("Microsoft JengHei", Font.BOLD, 16));
        g2.drawString(((twfrHour) ? now.getHour() : (now.getHour() > 12) ? now.getHour() - 12 : (now.getHour() == 0) ? 12 : now.getHour()) + ":" + ((now.getMinute() < 10) ? "0" : "") + now.getMinute() + ":" + ((now.getSecond() < 10) ? "0" : "") + now.getSecond() + ":" + now.getNano()/100000000, (int) (getWidth() * 0.4), (int) (getHeight() * 0.75));
        
        g2.setPaint(Color.BLACK);
        g2.setFont(new Font("Microsoft JengHei", Font.PLAIN, 17));
        g2.drawString(((twfrHour) ? now.getHour() : (now.getHour() > 12) ? now.getHour() - 12 : (now.getHour() == 0) ? 12 : now.getHour()) + ":" + ((now.getMinute() < 10) ? "0" : "") + now.getMinute() + ":" + ((now.getSecond() < 10) ? "0" : "") + now.getSecond() + ":" + now.getNano()/100000000, (int) (getWidth() * 0.4), (int) (getHeight() * 0.75));
        
        repaint();
        // - */
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_SPACE:
                twfrHour = !twfrHour;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        }
    }
}

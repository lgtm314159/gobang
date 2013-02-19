package gobang;

import java.awt.*;
import javax.swing.*;
import org.jmock.Mockery;
import org.junit.*;

public class MyTest extends JPanel {

        public MyTest() {
                this.setBackground(Color.BLACK);
                JButton b = new JButton("Button");
                
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // draw code
                Graphics2D g2d = (Graphics2D) g;
                setBackground(Color.blue);
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(4f, 0, 0));
                g2d.drawRect(10, 10, 50, 50);
                g2d.drawLine(10, 40, 60, 40);
                g2d.drawLine(35, 10, 35, 40);
        }

        private static void createUIAndShow() {
                JFrame frame = new JFrame();

                MyTest test = new MyTest();
                frame.getContentPane().add(test);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 399);
                frame.setVisible(true);
        }

        public static void main(String[] args) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                                MyTest.createUIAndShow();
                        }
                });
        }

}
package gobang;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass {

  public static void main(String[] args) {
    JFrame aWindow = new JFrame();
    aWindow.setBounds(200, 200, 200, 200);
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container content = aWindow.getContentPane();
    content.add(new MouseMotionAdapterDemo());
    aWindow.setVisible(true);
  }
}

class MouseMotionAdapterDemo extends JPanel {

  public MouseMotionAdapterDemo() {
    setBackground(Color.YELLOW);
    addMouseListener(new Adapter1(this));
    addMouseMotionListener(new Adapter2(this));
  }
}

class Adapter1 extends MouseAdapter {
  MouseMotionAdapterDemo mmad;

  public Adapter1(MouseMotionAdapterDemo mmad) {
    this.mmad = mmad;
  }

  public void mouseReleased(MouseEvent me) {
    mmad.setBackground(Color.white);
    mmad.repaint();
  }
}

class Adapter2 extends MouseMotionAdapter {
  MouseMotionAdapterDemo mmad;

  public Adapter2(MouseMotionAdapterDemo mmad) {
    this.mmad = mmad;
  }

  public void mouseDragged(MouseEvent me) {
    mmad.setBackground(Color.cyan);
    mmad.repaint();
  }
}

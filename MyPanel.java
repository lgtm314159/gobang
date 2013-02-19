package gobang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class MyPanel extends JPanel {

  public MyPanel() {
      setBorder(BorderFactory.createLineBorder(Color.black));
  }

  public Dimension getPreferredSize() {
      return new Dimension(250,200);
  }

  public void paintComponent(Graphics g) {
      super.paintComponent(g);       
      g.setColor(Color.WHITE);
      // Draw Text
      g.drawString("This is my custom Panel!",10,20);
  }  
}

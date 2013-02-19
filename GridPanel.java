package gobang;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridPanel extends JPanel implements MouseListener {
  private final int ROW = 10;
  private final int COLUMN = 10;
  private JPanel[][] panelHolder = new JPanel[ROW][COLUMN];
  private JPanel cellPanel;
  private ImageIcon black = new ImageIcon("src\\gobang\\images\\black.jpg");
  private ImageIcon white = new ImageIcon("src\\gobang\\images\\white.jpg");
  private Gobang gobang;
  private GobangGUI gobangGUI;

  public GridPanel(Gobang gobang) {
    this.gobang = gobang;
    setLayout(new GridLayout(ROW, COLUMN));
    setVisible(true);

    //Initialize the panelHolder array that represents positions for each chess.
    for(int i = 0; i < ROW; i++) {
      for(int j = 0; j < COLUMN; j++) {
        panelHolder[i][j] = new JPanel();
        cellPanel = panelHolder[i][j];
        cellPanel.setLayout(new BorderLayout());
        cellPanel.setName("" + i + j);
        cellPanel.setOpaque(false);
        cellPanel.addMouseListener(this);
        cellPanel.setVisible(true);
        add(cellPanel);
      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Color color = new Color(0xFFCC66);
    setBackground(color);
    double height = (double)this.getHeight();
    double width = (double)this.getWidth();
    double rowHeight = height / ROW;
    double rowWidth = width / COLUMN;
    for(int i = 0; i < 10; i++) {
      //Draw horizontal lines.
      Line2D line = new Line2D.Double(
          0.0, (double)i * rowHeight, width, (double)i * rowHeight);
      g2.draw(line);
      //Draw vertical lines.
      line = new Line2D.Double((double)i * rowWidth, 0.0, (double)i * rowWidth, height);
      g2.draw(line);
    }
  }

  protected void setGobangGUI(GobangGUI gobangGUI) {
    this.gobangGUI = gobangGUI;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if(gobang.isPlayerTurn()) {
      addPlayerChess(e);
      gobangGUI.regretMenuItem.setEnabled(true);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  //Place a player's chess on the chessboard.
  private void addPlayerChess(MouseEvent e) {
    int index = Integer.parseInt(e.getComponent().getName());
    int x = index / 10;
    int y = index % 10;
    cellPanel = panelHolder[x][y];
    if(panelHolder[x][y].getComponentCount() == 0) {
      panelHolder[x][y].add(new JLabel(white), BorderLayout.CENTER);
      panelHolder[x][y].revalidate();
      panelHolder[x][y].repaint();
      gobang.playerMove(x, y);
    }
  }

  //Place a computer's chess on the chessboard.
  protected void addComputerChess(int x, int y) {
    cellPanel = panelHolder[x][y];
    if(panelHolder[x][y].getComponentCount() == 0) {
      panelHolder[x][y].add(new JLabel(black), BorderLayout.CENTER);
      panelHolder[x][y].revalidate();
      panelHolder[x][y].repaint();
    }
  }

  //Reset the chessboard for a new game.
  protected void clearPanelHolder() {
    for(int i = 0; i < ROW; i++) {
      for(int j = 0; j < ROW; j++) {
        panelHolder[i][j].removeAll();
        panelHolder[i][j].revalidate();
        panelHolder[i][j].repaint();
      }
    }
  }

  // Regret player's last move.
  protected void regretPlayerMove(Tuple cMove, Tuple pMove) {
    panelHolder[cMove.getX()][cMove.getY()].removeAll();
    panelHolder[cMove.getX()][cMove.getY()].revalidate();
    panelHolder[cMove.getX()][cMove.getY()].repaint();
    panelHolder[pMove.getX()][pMove.getY()].removeAll();
    panelHolder[pMove.getX()][pMove.getY()].revalidate();
    panelHolder[pMove.getX()][pMove.getY()].repaint();
  }
}

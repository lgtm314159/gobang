package gobang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class GobangGUI implements WindowListener, ActionListener {
  private JFrame mainFrame;
  private JMenuBar menuBar;
  private JMenu playMenu;
  private Gobang gobang;
  protected JMenuItem newGameMenuItem, regretMenuItem, exitMenuItem;
  private GridPanel gridPanel;
  GameListener gameListener;
  public GobangGUI(){
    gobang = new Gobang();
    createMainFrame();
    createMenu();
  }
  
  private void createMainFrame() {
    mainFrame = new JFrame("Gobang");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gridPanel = new GridPanel(gobang);
    gobang.setGridPanel(gridPanel);
    gridPanel.setGobangGUI(this);
    mainFrame.setContentPane(gridPanel);
    mainFrame.setResizable(false);
  }

  private void createMenu() {
    menuBar = new JMenuBar();

    playMenu = new JMenu("Play");
    playMenu.setMnemonic(KeyEvent.VK_P);
    playMenu.getAccessibleContext().setAccessibleDescription("The game play menu");
    menuBar.add(playMenu);
    
    newGameMenuItem = new JMenuItem("New Game", KeyEvent.VK_N);
    newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_1, ActionEvent.ALT_MASK));
    newGameMenuItem.getAccessibleContext().setAccessibleDescription(
        "Start a new game");
    newGameMenuItem.setActionCommand("New Game");
    newGameMenuItem.addActionListener(this);

    regretMenuItem = new JMenuItem("Regret", KeyEvent.VK_R);
    regretMenuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_2, ActionEvent.ALT_MASK));
    regretMenuItem.getAccessibleContext().setAccessibleDescription(
        "Regret your last move");
    regretMenuItem.setActionCommand("Regret");
    regretMenuItem.addActionListener(this);
    regretMenuItem.setEnabled(false);
    
    exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_E);
    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_3, ActionEvent.ALT_MASK));
    exitMenuItem.getAccessibleContext().setAccessibleDescription(
        "Exit the Gobang game application");
    exitMenuItem.setActionCommand("Exit");
    exitMenuItem.addActionListener(this);
    
    playMenu.add(newGameMenuItem);
    playMenu.add(regretMenuItem);
    playMenu.add(exitMenuItem);

    if(mainFrame != null) {
      mainFrame.setJMenuBar(menuBar);
    }
  }

  public void showMainFrame() {    
    //Display the window.
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand() == "New Game") {
      gridPanel.clearPanelHolder();
      gobang.newGame();
      gameListener = new GameListener(gobang);
      gameListener.start();
    } else if(e.getActionCommand() == "Regret") {
      // TODO
      regretPlayerMove();
      regretMenuItem.setEnabled(false);
      if(!gameListener.isAlive()) {
        gameListener = new GameListener(gobang);
        gameListener.start();
      }
    } else if(e.getActionCommand() == "Exit") {
      // TODO
    }
  }

  public void regretPlayerMove() {
    Tuple computerLastMove = gobang.getComputerLastMove();
    Tuple playerLastMove = gobang.getPlayerLastMove();
    gobang.regretPlayerMove();
    gridPanel.regretPlayerMove(computerLastMove, playerLastMove);
  }

  @Override
  public void windowActivated(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void windowClosed(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void windowClosing(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void windowDeactivated(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void windowDeiconified(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void windowIconified(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void windowOpened(WindowEvent arg0) {
    // TODO Auto-generated method stub
    
  }

}

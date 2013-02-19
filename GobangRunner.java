package gobang;

public class GobangRunner {
  public static void main(String[] args) {
    final GobangGUI gui = new GobangGUI();
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        gui.showMainFrame();
      }
    });
  }
}

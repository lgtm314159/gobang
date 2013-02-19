package gobang;

public class GameListener extends Thread {
  private Gobang gobang;

  public GameListener(Gobang gobang) {
    this.gobang = gobang;
  }

  public void run() {
    while(!gobang.isGameOver()) {
      if(gobang.isComputerTurn()) {
        gobang.computerMove();
      }
    }
    if(!gobang.getWinner().equals("Tie game!")) {
      System.out.println("Winner is " + gobang.getWinner());
    } else {
      System.out.println("Tie game!");
    }
  }
}

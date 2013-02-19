package gobang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gobang {
  //Variables initialization.
  protected final int ROW = 10;
  protected final int COLUMN = 10;
  protected final int WINHANDS = 192;
  protected int playerChessCount = 0;
  protected int computerChessCount = 0;
  int[][] chessboard = new int[ROW][COLUMN];
  int[][] playerScore = new int[ROW][COLUMN];
  int[][] computerScore = new int[ROW][COLUMN];
  int[][][] playerWinTable = new int[ROW][COLUMN][192];
  int[][][] computerWinTable = new int[ROW][COLUMN][192];
  //[0][*] for player, [1][*] for computer.
  int[][] winhandChessCount = new int[2][192];
  boolean gameover = false, playerTurn = false, computerTurn = false;
  boolean tie = false;
  String winner = "";
  int i, j, k;
  private GridPanel gridPanel;
  Tuple playerLastMove;
  Tuple computerLastMove;
  int[] tempComputerWinTable1 = new int[192];
  int[] tempComputerWinTable2 = new int[192];
  int[] tempComputerWinhandChessCount = new int[192];
  int[] tempPlayerWinTable1 = new int[192];
  int[] tempPlayerWinTable2 = new int[192];
  int[] tempPlayerWinhandChessCount = new int[192];
  

  //Constructor with initializations.
  public Gobang() {
    initializeChessboardAndScore();
    initializeWinhands();
    initializeWinTables();
  }

  protected void initializeChessboardAndScore() {
    for(i = 0; i < ROW; i++) {
      for(j = 0; j < COLUMN; j++) {
        chessboard[i][j] = 0;
        playerScore[i][j] = 0;
        computerScore[i][j] = 0;
        for(k = 0; k < WINHANDS; k++) {
          playerWinTable[i][j][k] = 0;
          computerWinTable[i][j][k] = 0;
        }
      }
    }
  }

  protected void initializeWinhands() {
    for(i = 0; i < 2; i++) {
      for(j = 0; j < WINHANDS; j++) {
        winhandChessCount[i][j] = 0;
      }
    }
  }

  protected void oldInitializeWinTables() {
    for(i = 0; i < ROW; i++) {
      for(j = 0; j < COLUMN; j++) {
        //Initialize winning hands of horizontal and vertical lines.
        if(j <= 4) {
          for(int counter = 0; counter <= j; counter++) {
            playerWinTable[i][j][6 * i + counter] = 1;
            computerWinTable[i][j][6 * i + counter] = 1;
          }
        } else {
          for(int counter = 0; counter <= 9 - j; counter++) {
            playerWinTable[i][j][6 * i + 5 - counter] = 1;
            computerWinTable[i][j][6 * i + 5 - counter] = 1;
          }
        }
        if(i <= 4) {
          for(int counter = 0; counter <= i; counter++) {
            playerWinTable[i][j][6 * j + counter + 60] = 1;
            computerWinTable[i][j][6 * j + counter + 60] = 1;
          }
        } else {
          for(int counter = 0; counter <= 9 - i; counter++) {
            playerWinTable[i][j][6 * j + 5 - counter + 60] = 1;
            computerWinTable[i][j][6 * j + 5 - counter + 60] = 1;
          }
        }
        //Initialize winning hands of positions below chessboard diagonal.
        if(j < i) {
          switch(Math.abs(i - 9) + Math.abs(j - 0)) {
          case 4:
            playerWinTable[i][j][120] = 1;
            computerWinTable[i][j][120] = 1;
            break;
          case 5:
            if(j <= 4) {
              for(int counter = 1; counter <= Math.min(j + 1, 2); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][120 + 2 - counter] = 1;
                computerWinTable[i][j][120 + 2 - counter] = 1;
              }
            }
            break;
          case 6:
            if(j <= 4 ) {
              for(int counter = 3; counter <= Math.min(j + 3, 5); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][120 + 5 - counter] = 1;
                computerWinTable[i][j][120 + 5 - counter] = 1;
              }
            }
            break;
          case 7:
            if(j <= 4) {
              for(int counter = 6; counter <= Math.min(j + 6, 9); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][120 + 9 - counter] = 1;
                computerWinTable[i][j][120 + 9 - counter] = 1;
              }
            }
            break;
          case 8:
            if(j <= 4) {
              for(int counter = 10; counter <= Math.min(j + 10, 14); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][120 + 14 - counter] = 1;
                computerWinTable[i][j][120 + 14 - counter] = 1;
              }
            }
            break;
          }
        //Initialize winning hands of chessboard diagonal.
        } else if( i == j) {
          if(j <= 4) {
            for(int counter = 0; counter <= j; counter++) {
              playerWinTable[i][j][120 + 15 + counter] = 1;
              computerWinTable[i][j][120 + 15 + counter] = 1;
            }
          } else {
            for(int counter = 0; counter <= 9 - i; counter++) {
              playerWinTable[i][j][120 + 15 + 5 - counter] = 1;
              computerWinTable[i][j][120 + 15 + 5 - counter] = 1;
            }
          }
        //Initialize winning hands of positions above chessboard diagonal.
        } else {
          switch(Math.abs(i - 9) + Math.abs(j - 0)) {
          case 10:
            if(i <= 4) {
              for(int counter = 21; counter <= Math.min(i + 21, 25); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - j; counter++) {
                playerWinTable[i][j][120 + 25 - counter] = 1;
                computerWinTable[i][j][120 + 25 - counter] = 1;
              }
            }
            break;
          case 11:
            if(i <= 4) {
              for(int counter = 26; counter <= Math.min(i + 26, 29); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - j; counter++) {
                playerWinTable[i][j][120 + 29 - counter] = 1;
                computerWinTable[i][j][120 + 29 - counter] = 1;
              }
            }
            break;
          case 12:
            if(i <= 4) {
              for(int counter = 30; counter <= Math.min(i + 30, 32); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - j; counter++) {
                playerWinTable[i][j][120 + 32 - counter] = 1;
                computerWinTable[i][j][120 + 32 - counter] = 1;
              }
            }
            break;
          case 13:
            if(i <= 4) {
              for(int counter = 33; counter <= Math.min(i + 33, 34); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - j; counter++) {
                playerWinTable[i][j][120 + 34 - counter] = 1;
                computerWinTable[i][j][120 + 34 - counter] = 1;
              }
            }
            break;
          case 14:
            playerWinTable[i][j][120 + 35] = 1;
            computerWinTable[i][j][120 + 35] = 1;
            break;
          }
        }
        //Initialize winning hands of positions above chessboard counter diagonal.
        if((i + j) < 9) {
          switch(i + j) {
          case 4:
            playerWinTable[i][j][156] = 1;
            computerWinTable[i][j][156] = 1;
            break;
          case 5:
            if(i <= 4) {
              for(int counter = 1; counter <= Math.min(i + 1, 2); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= j; counter++) {
                playerWinTable[i][j][156 + 2 - counter] = 1;
                computerWinTable[i][j][156 + 2 - counter] = 1;
              }
            }
            break;
          case 6:
            if(i <= 4) {
              for(int counter = 3; counter <= Math.min(i + 3, 5); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= j; counter++) {
                playerWinTable[i][j][156 + 5 - counter] = 1;
                computerWinTable[i][j][156 + 5 - counter] = 1;
              }
            }
            break;
          case 7:
            if(i <= 4) {
              for(int counter = 6; counter <= Math.min(i + 6, 9); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= j; counter++) {
                playerWinTable[i][j][156 + 9 - counter] = 1;
                computerWinTable[i][j][156 + 9 - counter] = 1;
              }
            }
            break;
          case 8:
            if(i <= 4) {
              for(int counter = 10; counter <= Math.min(i + 10, 14); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= j; counter++) {
                playerWinTable[i][j][156 + 14 - counter] = 1;
                computerWinTable[i][j][156 + 14 - counter] = 1;
              }
            }
            break;  
          }
        //Initialize winning hands of chessboard counter diagonal.
        } else if((i + j) == 9){
          if(i <= 4) {
            for(int counter = 0; counter <= i; counter++) {
              playerWinTable[i][j][156 + 15 + counter] = 1;
              computerWinTable[i][j][156 + 15 + counter] = 1;
            }
          } else {
            for(int counter = 0; counter <= j; counter++) {
              playerWinTable[i][j][156 + 15 + 5 - counter] = 1;
              computerWinTable[i][j][156 + 15 + 5 - counter] = 1;
            }
          }
        //Initialize winning hands of positions below chessboard counter diagonal.
        } else {
          switch(i + j) {
          case 10:
            if(j > 4) {
              for(int counter = 21; counter <= Math.min(9 - j + 21, 25); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][156 + 25 - counter] = 1;
                computerWinTable[i][j][156 + 25 - counter] = 1;
              }
            }
            break;
          case 11:
            if(j > 4) {
              for(int counter = 26; counter <= Math.min(9 - j + 26, 29); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][156 + 29 - counter] = 1;
                computerWinTable[i][j][156 + 29 - counter] = 1;
              }
            }
            break;
          case 12:
            if(j > 4) {
              for(int counter = 30; counter <= Math.min(9 - j + 30, 32); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][156 + 32 - counter] = 1;
                computerWinTable[i][j][156 + 32 - counter] = 1;
              }
            }
            break;
          case 13:
            if(j > 4) {
              for(int counter = 33; counter <= Math.min(9 - j + 33, 34); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              for(int counter = 0; counter <= 9 - i; counter++) {
                playerWinTable[i][j][156 + 34 - counter] = 1;
                computerWinTable[i][j][156 + 34 - counter] = 1;
              }
            }
            break;
          case 14:
            playerWinTable[i][j][156 + 35] = 1;
            computerWinTable[i][j][156 + 35] = 1;
            break;
          }
        }
      }
    }
  }

  protected void computerMove() {
    Tuple computerNextMove;
    synchronized(this) {
      computerNextMove = findComputerNextMove();
      computerMoveAndUpdate(computerNextMove.getX(), computerNextMove.getY());
    }
    gridPanel.addComputerChess(computerNextMove.getX(), computerNextMove.getY());    
  }

  protected synchronized Tuple findComputerNextMove() {
    Tuple computerNextMove;
    calculateCurrentScore();
    Tuple playerMaxPos = getMaxPosition(playerScore);
    Tuple computerMaxPos = getMaxPosition(computerScore);
    int playerMax = playerScore[playerMaxPos.getX()][playerMaxPos.getY()];
    int computerMax = computerScore[computerMaxPos.getX()][computerMaxPos.getY()];
    //Defend.
    if(playerMax >= computerMax && playerMax >= 100) {
      // 2 represents computer's chess.
      computerNextMove = playerMaxPos;
    //Offend.
    } else {
      computerNextMove = computerMaxPos;
    }
    return computerNextMove;
  }

  protected synchronized void calculateCurrentScore() {
    for(i = 0; i < 10; i++) {
      for(j = 0; j < 10; j++) {
        playerScore[i][j] = 0;
        computerScore[i][j] = 0;
        if(chessboard[i][j] == 0) {
          for(k = 0; k < 192; k++) {
            if(playerWinTable[i][j][k] == 1) {
              switch(winhandChessCount[0][k]) {
              case 1: playerScore[i][j] += 5; break;
              case 2: playerScore[i][j] += 50; break;
              case 3: playerScore[i][j] += 100; break;
              case 4: playerScore[i][j] += 400; break;
              }
            }
            if(computerWinTable[i][j][k] == 1) {
              switch(winhandChessCount[1][k]) {
              case 1: computerScore[i][j] += 5; break;
              case 2: computerScore[i][j] += 50; break;
              case 3: computerScore[i][j] += 100; break;
              case 4: computerScore[i][j] += 400; break;
              }
            }
          }
        }
      }
    }
  }

  protected synchronized void computerMoveAndUpdate(int x, int y) {
    chessboard[x][y] = 2;
    //Update the win tables and chess counts.
    computerChessCount++;
    if(checkTie()) {
      setGameOver(true);
      setWinner("Tie game!");
      setComputerTurn(false);
      setPlayerTurn(false);
      return;
    }
    setComputerTurn(false);
    setPlayerTurn(true);
    computerLastMove = new Tuple(x, y);

    // Update the win tables and winning hand chess counts after computer moved.
    for(k = 0; k < 192; k++) {
      // Store the status before update for future potential player regret.
      tempComputerWinTable2[k] = computerWinTable[x][y][k];
      // Update the status to reflect the new computer move.
      if(computerWinTable[x][y][k] == 1 && winhandChessCount[1][k] != 7) {
        winhandChessCount[1][k]++;
        if(winhandChessCount[1][k] == 5) {
          setGameOver(true);
          setWinner("Computer");
          setComputerTurn(false);
          setPlayerTurn(false);
        }
      }
      // Store the status before update for future potential player regret.
      tempPlayerWinTable2[k] = playerWinTable[x][y][k];
      // Update the status to reflect the new computer move.
      if(playerWinTable[x][y][k] == 1) {
        //Player has lost this position on chessboard.
        playerWinTable[x][y][k] = 0;
        //Set the chess count to 7 to indicate that player won't be able
        //to win with this winning hand.
        winhandChessCount[0][k] = 7;
      }
    }
  }
  protected synchronized void setPlayerTurn(boolean value) {
    playerTurn = value;
  }

  protected synchronized boolean isPlayerTurn() {
    return playerTurn;
  }

  protected synchronized void setComputerTurn(boolean value) {
    computerTurn = value;
  }

  protected synchronized boolean isComputerTurn() {
    return computerTurn;
  }

  protected synchronized void setGameOver(boolean value) {
    gameover = value;
  }

  protected synchronized boolean isGameOver() {
    return gameover;
  }

  protected synchronized void setWinner(String winner) {
    this.winner = winner;
  }

  protected synchronized String getWinner() {
    return winner;
  }

  protected synchronized void playerMove(int x, int y) {
    chessboard[x][y] = 1;
    playerChessCount++;
    if(checkTie()) {
      setGameOver(true);
      setWinner("Tie game!");
      setComputerTurn(false);
      setPlayerTurn(false);
      return;
    }
    setPlayerTurn(false);
    setComputerTurn(true);
    playerLastMove = new Tuple(x, y);

    // Update the win tables and winning hand chess counts after player moved.
    for(k = 0; k < 192; k++) {
      // Store the status before update for future potential player regret.
      tempPlayerWinTable1[k] = playerWinTable[x][y][k];
      tempPlayerWinhandChessCount[k] = winhandChessCount[0][k];
      // Update the status to reflect the new player move.
      if(playerWinTable[x][y][k]
         == 1 && winhandChessCount[0][k] != 7) {
        winhandChessCount[0][k]++;
        //System.out.println("Player:" + winhandChessCount[0][k]);
        if(winhandChessCount[0][k] == 5) {
          setGameOver(true);
          setWinner("Player");
          setPlayerTurn(false);
          setComputerTurn(false);
        }
      }
      // Store the status before update for future potential player regret.
      tempComputerWinTable1[k] = computerWinTable[x][y][k];
      tempComputerWinhandChessCount[k] = winhandChessCount[1][k];
      // Update the status to reflect the new player move.
      if(computerWinTable[x][y][k] == 1) {
        // Computer has lost this position on chessboard.
        computerWinTable[x][y][k] =0;
        // Set the chess count to 7 to indicate that computer won't be able
        // to win with this winning hand.
        winhandChessCount[1][k] = 7;
      }
    }
  }

  protected synchronized Tuple getMaxPosition(int[][] scoreArray) {
    List<Tuple> list = new ArrayList<Tuple>();
    int m, n;
    int max = 0;
    for(m = 0; m < ROW; m++) {
      for(n = 0; n < COLUMN; n++) {
        if(max < scoreArray[m][n]) {
          max = scoreArray[m][n];
        }
      }
    }
    
    for(m = 0; m < ROW; m++) {
      for(n = 0; n < COLUMN; n++) {
        if(max == scoreArray[m][n] && chessboard[m][n] == 0) {
          list.add(new Tuple(m, n));
        }
      }
    }

    Collections.shuffle(list);
    return list.get(0);
  }

  protected boolean checkTie() {
    if(playerChessCount + computerChessCount == ROW * COLUMN && !isGameOver()) {
      return true;
    } else {
      return false;
    }
  }

  protected synchronized int getComputerChessCount() {
    return computerChessCount;
  }

  protected synchronized int getPlayerChessCount() {
    return playerChessCount;
  }

  protected void newGame() {
    resetForNewGame();
    gridPanel.addComputerChess(4, 4);
  }

  //Reset all the arrays for a new game.
  protected synchronized void resetForNewGame() {
    initializeChessboardAndScore();
    initializeWinhands();
    initializeWinTables();
    playerChessCount = 0;
    computerChessCount = 0;
    setGameOver(false);
    setWinner("");
    chessboard[4][4] = 2;
    computerMoveAndUpdate(4, 4);
  }

  protected void setGridPanel(GridPanel gridPanel) {
    this.gridPanel = gridPanel;
  }

  protected synchronized void regretPlayerMove() {
    if(playerLastMove != null) {
      setPlayerTurn(false);
      // Regret in reverse order.
      int x2 = computerLastMove.getX();
      int y2 = computerLastMove.getY();
      int x1 = playerLastMove.getX();
      int y1 = playerLastMove.getY();
      chessboard[x2][y2] = 0;
      chessboard[x1][y1] = 0;
      computerChessCount--;
      playerChessCount--;
      setGameOver(false);
      setWinner("");
      // Update the win tables and chess counts for this regret.
      for(int k = 0; k < 192; k++) {
        computerWinTable[x2][y2][k] = tempComputerWinTable2[k];
        playerWinTable[x2][y2][k] = tempPlayerWinTable2[k];
        computerWinTable[x1][y1][k] = tempComputerWinTable1[k];
        playerWinTable[x1][y1][k] = tempPlayerWinTable1[k];
        winhandChessCount[1][k] = tempComputerWinhandChessCount[k];
        winhandChessCount[0][k] = tempPlayerWinhandChessCount[k];
      }
      setPlayerTurn(true);
    }
  }

  protected Tuple getComputerLastMove() {
    return computerLastMove;
  }

  protected Tuple getPlayerLastMove() {
    return playerLastMove;
  }

  protected void initializeWinTables() {
    int counter = 0;
    for(int i = 0; i < ROW; i++) {
      for(int j = 0; j < COLUMN; j++) {
        if(j < 6) {
          for(int k = 0; k < 5; k++) {
            playerWinTable[i][j + k][counter] = 1;
            computerWinTable[i][j + k][counter] = 1;
          }
          counter++;
        }
        if(i < 6) {
          for(int k = 0; k < 5; k++) {
            playerWinTable[i + k][j][counter] = 1;
            computerWinTable[i + k][j][counter] = 1;
          }
          counter++;
        }
        if(i < 6 && j < 6) {
          for(int k = 0; k < 5; k++) {
            playerWinTable[i + k][j + k][counter] = 1;
            computerWinTable[i + k][j + k][counter] = 1;
          }
          counter++;
        }
        if(i > 3 && j < 6) {
          for(int k = 0; k < 5; k++) {
            playerWinTable[i - k][j + k][counter] = 1;
            computerWinTable[i - k][j + k][counter] = 1;
          }
          counter++;
        }
      }
    }
  }

/*
  public static void main(String[] args) {
    Gobang gobang = new Gobang();
    gobang.initializeWinTables();
  }
*/
}

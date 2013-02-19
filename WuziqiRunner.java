package gobang;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WuziqiRunner {
  static final int ROW = 10;
  static final int COLUMN = 10;
  static int playerChess = 0;
  static int computerChess = 0;

  public static void main() {

    int[][] chessboard = new int[ROW][COLUMN];
    int[][] playerScore = new int[ROW][COLUMN];
    int[][] computerScore = new int[ROW][COLUMN];
    int[][][] playerWinTable = new int[ROW][COLUMN][192];
    int[][][] computerWinTable = new int[ROW][COLUMN][192];
    // [0][*] for player, [1][*] for computer
    int[][] chessCount = new int[2][192];

    boolean gameover = false, playerMove = false, computerMove = false;
    boolean tie = false;
    String winner = "";
    int i, j, k = 0;

    //Initialization of the arrays.
    for(i = 0; i < 10; i++) {
      for(j = 0; j < 10; j++) {
        chessboard[i][j] = 0;
        playerScore[i][j] = 0;
        computerScore[i][j] = 0;
      }
    }
    for(i = 0; i < 10; i++) {
      for(j = 0; j < 10; j++) {
        for(k = 0; k < 192; k++) {
          playerWinTable[i][j][k] = 0;
          computerWinTable[i][j][k] = 0;
        }
      }
    }
    for(i = 0; i < 2; i++) {
      for(j = 0; j < 192; j++) {
        chessCount[i][j] = 0;
      }
    }

    //Initialization of the win tables.
    for(i = 0; i < 10; i++) {
      for(j = 0; j < 10; j++) {
        if(i <= 4) {
          for(int counter = 0; counter <= i; counter++) {
            playerWinTable[i][j][6 * j + counter] = 1;
            computerWinTable[i][j][6 * j + counter] = 1;
          }
        } else {
          playerWinTable[i][j][6 * j + 5] = 1;
          computerWinTable[i][j][6 * j + 5] = 1;
        }
        if(j <= 4) {
          for(int counter = 0; counter <= j; counter++) {
            playerWinTable[i][j][6 * i + counter + 60] = 1;
            computerWinTable[i][j][6 * i + counter + 60] = 1;
          }
        } else {
          playerWinTable[i][j][6 * i + 5 + 60] = 1;
          computerWinTable[i][j][6 * i + 5 + 60] = 1;
        }
        //对角线左下方的棋盘获胜组合初始化
        if(i < j) {
          switch(Math.abs(i - 0) + Math.abs(j - 9)) {
          case 4:
            playerWinTable[i][j][120] = 1;
            computerWinTable[i][j][120] = 1;
            break;
          case 5:
            if(i <= 4) {
              for(int counter = 1; counter <= Math.min(i + 1, 2); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 2] = 1;
              computerWinTable[i][j][120 + 2] = 1;
            }
            break;
          case 6:
            if(i <= 4 ) {
              for(int counter = 3; counter <= Math.min(i + 3, 5); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 5] = 1;
              computerWinTable[i][j][120 + 5] = 1;
            }
            break;
          case 7:
            if(i <= 4) {
              for(int counter = 6; counter <= Math.min(i + 6, 9); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 9] = 1;
              computerWinTable[i][j][120 + 9] = 1;
            }
            break;
          case 8:
            if(i <= 4) {
              for(int counter = 10; counter <= Math.min(i + 10, 14); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 14] = 1;
              computerWinTable[i][j][120 + 14] = 1;
            }
            break;
          }
        //棋盘对角线获胜组合初始化
        } else if( i == j) {
          if(i <= 4) {
            for(int counter = 0; counter <= i; counter++) {
              playerWinTable[i][j][120 + 15 + counter] = 1;
              computerWinTable[i][j][120 + 15 + counter] = 1;
            }
          } else {
            playerWinTable[i][j][120 + 15 + 5] = 1;
            computerWinTable[i][j][120 + 15 + 5] = 1;
          }
        //对角线右上方的棋盘获胜组合初始化
        } else {
          switch(Math.abs(i - 0) + Math.abs(j - 9)) {
          case 10:
            if(j <= 4) {
              for(int counter = 21; counter <= Math.min(j + 21, 25); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 25] = 1;
              computerWinTable[i][j][120 + 25] = 1;
            }
            break;
          case 11:
            if(j <= 4) {
              for(int counter = 26; counter <= Math.min(j + 26, 29); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 29] = 1;
              computerWinTable[i][j][120 + 29] = 1;
            }
            break;
          case 12:
            if(j <= 4) {
              for(int counter = 30; counter <= Math.min(j + 30, 32); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 32] = 1;
              computerWinTable[i][j][120 + 32] = 1;
            }
            break;
          case 13:
            if(j <= 4) {
              for(int counter = 33; counter <= Math.min(j + 33, 34); counter++) {
                playerWinTable[i][j][120 + counter] = 1;
                computerWinTable[i][j][120 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][120 + 34] = 1;
              computerWinTable[i][j][120 + 34] = 1;
            }
            break;
          case 14:
            playerWinTable[i][j][120 + 35] = 1;
            computerWinTable[i][j][120 + 35] = 1;
            break;
          }
        }
        //反对角线左上方棋盘获胜组合初始化
        if((i + j) < 9) {
          switch(i + j) {
          case 4:
            playerWinTable[i][j][156] = 1;
            computerWinTable[i][j][156] = 1;
            break;
          case 5:
            if(j <= 4) {
              for(int counter = 1; counter <= Math.min(j + 1, 2); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 2] = 1;
              computerWinTable[i][j][156 + 2] = 1;
            }
            break;
          case 6:
            if(j <= 4) {
              for(int counter = 3; counter <= Math.min(j + 3, 5); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 5] = 1;
              computerWinTable[i][j][156 + 5] = 1;
            }
            break;
          case 7:
            if(j <= 4) {
              for(int counter = 6; counter <= Math.min(j + 6, 9); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 9] = 1;
              computerWinTable[i][j][156 + 9] = 1;
            }
            break;
          case 8:
            if(j <= 4) {
              for(int counter = 10; counter <= Math.min(j + 10, 14); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 14] = 1;
              computerWinTable[i][j][156 + 14] = 1;
            }
            break;  
          }
        //棋盘反对角线获胜组合初始化
        } else if((i + j) == 9){
          if(j <= 4) {
            for(int counter = 0; counter <= j; counter++) {
              playerWinTable[i][j][156 + 15 + counter] = 1;
              computerWinTable[i][j][156 + 15 + counter] = 1;
            }
          } else {
            playerWinTable[i][j][156 + 15 + 5] = 1;
            computerWinTable[i][j][156 + 15 + 5] = 1;
          }
        //棋盘反对角线右下方棋盘获胜组合初始化
        } else {
          switch(i + j) {
          case 10:
            if(i > 4) {
              for(int counter = 21; counter <= Math.min(9 - i + 21, 25); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 25] = 1;
              computerWinTable[i][j][156 + 25] = 1;
            }
            break;
          case 11:
            if(i > 4) {
              for(int counter = 26; counter <= Math.min(9 - i + 26, 29); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 29] = 1;
              computerWinTable[i][j][156 + 29] = 1;
            }
            break;
          case 12:
            if(i > 4) {
              for(int counter = 30; counter <= Math.min(9 - i + 30, 32); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 32] = 1;
              computerWinTable[i][j][156 + 32] = 1;
            }
            break;
          case 13:
            if(i > 4) {
              for(int counter = 33; counter <= Math.min(9 - i + 33, 34); counter++) {
                playerWinTable[i][j][156 + counter] = 1;
                computerWinTable[i][j][156 + counter] = 1;
              }
            } else {
              playerWinTable[i][j][156 + 34] = 1;
              computerWinTable[i][j][156 + 34] = 1;
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

    // Let the game begin...
    // Player's chess is presented by 1, and computer by 2.
    chessboard[4][4] = 1;
    playerMove = false;
    computerMove = true;
    gameover = false;
    tie = false;
    while(!gameover) {
      if(computerMove) {
        // Loop the chessboard to calculate the scores for computer and player.
        for(i = 0; i < 10; i++) {
          for(j = 0; j < 10; j++) {
            playerScore[i][j]= 0;
            computerScore[i][j] = 0;
            if(chessboard[i][j] == 0) {
              for(k = 0; k < 192; k++) {
                if(playerWinTable[i][j][k] == 1) {
                  switch(chessCount[0][k]) {
                  case 1: playerScore[i][j] += 5;
                  case 2: playerScore[i][j] += 50;
                  case 3: playerScore[i][j] += 100;
                  case 4: playerScore[i][j] += 400;
                  }
                }
                if(computerWinTable[i][j][k] == 1) {
                  switch(chessCount[1][k]) {
                  case 1: computerScore[i][j] += 5;
                  case 2: computerScore[i][j] += 50;
                  case 3: computerScore[i][j] += 100;
                  case 4: computerScore[i][j] += 400;
                  }
                }
              }
            }
          }
        }

        Tuple playerMaxPos = getMaxPosition(playerScore);
        Tuple computerMaxPos = getMaxPosition(computerScore);
        Tuple moveTaken;
        int playerMax = playerScore[playerMaxPos.getX()]
                                   [playerMaxPos.getY()];
        int computerMax = computerScore[computerMaxPos.getX()]
                                       [computerMaxPos.getY()];
        // Defend.        
        if(playerMax >= computerMax && playerMax >= 100) {
          chessboard[playerMaxPos.getX()][playerMaxPos.getY()] = 2;
          moveTaken = playerMaxPos;
        // Offend.
        } else {
          chessboard[computerMaxPos.getX()][computerMaxPos.getY()] = 2;
          moveTaken = computerMaxPos;
        }
        computerChess++;

        // Update the win tables and chess counts.
        for(k = 0; k < 192; k++) {
          if(computerWinTable[moveTaken.getX()][moveTaken.getY()][k] == 1
             && chessCount[1][k] != 7) {
            chessCount[1][k]++;
            if(chessCount[1][k] == 5) {
              gameover = true;
              winner = "Computer";
            }
          }
          if(playerWinTable[moveTaken.getX()][moveTaken.getY()][k] == 1) {
            // Player has lost this position on chessboard.
            playerWinTable[moveTaken.getX()][moveTaken.getY()][k] = 0;
            // Set the chess count to 7 to indicate that player won't be able
            // to win with this winning hand.
            chessCount[0][k] = 7;
          }
        }
      } else {
        Tuple playerMoveTaken = getPlayerMove();
        playerChess++;
        // Update the win tables and chess counts.
        for(k = 0; k < 192; k++) {
          if(playerWinTable[playerMoveTaken.getX()][playerMoveTaken.getY()][k]
             == 1 && chessCount[0][k] != 7) {
            chessCount[0][k]++;
            if(chessCount[0][k] == 5) {
              gameover = true;
              winner = "Player";
            }
          }
          if(computerWinTable[playerMoveTaken.getX()][playerMoveTaken.getY()][k] == 1) {
            // Computer has lost this position on chessboard.
            computerWinTable[playerMoveTaken.getX()][playerMoveTaken.getY()][k] =0;
            // Set the chess count to 7 to indicate that computer won't be able
            // to win with this winning hand.
            chessCount[1][k] = 7;
          }
        }
      }
      tie = checkTie();
      if(tie) {
        gameover = true;
        winner = "Tie!";
      }
    }
  }

  public static Tuple getMaxPosition(int[][] scoreArray) {
    List<Tuple> list = new ArrayList<Tuple>(); 
    int i,j;
    int max = 0;
    for(i = 0; i < 10; i++) {
      for(j = 0; j < 10; j++) {
        if(max < scoreArray[i][j]) {
          max = scoreArray[i][j];
        }
      }
    }

    for(i = 0; i < 10; i++) {
      for(j = 0; j < 10; j++) {
        if(max == scoreArray[i][j]) {
          list.add(new Tuple(i, j));
        }
      }
    }

    Collections.shuffle(list);
    return list.get(0);
  }
  
  public static Tuple getPlayerMove() {
    int x, y;
    // TODO: Update these two lines to get the player move.
    x = 0;
    y = 0;
    return new Tuple(x, y);
  }
  
  public static boolean checkTie() {
    if(playerChess == computerChess && playerChess == (ROW * COLUMN / 2)) {
      return true;
    } else {
      return false;
    }
  }
}

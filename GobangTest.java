package gobang;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class GobangTest {
  private Gobang gobang;
  int expectedChessboard[][];
  int expectedPlayerScore[][];
  int expectedComputerScore[][];
  int expectedPlayerWinTable[][][];
  int expectedComputerWinTable[][][];
  int expectedWinhandChessCount[][];
  int expectedComputerChessCount;
  int expectedPlayerChessCount;
  @Before
  public void setUp() {
    gobang = new Gobang();
    expectedChessboard = new int[gobang.ROW][gobang.COLUMN];
    expectedPlayerScore = new int[gobang.ROW][gobang.COLUMN];
    expectedComputerScore = new int[gobang.ROW][gobang.COLUMN];
    expectedPlayerWinTable = new int[gobang.ROW][gobang.COLUMN][gobang.WINHANDS];
    expectedComputerWinTable = new int[gobang.ROW][gobang.COLUMN][gobang.WINHANDS];
    expectedWinhandChessCount = new int[2][gobang.WINHANDS];
    expectedComputerChessCount = 0;
    expectedPlayerChessCount = 0;
    
    for(int i = 0; i < gobang.ROW; i++) {
      for(int j = 0; j < gobang.COLUMN; j++) {
        expectedChessboard[i][j] = 0;
        expectedPlayerScore[i][j] = 0;
        expectedComputerScore[i][j] = 0;
        for(int k = 0; k < gobang.WINHANDS; k++) {
          expectedPlayerWinTable[i][j][k] = 0;
          expectedComputerWinTable[i][j][k] = 0;
        }
      }
    }

    for(int i = 0; i < 2; i++) {
      for(int j = 0; j < gobang.WINHANDS; j++) {
        expectedWinhandChessCount[i][j] = 0;
      }
    }
  }

  @Test
  public void testInitializeChessboardAndScore() {
    gobang.initializeChessboardAndScore();
    assertArrayEquals(expectedChessboard, gobang.chessboard);
    assertArrayEquals(expectedPlayerScore, gobang.playerScore);
    assertArrayEquals(expectedComputerScore, gobang.computerScore);
    assertArrayEquals(expectedPlayerWinTable, gobang.playerWinTable);
    assertArrayEquals(expectedComputerWinTable, gobang.computerWinTable);
  }

  @Test
  public void testInitializeWinhands() {
    assertArrayEquals(expectedWinhandChessCount, gobang.winhandChessCount);
  }

  @Test
  public void testSetPlayerTurn() {
    gobang.setPlayerTurn(true);
    assertTrue(gobang.isPlayerTurn());
  }

  @Test
  public void testIsPlayerTurn() {
    gobang.setPlayerTurn(false);

    assertFalse(gobang.isPlayerTurn());
  }

  @Test
  public void testSetComputerTurn() {
    gobang.setComputerTurn(true);
    assertTrue(gobang.isComputerTurn());
  }

  @Test
  public void testIsComputerTurn() {
    gobang.setComputerTurn(false);
    assertFalse(gobang.isComputerTurn());
  }

  @Test
  public void testIsGameOver() {
    gobang.setGameOver(true);
    assertTrue(gobang.isGameOver());
  }

  @Test
  public void testSetWinner() {
    gobang.setWinner(null);
    assertNull(gobang.getWinner());

    gobang.setWinner("player");
    assertEquals("player", gobang.getWinner());
  }

  @Test
  public void testGetWinner() {
    gobang.setWinner(null);
    assertNull(gobang.getWinner());
    gobang.setWinner("computer");
    assertEquals("computer", gobang.getWinner());
  }

  @Test
  public void testResetForNewGame() {
    expectedPlayerWinTable = gobang.playerWinTable;
    expectedComputerWinTable = gobang.computerWinTable;
    gobang.resetForNewGame();
    assertFalse(gobang.isGameOver());
    assertFalse(gobang.isComputerTurn());
    assertTrue(gobang.isPlayerTurn());
    assertEquals("", gobang.getWinner());

    expectedComputerChessCount = 1;
    assertEquals(expectedComputerChessCount, gobang.getComputerChessCount());
    assertEquals(expectedPlayerChessCount, gobang.getPlayerChessCount());

    expectedChessboard[4][4] = 2;
    assertArrayEquals(expectedChessboard, gobang.chessboard);

    for(int i = 0; i < 192; i++) {
      expectedPlayerWinTable[4][4][i] = 0;
    }
    assertArrayEquals(expectedPlayerWinTable, gobang.playerWinTable);
  }

/*
  @Test
  public void testComputerMoveAndUpdate() {
    expectedPlayerWinTable = gobang.playerWinTable;
    expectedComputerWinTable = gobang.computerWinTable;
    gobang.chessboard[3][2] = 2;
    gobang.computerMoveAndUpdate(3, 2);
    assertFalse(gobang.isGameOver());
    assertFalse(gobang.isComputerTurn());
    assertTrue(gobang.isPlayerTurn());
    expectedComputerChessCount = 1;
    assertEquals(expectedComputerChessCount, gobang.getComputerChessCount());
    assertEquals(expectedPlayerChessCount, gobang.getPlayerChessCount());
    assertEquals("", gobang.getWinner());

    for(int i = 0; i < 3; i++) {
      expectedWinhandChessCount[1][18 + i] = 1;
      expectedWinhandChessCount[0][18 + i] = 7;
      expectedWinhandChessCount[1][130 + i] = 1;
      expectedWinhandChessCount[0][130 + i] = 7;
      expectedPlayerWinTable[3][2][18 + i] = 0;
    }
    for(int i = 0; i < 4; i++) {
      expectedWinhandChessCount[1][72 + i] = 1;
      expectedWinhandChessCount[0][72 + i] = 7;
      expectedPlayerWinTable[3][2][72 + i] = 0;
    }
    for(int i = 0; i < 2; i++) {
      expectedWinhandChessCount[1][157 + i] = 1;
      expectedWinhandChessCount[0][157 + i] = 7;
      expectedPlayerWinTable[3][2][157 + i] = 0;
    }
    assertArrayEquals(expectedWinhandChessCount, gobang.winhandChessCount);
    assertArrayEquals(expectedComputerWinTable, gobang.computerWinTable);
    assertArrayEquals(expectedPlayerWinTable, gobang.playerWinTable);

    // Test for computer winning condition.
    gobang.resetForNewGame();
    for(int i = 0; i < 4; i++) {
      gobang.chessboard[3][i] = 2;
    }
    gobang.winhandChessCount[1][18] = 4;
    // Add the 5th chess in a row for computer to make it win.
    gobang.chessboard[3][4] = 2;
    gobang.computerMoveAndUpdate(3, 4);
    assertTrue(gobang.isGameOver());
    assertFalse(gobang.isComputerTurn());
    assertFalse(gobang.isPlayerTurn());
    assertEquals("Computer", gobang.getWinner());

    // Test for tie game condition.
    gobang.resetForNewGame();
    gobang.computerChessCount = 49;
    gobang.playerChessCount = 50;
    gobang.computerMoveAndUpdate(5, 5);
    assertTrue(gobang.isGameOver());
    assertFalse(gobang.isComputerTurn());
    assertFalse(gobang.isPlayerTurn());
    assertEquals("Tie game!", gobang.getWinner());
  }

  @Test
  public void testPlayerMove() {
    expectedPlayerWinTable = gobang.playerWinTable;
    expectedComputerWinTable = gobang.computerWinTable;
    gobang.playerMove(3, 2);
    assertFalse(gobang.isGameOver());
    assertTrue(gobang.isComputerTurn());
    assertFalse(gobang.isPlayerTurn());
    expectedPlayerChessCount = 1;
    assertEquals(expectedComputerChessCount, gobang.getComputerChessCount());
    assertEquals(expectedPlayerChessCount, gobang.getPlayerChessCount());
    assertEquals("", gobang.getWinner());

    for(int i = 0; i < 3; i++) {
      expectedWinhandChessCount[0][18 + i] = 1;
      expectedWinhandChessCount[1][18 + i] = 7;
      expectedWinhandChessCount[0][130 + i] = 1;
      expectedWinhandChessCount[1][130 + i] = 7;
      expectedComputerWinTable[3][2][18 + i] = 0;
    }
    for(int i = 0; i < 4; i++) {
      expectedWinhandChessCount[0][72 + i] = 1;
      expectedWinhandChessCount[1][72 + i] = 7;
      expectedComputerWinTable[3][2][72 + i] = 0;
    }
    for(int i = 0; i < 2; i++) {
      expectedWinhandChessCount[0][157 + i] = 1;
      expectedWinhandChessCount[1][157 + i] = 7;
      expectedComputerWinTable[3][2][157 + i] = 0;
    }
    assertArrayEquals(expectedWinhandChessCount, gobang.winhandChessCount);
    assertArrayEquals(expectedComputerWinTable, gobang.computerWinTable);
    assertArrayEquals(expectedPlayerWinTable, gobang.playerWinTable);

    // Test for player winning condition.
    gobang.resetForNewGame();
    for(int i = 0; i < 4; i++) {
      gobang.chessboard[3][i] = 1;
    }
    gobang.winhandChessCount[0][18] = 4;
    // Add the 5th chess in a row for computer to make it win.
    gobang.chessboard[3][4] = 1;
    gobang.playerMove(3, 4);
    assertTrue(gobang.isGameOver());
    assertFalse(gobang.isComputerTurn());
    assertFalse(gobang.isPlayerTurn());
    assertEquals("Player", gobang.getWinner());

    // Test for tie game condition.
    gobang.resetForNewGame();
    gobang.computerChessCount = 50;
    gobang.playerChessCount = 49;
    gobang.playerMove(5, 5);
    assertTrue(gobang.isGameOver());
    assertFalse(gobang.isComputerTurn());
    assertFalse(gobang.isPlayerTurn());
    assertEquals("Tie game!", gobang.getWinner());
  }
*/

  @Test
  public void testCalculateCurrentScore() {
    gobang.computerMoveAndUpdate(3, 2);
    gobang.playerMove(5, 5);
    gobang.calculateCurrentScore();

    expectedComputerScore[0][2] = 5;
    expectedComputerScore[0][5] = 5;
    expectedComputerScore[1][0] = 5;
    expectedComputerScore[1][2] = 10;
    expectedComputerScore[1][4] = 10;
    expectedComputerScore[2][1] = 10;
    expectedComputerScore[2][2] = 15;
    expectedComputerScore[2][3] = 10;
    expectedComputerScore[3][0] = 5;
    expectedComputerScore[3][1] = 10;
    expectedComputerScore[3][3] = 15;
    expectedComputerScore[3][4] = 15;
    expectedComputerScore[3][5] = 10;
    expectedComputerScore[3][6] = 5;
    expectedComputerScore[4][1] = 10;
    expectedComputerScore[4][2] = 20;
    expectedComputerScore[4][3] = 15;
    expectedComputerScore[5][0] = 5;
    expectedComputerScore[5][2] = 15;
    expectedComputerScore[5][4] = 15;
    expectedComputerScore[6][2] = 10;
    expectedComputerScore[6][5] = 10;
    expectedComputerScore[7][2] = 5;
    expectedComputerScore[7][6] = 5;

    expectedPlayerScore[1][1] = 5;
    expectedPlayerScore[1][5] = 5;
    expectedPlayerScore[1][9] = 5;
    expectedPlayerScore[2][2] = 10;
    expectedPlayerScore[2][5] = 10;
    expectedPlayerScore[2][8] = 10;
    expectedPlayerScore[3][3] = 15;
    expectedPlayerScore[3][5] = 15;
    expectedPlayerScore[3][7] = 15;
    expectedPlayerScore[4][4] = 20;
    expectedPlayerScore[4][5] = 20;
    expectedPlayerScore[4][6] = 20;
    expectedPlayerScore[5][1] = 5;
    expectedPlayerScore[5][2] = 10;
    expectedPlayerScore[5][3] = 15;
    expectedPlayerScore[5][4] = 20;
    expectedPlayerScore[5][6] = 20;
    expectedPlayerScore[5][7] = 15;
    expectedPlayerScore[5][8] = 10;
    expectedPlayerScore[5][9] = 5;
    expectedPlayerScore[6][4] = 20;
    expectedPlayerScore[6][5] = 20;
    expectedPlayerScore[6][6] = 20;
    expectedPlayerScore[7][3] = 15;
    expectedPlayerScore[7][5] = 15;
    expectedPlayerScore[7][7] = 15;
    expectedPlayerScore[8][2] = 10;
    expectedPlayerScore[8][5] = 10;
    expectedPlayerScore[8][8] = 10;
    expectedPlayerScore[9][1] = 5;
    expectedPlayerScore[9][5] = 5;
    expectedPlayerScore[9][9] = 5;

    assertArrayEquals(expectedComputerScore, gobang.computerScore);
    assertArrayEquals(expectedPlayerScore, gobang.playerScore);
  }

  @Test
  public void testFindComputerNextMove() {
    gobang.computerMoveAndUpdate(3, 2);
    gobang.playerMove(5, 5);
    Tuple computerNextMove = gobang.findComputerNextMove();
    Tuple expectedComputerNextMove = new Tuple(4, 2);
    assertEquals(expectedComputerNextMove, computerNextMove);

    gobang.computerMoveAndUpdate(3, 1);
    gobang.playerMove(4, 4);
    gobang.computerMoveAndUpdate(2, 1);
    gobang.playerMove(3, 3);
    computerNextMove = gobang.findComputerNextMove();
    expectedComputerNextMove = new Tuple(6, 6);
    assertEquals(expectedComputerNextMove, gobang.findComputerNextMove());
  }

  @Test
  public void testCheckTie() {
    gobang.playerChessCount = 50;
    gobang.computerChessCount = 50;
    assertTrue(gobang.checkTie());

    gobang.playerChessCount = 30;
    gobang.computerChessCount = 20;
    assertFalse(gobang.checkTie());
  }
}

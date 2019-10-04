package test;

import model.*;
import model.game.Game;
import model.game.Initializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
  private Initializer initializer;
  private Game game;
  private Game g2;
  private Piece[][] board;

  public GameTest() {
    initializer = new Initializer();
    board = initializer.createBoard();
    game = new Game(board, 2);
    g2 = new Game(initializer.newGame(false), 1);
    Game g3 = new Game(false);
  }

  @Test
  public void isCheckmate() {
    board[5][5] = new King(1);
    board[7][5] = new King(2);
    board[5][7] = new Knight(1);
    board[3][1] = new Bishop(1);
    board[7][0] = new Rook(1);
    assertTrue(game.isCheckmate(2));
    board[4][2] = new Queen(2);
    assertFalse(game.isCheckmate(2));
    board[4][2] = new Knight(2);
    assertTrue(game.isCheckmate(2));
    board[4][0] = new Piece();
    board[5][5] = new King(1);
    assertFalse(g2.isCheckmate(1));
  }

  @Test
  public void isStalemate() {
    board[5][0] = new King(2);
    board[5][1] = new Pawn(1);
    board[5][2] = new King(1);
    assertTrue(game.isStalemate(2));
    Piece[][] board2 = initializer.createBoard();
    Game game2 = new Game(board2, 2);
    board2[0][7] = new King(2);
    board2[1][7] = new Bishop(2);
    board2[7][7] = new Rook(1);
    board2[1][5] = new King(1);
    assertTrue(game2.isStalemate(2));
  }

  @Test
  public void isInCheck() {
    board[4][4] = new King(1);
    board[5][5] = new Pawn(2);
    assertTrue(game.isInCheck(2, 4, 4));
  }

  @Test
  public void findKings() {
    board[5][2] = new King(1);
    assertArrayEquals(game.findKings(1), new int[] {5, 2});
  }
}

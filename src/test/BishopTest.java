package test;

import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Bishop;
import model.Piece;
import model.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
  private Initializer initializer;
  private Piece[][] board;

  public BishopTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][7] = new Bishop(1);
  }

  @Test
  public void checkValidMove() {
    assertTrue(this.board[4][7].checkValidMove(4, 7, 1, 4, this.board));
  }

  @org.junit.Test
  public void checkInvalidMove() {
    // off the board
    assertFalse(this.board[4][7].checkValidMove(4, 7, -14, 17, this.board));
    assertFalse(this.board[4][7].checkValidMove(4, 7, 14, 17, this.board));
    // move not by rule
    assertFalse(this.board[7][7].checkValidMove(4, 7, 5, 6, this.board));
    // occupied by same side piece
    this.board[5][6] = new Queen(1);
    assertFalse(this.board[7][7].checkValidMove(4, 7, 5, 6, this.board));
  }
}

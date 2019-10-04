package test;

import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Piece;
import model.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {
  private Initializer initializer;
  private Piece[][] board;

  public QueenTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][4] = new Queen(1);
  }

  @Test
  public void checkValidMove() {
    assertTrue(this.board[4][4].checkValidMove(4, 4, 4, 1, this.board));
    assertTrue(this.board[4][4].checkValidMove(4, 4, 7, 7, this.board));
  }

  @Test
  public void checkInvalidMove() {
    // off the board
    assertFalse(this.board[4][4].checkValidMove(4, 4, -1, 7, this.board));
    assertFalse(this.board[4][4].checkValidMove(4, 4, 8, 7, this.board));
    // move not by rule
    assertFalse(this.board[4][4].checkValidMove(4, 4, 5, 1, this.board));
    // occupied by same side piece
    this.board[5][5] = new Queen(1);
    assertFalse(this.board[4][4].checkValidMove(4, 4, 5, 5, this.board));
  }
}

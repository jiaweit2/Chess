package test;

import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Piece;
import model.Queen;
import model.Rook;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {
  private Initializer initializer;
  private Piece[][] board;

  public RookTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][7] = new Rook(1);
  }

  @Test
  public void checkValidMove() {
    assertTrue(this.board[4][7].checkValidMove(4, 7, 4, 1, this.board));
  }

  @Test
  public void checkInvalidMove() {
    // off the board
    assertFalse(this.board[4][7].checkValidMove(4, 7, -1, 7, this.board));
    assertFalse(this.board[4][7].checkValidMove(4, 7, 8, 7, this.board));
    // move not by rule
    assertFalse(this.board[4][7].checkValidMove(4, 7, 5, 1, this.board));
    // occupied by same side piece
    this.board[4][5] = new Queen(1);
    assertFalse(this.board[4][7].checkValidMove(4, 7, 4, 5, this.board));
  }
}

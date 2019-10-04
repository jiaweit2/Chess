package test;

import model.game.Initializer;
import org.junit.Test;
import model.King;
import model.Piece;
import model.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
  private Initializer initializer;
  private Piece[][] board;

  public KingTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[7][7] = new King(1);
  }

  @Test
  public void checkValidMove() {
    assertTrue(this.board[7][7].checkValidMove(7, 7, 6, 7, this.board));
  }

  @Test
  public void checkInvalidMove() {
    // off the board
    assertFalse(this.board[7][7].checkValidMove(7, 7, 8, 7, this.board));
    // move not by rule
    assertFalse(this.board[7][7].checkValidMove(7, 7, 5, 6, this.board));
    // occupied by same side piece
    this.board[6][7] = new Queen(1);
    assertFalse(this.board[7][7].checkValidMove(7, 7, 6, 7, this.board));
    // king threat path
    this.board[7][7].threatPath(7, 7, 7, 6, this.board);
  }
}

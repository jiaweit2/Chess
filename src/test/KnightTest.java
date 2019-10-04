package test;

import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Knight;
import model.Piece;
import model.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
  private Initializer initializer;
  private Piece[][] board;

  public KnightTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][4] = new Knight(1);
  }

  @Test
  public void checkValidMove() {
    assertTrue(this.board[4][4].checkValidMove(4, 4, 5, 6, this.board));
    // test threat path
    this.board[4][4].threatPath(4, 4, 5, 6, this.board);
  }

  @Test
  public void checkInvalidMove() {
    // off the board
    assertFalse(this.board[4][4].checkValidMove(4, 4, -1, 7, this.board));
    assertFalse(this.board[4][4].checkValidMove(4, 4, 8, 7, this.board));
    // move not by rule
    assertFalse(this.board[4][4].checkValidMove(4, 4, 5, 1, this.board));
    // occupied by same side piece
    this.board[5][6] = new Queen(1);
    assertFalse(this.board[4][4].checkValidMove(4, 4, 5, 6, this.board));
  }
}

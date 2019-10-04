package test;

import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Pawn;
import model.Piece;
import model.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
  private Initializer initializer;
  private Piece[][] board;

  public PawnTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][4] = new Pawn(1);
    this.board[0][1] = new Pawn(1);
  }

  @Test
  public void checkValidMove() {
    assertTrue(this.board[4][4].checkValidMove(4, 4, 4, 5, this.board));
    assertTrue(this.board[0][1].checkValidMove(0, 1, 0, 3, this.board));
  }

  @Test
  public void checkInvalidMove() {
    // off the board
    assertFalse(this.board[4][4].checkValidMove(4, 4, -2, 7, this.board));
    assertFalse(this.board[4][4].checkValidMove(4, 4, 8, 7, this.board));
    // move not by rule
    assertFalse(this.board[4][4].checkValidMove(4, 4, 5, 1, this.board));
    // occupied by same side piece
    this.board[4][5] = new Queen(1);
    assertFalse(this.board[4][4].checkValidMove(4, 4, 4, 5, this.board));
  }
}

package test;

import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Bus;
import model.Piece;
import model.Queen;

import static org.junit.jupiter.api.Assertions.*;

class BusTest {
  private Initializer initializer;
  private Piece[][] board;

  public BusTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][7] = new Bus(1);
  }

  @Test
  void checkValidMove() {
    // off the board
    assertFalse(this.board[4][7].checkValidMove(4, 7, 11, 7, this.board));
    // not by rule
    assertFalse(this.board[4][7].checkValidMove(4, 7, 1, 1, this.board));
    assertFalse(this.board[4][7].checkValidMove(4, 7, 4, 1, this.board));
    this.board[3][1] = new Queen(1);
    assertTrue(this.board[4][7].checkValidMove(4, 7, 4, 1, this.board));
  }
}

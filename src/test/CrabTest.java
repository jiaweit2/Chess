package test;

import model.game.Game;
import model.game.Initializer;
import org.junit.jupiter.api.Test;
import model.Crab;
import model.King;
import model.Piece;

import static org.junit.jupiter.api.Assertions.*;

class CrabTest {
  private Initializer initializer;
  private Piece[][] board;

  public CrabTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
    this.board[4][7] = new Crab(1);
  }

  @Test
  void checkValidMove() {
    assertTrue(this.board[4][7].checkValidMove(4, 7, 1, 7, this.board));
    assertFalse(this.board[4][7].checkValidMove(4, 7, 1, 4, this.board));
    assertFalse(this.board[4][7].checkValidMove(4, 7, 11, 4, this.board));
    assertFalse(this.board[4][7].checkValidMove(4, 7, 4, 2, this.board));
  }

  @Test
  void move() {
    this.board[0][0] = new King(1);
    Game g = new Game(this.board, 1);
    this.board[3][7] = new Crab(2);
    this.board[3][7].move(3, 7, 5, 7, g);
    this.board[4][7].move(4, 7, 3, 7, g);
    assertTrue(this.board[3][7].checkValidMove(3, 7, 3, 2, this.board));
  }
}

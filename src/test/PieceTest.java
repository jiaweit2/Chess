package test;

import model.*;
import model.game.Game;
import model.game.Initializer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
  private Initializer initializer;
  private Piece[][] board;

  public PieceTest() {
    this.initializer = new Initializer();
    this.board = this.initializer.createBoard();
  }

  @Test
  public void threatPath() {
    board[3][2] = new Rook(1);
    board[1][3] = new Pawn(1);
    board[6][2] = new King(2);

    assertEquals(board[1][3].threatPath(1, 3, 6, 2, board), new HashSet<int[]>());
    Set<List<Integer>> output = new HashSet<List<Integer>>();
    output.add(
        new ArrayList<Integer>() {
          {
            add(3);
            add(2);
          }
        });
    output.add(
        new ArrayList<Integer>() {
          {
            add(4);
            add(2);
          }
        });
    output.add(
        new ArrayList<Integer>() {
          {
            add(5);
            add(2);
          }
        });
    assertEquals(board[3][2].threatPath(3, 2, 6, 2, board), output);
  }

  @Test
  public void move() {
    this.board[5][3] = new King(1);
    this.board[3][2] = new Rook(1);
    this.board[6][2] = new Pawn(2);
    this.board[7][7] = new King(2);
    this.board = this.board[3][2].move(3, 2, 6, 2, new Game(this.board, 1));
    assertEquals(this.board[3][2].getPlayer(), -1);
    assertEquals(this.board[3][2].getType(), Type.EMPTY);
  }
}

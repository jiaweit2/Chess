package model;

import model.game.Game;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Piece extends JButton {
  private Type type;
  private int player;

  /*
     Constructor
  */
  public Piece() {
    this.type = Type.EMPTY;
    this.player = -1;
  }

  public Piece copy(Piece p) {
    return new Piece();
  }

  public Type getType() {
    return this.type;
  }

  public int getPlayer() {
    return this.player;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setPlayer(int player) {
    this.player = player;
  }

  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    return false;
  }

  public Set<List<Integer>> threatPath(
      int startX, int startY, int endX, int endY, Piece[][] board) {
    return new HashSet<List<Integer>>();
  }

  public boolean checkValidMoveBasic(int startX, int startY, int endX, int endY, Piece[][] board) {
    if ((startX == endX && startY == endY) // didn't move
        || (endX < 0 || endX >= 8 || endY < 0 || endY >= 8) // off the board
        || (board[endX][endY].getPlayer()
            == board[startX][startY].getPlayer())) { // occupied by same side's piece
      return false;
    } else {
      return true;
    }
  }

  public Piece[][] move(int startX, int startY, int endX, int endY, Game game) {
    Piece[][] board = game.getBoard();
    int player = board[startX][startY].getPlayer();
    if (player != game.getCurrPlayer()) {
      return board;
    }
    int opponent = player == 1 ? 2 : 1;
    Piece p = board[endX][endY];
    board[endX][endY] = board[startX][startY];
    board[startX][startY] = new Piece();
    int[] king_pos = game.findKings(player);
    if (game.isInCheck(opponent, king_pos[0], king_pos[1])) {
      board[startX][startY] = board[endX][endY];
      board[endX][endY] = p;
      return board;
    }
    return board;
  }
}

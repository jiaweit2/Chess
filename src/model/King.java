package model;

import model.game.Game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class King extends Piece {
  public King(int player) {
    this.setPlayer(player);
    this.setType(Type.KING);
    setIcon(
        new ImageIcon("src/model/game/images/" + (player == 1 ? "White" : "Black") + "/King.png"));
  }

  @Override
  public King copy(Piece p) {
    return new King(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMoveBasic(startX, startY, endX, endY, board)) {
      return false;
    }
    if (Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1) {
      //      int player = board[startX][startY].getPlayer();
      //      Game g = new Game(board, player);
      //      int opponent = player == 1 ? 2 : 1;
      //      if (!g.isInCheck(opponent, endX, endY)) {
      //        return true;
      //      }
      return true;
    }
    return false;
  }

  @Override
  public Set<List<Integer>> threatPath(
      int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMove(startX, startY, endX, endY, board)) {
      return new HashSet<List<Integer>>();
    }
    Set<List<Integer>> path_set = new HashSet<List<Integer>>();
    path_set.add(
        new ArrayList<Integer>() {
          {
            add(startX);
            add(startY);
          }
        });
    return path_set;
  }
}

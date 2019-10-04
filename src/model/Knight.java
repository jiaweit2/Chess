package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knight extends Piece {
  public Knight(int player) {
    this.setPlayer(player);
    this.setType(Type.KNIGHT);
    setIcon(
        new ImageIcon(
            "src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Knight.png"));
  }

  @Override
  public Knight copy(Piece p) {
    return new Knight(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMoveBasic(startX, startY, endX, endY, board)) {
      return false;
    }
    if (Math.abs(startX - endX) == 1) {
      if (Math.abs(startY - endY) == 2) {
        return true;
      }
    }
    if (Math.abs(startY - endY) == 1) {
      if (Math.abs(startX - endX) == 2) {
        return true;
      }
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

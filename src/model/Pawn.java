package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {
  public Pawn(int player) {
    this.setPlayer(player);
    this.setType(Type.PAWN);
    setIcon(
        new ImageIcon("src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Pawn.png"));
  }

  @Override
  public Pawn copy(Piece p) {
    return new Pawn(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMoveBasic(startX, startY, endX, endY, board)) {
      return false;
    }
    // forward
    if (startX == endX && board[endX][endY].getType() == Type.EMPTY) {
      // forward one step
      if (endY - startY == 1) {
        return true;
      }
      // forward two steps
      if (endY - startY == 2
          && startY == 1
          && board[endX][endY - 1].getType() == Type.EMPTY) { // at the starting line
        return true;
      }
    }
    // attempt to capture
    if (Math.abs(startX - endX) == 1
        && endY - startY == 1
        && board[endX][endY].getType() != Type.EMPTY) {
      return true;
    }
    return false;
  }

  @Override
  public Set<List<Integer>> threatPath(
      int startX, int startY, int endX, int endY, Piece[][] board) {
    Set<List<Integer>> path_set = new HashSet<List<Integer>>();
    if (Math.abs(startX - endX) == 1 && startY - endY == 1) {
      path_set.add(
          new ArrayList<Integer>() {
            {
              add(startX);
              add(startY);
            }
          });
    }
    return path_set;
  }
}

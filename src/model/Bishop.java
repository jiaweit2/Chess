package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bishop extends Piece {
  public Bishop(int player) {
    this.setPlayer(player);
    this.setType(Type.BISHOP);
    setIcon(
        new ImageIcon(
            "src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Bishop.png"));
  }

  @Override
  public Bishop copy(Piece p) {
    return new Bishop(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if ((!checkValidMoveBasic(startX, startY, endX, endY, board))
        || (Math.abs(startX - endX) != Math.abs(startY - endY))) {
      return false;
    }
    int incrementX = startX < endX ? 1 : -1;
    int incrementY = startY < endY ? 1 : -1;
    while (true) {
      startX += incrementX;
      startY += incrementY;
      if (startX == endX) {
        break;
      }
      if (board[startX][startY].getType() != Type.EMPTY) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Set<List<Integer>> threatPath(
      int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMove(startX, startY, endX, endY, board)) {
      return new HashSet<List<Integer>>();
    }
    Set<List<Integer>> path_set = new HashSet<List<Integer>>();
    int finalStartX = startX;
    int finalStartY = startY;
    path_set.add(
        new ArrayList<Integer>() {
          {
            add(finalStartX);
            add(finalStartY);
          }
        });
    int incrementX = startX < endX ? 1 : -1;
    int incrementY = startY < endY ? 1 : -1;
    while (true) {
      startX += incrementX;
      startY += incrementY;
      if (startX == endX) {
        break;
      }
      if (board[startX][startY].getType() != Type.EMPTY) {
        return new HashSet<List<Integer>>();
      }
      int finalStartX1 = startX;
      int finalStartY1 = startY;
      path_set.add(
          new ArrayList<Integer>() {
            {
              add(finalStartX1);
              add(finalStartY1);
            }
          });
    }
    return path_set;
  }
}

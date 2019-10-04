package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rook extends Piece {
  public Rook(int player) {
    this.setPlayer(player);
    this.setType(Type.ROOK);
    setIcon(
        new ImageIcon("src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Rook.png"));
  }

  @Override
  public Rook copy(Piece p) {
    return new Rook(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMoveBasic(startX, startY, endX, endY, board)) {
      return false;
    }
    if (startX == endX && startY != endY) {
      while (true) {
        startY += getIncrement(startY, endY);
        if (startY == endY) {
          break;
        }
        if (board[startX][startY].getType() != Type.EMPTY) {
          return false;
        }
      }
      return true;
    } else if (startY == endY && startX != endX) {
      while (true) {
        startX += getIncrement(startX, endX);
        if (startX == endX) {
          break;
        }
        if (board[startX][startY].getType() != Type.EMPTY) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  private int getIncrement(int start, int end) {
    return start < end ? 1 : -1;
  }

  @Override
  public Set<List<Integer>> threatPath(
      int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMove(startX, startY, endX, endY, board)) {
      return new HashSet<List<Integer>>();
    }
    Set<List<Integer>> path_set = new HashSet<List<Integer>>();
    int finalStartX2 = startX;
    int finalStartY2 = startY;
    path_set.add(
        new ArrayList<Integer>() {
          {
            add(finalStartX2);
            add(finalStartY2);
          }
        });
    if (startX == endX && startY != endY) {
      int increment = getIncrement(startY, endY);
      while (true) {
        startY += increment;
        if (startY == endY) {
          break;
        }
        path_set = pathSetCalculation(startX, startY, board[startX][startY], path_set);
        if (path_set.size() == 0) return new HashSet<List<Integer>>();
      }
    } else if (startY == endY && startX != endX) {
      int increment = getIncrement(startX, endX);
      while (true) {
        startX += increment;
        if (startX == endX) {
          break;
        }
        path_set = pathSetCalculation(startX, startY, board[startX][startY], path_set);
        if (path_set.size() == 0) return new HashSet<List<Integer>>();
      }
    }
    return path_set;
  }

  private Set<List<Integer>> pathSetCalculation(
      int startX, int startY, Piece piece, Set<List<Integer>> path_set) {
    if (piece.getType() != Type.EMPTY) {
      return new HashSet<List<Integer>>();
    }
    int finalStartX = startX;
    int finalStartY = startY;
    path_set.add(
        new ArrayList<Integer>() {
          {
            add(finalStartX);
            add(finalStartY);
          }
        });
    return path_set;
  }
}

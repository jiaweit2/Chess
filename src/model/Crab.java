package model;

import model.game.Game;

import javax.swing.*;

public class Crab extends Piece {
  public boolean is_horizontal; // Direction

  public Crab(int player) {
    this.setPlayer(player);
    this.setType(Type.CRAB);
    this.is_horizontal = true;
    setIcon(
        new ImageIcon("src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Crab.png"));
  }

  @Override
  public Crab copy(Piece p) {
    Crab new_crab = new Crab(p.getPlayer());
    new_crab.is_horizontal = ((Crab) p).is_horizontal;
    return new_crab;
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMoveBasic(startX, startY, endX, endY, board)) {
      return false;
    }
    // Crab can only move horizontally or vertically
    if (!is_horizontal && startX == endX && startY != endY) {
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
    } else if (is_horizontal && startY == endY && startX != endX) {
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
  public Piece[][] move(int startX, int startY, int endX, int endY, Game game) {
    Piece[][] board = game.getBoard();
    int player = board[startX][startY].getPlayer();
    if (player != game.getCurrPlayer()) {
      return board;
    }
    int opponent = player == 1 ? 2 : 1;
    // Change direction once capture opponent's piece
    boolean change_direction = false;
    if (board[endX][endY].getPlayer() == opponent) {
      change_direction = true;
    }
    int[] king_pos = game.findKings(player);
    Piece p = board[endX][endY];
    board[endX][endY] = new Piece();
    if (!checkValidMove(startX, startY, endX, endY, board)
        || game.isInCheck(opponent, king_pos[0], king_pos[1])) {
      board[endX][endY] = p;
      return board;
    }
    if (change_direction) {
      this.is_horizontal = !this.is_horizontal;
    }
    board[endX][endY] = board[startX][startY];
    board[startX][startY] = new Piece();
    return board;
  }
}

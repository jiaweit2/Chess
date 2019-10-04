package model;

import javax.swing.*;

public class Bus extends Piece {
  public Bus(int player) {
    this.setPlayer(player);
    this.setType(Type.BUS);
    setIcon(
        new ImageIcon("src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Bus.png"));
  }

  @Override
  public Bus copy(Piece p) {
    return new Bus(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    if (!checkValidMoveBasic(startX, startY, endX, endY, board)) {
      return false;
    }
    // Bus works like Rook, but the destination of the move must be next to at least an another
    // piece of our side.
    Rook r = new Rook(this.getPlayer());
    if (!r.checkValidMove(startX, startY, endX, endY, board)) {
      return false;
    }
    for (int i = endX - 1; i <= endX + 1; i += 1) {
      for (int j = endY - 1; j <= endY + 1; j += 1) {
        if (i < 0 || i > 7 || j < 0 || j > 7) {
          continue;
        }
        if ((i != endX || j != endY) && board[i][j].getPlayer() == this.getPlayer()) {
          return true;
        }
      }
    }
    return false;
  }
}

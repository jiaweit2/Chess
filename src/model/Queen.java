package model;

import javax.swing.*;
import java.util.List;
import java.util.Set;

public class Queen extends Piece {
  public Queen(int player) {
    this.setPlayer(player);
    this.setType(Type.QUEEN);
    setIcon(
        new ImageIcon("src/model/game/images/" + (player == 1 ? "White" : "Black") + "/Queen.png"));
  }

  @Override
  public Queen copy(Piece p) {
    return new Queen(p.getPlayer());
  }

  @Override
  public boolean checkValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
    Rook r = new Rook(-1);
    Bishop b = new Bishop(-1);
    return r.checkValidMove(startX, startY, endX, endY, board)
        || b.checkValidMove(startX, startY, endX, endY, board);
  }

  @Override
  public Set<List<Integer>> threatPath(
      int startX, int startY, int endX, int endY, Piece[][] board) {
    Rook r = new Rook(-1);
    Bishop b = new Bishop(-1);
    Set<List<Integer>> output = r.threatPath(startX, startY, endX, endY, board);
    output.addAll(b.threatPath(startX, startY, endX, endY, board));
    return output;
  }
}

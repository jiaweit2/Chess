package model.game;

import model.*;

public class Initializer {
  public Initializer() {}

  public Piece[][] createBoard() {
    Piece[][] board = new Piece[8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        board[i][j] = new Piece();
      }
    }
    return board;
  }

  public boolean equals(Piece[][] b1, Piece[][] b2) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (b1[i][j].getPlayer() != b2[i][j].getPlayer()
            || b1[i][j].getType() != b2[i][j].getType()) {
          return false;
        }
      }
    }
    return true;
  }

  public Piece[][] copy(Piece[][] board) {
    Piece[][] b = new Piece[8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        b[i][j] = board[i][j].copy(board[i][j]);
      }
    }
    return b;
  }

  public Piece[][] newGame(boolean includes) {
    Piece[][] board = createBoard();
    // set up pawns
    for (int i = 0; i < 8; i++) {
      board[i][1] = new Pawn(1);
      board[i][6] = new Pawn(2);
    }
    // set up rooks
    board[0][0] = new Rook(1);
    board[7][0] = new Rook(1);
    board[0][7] = new Rook(2);
    board[7][7] = new Rook(2);
    // set up knights
    board[1][0] = new Knight(1);
    board[6][0] = new Knight(1);
    board[1][7] = new Knight(2);
    board[6][7] = new Knight(2);
    // set up bishops
    board[2][0] = new Bishop(1);
    board[5][0] = new Bishop(1);
    board[2][7] = new Bishop(2);
    board[5][7] = new Bishop(2);
    // set up kings
    board[4][0] = new King(1);
    board[4][7] = new King(2);
    // set up queens
    board[3][0] = new Queen(1);
    board[3][7] = new Queen(2);
    // set up custom model.pieces
    if (includes) {
      board[4][2] = new Crab(1);
      board[2][2] = new Bus(1);
      board[4][5] = new Bus(2);
      board[2][5] = new Crab(2);
    }
    return board;
  }
}

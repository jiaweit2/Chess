package model.game;

import model.Piece;
import model.Type;
import model.King;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
  private Initializer initializer = new Initializer();
  private Piece[][] board;
  private int curr_player;
  public double score1;
  public double score2;

  public Game(boolean includes) {
    this.board = this.initializer.newGame(includes);
    this.score1 = 0;
    this.score2 = 0;
    this.curr_player = 1;
  }

  public Game(Piece[][] board, int curr_player) {
    this.board = board;
    this.curr_player = curr_player;
  }

  public Piece[][] getBoard() {
    return this.board;
  }

  public void setBoard(Piece[][] board) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        this.board[i][j] = board[i][j].copy(board[i][j]);
      }
    }
  }

  public int getCurrPlayer() {
    return this.curr_player;
  }

  public void setCurrPlayer(int player) {
    this.curr_player = player;
  }

  public void setNextPlayer() {
    this.curr_player = this.curr_player == 1 ? 2 : 1;
  }

  public boolean isCheckmate(int player) { // player: inferior side
    int[] king_pos = findKings(player);
    int opponent = player == 1 ? 2 : 1;
    if (!isInCheck(opponent, king_pos[0], king_pos[1])) {
      return false;
    }
    Piece king = board[king_pos[0]][king_pos[1]];
    board[king_pos[0]][king_pos[1]] = new Piece();
    if (canKingMove(player, king_pos, opponent, false)) {
      board[king_pos[0]][king_pos[1]] = king;
      return false;
    }
    // Calculate threat path
    Set<List<Integer>> threats = new HashSet<List<Integer>>();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (this.board[i][j].getPlayer() == opponent) {
          Set<List<Integer>> s =
              this.board[i][j].threatPath(i, j, king_pos[0], king_pos[1], this.board);
          if (s.size() > 0 && threats.size() > 0) {
            board[king_pos[0]][king_pos[1]] = king;
            return true;
          } else if (s.size() > 0) {
            threats.addAll(s);
          }
        }
      }
    }
    // Try to protect the king
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (this.board[i][j].getPlayer() == player && i != king_pos[0] && j != king_pos[1]) {
          for (List<Integer> threat : threats) {
            if (this.board[i][j].checkValidMove(i, j, threat.get(0), threat.get(1), this.board)) {
              board[king_pos[0]][king_pos[1]] = king;
              return false;
            }
          }
        }
      }
    }
    board[king_pos[0]][king_pos[1]] = king;
    return true;
  }

  private boolean canKingMove(int player, int[] king_pos, int opponent, boolean check_current_pos) {
    Piece p = board[king_pos[0]][king_pos[1]];
    board[king_pos[0]][king_pos[1]] = new King(player);
    // Try to escape King
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (king_pos[0] - 1 + i < 0
            || king_pos[0] - 1 + i >= 8
            || king_pos[1] - 1 + j < 0
            || king_pos[1] - 1 + j >= 8
            || (check_current_pos && i == 1 && j == 1)) {
          continue;
        }

        if (!isInCheck(opponent, king_pos[0] - 1 + i, king_pos[1] - 1 + j)
            && board[king_pos[0]][king_pos[1]].checkValidMove(
                king_pos[0], king_pos[1], king_pos[0] - 1 + i, king_pos[1] - 1 + j, this.board)) {
          board[king_pos[0]][king_pos[1]] = p;
          return true;
        }
      }
    }
    board[king_pos[0]][king_pos[1]] = p;
    return false;
  }

  public boolean isStalemate(int player) { // player: inferior side
    int[] king_pos = findKings(player);
    int opponent = player == 1 ? 2 : 1;

    if (isInCheck(opponent, king_pos[0], king_pos[1])) { // in check
      return false;
    }
    // Try to move King
    if (canKingMove(player, king_pos, opponent, true)) {
      return false;
    }
    // Try to move any other piece
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((i != king_pos[0] || j != king_pos[1])
            && board[i][j].getPlayer() == player
            && canMove(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean canMove(int x, int y) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[x][y].checkValidMove(x, y, i, j, board)) {
          Piece[][] curr_board = initializer.copy(this.board);
          Piece[][] new_board = board[x][y].move(x, y, i, j, this);
          if (!initializer.equals(curr_board, new_board)) {
            this.setBoard(curr_board);
            return true;
          }
        }
      }
    }
    return false;
  }

  public int[] findKings(int player) {
    int[] king_pos = new int[] {-1, -1};

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (this.board[i][j].getType() == Type.KING && this.board[i][j].getPlayer() == player) {
          king_pos[0] = i;
          king_pos[1] = j;
          break;
        }
      }
    }
    return king_pos;
  }

  public boolean isInCheck(int opponent, int king_pos_x, int king_pos_y) {
    Piece p = this.board[king_pos_x][king_pos_y];
    this.board[king_pos_x][king_pos_y] = new Piece();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (this.board[i][j].getPlayer() == opponent) {
          if ((this.board[i][j].getType() != Type.PAWN
                  && this.board[i][j].checkValidMove(i, j, king_pos_x, king_pos_y, this.board))
              || this.board[i][j].threatPath(i, j, king_pos_x, king_pos_y, this.board).size() > 0) {
            this.board[king_pos_x][king_pos_y] = p;
            return true;
          }
        }
      }
    }
    this.board[king_pos_x][king_pos_y] = p;
    return false;
  }
}

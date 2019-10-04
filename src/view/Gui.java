package view;

import control.Controller;
import model.Piece;
import model.Type;
import model.game.Game;
import model.game.Initializer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Stack;

public class Gui {
  public JFrame frame = new JFrame("Chess");
  GridLayout grids = new GridLayout(8, 8);
  private JPanel boardUI = new JPanel(grids);
  public Game game;
  private JPanel ui = new JPanel();
  private JPanel textUI = new JPanel(new BorderLayout(1, 10));
  public JTextField player2;
  public JTextField player1;
  public boolean firstClick = true;
  public boolean isStarted = true;
  boolean includes = true;
  int[] lastClickPos = new int[2];
  Initializer initializer = new Initializer();
  Controller controller = new Controller();
  public Stack stack;

  public void initialize(boolean includes) {
    this.includes = includes;
    game = new Game(includes);
    stack = new Stack();
    player2 = new JTextField("Black", 8);
    player1 = new JTextField("White", 8);
    ui.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    ui.add(textUI);
    ui.add(boardUI);
    this.boardUI.setBorder(new EmptyBorder(2, 2, 2, 2));
    player1.setHorizontalAlignment(JTextField.CENTER);
    player2.setHorizontalAlignment(JTextField.CENTER);
    controller.setupMenuBar(this);
    update();
    updateScore();
  }

  public void updateScore() {
    textUI.removeAll();
    textUI.add(player2, BorderLayout.NORTH);

    JLabel lab1 =
        new JLabel(
            "<html><center>"
                + Double.toString(game.score2)
                + "<br>---------<br>"
                + Double.toString(game.score1)
                + "</center></html>",
            JLabel.CENTER);
    textUI.add(lab1, BorderLayout.CENTER);
    textUI.add(player1, BorderLayout.SOUTH);
    frame.pack();
  }

  public void update() {
    boardUI.removeAll();
    Piece[][] board = game.getBoard();
    for (int y = 7; y >= 0; y--) {
      for (int x = 0; x < 8; x++) {
        Piece p = board[x][y];
        p.setBackground((x + y) % 2 == 1 ? new Color(255, 253, 231) : new Color(123, 140, 97));
        p.setOpaque(true);
        p.setBorderPainted(false);
        boardUI.add(p);
        int final_x = x;
        int final_y = y;
        for (ActionListener al : board[x][y].getActionListeners()) {
          board[x][y].removeActionListener(al);
        }
        board[x][y].addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                pieceAction(final_x, final_y);
              }
            });
      }
    }
    frame.pack();
  }

  public void highlightPotential(int x, int y) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (game.getBoard()[x][y].checkValidMove(x, y, i, j, game.getBoard())) {
          game.getBoard()[i][j].setBackground(new Color(187, 191, 69));
        }
      }
    }
  }

  public void pieceAction(int x, int y) {
    if (!isStarted) {
      JOptionPane.showMessageDialog(null, "Game has ended. Click restart to play again!");
      return;
    }
    if (firstClick && game.getBoard()[x][y].getType() == Type.EMPTY) {
      return;
    }
    if (firstClick) {
      if (game.getCurrPlayer() != game.getBoard()[x][y].getPlayer()) {
        JOptionPane.showMessageDialog(null, "Not your turn! fool...");
        return;
      }
      // highlight current position
      game.getBoard()[x][y].setBackground(new Color(187, 191, 69));
      // highlight potential path
      highlightPotential(x, y);

      lastClickPos[0] = x;
      lastClickPos[1] = y;
      firstClick = false;
    } else { // secondClick
      if (!game.getBoard()[lastClickPos[0]][lastClickPos[1]].checkValidMove(
          lastClickPos[0], lastClickPos[1], x, y, game.getBoard())) {
        JOptionPane.showMessageDialog(null, "Not a valid move!");
        deHighlight();
      } else {
        Piece[][] prev_board = initializer.copy(game.getBoard());
        Piece[][] new_board =
            game.getBoard()[lastClickPos[0]][lastClickPos[1]].move(
                lastClickPos[0], lastClickPos[1], x, y, game);
        if (initializer.equals(prev_board, new_board)) {
          deHighlight();
          JOptionPane.showMessageDialog(null, "In check...");
          firstClick = true;
          return;
        }
        stack.push(prev_board);
        game.setNextPlayer();
        new_board = switchSide(new_board);
        game.setBoard(new_board);

        if (game.isCheckmate(1)) {
          game.score2 += 1;
          announceWinner(player2.getText());
        } else if (game.isCheckmate(2)) {
          game.score1 += 1;
          announceWinner(player1.getText());
        } else if (game.isStalemate(game.getCurrPlayer())) {
          announceDraw();
        }
        update();
      }
      firstClick = true;
    }
  }

  private Piece[][] switchSide(Piece[][] board) {
    Piece[][] new_board = new Piece[8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        new_board[7 - i][7 - j] = board[i][j].copy(board[i][j]);
      }
    }
    return new_board;
  }

  private void deHighlight() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        game.getBoard()[i][j].setBackground(
            (i + j) % 2 == 1 ? new Color(255, 253, 231) : new Color(123, 140, 97));
      }
    }
  }

  public void announceDraw() {
    game.score1 += 0.5;
    game.score2 += 0.5;
    updateScore();
    stack = new Stack();
    JOptionPane.showMessageDialog(null, "Draw!");
    isStarted = false;
  }

  public void announceWinner(String player) {
    updateScore();
    stack = new Stack();
    JOptionPane.showMessageDialog(null, player + " wins!");
    isStarted = false;
  }

  public void restart() {
    isStarted = true;
    game.setBoard(initializer.newGame(includes));
    game.setCurrPlayer(1);
    update();
  }

  public static void main(String[] args) {
    Gui gui = new Gui();
    gui.initialize(false);
    gui.frame.add(gui.ui);
    gui.frame.setLocationRelativeTo(null);
    gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.frame.pack();
    gui.frame.setMinimumSize(new Dimension(500, 500));
    gui.frame.setVisible(true);
  }
}

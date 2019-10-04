package control;

import model.Piece;
import view.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
  public JMenuItem[] menu;

  public void setupMenuBar(Gui gui) {
    menu = new JMenuItem[5];
    JMenuBar bar = new JMenuBar();
    bar.setBorderPainted(true);
    menu[0] = new JMenuItem("New Game");
    menu[0].addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int result =
                JOptionPane.showConfirmDialog(
                    null, "Do you want to start a new game?", "Warning", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
              String[] options = new String[] {"Classic", "With Crab/Bus"};
              int result2 =
                  JOptionPane.showOptionDialog(
                      null,
                      "which kind of game?",
                      "",
                      JOptionPane.DEFAULT_OPTION,
                      JOptionPane.PLAIN_MESSAGE,
                      null,
                      options,
                      options[0]);
              if (result2 == 0) {
                gui.initialize(false);
              } else {
                gui.initialize(true);
              }
              gui.isStarted = true;
            }
          }
        });
    menu[1] = new JMenuItem("Restart");
    menu[1].addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (!gui.isStarted) {
              gui.restart();
              return;
            }
            int result =
                JOptionPane.showConfirmDialog(
                    null, "Make a draw and restart?", "", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
              gui.announceDraw();
              gui.restart();
            }
          }
        });
    menu[2] = new JMenuItem("Forfeit");
    menu[2].addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (gui.isStarted == false) {
              JOptionPane.showMessageDialog(null, "Start the game first!");
              return;
            }
            int result =
                JOptionPane.showConfirmDialog(
                    null, "You want to resign?", "", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
              int opponent = gui.game.getCurrPlayer() == 1 ? 2 : 1;
              String player;
              if (opponent == 1) {
                gui.game.score1 += 1;
                player = gui.player1.getText();
              } else {
                gui.game.score2 += 1;
                player = gui.player2.getText();
              }
              gui.announceWinner(player);
              gui.restart();
            }
          }
        });
    menu[3] = new JMenuItem("Switch");
    menu[3].addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            double score_temp = gui.game.score1;
            gui.game.score1 = gui.game.score2;
            gui.game.score2 = score_temp;
            JTextField player_temp = gui.player1;
            gui.player1 = gui.player2;
            gui.player2 = player_temp;
            gui.updateScore();
          }
        });
    menu[4] = new JMenuItem("Undo");
    menu[4].addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (gui.stack.empty()) {
              JOptionPane.showMessageDialog(null, "Can't undo!");
              return;
            }
            Piece[][] last_board = (Piece[][]) gui.stack.pop();
            gui.game.setNextPlayer();
            gui.game.setBoard(last_board);
            gui.update();
            gui.firstClick = true;
          }
        });
    for (JMenuItem m : menu) {
      m.setOpaque(true);
      m.setBackground(Color.white);
      bar.add(m);
    }
    gui.frame.getRootPane().setJMenuBar(bar);
  }
}

package test;

import control.Controller;
import view.Gui;
import org.junit.jupiter.api.Test;

class GuiTest {
  private Gui gui;

  public GuiTest() {
    gui = new Gui();
  }

  @Test
  void update() {}

  @Test
  void main() {
    gui.main(new String[1]);
    gui.initialize(false);
    // Test move empty piece
    gui.pieceAction(3, 3);
    // Test not your turn
    gui.pieceAction(1, 7);
    // Test wrong move
    gui.pieceAction(1, 1);
    gui.pieceAction(2, 6);
    // Test move
    gui.pieceAction(1, 1);
    gui.pieceAction(1, 2);
    Controller c = new Controller();
    c.setupMenuBar(gui);
    c.menu[4].doClick();
    c.menu[4].doClick(); // undo
    c.menu[0].doClick();
    c.menu[1].doClick();
    c.menu[2].doClick();
    c.menu[3].doClick();
    gui.restart();
    gui.announceWinner("Player 1");
    gui.announceDraw();
    c.menu[2].doClick();
    gui.pieceAction(1, 1);
  }
}

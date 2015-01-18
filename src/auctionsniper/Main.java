package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import auctionsniper.ui.MainWindow;


public class Main {

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
	private MainWindow ui;
	
	public Main() throws Exception {
		startUserInterface();
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow();
			}
		});
	}

	public static void main() throws Exception {
    System.out.println("debug Main main 1");
    System.out.println("debug Main main 2");
    System.out.println("debug Main main 3");
		Main main = new Main();
	}

	private void displayStatus(final String status) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ui.showStatus(status);
      }
    });
  }

}

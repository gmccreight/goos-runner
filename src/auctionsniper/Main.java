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
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
        // handle the exception...        
        // For example consider calling Thread.currentThread().interrupt(); here.
    }
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

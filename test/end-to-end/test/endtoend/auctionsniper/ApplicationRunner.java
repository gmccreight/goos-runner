package test.endtoend.auctionsniper;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

public class ApplicationRunner {

  private AuctionSniperDriver driver;

	public void startIt() {
		Thread thread = new Thread("Test Application") {
			@Override
			public void run() {
				try {
					Main.main();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.showSniperStatus(MainWindow.STATUS_JOINING);
	}

	public void stop() {
		if (driver != null) {
			driver.dispose();
		}
	}

	public void hasShownSnipperIsBidding() {
		driver.showSniperStatus(MainWindow.STATUS_BIDDING);
	}

}

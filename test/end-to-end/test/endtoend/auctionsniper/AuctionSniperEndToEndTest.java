package test.endtoend.auctionsniper;

import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {

	private ApplicationRunner application = new ApplicationRunner();

	@After
	public void stopApplication() {
		application.stop();
	}
	
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws InterruptedException {
		application.startIt();
	}

	@Test
	public void sniperJoinsAuctionUntilAuctionClosesAgain() throws InterruptedException {
		application.startIt();
	}
	
}

package test.endtoend.auctionsniper;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {

	private FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private ApplicationRunner application = new ApplicationRunner();

	@After
	public void stopAuction() {
		auction.stop();
	}
	
	@After
	public void stopApplication() {
		application.stop();
	}
	
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws XMPPException, InterruptedException {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}

	@Test
	public void sniperJoinsAuctionUntilAuctionClosesAgain() throws XMPPException, InterruptedException {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}
	
}

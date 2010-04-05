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
	public void sniperMakesAHigherBidButLoses() throws Exception {
		
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSnipperIsBidding(1000, 1098);
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}
	
	@Test
	public void sniperWinsAuctionByBiddingHigher() throws Exception {
		
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSnipperIsBidding(1000, 1098);
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		application.hasShownSnipperIsWinning(1098);
		
		auction.announceClosed();
		application.showSniperHasWonAuction(1098);
		
	}
	
}

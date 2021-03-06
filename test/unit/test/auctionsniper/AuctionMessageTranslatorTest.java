package test.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;



import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionMessageTranslator;
import auctionsniper.AuctionEventListener.PriceSource;
import auctionsniper.xmpp.XMPPFailureReporter;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
	private final Mockery context = new Mockery();
	public static final Chat UNUSED_CHAT = null;
	private static final String SNIPER_ID = "FromSniper";
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	private final XMPPFailureReporter failureReporter = context.mock(XMPPFailureReporter.class);

	AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener, failureReporter);
	
	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
		context.checking(new Expectations() {{
			oneOf(listener).auctionClosed();
		}});
		
		Message message = new Message();
		message.setBody("SOL version: 1.1; Event: CLOSE;");
		
		translator.processMessage(UNUSED_CHAT, message);
		
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
		}});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).currentPrice(234, 5, PriceSource.FromSniper);
		}});
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + 
				SNIPER_ID + ";");
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test
	public void notifiesAuctionFailedWhenBadMessageReceived() {
		String badMessage = "a bad message";
		expectFailureWithMessage(badMessage);
		
		Message message = message(badMessage);
		
		translator.processMessage(UNUSED_CHAT, message);
	}

	private void expectFailureWithMessage(final String badMessage) {
		context.checking(new Expectations() {{ 
			oneOf(listener).auctionFailed();
			oneOf(failureReporter).cannotTranslateMessage(with(SNIPER_ID), with(badMessage),
					with(any(Exception.class)));
		}});
	}

	private Message message(String badMessage) {
		Message message = new Message();
		message.setBody(badMessage);
		return message;
	}
	
	@Test
	public void notifiesAuctionFailedWhenEventTypeMissing() {
		String badMessage = "SOLVersion: 1.1; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";";
		expectFailureWithMessage(badMessage);
		Message message = new Message();
		message.setBody(badMessage);
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test
	public void notifiesAuctionFailedWhenCurrentPriceMissing() {
		String badMessage = "SOLVersion: 1.1; Event: PRICE; Increment: 5; Bidder: " + SNIPER_ID + ";";
		expectFailureWithMessage(badMessage);
		Message message = new Message();
		message.setBody(badMessage);
		translator.processMessage(UNUSED_CHAT, message);
		
	}
	

}

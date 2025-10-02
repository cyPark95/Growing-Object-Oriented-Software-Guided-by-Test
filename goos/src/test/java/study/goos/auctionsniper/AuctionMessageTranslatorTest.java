package study.goos.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import study.goos.auctionsniper.AuctionEventListener.PriceSource;

import static study.goos.e2e.ApplicationRunner.SNIPER_ID;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {

    public static final Chat UNUSED_CHAT = null;

    private final Mockery context = new Mockery();
    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener);

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {
        context.checking(new Expectations() {{
            oneOf(listener).auctionClosed();
        }});

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");
        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).currentPrice(192, 7, PriceSource.FROM_OTHER_BIDDER);
        }});

        Message message = new Message();
        message.setBody("SQLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).currentPrice(234, 5, PriceSource.FROM_SNIPER);
        }});

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";");
        translator.processMessage(UNUSED_CHAT, message);
    }
}

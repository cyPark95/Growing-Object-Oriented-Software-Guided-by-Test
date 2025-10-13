package learningtest.jmock;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {


    private final Mockery context = new JUnit4Mockery();
    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

    private String UNUSED_CHAT = "Unused CHAT";

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {
        Message message = new Message();
        message.setBody("SQLVersion: 1.1; Event: CLOSE;");

        context.checking(new Expectations() {{
            oneOf(listener).auctionClosed();
        }});

        translator.processMessage(UNUSED_CHAT, message);
    }
}

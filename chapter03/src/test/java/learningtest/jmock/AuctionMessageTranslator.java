package learningtest.jmock;

public class AuctionMessageTranslator {

    private final AuctionEventListener listener;

    public AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener;
    }

    public void processMessage(String chat, Message message) {
        System.out.printf("[%s] %s%n", chat, message.getBody());
        listener.auctionClosed();
    }
}

package study.goos.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import study.goos.auctionsniper.AuctionEventListener.PriceSource;

import java.util.HashMap;
import java.util.Map;

import static study.goos.auctionsniper.AuctionEventListener.PriceSource.FROM_OTHER_BIDDER;
import static study.goos.auctionsniper.AuctionEventListener.PriceSource.FROM_SNIPER;

public class AuctionMessageTranslator implements MessageListener {

    private final String sniperId;
    private final AuctionEventListener listener;

    public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
        this.sniperId = sniperId;
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        AuctionEvent event = AuctionEvent.from(message.getBody());

        String type = event.type();
        if ("CLOSE".equals(type)) {
            listener.auctionClosed();
        } else if ("PRICE".equals(type)) {
            listener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));
        }
    }

    private static class AuctionEvent {

        private final Map<String, String> fields = new HashMap<>();

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }

        private static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }

        public String type() {
            return get("Event");
        }

        public int currentPrice() {
            return getInt("CurrentPrice");
        }

        public int increment() {
            return getInt("Increment");
        }

        private int getInt(String fieldName) {
            return Integer.parseInt(get(fieldName));
        }

        private String get(String fieldName) {
            return fields.get(fieldName);
        }

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        public PriceSource isFrom(String sniperId) {
            return sniperId.equals(bidder()) ? FROM_SNIPER : FROM_OTHER_BIDDER;
        }

        private String bidder() {
            return get("Bidder");
        }
    }
}

package study.goos;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import study.goos.auctionsniper.Auction;

import static study.goos.Main.BID_COMMAND_FORMAT;
import static study.goos.Main.JOIN_COMMAND_FORMAT;

public class XMPPAuction implements Auction {

        private final Chat chat;

        public XMPPAuction(Chat chat) {
            this.chat = chat;
        }

        @Override
        public void join() {
            sendMessage(JOIN_COMMAND_FORMAT);
        }

        @Override
        public void bid(int amount) {
            sendMessage(String.format(BID_COMMAND_FORMAT, amount));
        }

        private void sendMessage(final String message) {
            try {
                chat.sendMessage(message);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

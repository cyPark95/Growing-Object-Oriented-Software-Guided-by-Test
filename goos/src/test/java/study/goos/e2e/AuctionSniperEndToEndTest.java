package study.goos.e2e;

import org.junit.After;
import org.junit.Test;

import static study.goos.e2e.ApplicationRunner.SNIPER_XMPP_ID;

public class AuctionSniperEndToEndTest {

    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        // 1단계 경매에서 품목을 판매하고
        auction.startSellingItem();
        // 2단계 경매 스나이퍼가 해당 경매에서 입찰을 시작하면
        application.startBiddingIn(auction);
        // 3단계 경매에서는 경매 스나이퍼로부터 Join 요청을 받을 것이다.
        auction.hasReceivedJoinRequestFromSniper();
        // 4단계 경매가 Close됐다고 선언되면
        auction.announceClosed();
        // 5단계 경매 스나이퍼는 경매에서 낙찰에 실패했음을 보여줄 것이다.
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperMakesAHigherBidButLoses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(SNIPER_XMPP_ID);

        // 1. 경매에서 스나이퍼로 가격을 보내게 한다.
        auction.reportPrice(1000, 98, "other bidder");
        // 2. 스나이퍼에서 가격을 받고 응답했는지 확인한다.
        application.hasShownSniperIsBidding(1000, 1098);

        // 3. 경매에서 스나이퍼로부터 입찰가가 오른 입찰을 받았는지 확인한다.
        auction.hasReceivedBid(1098, SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperWinsAnAuctionByBiddingHigher() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(SNIPER_XMPP_ID);

        auction.reportPrice(1000, 98, "other bidder");
        application.hasShownSniperIsBidding(1000, 1098);  // 최종 가격과 입찰

        auction.hasReceivedBid(1098, SNIPER_XMPP_ID);

        auction.reportPrice(1098, 97, SNIPER_XMPP_ID);
        // 최종 가격을 제시한 입찰자가 스나이퍼인지 확인한다.
        application.hasShownSniperIsWinning(1098);  // 낙찰

        auction.announceClosed();
        application.showsSniperHasWonAuction(1098);  // 최종 가격
    }

    @After
    public void stopAuction() {
        auction.stop();
    }

    @After
    public void stopApplication() {
        application.stop();
    }
}

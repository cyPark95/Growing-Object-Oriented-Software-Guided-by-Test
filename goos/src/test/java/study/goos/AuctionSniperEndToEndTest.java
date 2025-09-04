package study.goos;

import org.junit.After;
import org.junit.Test;

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

    @After
    public void stopAuction() {
        auction.stop();
    }

    @After
    public void stopApplication() {
        application.stop();
    }
}

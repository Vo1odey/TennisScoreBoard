import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.services.CheckMatchStatus;

import com.dragunov.tennisscoreboard.services.matchscore.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculationTest {
    Player createPlayer(String name) {
        PlayerMatchScore playerMatchScore = new PlayerMatchScore();
        return new Player(name, playerMatchScore);
    }
    @Test
    void getMatchStatus_PointsZeroZero_Ongoing() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        MatchStatus matchStatus = CheckMatchStatus.INSTANCE.getMatchStatus(first, second);
        Assertions.assertEquals(MatchStatus.ONGOING, matchStatus);
    }
    @Test
    void getMatchStatus_PointsFortyForty_Advantage() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountPoint().setPoint(Points.FORTY);
        second.getCountPoint().setPoint(Points.FORTY);
        MatchStatus matchStatus = CheckMatchStatus.INSTANCE.getMatchStatus(first, second);
        Assertions.assertEquals(MatchStatus.ADVANTAGE, matchStatus);
    }
    @Test
    void getMatchStatus_GamesSixSix_TieBreak() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountGame().setGame(6);
        second.getCountGame().setGame(6);
        MatchStatus matchStatus = CheckMatchStatus.INSTANCE.getMatchStatus(first, second);
        Assertions.assertEquals(MatchStatus.TIE_BREAK, matchStatus);
    }
    @Test
    void getMatchStatus_SetsTwoZero_END() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountSet().setSet(2);
        MatchStatus matchStatus = CheckMatchStatus.INSTANCE.getMatchStatus(first, second);
        Assertions.assertEquals(MatchStatus.END, matchStatus);
    }

    @Test
    void ongoingPointWon_PointsZeroZero_PointsFortyForty() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        MatchStatus ongoing = MatchStatus.ONGOING;
        for (int i = 1; i <= 3; i++) {
            //15...30...40
            first.getCountPoint().wonPoint(ongoing, second);
            second.getCountPoint().wonPoint(ongoing, second);
        }
        Assertions.assertEquals(Points.FORTY, first.getCountPoint().getPoint());
        Assertions.assertEquals(Points.FORTY, second.getCountPoint().getPoint());
    }
    @Test
    void tieBreakPointWon_PointsZeroZero_PointsSixSix() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        MatchStatus tieBreak = MatchStatus.TIE_BREAK;
        for (int i = 1; i <= 6; i++) {
            //1..2..3..
            first.getCountPoint().wonPoint(tieBreak, second);
            second.getCountPoint().wonPoint(tieBreak, second);
        }
        Assertions.assertEquals(6, first.getCountPoint().getTieBreakPoint());
        Assertions.assertEquals(6, second.getCountPoint().getTieBreakPoint());
    }
    @Test
    void advantagePointWon_PointsFortyForty_FortyAd() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountPoint().setPoint(Points.FORTY);
        second.getCountPoint().setPoint(Points.FORTY);
        MatchStatus advantage = MatchStatus.ADVANTAGE;
        //Ad:40
        first.won(advantage, second);
        Assertions.assertEquals(Points.AD, first.getCountPoint().getPoint());
        Assertions.assertEquals(Points.FORTY, second.getCountPoint().getPoint());
        //40:40
        second.won(advantage, first);
        Assertions.assertEquals(Points.FORTY, first.getCountPoint().getPoint());
        Assertions.assertEquals(Points.FORTY, second.getCountPoint().getPoint());
        //40:Ad
        second.won(advantage, first);
        Assertions.assertEquals(Points.FORTY, first.getCountPoint().getPoint());
        Assertions.assertEquals(Points.AD, second.getCountPoint().getPoint());
    }
    @Test
    void ongoingGameWon_PointsFortyForty_GamesOneZero() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountPoint().setWonForty(true);
        second.getCountPoint().setWonForty(true);
        MatchStatus ongoing = MatchStatus.ONGOING;
        first.getCountPoint().wonPoint(ongoing, second);
        first.getCountGame().wonGame(ongoing, second);
        Assertions.assertEquals(1, first.getCountGame().getGame());
        Assertions.assertEquals(0, second.getCountGame().getGame());
    }
    @Test
    void advantageGameWon_PointsAdForty_GamesOneZero() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountPoint().setPoint(Points.AD);
        second.getCountPoint().setPoint(Points.FORTY);
        MatchStatus advantage = MatchStatus.ADVANTAGE;
        first.getCountPoint().wonPoint(advantage, second);
        first.getCountGame().wonGame(advantage, second);
        Assertions.assertEquals(1, first.getCountGame().getGame());
        Assertions.assertEquals(0, second.getCountGame().getGame());
    }
    @Test
    void ongoingSetWonToDifference_GameFiveFive_SetsOneZero() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountGame().setGame(5);
        second.getCountGame().setGame(5);
        MatchStatus ongoing = MatchStatus.ONGOING;
        for (int i = 1; i <= 8; i++) {
            //15...30...40...0 -> game++ -> 15...30...
            first.won(ongoing, second);
        }
        Assertions.assertEquals(1, first.getCountSet().getSet());
        Assertions.assertEquals(0, second.getCountSet().getSet());
    }
    @Test
    void tieBreakSetWon_GameSixSix_SetsOneZero() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountGame().setGame(6);
        second.getCountGame().setGame(6);
        MatchStatus tieBreak = MatchStatus.TIE_BREAK;
        for (int i = 1; i <= 7; i++) {
            //1...2...3...
            first.getCountPoint().wonPoint(tieBreak, second);
            first.getCountGame().wonGame(tieBreak, second);
            first.getCountSet().wonSet();
        }
        Assertions.assertEquals(1, first.getCountSet().getSet());
        Assertions.assertEquals(0, second.getCountSet().getSet());
    }
    @Test
    void matchWonForSets_SetsOneOne_SetsTwoOne() {
        PlayerMatchScore first = createPlayer("Bob").getPlayerMatchScore();
        PlayerMatchScore second = createPlayer("Sam").getPlayerMatchScore();
        first.getCountSet().setSet(1);
        second.getCountSet().setSet(1);;
        MatchStatus matchStatus = MatchStatus.ONGOING;
        for (int i = 1; i <= 24; i++) {
            //first set = 2
            first.won(matchStatus, second);
        }
        Assertions.assertEquals(2, first.getCountSet().getSet());
        Assertions.assertEquals(1, second.getCountSet().getSet());
    }
}

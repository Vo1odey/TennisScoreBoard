package com.dragunov.tennisscoreboard.services.matchscore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CountPoint{
    private Points point;
    private int tieBreakPoint;
    private boolean wonForty;
    private boolean wonGame;
    private String renderPoint;
    public CountPoint(){
        this.point = Points.ZERO;
        tieBreakPoint = 0;
        wonForty = false;
        wonGame = false;
        renderPoint = "0";
    }
    public void renderingPoint() {
        if (tieBreakPoint > 0) {
            renderPoint = String.valueOf(tieBreakPoint);
        }
        if (tieBreakPoint == 0) {
            renderPoint = String.valueOf(point.getValue());
        }
    }
    private void ongoingPointWon(MatchStatus matchStatus, PlayerMatchScore secondPlayer) {
        if (matchStatus == MatchStatus.ONGOING) {
            point = point.next(point);
            if (isWonForty()){
                wonGame = true;
                wonForty = false;
                resetPoints(secondPlayer);
                return;
            }
            if (point == Points.FORTY) {
                wonForty = true;
            }
        }
    }
    private void tieBreakPointWon(MatchStatus matchStatus) {
        if (matchStatus == MatchStatus.TIE_BREAK) {
            tieBreakPoint = tieBreakPoint + 1;
        }
    }
    private void advantagePointWon(MatchStatus matchStatus, PlayerMatchScore secondPlayer) {
        if (matchStatus == MatchStatus.ADVANTAGE) {
            wonForty = false;
            if (point == Points.AD) {
                wonGame = true;
                resetPoints(secondPlayer);
                return;
            }
            if (secondPlayer.getCountPoint().getPoint() == Points.FORTY) {
                point = Points.AD;
                renderPoint = "Ad";
                return;
            }
            if (secondPlayer.getCountPoint().getPoint() == Points.AD) {
                secondPlayer.getCountPoint().setPoint(Points.FORTY);
                secondPlayer.getCountPoint().setRenderPoint("40");
            }
        }
    }
    private void resetPoints(PlayerMatchScore secondPlayer){
        point = Points.ZERO;
        tieBreakPoint = 0;
        renderPoint = "0";
        secondPlayer.getCountPoint().setPoint(Points.ZERO);
        secondPlayer.getCountPoint().setTieBreakPoint(0);
        secondPlayer.getCountPoint().setRenderPoint("0");
    }
    public void wonPoint(MatchStatus matchStatus, PlayerMatchScore secondPlayer) {
        renderingPoint();
        ongoingPointWon(matchStatus, secondPlayer);
        tieBreakPointWon(matchStatus);
        advantagePointWon(matchStatus, secondPlayer);
        renderingPoint();
    }
}

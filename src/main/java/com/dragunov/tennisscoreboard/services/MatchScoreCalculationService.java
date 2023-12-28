package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.dto.GameScore;

public class MatchScoreCalculationService {

    private boolean isAd(GameScore target, GameScore player2){
        if (target.getPoint() == Points.FORTY && player2.getPoint() == Points.FORTY) return true;
        if (target.getPoint() == Points.AD || player2.getPoint() == Points.AD) return true;
        return false;
    }
    private void addPoint(GameScore target, GameScore player2) {
        if (isAd(target, player2)) {
            if (target.isAdvantage()) {
                target.setNextPoint(true);
            }
        }
        if (isAd(target, player2)){
            target.setAdvantage(true);
            target.setPoint(Points.AD);
            player2.setPoint(Points.FORTY);
            player2.setAdvantage(false);
        }
        //switch?
        if (!isAd(target, player2)) {
            if (target.getPoint() == Points.ZERO) {
                target.setPoint(Points.FIFTEEN);
            } else if (target.getPoint() == Points.FIFTEEN) {
                target.setPoint(Points.THIRTY);
            } else if (target.getPoint() == Points.THIRTY) {
                target.setPoint(Points.FORTY);
            } else if (target.getPoint() == Points.FORTY) {
                target.setPoint(Points.GAME);
            } else {
                target.setPoint(Points.ZERO);
            }
        }
    }
    private boolean isWinGame(GameScore target, GameScore player2){
        if (target.isNextPoint()) {
            return true;
        }
        if (player2.getPoint().ordinal() <= Points.THIRTY.ordinal() && target.getPoint() == Points.GAME) {
            return true;
        }
        return false;
    }
    private void addGame(GameScore target, GameScore player2) {
        if (isWinGame(target, player2)) {
            target.setGame(target.getGame() + 1);
            target.setPoint(Points.ZERO);
            target.setAdvantage(false);
            target.setNextPoint(false);
            player2.setPoint(Points.ZERO);
            player2.setAdvantage(false);
            player2.setNextPoint(false);
        }
    }
    private boolean isSixGame(GameScore player) {
        return player.getGame() >= 6;
    }
    private boolean isGapToGame(GameScore player1, GameScore player2) {
        return (Math.abs(player1.getGame() - player2.getGame()) >= Math.abs(2))
                && (isSixGame(player1) || isSixGame(player2));
    }
    private void addSet(GameScore player1, GameScore player2) {
        if (isGapToGame(player1, player2)) {
            if (player1.getGame() > player2.getGame()) {
                player1.setSet(player1.getSet() + 1);
                player1.setGame(0);
                player2.setGame(0);
            }
            if (player2.getGame() > player1.getGame()) {
                player2.setSet(player2.getSet() + 1);
                player2.setGame(0);
                player1.setGame(0);
            }
        }
    }
    private boolean isSevenPoint(GameScore player) {
        return (int) player.getPoint().getValue() >= 7;
    }
    private boolean isGapToPoint(GameScore player1, GameScore player2) {
        int p1point = (int) player1.getPoint().getValue();
        int p2point = (int) player2.getPoint().getValue();
        return (Math.abs(p1point - p2point) >= Math.abs(2))
                && (isSevenPoint(player1) || isSevenPoint(player2));
    }
    private void addPointToTieBreak(GameScore player) {
        Points[] values = Points.values();
        int currentOrdinal = player.getPoint().ordinal();
        if (currentOrdinal <= 15) {
            player.setPoint(values[currentOrdinal + 1]);
        }
    }
    private void tieBreak(GameScore target, GameScore player2){
        addPointToTieBreak(target);
        if (isGapToPoint(target, player2)) {
            player2.setPoint(Points.ZERO);
            player2.setGame(0);
            target.setPoint(Points.ZERO);
            target.setGame(0);
            target.setSet(target.getSet() + 1);
        }
    }
    public void play(GameScore target, GameScore player2) {
        if (target.getGame() == 6 && player2.getGame() == 6) {
            tieBreak(target, player2);
        } else {
            addPoint(target, player2);
            addGame(target, player2);
            addSet(target, player2);
        }
    }
}

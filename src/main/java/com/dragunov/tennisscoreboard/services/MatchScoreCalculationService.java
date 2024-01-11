package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.dto.GameScore;

public class MatchScoreCalculationService {

    private boolean isAd(GameScore target, GameScore player2){
        if (target.getPoint() == Points.FORTY && player2.getPoint() == Points.FORTY) return true;
        //40:40
        if (target.getPoint() == Points.AD || player2.getPoint() == Points.AD) return true;
        //Ad:40
        return false;
    }
    private void addPoint(GameScore target, GameScore player2) {
        if (isAd(target, player2)) {
            if (target.isAdvantage()) {
                target.setNextPoint(true);
                return;
            }
        }
        if (isAd(target, player2)){
            if (player2.isAdvantage()){
                player2.setAdvantage(false);
                player2.setPoint(Points.FORTY);
            } else {
                target.setAdvantage(true);
                target.setPoint(Points.AD);
            }
        }
        if (!isAd(target, player2)) {
            switch (target.getPoint()) {
                case ZERO: target.setPoint(Points.FIFTEEN);
                    break;
                case FIFTEEN: target.setPoint(Points.THIRTY);
                    break;
                case THIRTY: target.setPoint(Points.FORTY);
                    break;
                case FORTY: target.setPoint(Points.GAME);
                    break;
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

    private void addPointToTieBreak(GameScore player) {
        player.setTieBreakPoint(player.getTieBreakPoint()+1);
    }
    private boolean isGapToPointTieBreak(GameScore player1, GameScore player2) {
        int p1point = player1.getTieBreakPoint();
        int p2point = player2.getTieBreakPoint();
        return (Math.abs(p1point - p2point) >= Math.abs(2))
                && (p1point>=7 || p2point>=7);
    }
    private void tieBreak(GameScore target, GameScore player2){
        addPointToTieBreak(target);
        if (isGapToPointTieBreak(target, player2)) {
            player2.setPoint(Points.ZERO);
            player2.setTieBreakPoint(0);
            player2.setGame(0);
            target.setPoint(Points.ZERO);
            target.setTieBreakPoint(0);
            target.setGame(0);
            target.setSet(target.getSet() + 1);
        }
    }
    public void wonPoint(GameScore target, GameScore player2) {
        if (target.getGame() == 6 && player2.getGame() == 6) {
            tieBreak(target, player2);
        } else {
            addPoint(target, player2);
            addGame(target, player2);
            addSet(target, player2);
        }
    }
}

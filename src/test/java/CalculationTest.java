import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;
import com.dragunov.tennisscoreboard.services.MatchScoreCalculationService;
import com.dragunov.tennisscoreboard.services.Points;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/*
Если игрок 1 выигрывает очко при счёте 40-40, гейм не заканчивается
Если игрок 1 выигрывает очко при счёте 40-0, то он выигрывает и гейм
При счёте 6-6 начинается тайбрейк вместо обычного гейма
 */

public class CalculationTest {
    MatchScoreCalculationService calculation = new MatchScoreCalculationService();
    GameScore kelli = new GameScore();
    GameScore Bob = new GameScore();
    GameScore Taylor = new GameScore();

    @Test
    public void gameIsNotOver() {
        Bob.setPoint(Points.FORTY);
        Taylor.setPoint(Points.FORTY);
        calculation.play(Bob, Taylor);
        Assertions.assertEquals(Points.FORTY, Bob.getPoint());
        Assertions.assertEquals(Points.FORTY, Taylor.getPoint());
    }

    @Test
    public void gameIsOver() {
        Bob.setPoint(Points.FORTY);
        Taylor.setPoint(Points.ZERO);
        Assertions.assertEquals(Points.FORTY, Bob.getPoint());
        Assertions.assertEquals(Points.ZERO, Taylor.getPoint());
        calculation.play(Bob, Taylor);
        Assertions.assertEquals(1, Bob.getGame());
        Assertions.assertEquals(0, Taylor.getGame());
    }
    @Test
    public void isTieBreak() {
        Bob.setGame(6);
        Taylor.setGame(6);
        Assertions.assertEquals(Points.ZERO, Bob.getPoint());
        while (Bob.getPoint().ordinal() < 6) {
            calculation.play(Bob, Taylor);
        }
        Assertions.assertEquals(Points.SIX, Bob.getPoint());
    }
    @Test
    public void getPlayer(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchRepository.class)
                .addAnnotatedClass(PlayerModel.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        MatchRepository matchRepository = new MatchRepository(sessionFactory);
        playerRepository.addPlayerToH2();
        matchRepository.addMatchToTableScoreBoard();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
        List<MatchModel> matches = finishedMatchesPersistenceService.MatchesInPageByPlayerName(matchRepository, 1, "Kelli");
        for (MatchModel match: matches) {
            System.out.println(match);
        }
    }
    @Test
    public void ScorePlayer(){


    }
}

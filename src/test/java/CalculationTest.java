import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;
import com.dragunov.tennisscoreboard.services.MatchScoreCalculationService;
import com.dragunov.tennisscoreboard.services.Points;
import com.dragunov.tennisscoreboard.services.Validation;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class CalculationTest {
    MatchScoreCalculationService calculation = new MatchScoreCalculationService();
    GameScore Bob = new GameScore();
    GameScore Taylor = new GameScore();

    @Test
    public void gameIsNotOver() {
        Bob.setPoint(Points.FORTY);
        Taylor.setPoint(Points.FORTY);
        calculation.play(Bob, Taylor);
        Assertions.assertEquals(0, Bob.getGame());
        Assertions.assertEquals(0, Taylor.getGame());
    }
    @Test
    public void gameOver() {
        Bob.setPoint(Points.FORTY);
        Taylor.setPoint(Points.ZERO);
        calculation.play(Bob, Taylor);
        Assertions.assertEquals(1, Bob.getGame());
        Assertions.assertEquals(0, Taylor.getGame());
    }
    @Test
    public void tieBreak() {
        Bob.setGame(6);
        Taylor.setGame(6);
        calculation.play(Bob, Taylor);
        Assertions.assertEquals(1, Bob.getTieBreakPoint());
        Assertions.assertEquals(0, Taylor.getTieBreakPoint());
    }
    //При счете в тайбрейке 6-7 тайбрейк продолжается
    @Test
    public void continueTieBreak() {
        Bob.setGame(6);
        Bob.setTieBreakPoint(5);
        Taylor.setGame(6);
        Taylor.setTieBreakPoint(6);
        calculation.play(Bob, Taylor);
        calculation.play(Taylor, Bob);
        Assertions.assertEquals(0, Bob.getSet());
        Assertions.assertEquals(0, Taylor.getSet());
    }
    //При счете геймов 5-7 игрок выигрывает сет
    @Test
    public void winTieBreak() {
        Bob.setGame(6);
        Bob.setTieBreakPoint(5);
        Taylor.setGame(6);
        Taylor.setTieBreakPoint(6);
        calculation.play(Taylor, Bob);
        Assertions.assertEquals(1, Taylor.getSet());
        Assertions.assertEquals(0, Bob.getSet());
    }
    //Если игрок 1 выигрывает очко - он получает преимущество
    @Test
    public void playerAd() {
        Bob.setPoint(Points.FORTY);
        Taylor.setPoint(Points.FORTY);
        calculation.play(Bob, Taylor);
        Assertions.assertEquals("Ad", Bob.getPoint().getValue());
    }
    //Если игрок 1 имеет преимущество, а игрок 2 выигрывает очко - игрок 1 теряет преимущество
    @Test
    public void playerDropAd() {
        Taylor.setPoint(Points.FORTY);
        Bob.setPoint(Points.AD);
        Bob.setAdvantage(true);
        calculation.play(Taylor, Bob);
        Assertions.assertEquals(Points.FORTY, Bob.getPoint());
        Assertions.assertEquals(Points.FORTY, Taylor.getPoint() );
    }
    //Если игрок 1 имеет преимущество и выигрывает следующее очко - то он выигрывает и гейм
    @Test
    public void playerAdWinGame() {
        Taylor.setPoint(Points.FORTY);
        Bob.setPoint(Points.AD);
        Bob.setAdvantage(true);
        calculation.play(Bob, Taylor);
        Assertions.assertEquals(1, Bob.getGame());
        Assertions.assertEquals(0, Taylor.getGame());
    }

    @Test
    public void paginationHibernate() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchRepository.class)
                .addAnnotatedClass(PlayerModel.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        MatchRepository matchRepository = new MatchRepository(sessionFactory);
        playerRepository.addPlayerToH2();
        matchRepository.addMatchToTableScoreBoard();
        List<MatchModel> matches = finishedMatchesPersistenceService.usePaginationHibernate(sessionFactory, 1,"");
        for (MatchModel match:matches) {
            System.out.println(match);
        }
        System.out.println(finishedMatchesPersistenceService.quantityPages(sessionFactory, ""));
    }
    @Test
    public void validateName(){
        Validation validation = new Validation();
        System.out.println(validation.validatePlayerName(" VOvAN Dragunov"));
    }
}

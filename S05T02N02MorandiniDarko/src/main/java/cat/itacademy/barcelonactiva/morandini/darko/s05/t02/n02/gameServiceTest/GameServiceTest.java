package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.gameServiceTest;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.repository.IgameRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.game.service.GameService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.player.service.PlayerService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.repository.IplayerRepository;

import org.springframework.http.HttpStatus;

public class GameServiceTest {
    @InjectMocks
    private GameService gameService;

    @Mock
    private IgameRepository gameRepository;

    @Mock
    private IplayerRepository playerRepository;

    @Mock
    private PlayerService playerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPlayGame() {
        Player player = new Player();
        player.setId(1);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(playerService.getPlayerSuccessPercentage(1)).thenReturn(50.0);

        Game game = gameService.playGame(1);

        assertNotNull(game);
        assertEquals(player, game.getPlayer());
    }

    @Test
    public void testGetPlayerGames() {
        Player player = new Player();
        player.setId(1);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        List<Game> games = new ArrayList<>();
        games.add(new Game());
        games.add(new Game());

        when(gameRepository.findByPlayerId(1)).thenReturn(games);

        List<Game> result = gameService.getPlayerGames(1);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetPlayerGamesPlayerNotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        try {
            gameService.getPlayerGames(1);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    public void testDeletePlayerGames() {
        Player player = new Player();
        player.setId(1);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        gameService.deletePlayerGames(1);

        verify(gameRepository).deleteByPlayer(player);
    }

    @Test
    public void testGetAverageSuccessPercentage() {
        Player player1 = new Player();
        player1.setId(1);

        Player player2 = new Player();
        player2.setId(2);

        when(playerRepository.findAll()).thenReturn(List.of(player1, player2));
        when(playerService.getPlayerSuccessPercentage(1)).thenReturn(50.0);
        when(playerService.getPlayerSuccessPercentage(2)).thenReturn(75.0);

        double average = gameService.getAverageSuccessPercentage();

        assertEquals(62.5, average, 0.01);
    }

    @Test
    public void testGetLoser() {
        Player player1 = new Player();
        player1.setId(1);

        Player player2 = new Player();
        player2.setId(2);

        when(playerRepository.findAll()).thenReturn(List.of(player1, player2));
        when(playerService.getPlayerSuccessPercentage(1)).thenReturn(50.0);
        when(playerService.getPlayerSuccessPercentage(2)).thenReturn(25.0);

        Player loser = gameService.getLoser();

        assertEquals(player2, loser);
    }
}

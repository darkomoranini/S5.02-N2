package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.playerServiceTest;

import org.junit.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.repository.IgameRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.player.service.PlayerService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.repository.IplayerRepository;

public class PlayerServiceTest {
	
	@InjectMocks
    private PlayerService playerService;

    @Mock
    private IplayerRepository playerRepository;

    @Mock
    private IgameRepository gameRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreatePlayer() {
        Player player = new Player();
        player.setName("John");

        LocalDateTime currentDateTime = LocalDateTime.now();
        Date currentDate = Date.valueOf(currentDateTime.toLocalDate());

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player createdPlayer = playerService.createPlayer(player);

        assertEquals("John", createdPlayer.getName());
        assertEquals(currentDate, createdPlayer.getRegistrationDate());
    }

    @Test
    public void testUpdatePlayer() {
        Player existingPlayer = new Player();
        existingPlayer.setId(1);
        existingPlayer.setName("Alice");

        Player updatedPlayer = new Player();
        updatedPlayer.setId(1);
        updatedPlayer.setName("Bob");

        when(playerRepository.findById(1)).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(existingPlayer)).thenReturn(updatedPlayer);

        Player result = playerService.updatePlayer(1, updatedPlayer);

        assertEquals("Bob", result.getName());
    }

    @Test
    public void testUpdatePlayerNotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        try {
            playerService.updatePlayer(1, new Player());
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    public void testGetPlayerById() {
        Player player = new Player();
        player.setId(1);
        player.setName("Alice");

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        Optional<Player> result = playerService.getPlayerById(1);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    public void testGetPlayerByIdNotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Player> result = playerService.getPlayerById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllPlayers() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        player1.setId(1);
        player1.setName("Alice");
        Player player2 = new Player();
        player2.setId(2);
        player2.setName("Bob");
        players.add(player1);
        players.add(player2);

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertEquals(2, result.size());
    }

    @Test
    public void testDeletePlayer() {
        when(playerRepository.findById(1)).thenReturn(Optional.of(new Player()));

        playerService.deletePlayer(1);

        verify(playerRepository).deleteById(1);
    }

    @Test
    public void testGetPlayerSuccessPercentage() {
        Player player = new Player();
        player.setId(1);
        List<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setWon(true);
        Game game2 = new Game();
        game2.setWon(false);

        games.add(game1);
        games.add(game2);

        when(gameRepository.findByPlayerId(1)).thenReturn(games);

        double successPercentage = playerService.getPlayerSuccessPercentage(1);

        assertEquals(50.0, successPercentage, 0.01);
    }
}

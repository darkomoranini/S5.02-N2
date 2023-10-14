package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.playerControllerTest;

import java.util.*;
import org.junit.*;
import org.mockito.InjectMocks;
import org.springframework.*;
import org.springframework.web.servlet.View;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.repository.IgameRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.controller.PlayerController;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.repository.IplayerRepository;

public class PlayerControllerTest {
    @InjectMocks
    private PlayerController playerController;

    @Mock
    private IplayerRepository playerRepository;

    @Mock
    private IgameRepository gameRepository;

    @Mock
    private View mockView;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(playerController)
                .setSingleView(mockView)
                .build();
    }

    @Test
    public void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/players/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-player"))
                .andExpect(model().attributeExists("player"));
    }

    @Test
    public void testCreatePlayer() throws Exception {
        mockMvc.perform(post("/players/create")
                .param("name", "Alice"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/players/1"));

    }

    @Test
    public void testGetPlayerDetails() throws Exception {
        Player player = new Player();
        player.setId(1);
        player.setName("Alice");

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("player-details"))
                .andExpect(model().attribute("player", player));
    }

    @Test
    public void testGetPlayerDetailsNotFound() throws Exception {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/players/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPlayerGames() throws Exception {
        Player player = new Player();
        player.setId(1);

        List<Game> games = new ArrayList<>();
        games.add(new Game());
        games.add(new Game());

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(gameRepository.findByPlayerId(1)).thenReturn(games);

        mockMvc.perform(get("/players/1/games"))
                .andExpect(status().isOk())
                .andExpect(view().name("game-result"))
                .andExpect(model().attribute("player", player))
                .andExpect(model().attribute("games", games));
    }
}

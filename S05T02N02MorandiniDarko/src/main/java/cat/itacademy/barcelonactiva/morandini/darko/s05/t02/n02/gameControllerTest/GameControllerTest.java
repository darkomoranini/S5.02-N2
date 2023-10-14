package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.gameControllerTest;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.View;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.controller.GameController;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.repository.IgameRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.repository.IplayerRepository;

public class GameControllerTest {
    @InjectMocks
    private GameController gameController;

    @Mock
    private IgameRepository gameRepository;

    @Mock
    private IplayerRepository playerRepository;

    @Mock
    private View mockView;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(gameController)
                .setSingleView(mockView)
                .build();
    }

    @Test
    public void testPlayGame() throws Exception {
        Player player = new Player();
        player.setId(1);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(playerService.getPlayerSuccessPercentage(1)).thenReturn(50.0);

        mockMvc.perform(post("/games/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game-result"));

    }

    @Test
    public void testDeletePlayerGames() throws Exception {
        Player player = new Player();
        player.setId(1);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        mockMvc.perform(delete("/games/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/players/1/games"));

    }
}

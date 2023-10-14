package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.applicationIntegrationTest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationIntegrationTest {
	@Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testCreatePlayerAndPlayGame() throws Exception {
        
        String playerCreationResponse = mockMvc.perform(post("/players/create")
                .param("name", "Alice"))
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getResponse()
                .getRedirectedUrl();

        int playerId = Integer.parseInt(playerCreationResponse.replace("/players/", ""));

        mockMvc.perform(post("/games/" + playerId))
                .andExpect(status().isOk())
                .andExpect(view().name("game-result"));
    }

    @Test
    public void testGetPlayerDetailsAndGames() throws Exception {
        String playerCreationResponse = mockMvc.perform(post("/players/create")
                .param("name", "Bob"))
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getResponse()
                .getRedirectedUrl();

        int playerId = Integer.parseInt(playerCreationResponse.replace("/players/", ""));

        mockMvc.perform(get("/players/" + playerId))
                .andExpect(status().isOk())
                .andExpect(view().name("player-details"));

        mockMvc.perform(get("/players/" + playerId + "/games"))
                .andExpect(status().isOk())
                .andExpect(view().name("game-result"));
    }
}

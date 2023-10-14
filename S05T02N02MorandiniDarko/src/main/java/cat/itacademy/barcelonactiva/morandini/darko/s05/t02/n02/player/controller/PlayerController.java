package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.repository.IgameRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.repository.IplayerRepository;

@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private IplayerRepository playerRepository;
    @Autowired
    private IgameRepository gameRepository;
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("player", new Player());
        
        return "create-player";
    }

    @PostMapping("/create")
    public String createPlayer(@Validated @ModelAttribute("player") Player player, RedirectAttributes redirectAttributes) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date currentDate = Date.valueOf(currentDateTime.toLocalDate());
        player.setRegistrationDate(currentDate);
        playerRepository.save(player);
        redirectAttributes.addFlashAttribute("Jugador creado exitosamente");
        
        return "redirect:/players/" + player.getId();
    }
    
    @GetMapping("/{id}")
    public String getPlayerDetails(@PathVariable int id, Model model) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        model.addAttribute("player", player);
        
        return "player-details";
    }

    @GetMapping("/{id}/games")
    public String getPlayerGames(@PathVariable int id, Model model) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        List<Game> games = gameRepository.findByPlayerId(id);
        model.addAttribute("player", player);
        model.addAttribute("games", games);
        
        return "game-result";
    }
}

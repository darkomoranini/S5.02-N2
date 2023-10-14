package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.player.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.game.repository.IgameRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.player.iservice.IplayerService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.player.repository.IplayerRepository;

@Service
public class PlayerService implements IplayerService {

    @Autowired
    private IplayerRepository playerRepository;
    @Autowired
    private IgameRepository gameRepository; 

    public Player createPlayer(Player player) {
    	LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        Date currentDate = new Date(timestamp.getTime());

        player.setRegistrationDate(currentDate);
       
        return playerRepository.save(player);
    }

    public Player updatePlayer(int id, Player updatedPlayer) {
        Player existingPlayer = null;
		try {
			existingPlayer = playerRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			
			e.printStackTrace();
		}
        existingPlayer.setName(updatedPlayer.getName());
        	
        return playerRepository.save(existingPlayer);
    }

    public Optional<Player> getPlayerById(int id) {

        try {
			playerRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
        
        return playerRepository.findById(id);
    }

    public List<Player> getAllPlayers() {    
        
    	return playerRepository.findAll();
    }

    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }

    public double getPlayerSuccessPercentage(int id) {
        List<Game> playerGames = gameRepository.findByPlayerId(id);
        if (playerGames.isEmpty()) {
            return 0.0;
        }

        double wonGames = playerGames.stream().filter(Game::won).count();
       
        return wonGames / playerGames.size() * 100.0;
    }
}

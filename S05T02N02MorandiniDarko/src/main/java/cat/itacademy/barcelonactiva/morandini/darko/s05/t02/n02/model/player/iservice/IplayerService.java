package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.player.iservice;

import java.util.List;
import java.util.Optional;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.Player;

public interface IplayerService {
	
	 public Player createPlayer(Player player);
	
	 public Player updatePlayer(int id, Player updatedPlayer);
	 
	 public Optional<Player> getPlayerById(int id);
	 
	 public List<Player> getAllPlayers();
	 
	 public void deletePlayer(int id);
	 
	 public double getPlayerSuccessPercentage(int id);
	 
}

package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.game.dto;

import java.sql.Date;

public class GameDto {
    private int id;
    private int roll1;
    private int roll2;
    private boolean won;
    private Date timestamp;

    public GameDto() {
    }

    public GameDto(int id, int roll1, int roll2, boolean won, Date timestamp) {
        this.id = id;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.won = won;
        this.timestamp = timestamp;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoll1() {
		return roll1;
	}

	public void setRoll1(int roll1) {
		this.roll1 = roll1;
	}

	public int getRoll2() {
		return roll2;
	}

	public void setRoll2(int roll2) {
		this.roll2 = roll2;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

    
}
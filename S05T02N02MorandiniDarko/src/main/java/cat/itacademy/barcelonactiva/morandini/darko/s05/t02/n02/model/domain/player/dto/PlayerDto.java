package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.model.domain.player.dto;

import java.sql.Date;

public class PlayerDto {
    private int id;
    private String name;
    private Date registrationDate;
    private double successPercentage; 

    public PlayerDto() {
    }
    public PlayerDto(int id, String name, Date registrationDate, double successPercentage) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.successPercentage = successPercentage;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public double getSuccessPercentage() {
		return successPercentage;
	}

	public void setSuccessPercentage(double successPercentage) {
		this.successPercentage = successPercentage;
	}

    
}
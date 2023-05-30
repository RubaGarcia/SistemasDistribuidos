package common;

import java.io.Serializable;

/**
 * PlayerData.java
 * 
 * Class that represents the data of a single player
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class PlayerData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int color;
	private String Name;

	public PlayerData(int color, String name) {
		this.color = color;
		Name = name;
	}
	public int getColor() {
		return color;
	}
	public String getName() {
		return Name;
	}
}

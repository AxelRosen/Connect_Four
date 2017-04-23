package model;

public class Player {
	private String name;
	private int id;

	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	
}

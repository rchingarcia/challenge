package uy.com.fire.quasar.operation.model;

public class Satellite {

	private String name;
	private Position position;

	public Satellite() {
		super();
		this.name = "";
		this.position = new Position(0, 0);
	}

	public Satellite(String name, Position position) {
		super();
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}

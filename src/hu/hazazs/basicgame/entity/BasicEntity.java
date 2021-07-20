package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Coordinates;
import hu.hazazs.basicgame.Level;

public abstract class BasicEntity implements Entity {

	final Level level;
	Coordinates coordinates;
	private final String mark;

	BasicEntity(Level level, Coordinates coordinates, String mark) {
		this.level = level;
		this.coordinates = coordinates;
		this.mark = mark;
	}

	@Override
	public String getMark() {
		return mark;
	}

	@Override
	public Coordinates getCoordinates() {
		return coordinates;
	}

	@Override
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Level getLevel() {
		return level;
	}

}
package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Coordinates;
import hu.hazazs.basicgame.Direction;
import hu.hazazs.basicgame.Level;
import hu.hazazs.basicgame.move.MovingStrategy;

public abstract class MovingEntity extends BasicEntity {

	MovingStrategy currentMovingStrategy;
	Direction direction = Direction.STAY;
	private Coordinates escapeCoordinates;

	MovingEntity(Level level, Coordinates coordinates, String mark) {
		super(level, coordinates, mark);
		if (this instanceof Player) {
			escapeCoordinates = level.getEscapeCoordinates(coordinates);
		}
	}

	public MovingStrategy getMovingStrategy() {
		return currentMovingStrategy;
	}

	public Coordinates getEscapeCoordinates() {
		return escapeCoordinates;
	}

	public void setEscapeCoordinates(Coordinates escapeCoordinates) {
		this.escapeCoordinates = escapeCoordinates;
	}

	public Direction getDirection() {
		return direction;
	}

	public Coordinates update() {
		int newRow = coordinates.getRow();
		int newColumn = coordinates.getColumn();
		return switch (direction) {
			case RIGHT -> new Coordinates(newRow, newColumn + 1);
			case DOWN -> new Coordinates(newRow + 1, newColumn);
			case LEFT -> new Coordinates(newRow, newColumn - 1);
			case UP -> new Coordinates(newRow - 1, newColumn);
			default -> new Coordinates(newRow, newColumn);
		};
	}

}
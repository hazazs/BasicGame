package hu.hazazs.basicgame;

public class Coordinates {

	private final int row;
	private final int column;

	public Coordinates(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isEqualTo(Coordinates coordinates) {
		return this.row == coordinates.row && this.column == coordinates.column;
	}

	public int getAirDistanceFrom(Coordinates target) {
		return Math.abs(this.row - target.row) + Math.abs(this.column - target.column);
	}

}
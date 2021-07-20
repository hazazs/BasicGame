package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Coordinates;
import hu.hazazs.basicgame.Level;

public interface Entity {

	String getMark();

	Coordinates getCoordinates();

	void setCoordinates(Coordinates coordinates);

	Level getLevel();

}
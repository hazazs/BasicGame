package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Coordinates;
import hu.hazazs.basicgame.Level;
import hu.hazazs.basicgame.move.ChasingEnemiesMovingStrategy;
import hu.hazazs.basicgame.move.EscapeMovingStrategy;
import hu.hazazs.basicgame.move.PickUpPowerUpMovingStrategy;

public class Player extends MovingEntity {

	public Player(Level level, String mark) {
		super(level, level.getRandomCoordinates(), mark);
		currentMovingStrategy = new EscapeMovingStrategy(this);
	}

	@Override
	public Coordinates update() {
		if (level.isAnyStrengthPowerUpActive()) {
			currentMovingStrategy = new ChasingEnemiesMovingStrategy(this);
		} else if (level.isAnyPowerUpVisible()) {
			currentMovingStrategy = new PickUpPowerUpMovingStrategy(this);
		} else {
			currentMovingStrategy = new EscapeMovingStrategy(this);
		}
		direction = currentMovingStrategy.calculateNewDirection();
		return super.update();
	}

}
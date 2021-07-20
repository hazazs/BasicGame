package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Level;

public class StrengthPowerUp extends PowerUp {

	public StrengthPowerUp(Level level, String mark) {
		super(level, mark);
	}

	@Override
	public void activate() {
		if (!level.isAnyStrengthPowerUpActive()) {
			for (MovingEntity enemy : level.getEnemies()) {
				enemy.setEscapeCoordinates(level.getEscapeCoordinates(enemy.coordinates));
			}
		}
		super.activate();
	}

	@Override
	void deactivate() {
		super.deactivate();
		if (!level.isAnyStrengthPowerUpActive() && !level.isAnyPowerUpVisible()) {
			level.getPlayer().setEscapeCoordinates(level.getEscapeCoordinates(level.getPlayer().coordinates));
		}
	}

}
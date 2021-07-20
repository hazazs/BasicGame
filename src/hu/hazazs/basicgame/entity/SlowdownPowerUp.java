package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Level;

public class SlowdownPowerUp extends PowerUp {

	public SlowdownPowerUp(Level level, String mark) {
		super(level, mark);
	}

	@Override
	public void activate() {
		if (!level.isAnySlowdownPowerUpActive()) {
			Enemy.setSlowdowned(true);
			Enemy.setLeftover((level.getRound() - 1) % 4);
		}
		super.activate();
	}

	@Override
	void deactivate() {
		super.deactivate();
		if (!level.isAnySlowdownPowerUpActive()) {
			Enemy.setSlowdowned(false);
			Enemy.setLeftover((level.getRound() - 1) % 2);
		}
	}

}
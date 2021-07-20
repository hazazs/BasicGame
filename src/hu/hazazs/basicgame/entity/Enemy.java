package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Coordinates;
import hu.hazazs.basicgame.Level;
import hu.hazazs.basicgame.move.ChasingPlayerMovingStrategy;
import hu.hazazs.basicgame.move.EscapeMovingStrategy;

public class Enemy extends MovingEntity {

	private static boolean SLOWDOWNED;
	private static int LEFTOVER;

	public Enemy(Level level, int distanceFromPlayer, String mark) {
		super(level, level.getRandomCoordinatesWithDistance(level.getPlayer().coordinates, distanceFromPlayer), mark);
		currentMovingStrategy = new ChasingPlayerMovingStrategy(this);
	}

	public static boolean isSlowdowned() {
		return SLOWDOWNED;
	}

	static void setSlowdowned(boolean isSlowdowned) {
		SLOWDOWNED = isSlowdowned;
	}

	static void setLeftover(int leftover) {
		LEFTOVER = leftover;
	}

	@Override
	public Coordinates update() {
		if (this.getLevel().getRound() % (SLOWDOWNED ? 4 : 2) == LEFTOVER) {
			if (level.isAnyStrengthPowerUpActive()) {
				currentMovingStrategy = new EscapeMovingStrategy(this);
			} else {
				currentMovingStrategy = new ChasingPlayerMovingStrategy(this);
			}
			direction = currentMovingStrategy.calculateNewDirection();
			return super.update();
		}
		return coordinates;
	}

}
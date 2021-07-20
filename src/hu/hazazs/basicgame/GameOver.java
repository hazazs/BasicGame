package hu.hazazs.basicgame;

import hu.hazazs.basicgame.entity.MovingEntity;
import hu.hazazs.basicgame.move.EscapeMovingStrategy;

public class GameOver {

	private final Level level;
	private Result result = Result.TIE;

	GameOver(Level level) {
		this.level = level;
	}

	boolean isGameOver(boolean middleCheck) {
		for (MovingEntity enemy : level.getEnemies()) {
			if (level.getPlayer().getCoordinates().isEqualTo(enemy.getCoordinates())) {
				if (middleCheck && enemy.getMovingStrategy() instanceof EscapeMovingStrategy) {
					return false;
				}
				if (level.isAnyStrengthPowerUpActive()) {
					result = Result.WIN;
				} else {
					result = Result.LOSE;
				}
				return true;
			}
		}
		return false;
	}

	void gameOver() {
		System.out.print("GAME OVER! " + switch (result) {
			case WIN -> "PLAYER WINS!";
			case LOSE -> "ENEMY WINS!";
			case TIE -> "DRAW!";
		});
	}

}
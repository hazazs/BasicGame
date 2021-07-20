package hu.hazazs.basicgame;

import java.util.Random;

import hu.hazazs.basicgame.entity.Enemy;
import hu.hazazs.basicgame.entity.MovingEntity;
import hu.hazazs.basicgame.entity.Player;
import hu.hazazs.basicgame.entity.PowerUp;
import hu.hazazs.basicgame.entity.SlowdownPowerUp;
import hu.hazazs.basicgame.entity.StrengthPowerUp;

public class BasicGame {

	private static final int HEIGHT = 40;
	private static final int WIDTH = 40;
	private static final int NUMBER_OF_HORIZONTAL_WALLS = 10;
	private static final int NUMBER_OF_VERTICAL_WALLS = 10;
	private static final String PLAYER_MARK = "o";
	private static final String ENEMY_MARK = "-";
	private static final String SLOWDOWNED_ENEMY_MARK = "~";
	private static final int NUMBER_OF_ENEMIES = 3;
	private static final int PLAYER_ENEMY_DISTANCE = 10;
	private static final String ENEMY_PLAYER_MARK = "ō";
	private static final String SLOWDOWNED_ENEMY_PLAYER_MARK = "õ";
	private static final String STRENGTH_POWERUP_MARK = "*";
	private static final String SLOWDOWN_POWERUP_MARK = "¤";
	private static final int NUMBER_OF_STRENGTH_POWERUPS = 2;
	private static final int NUMBER_OF_SLOWDOWN_POWERUPS = 2;
	private static final int POWERUP_VISIBLE_DURATION = 60;
	private static final int POWERUP_ACTIVE_DURATION = 60;
	private static final String PLAYER_STRENGTH_POWERUP_MARK = "O";
	private static final String PLAYER_STRENGTH_POWERUP_ENEMY_MARK = "Ō";
	private static final String PLAYER_STRENGTH_POWERUP_SLOWDOWNED_ENEMY_MARK = "Õ";
	private static final int NUMBER_OF_ROUNDS = 1_000;
	private static final long TIMEOUT = 300L;
	private static final Random RANDOM = new Random(8);

	public static void main(String[] args) throws InterruptedException {
		Level level = new Level(HEIGHT, WIDTH, NUMBER_OF_HORIZONTAL_WALLS, NUMBER_OF_VERTICAL_WALLS, RANDOM);
		Player player = new Player(level, PLAYER_MARK);
		level.setPlayer(player);
		Enemy[] enemies = new Enemy[NUMBER_OF_ENEMIES];
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy(level, PLAYER_ENEMY_DISTANCE, ENEMY_MARK);
		}
		level.setEnemies(enemies);
		PowerUp[] powerUps = new PowerUp[NUMBER_OF_STRENGTH_POWERUPS + NUMBER_OF_SLOWDOWN_POWERUPS];
		for (int i = 0; i < NUMBER_OF_STRENGTH_POWERUPS; i++) {
			powerUps[i] = new StrengthPowerUp(level, STRENGTH_POWERUP_MARK);
		}
		for (int i = NUMBER_OF_STRENGTH_POWERUPS; i < powerUps.length; i++) {
			powerUps[i] = new SlowdownPowerUp(level, SLOWDOWN_POWERUP_MARK);
		}
		level.setPowerUps(powerUps);
		GameOver gameOver = new GameOver(level);
		for (; level.getRound() <= NUMBER_OF_ROUNDS; level.nextRound()) {
			player.setCoordinates(player.update());
			for (PowerUp powerUp : powerUps) {
				powerUp.update(POWERUP_VISIBLE_DURATION, POWERUP_ACTIVE_DURATION, player.getCoordinates());
				if (powerUp.isVisible() && player.getCoordinates().isEqualTo(powerUp.getCoordinates())) {
					powerUp.activate();
					powerUp.hide();
					powerUp.setCoordinates(level.getRandomCoordinatesWithDistance(player.getCoordinates(), 1));
					if (!level.isAnyPowerUpVisible() && !level.isAnyStrengthPowerUpActive()) {
						player.setEscapeCoordinates(level.getEscapeCoordinates(player.getCoordinates()));
					}
				}
			}
			if (gameOver.isGameOver(true)) {
				level.draw(SLOWDOWNED_ENEMY_MARK, ENEMY_PLAYER_MARK, SLOWDOWNED_ENEMY_PLAYER_MARK,
						PLAYER_STRENGTH_POWERUP_MARK, PLAYER_STRENGTH_POWERUP_ENEMY_MARK,
						PLAYER_STRENGTH_POWERUP_SLOWDOWNED_ENEMY_MARK, level.getRound());
				break;
			}
			for (MovingEntity enemy : enemies) {
				enemy.setCoordinates(enemy.update());
			}
			level.draw(SLOWDOWNED_ENEMY_MARK, ENEMY_PLAYER_MARK, SLOWDOWNED_ENEMY_PLAYER_MARK,
					PLAYER_STRENGTH_POWERUP_MARK, PLAYER_STRENGTH_POWERUP_ENEMY_MARK,
					PLAYER_STRENGTH_POWERUP_SLOWDOWNED_ENEMY_MARK, level.getRound());
			if (gameOver.isGameOver(false)) {
				break;
			}
			Thread.sleep(TIMEOUT);
		}
		gameOver.gameOver();
	}

}
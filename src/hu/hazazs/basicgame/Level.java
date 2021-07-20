package hu.hazazs.basicgame;

import java.util.Arrays;
import java.util.Random;

import hu.hazazs.basicgame.entity.Enemy;
import hu.hazazs.basicgame.entity.Player;
import hu.hazazs.basicgame.entity.PowerUp;
import hu.hazazs.basicgame.entity.SlowdownPowerUp;
import hu.hazazs.basicgame.entity.StrengthPowerUp;

public class Level {

	private final int height;
	private final int width;
	private final String[][] level;
	private int round = 1;
	private final Random random;
	private Player player;
	private Enemy[] enemies;
	private PowerUp[] powerUps;

	public Level(int height, int width, int numberOfHorizontalWalls, int numberOfVerticalWalls, Random random) {
		this.height = height;
		this.width = width;
		this.level = new String[height][width];
		this.random = random;
		do {
			initLevel();
			addRandomWalls(numberOfHorizontalWalls, numberOfVerticalWalls);
		} while (!isValidLevel());
	}

	public int getRound() {
		return round;
	}

	void nextRound() {
		round++;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Enemy[] getEnemies() {
		return enemies;
	}

	public void setEnemies(Enemy[] enemies) {
		this.enemies = enemies;
	}

	public PowerUp[] getPowerUps() {
		return powerUps;
	}

	public void setPowerUps(PowerUp[] powerUps) {
		this.powerUps = powerUps;
	}

	private void initLevel() {
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (row == 0 || row == height - 1 || column == 0 || column == width - 1) {
					level[row][column] = "X";
				} else {
					level[row][column] = " ";
				}
			}
		}
	}

	private void addRandomWalls(int numberOfHorizontalWalls, int numberOfVerticalWalls) {
		for (int i = 0; i < numberOfHorizontalWalls; i++) {
			addRandomHorizontalWall(getRandomStartingCoordinatesForWall());
		}
		for (int i = 0; i < numberOfVerticalWalls; i++) {
			addRandomVerticalWall(getRandomStartingCoordinatesForWall());
		}
	}

	private void addRandomHorizontalWall(Coordinates startingCoordinates) {
		int wallWidth = random.nextInt(Math.min(width - 1 - startingCoordinates.getColumn(), width - 3)) + 1;
		for (int i = 0; i < wallWidth; i++) {
			level[startingCoordinates.getRow()][startingCoordinates.getColumn() + i] = "X";
		}
	}

	private void addRandomVerticalWall(Coordinates startingCoordinates) {
		int wallHeight = random.nextInt(Math.min(height - 1 - startingCoordinates.getRow(), height - 3)) + 1;
		for (int i = 0; i < wallHeight; i++) {
			level[startingCoordinates.getRow() + i][startingCoordinates.getColumn()] = "X";
		}
	}

	private Coordinates getRandomStartingCoordinatesForWall() {
		int row = random.nextInt(height - 2) + 1;
		int column = random.nextInt(width - 2) + 1;
		return new Coordinates(row, column);
	}

	private boolean isValidLevel() {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		Coordinates firstFreeCoordinates = getFirstFreeCoordinates(copy);
		copy[firstFreeCoordinates.getRow()][firstFreeCoordinates.getColumn()] = "*";
		while (spreadAsterisks(copy, false)) {
		}
		return checkValidityOf(copy);
	}

	private Coordinates getFirstFreeCoordinates(String[][] level) {
		int freeRow = 1;
		int freeColumn = 1;
		outer: for (int row = 1; row < height - 1; row++) {
			for (int column = 1; column < width - 1; column++) {
				if (" ".equals(level[row][column])) {
					freeRow = row;
					freeColumn = column;
					break outer;
				}
			}
		}
		return new Coordinates(freeRow, freeColumn);
	}

	private boolean spreadAsterisks(String[][] level, boolean oneByOne) {
		boolean isChanged = false;
		boolean[][] mask = null;
		if (oneByOne) {
			mask = new boolean[height][width];
			for (int row = 1; row < height - 1; row++) {
				for (int column = 1; column < width - 1; column++) {
					if ("*".equals(level[row][column])) {
						mask[row][column] = true;
					}
				}
			}
		}
		for (int row = 1; row < height - 1; row++) {
			for (int column = 1; column < width - 1; column++) {
				if ("*".equals(level[row][column]) && (!oneByOne || oneByOne && mask[row][column])) {
					if (" ".equals(level[row - 1][column])) {
						level[row - 1][column] = "*";
						isChanged = true;
					}
					if (" ".equals(level[row][column + 1])) {
						level[row][column + 1] = "*";
						isChanged = true;
					}
					if (" ".equals(level[row + 1][column])) {
						level[row + 1][column] = "*";
						isChanged = true;
					}
					if (" ".equals(level[row][column - 1])) {
						level[row][column - 1] = "*";
						isChanged = true;
					}
				}
			}
		}
		return isChanged;
	}

	private boolean checkValidityOf(String[][] level) {
		for (int row = 1; row < height - 1; row++) {
			for (int column = 1; column < width - 1; column++) {
				if (" ".equals(level[row][column])) {
					return false;
				}
			}
		}
		return true;
	}

	public Coordinates getRandomCoordinates() {
		Coordinates randomCoordinates;
		do {
			randomCoordinates = new Coordinates(random.nextInt(height - 2) + 1, random.nextInt(width - 2) + 1);
		} while (!isEmpty(randomCoordinates));
		return randomCoordinates;
	}

	public boolean isEmpty(Coordinates coordinates) {
		return " ".equals(level[coordinates.getRow()][coordinates.getColumn()]);
	}

	public Coordinates getRandomCoordinatesWithDistance(Coordinates target, int distance) {
		Coordinates randomCoordinates;
		do {
			randomCoordinates = getRandomCoordinates();
		} while (randomCoordinates.getAirDistanceFrom(target) < distance);
		return randomCoordinates;
	}

	public Coordinates getEscapeCoordinates(Coordinates source) {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		copy[source.getRow()][source.getColumn()] = "*";
		int escapeRow = 1;
		int escapeColumn = 1;
		do {
			outer: for (int row = 1; row < height - 1; row++) {
				for (int column = 1; column < width - 1; column++) {
					if (" ".equals(copy[row][column])) {
						escapeRow = row;
						escapeColumn = column;
						break outer;
					}
				}
			}
		} while (spreadAsterisks(copy, true));
		return new Coordinates(escapeRow, escapeColumn);
	}

	public int getDistanceWithShortestPath(Coordinates source, Coordinates target) {
		int distance = 1;
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		copy[target.getRow()][target.getColumn()] = "*";
		do {
			if ("*".equals(copy[source.getRow() - 1][source.getColumn()])
					|| "*".equals(copy[source.getRow() + 1][source.getColumn()])
					|| "*".equals(copy[source.getRow()][source.getColumn() - 1])
					|| "*".equals(copy[source.getRow()][source.getColumn() + 1])) {
				return distance;
			}
			distance++;
		} while (spreadAsterisks(copy, true));
		return distance;
	}

	public Direction getDirectionWithShortestPath(Coordinates source, Coordinates target) {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		copy[target.getRow()][target.getColumn()] = "*";
		do {
			if ("*".equals(copy[source.getRow() - 1][source.getColumn()])) {
				return Direction.UP;
			}
			if ("*".equals(copy[source.getRow() + 1][source.getColumn()])) {
				return Direction.DOWN;
			}
			if ("*".equals(copy[source.getRow()][source.getColumn() - 1])) {
				return Direction.LEFT;
			}
			if ("*".equals(copy[source.getRow()][source.getColumn() + 1])) {
				return Direction.RIGHT;
			}
		} while (spreadAsterisks(copy, true));
		return Direction.STAY;
	}

	public boolean isAnyEnemyOnLevel() {
		return enemies.length > 0;
	}

	public boolean isAnyPowerUpVisible() {
		for (PowerUp powerUp : powerUps) {
			if (powerUp.isVisible()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAnyStrengthPowerUpActive() {
		for (PowerUp powerUp : powerUps) {
			if (powerUp instanceof StrengthPowerUp && powerUp.isActive()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAnySlowdownPowerUpActive() {
		for (PowerUp powerUp : powerUps) {
			if (powerUp instanceof SlowdownPowerUp && powerUp.isActive()) {
				return true;
			}
		}
		return false;
	}

	public void draw(String slowdownedEnemyMark, String enemyPlayerMark, String slowdownedEnemyPlayerMark,
			String playerStrengthPowerUpMark, String playerStrengthPowerUpEnemyMark,
			String playerStrengthPowerUpSlowdownedEnemyMark, int round) {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		for (PowerUp powerUp : powerUps) {
			if (powerUp.isVisible()) {
				copy[powerUp.getCoordinates().getRow()][powerUp.getCoordinates().getColumn()] = powerUp.getMark();
			}
		}
		copy[player.getCoordinates().getRow()][player.getCoordinates().getColumn()] = isAnyStrengthPowerUpActive()
				? playerStrengthPowerUpMark
				: player.getMark();
		for (Enemy enemy : enemies) {
			if (player.getCoordinates().isEqualTo(enemy.getCoordinates())) {
				if (isAnyStrengthPowerUpActive()) {
					copy[enemy.getCoordinates().getRow()][enemy.getCoordinates().getColumn()] = Enemy.isSlowdowned()
							? playerStrengthPowerUpSlowdownedEnemyMark
							: playerStrengthPowerUpEnemyMark;
				} else {
					copy[enemy.getCoordinates().getRow()][enemy.getCoordinates().getColumn()] = Enemy.isSlowdowned()
							? slowdownedEnemyPlayerMark
							: enemyPlayerMark;
				}
			} else {
				copy[enemy.getCoordinates().getRow()][enemy.getCoordinates().getColumn()] = Enemy.isSlowdowned()
						? slowdownedEnemyMark
						: enemy.getMark();
			}
		}
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				System.out.print(copy[row][column]);
			}
			System.out.println();
		}
		drawCurrentRound(round);
	}

	private void drawCurrentRound(int round) {
		for (int i = 0; i < width; i++) {
			System.out.print("-");
		}
		System.out.printf(" %d%n", round);
	}

}
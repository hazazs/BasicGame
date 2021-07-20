package hu.hazazs.basicgame.move;

import hu.hazazs.basicgame.Direction;
import hu.hazazs.basicgame.entity.Entity;
import hu.hazazs.basicgame.entity.MovingEntity;

public class ChasingEnemiesMovingStrategy extends AbstractMovingStrategy {

	public ChasingEnemiesMovingStrategy(MovingEntity entity) {
		super(entity);
	}

	@Override
	public Direction calculateNewDirection() {
		if (entity.getLevel().isAnyEnemyOnLevel()) {
			return entity.getLevel().getDirectionWithShortestPath(entity.getCoordinates(),
					getClosestEnemy().getCoordinates());
		}
		return entity.getDirection();
	}

	private Entity getClosestEnemy() {
		Entity closestEnemy = null;
		int shortestDistance = Integer.MAX_VALUE;
		for (Entity enemy : entity.getLevel().getEnemies()) {
			int distanceFromEnemy = entity.getLevel().getDistanceWithShortestPath(entity.getCoordinates(),
					enemy.getCoordinates());
			if (distanceFromEnemy < shortestDistance) {
				closestEnemy = enemy;
				shortestDistance = distanceFromEnemy;
			}
		}
		return closestEnemy;
	}

}
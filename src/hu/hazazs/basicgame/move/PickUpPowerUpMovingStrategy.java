package hu.hazazs.basicgame.move;

import hu.hazazs.basicgame.Direction;
import hu.hazazs.basicgame.entity.Entity;
import hu.hazazs.basicgame.entity.MovingEntity;
import hu.hazazs.basicgame.entity.PowerUp;

public class PickUpPowerUpMovingStrategy extends AbstractMovingStrategy {

	public PickUpPowerUpMovingStrategy(MovingEntity entity) {
		super(entity);
	}

	@Override
	public Direction calculateNewDirection() {
		if (entity.getLevel().isAnyPowerUpVisible()) {
			return entity.getLevel().getDirectionWithShortestPath(entity.getCoordinates(),
					getClosestVisiblePowerUp().getCoordinates());
		}
		return entity.getDirection();
	}

	private Entity getClosestVisiblePowerUp() {
		Entity closestVisiblePowerUp = null;
		int shortestDistance = Integer.MAX_VALUE;
		for (PowerUp powerUp : entity.getLevel().getPowerUps()) {
			if (powerUp.isVisible()) {
				int distanceFromPowerUp = entity.getLevel().getDistanceWithShortestPath(entity.getCoordinates(),
						powerUp.getCoordinates());
				if (distanceFromPowerUp < shortestDistance) {
					closestVisiblePowerUp = powerUp;
					shortestDistance = distanceFromPowerUp;
				}
			}
		}
		return closestVisiblePowerUp;
	}

}
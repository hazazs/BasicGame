package hu.hazazs.basicgame.move;

import hu.hazazs.basicgame.Direction;
import hu.hazazs.basicgame.entity.MovingEntity;

public class ChasingPlayerMovingStrategy extends AbstractMovingStrategy {

	public ChasingPlayerMovingStrategy(MovingEntity entity) {
		super(entity);
	}

	@Override
	public Direction calculateNewDirection() {
		return entity.getLevel().getDirectionWithShortestPath(entity.getCoordinates(),
				entity.getLevel().getPlayer().getCoordinates());
	}

}
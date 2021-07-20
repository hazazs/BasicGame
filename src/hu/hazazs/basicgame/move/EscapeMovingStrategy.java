package hu.hazazs.basicgame.move;

import hu.hazazs.basicgame.Direction;
import hu.hazazs.basicgame.entity.MovingEntity;

public class EscapeMovingStrategy extends AbstractMovingStrategy {

	public EscapeMovingStrategy(MovingEntity entity) {
		super(entity);
	}

	@Override
	public Direction calculateNewDirection() {
		if (entity.getCoordinates().isEqualTo(entity.getEscapeCoordinates())) {
			entity.setEscapeCoordinates(entity.getLevel().getEscapeCoordinates(entity.getCoordinates()));
		}
		return entity.getLevel().getDirectionWithShortestPath(entity.getCoordinates(), entity.getEscapeCoordinates());
	}

}
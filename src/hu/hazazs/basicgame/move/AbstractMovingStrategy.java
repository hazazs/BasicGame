package hu.hazazs.basicgame.move;

import hu.hazazs.basicgame.entity.MovingEntity;

public abstract class AbstractMovingStrategy implements MovingStrategy {

	final MovingEntity entity;

	AbstractMovingStrategy(MovingEntity entity) {
		this.entity = entity;
	}

}
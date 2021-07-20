package hu.hazazs.basicgame.entity;

import hu.hazazs.basicgame.Coordinates;
import hu.hazazs.basicgame.Level;

public abstract class PowerUp extends BasicEntity {

	private boolean visible;
	private int visibleCounter;
	private boolean active;
	private int activeCounter;

	PowerUp(Level level, String mark) {
		super(level, level.getRandomCoordinatesWithDistance(level.getPlayer().coordinates, 1), mark);
	}

	public boolean isVisible() {
		return visible;
	}

	private void show() {
		visible = true;
	}

	public void hide() {
		visible = false;
	}

	private int increaseVisibleCounter() {
		return ++visibleCounter;
	}

	private void resetVisibleCounter() {
		visibleCounter = 0;
	}

	public boolean isActive() {
		return active;
	}

	public void activate() {
		active = true;
	}

	void deactivate() {
		active = false;
	}

	private int increaseActiveCounter() {
		return ++activeCounter;
	}

	private void resetActiveCounter() {
		activeCounter = 0;
	}

	public void update(int visibleDuration, int activeDuration, Coordinates target) {
		if (active) {
			increaseActiveCounter();
		} else {
			increaseVisibleCounter();
		}
		if (activeCounter >= activeDuration) {
			deactivate();
			resetActiveCounter();
			resetVisibleCounter();
		}
		if (visibleCounter >= visibleDuration) {
			if (visible) {
				hide();
				coordinates = level.getRandomCoordinatesWithDistance(target, 1);
				if (!level.isAnyPowerUpVisible() && !level.isAnyStrengthPowerUpActive()) {
					level.getPlayer().setEscapeCoordinates(level.getEscapeCoordinates(level.getPlayer().coordinates));
				}
			} else {
				show();
			}
			resetVisibleCounter();
		}
	}

}
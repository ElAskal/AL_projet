package gogol.entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManagerDefaultImpl;
import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;
import gogol.util.MiddleAgeFactory;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Sanchez extends GameMovable implements Drawable, GameEntity,
	Overlappable {
	protected static DrawableImage image = null;
	protected boolean movable = true;
	private final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 24;
	public ArmedUnitGroup aug;
	public MiddleAgeFactory maf;
	
	public Sanchez(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/infatryman.gif",
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"down", "left", "right", "up");
		maf = new MiddleAgeFactory();
		aug = new ArmedUnitGroup(maf, "");
		aug.addUnit(new ArmedUnitSoldier(maf, "Simple", ""));
	}
	
	public ArmedUnitGroup getAug(){
		return aug;
	}
	
	public MiddleAgeFactory getMaf(){
		return maf;
	}
		
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (tmp.getX() == 1) {
			spriteType += "right";
		} else if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType = "up";
			spriteManager.reset();
			movable = false;
		}
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());

	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
		}
	}
	
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

}

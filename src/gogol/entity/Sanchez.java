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
	public static final int RENDERING_SIZE = 16;
	public ArmedUnitGroup aug;
	
	public Sanchez(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/infantryman.gif",
				defaultCanvas, RENDERING_SIZE, 6);
		spriteManager.setTypes(
				//
				"down", "left", "right", "up",
				//
				
				"static", "unused");
		MiddleAgeFactory maf = new MiddleAgeFactory();
		aug = new ArmedUnitGroup(maf, "");
		aug.addUnit(new ArmedUnitSoldier(maf, "Simple", ""));
	}
	
	public ArmedUnitGroup getAug(){
		return aug;
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
			spriteType = "static";
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

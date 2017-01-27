package gogol.entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManagerDefaultImpl;
import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;
import gogol.soldier.Horseman;
import gogol.util.MiddleAgeFactory;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cavalry extends GameMovable implements Drawable, GameEntity,
	Overlappable {
	protected static DrawableImage image = null;
	protected boolean movable = true;
	private final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 24;
	private Horseman h;
	public boolean beaten = false;
	public ArmedUnitGroup aug;
	
	public Cavalry(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/horseman.png",
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"down", "left", "right", "up",
				//
				"unused");
		MiddleAgeFactory maf = new MiddleAgeFactory();
		aug = new ArmedUnitGroup(maf, "");
		aug.addUnit(new ArmedUnitSoldier(maf, "Rider", ""));
	}
	
	public ArmedUnitGroup getAug(){
		return aug;
	}
	
	public boolean getBeaten(){
		return beaten;
	}
	
	public void setBeaten(boolean b){
		this.beaten = b;
	}
	
	public Horseman getHorseman(){
		return h;
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
			spriteType = "down";
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

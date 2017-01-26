package gogol.entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManagerDefaultImpl;
import gogol.soldier.Horseman;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cavalry extends GameMovable implements Drawable, GameEntity,
	Overlappable {
	protected static DrawableImage image = null;
	protected boolean movable = true;
	private final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 16;
	private Horseman h;
	public boolean beaten = false;
	
	public Cavalry(Canvas defaultCanvas, Horseman h) {
		spriteManager = new SpriteManagerDefaultImpl("images/horseman.gif",
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"down", "left", "right", "up",
				//
				
				"static", "unused");
		this.h = h;
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

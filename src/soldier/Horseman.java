package soldier;

public class Horseman extends SoldierAbstract {
	private static final int FORCE = 20;
	private static final int HEALTHPOINTS = 120;

	public Horseman(String name) {
		super(name, HEALTHPOINTS, FORCE);
	}

	public void heal() { // XXX resurrection allowed
		healthPoints = HEALTHPOINTS;
	}
}
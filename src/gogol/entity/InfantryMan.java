package gogol.entity;

public class InfantryMan extends SoldierAbstract {
	private static final int FORCE = 15;
	private static final int HEALTHPOINTS = 100;

	public InfantryMan(String nom) {
		super(nom, HEALTHPOINTS, FORCE);
	}

	public void heal() { // XXX resurrection allowed
		healthPoints = HEALTHPOINTS;
	}
}
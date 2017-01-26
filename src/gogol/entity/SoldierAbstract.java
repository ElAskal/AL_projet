package gogol.entity;

public abstract class SoldierAbstract implements Soldier {
	protected String name;
	protected float healthPoints;
	protected float force;

	public SoldierAbstract(String nom, float healthPoints, float force) {
		this.name = nom;
		this.healthPoints = healthPoints;
		this.force = force;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public float getHealthPoints() {
		return healthPoints;
	}

	@Override
	public boolean alive() {
		return getHealthPoints() > 0;
	}

	@Override
	public boolean parry(float force) { //default: no parry effect
		healthPoints = (getHealthPoints() > force) ? 
				               getHealthPoints() - force : 0;
	    return healthPoints > 0;
	}

	@Override
	public float strike() {
		return alive() ? force : 0; 
	} 
}

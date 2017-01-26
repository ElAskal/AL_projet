package soldier;

public interface Soldier {
	public String getName();

	public float getHealthPoints();

	public boolean alive();

	public void heal();

	public boolean parry(float force);
	// true if the receiver is still alive after the strike

	public float strike();
}

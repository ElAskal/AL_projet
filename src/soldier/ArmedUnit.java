package soldier;

import util.AgeFactory;
import util.VisitorClassicForArmedUnit;
import util.VisitorFunctForArmedUnit;

public interface ArmedUnit {
	public String getName();

	public float getHealthPoints();

	public AgeFactory getAge();

	public boolean alive();

	public void heal();

	public boolean parry(float force); // true if the receiver is still alive
										// after the strike

	public float strike();

	public void addEquipmentOneEach(String weaponType);

	public void accept(VisitorClassicForArmedUnit v);

	public <T> T accept(VisitorFunctForArmedUnit<T> v);
}
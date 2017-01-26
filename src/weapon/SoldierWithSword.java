package weapon;

import soldier.Soldier;

public class SoldierWithSword extends SoldierArmed<Sword> {

	public SoldierWithSword(Soldier s) {
		super(s, new Sword());
	}
}

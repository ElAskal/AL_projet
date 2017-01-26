package gogol.weapon;

import gogol.soldier.Soldier;

public class SoldierWithShield extends SoldierArmed<Shield> {

	public SoldierWithShield(Soldier s) {
		super(s, new Shield());
	}
}

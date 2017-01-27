package gogol.util;

import gogol.soldier.Horseman;
import gogol.soldier.InfantryMan;
import gogol.soldier.Soldier;
import gogol.soldier.SoldierAbstract;
import gogol.weapon.SoldierArmed;
import gogol.weapon.SoldierWithShield;
import gogol.weapon.SoldierWithSword;

public class MiddleAgeFactory implements AgeFactory {

	@Override
	public SoldierAbstract getSimpleSoldier(String name) {
		return new InfantryMan(name);
	}

	@Override
	public SoldierAbstract getRiderSoldier(String name) {
		return new Horseman(name);
	}

	@Override
	public SoldierArmed<?> addDefensiveWeapon(Soldier s) {
		return new SoldierWithShield(s);
	}

	@Override
	public SoldierArmed<?> addOffensiveWeapon(Soldier s) {
		return new SoldierWithSword(s);
	}
}

package util;

import soldier.*;
import weapon.*;

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

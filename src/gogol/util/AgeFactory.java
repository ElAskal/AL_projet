package gogol.util;

import soldier.Soldier;
import soldier.SoldierAbstract;
import weapon.SoldierArmed;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name);

	SoldierAbstract getRiderSoldier(String name);

	SoldierArmed<?> addDefensiveWeapon(Soldier s);

	SoldierArmed<?> addOffensiveWeapon(Soldier s);
}

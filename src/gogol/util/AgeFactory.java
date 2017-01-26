package gogol.util;

import gogol.soldier.Soldier;
import gogol.soldier.SoldierAbstract;
import gogol.weapon.SoldierArmed;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name);

	SoldierAbstract getRiderSoldier(String name);

	SoldierArmed<?> addDefensiveWeapon(Soldier s);

	SoldierArmed<?> addOffensiveWeapon(Soldier s);
}

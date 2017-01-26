package gogol.util;

import gogol.entity.Soldier;
import gogol.entity.SoldierAbstract;
import gogol.entity.SoldierArmed;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name);

	SoldierAbstract getRiderSoldier(String name);

	SoldierArmed<?> addDefensiveWeapon(Soldier s);

	SoldierArmed<?> addOffensiveWeapon(Soldier s);
}

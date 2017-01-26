package gogol.util;

import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;

public interface VisitorClassicForArmedUnit {
	void visit(ArmedUnitSoldier aus);

	void visit(ArmedUnitGroup a);
}

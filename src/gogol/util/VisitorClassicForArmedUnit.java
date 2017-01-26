package gogol.util;

import gogol.entity.ArmedUnitGroup;
import gogol.entity.ArmedUnitSoldier;

public interface VisitorClassicForArmedUnit {
	void visit(ArmedUnitSoldier aus);

	void visit(ArmedUnitGroup a);
}

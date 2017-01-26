package util;

import soldier.ArmedUnitGroup;
import soldier.ArmedUnitSoldier;

public interface VisitorClassicForArmedUnit {
	void visit(ArmedUnitSoldier s);

	void visit(ArmedUnitGroup a);
}

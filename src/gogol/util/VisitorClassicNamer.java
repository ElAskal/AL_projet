package gogol.util;

import gogol.entity.ArmedUnitGroup;
import gogol.entity.ArmedUnitSoldier;

public class VisitorClassicNamer implements VisitorClassicForArmedUnit {
	private String s = "";

	@Override
	public void visit(ArmedUnitSoldier f) {
		s += "Soldier " + f.getName() + "\n";
	}

	@Override
	public void visit(ArmedUnitGroup a) {
		s += "Group " + a.getName() + " : \n";
	}

	public void reset() {
		s = "";
	}

	public String getNames() {
		return s;
	}
}

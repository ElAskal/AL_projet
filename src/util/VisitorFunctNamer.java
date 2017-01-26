package util;

import soldier.ArmedUnitGroup;
import soldier.ArmedUnitSoldier;

public class VisitorFunctNamer implements VisitorFunctForArmedUnit<String> {

	@Override
	public String visit(ArmedUnitSoldier f) {
		return ("Soldier " + f.getName() + "\n");
	}

	@Override
	public String visit(ArmedUnitGroup a) {
		return ("Group " + a.getName() + " : \n");
	}

	@Override
	public String compos(String s1, String s2) {
		return s1 + s2;
	}
}

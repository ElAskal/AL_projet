package util;

import soldier.ArmedUnitGroup;
import soldier.ArmedUnitSoldier;

public class VisitorFunctCounter implements VisitorFunctForArmedUnit<Integer> {

	@Override
	public Integer visit(ArmedUnitSoldier f) {
		return 1;
	}

	@Override
	public Integer visit(ArmedUnitGroup a) {
		return 0;
	}

	@Override
	public Integer compos(Integer i1, Integer i2) {
		return i1 + i2;
	}
}

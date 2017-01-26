package gogol.util;

import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;

public class VisitorClassicCounter implements VisitorClassicForArmedUnit {
	private Integer count = 0;

	@Override
	public void visit(ArmedUnitSoldier f) {
		count++;
	}

	@Override
	public void visit(ArmedUnitGroup a) {
	}

	public void reset() {
		count = 0;
	}

	public Integer getCount() {
		return count;
	}
}

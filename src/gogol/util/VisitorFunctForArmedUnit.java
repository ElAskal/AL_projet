package gogol.util;

import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;

public interface VisitorFunctForArmedUnit<T> {
	T visit(ArmedUnitSoldier s);

	T visit(ArmedUnitGroup a);

	T compos(T x1, T x2);
}

package gogol.util;

import gogol.entity.ArmedUnitGroup;
import gogol.entity.ArmedUnitSoldier;

public interface VisitorFunctForArmedUnit<T> {
	T visit(ArmedUnitSoldier s);

	T visit(ArmedUnitGroup a);

	T compos(T x1, T x2);
}

package gogol.util;

import java.util.ArrayList;
import java.util.List;

import gogol.soldier.ArmedUnit;
import gogol.soldier.ArmedUnitSoldier;

public class ObserverBodyCount implements Observer<ArmedUnit> {

	private static ObserverBodyCount uniqueInstance;

	private ObserverBodyCount() {} // inhibition of the default 

	public static synchronized ObserverBodyCount getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ObserverBodyCount();
		return uniqueInstance;
	}

	protected int count = 0;
	// for memory purpose:
	List<String> deads = new ArrayList<String>();

	@Override
	public void update(ArmedUnit s) {
		if ((!s.alive()) && (!deads.contains(s.getName()))
				&& (s instanceof ArmedUnitSoldier)) {
			count++;
			deads.add(s.getName());
			System.out.println("Current body count: " + count);
		}
	}

	public void resetCount() {
		count = 0;
	}
}

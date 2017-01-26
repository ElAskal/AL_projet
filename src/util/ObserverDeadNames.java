package util;

import java.util.ArrayList;
import java.util.List;

import soldier.ArmedUnit;

public class ObserverDeadNames implements Observer<ArmedUnit> {

	private static ObserverDeadNames uniqueInstance;

	private ObserverDeadNames() {}// inhibition of the default 

	public static synchronized ObserverDeadNames getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ObserverDeadNames();
		return uniqueInstance;
	}

	// for memory purpose:
	protected List<String> deads = new ArrayList<String>();  

	@Override
	public void update(ArmedUnit s) {
		if ((!s.alive()) && (!deads.contains(s.getName()))) {
			deads.add(s.getName());
			System.out.println(s.getName() + " is dead");
		}
	}

	public void resetDeadNames() {
		deads = new ArrayList<String>();
	}
}

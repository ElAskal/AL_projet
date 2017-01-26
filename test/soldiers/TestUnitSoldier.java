package soldiers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soldier.ArmedUnit;
import soldier.ArmedUnitSoldier;
import soldier.UnknownSoldierTypeException;
import util.*;

public class TestUnitSoldier {
	ArmedUnit sf, sc;
    AgeFactory age; 
    
	@Before
	public void setUp() throws Exception {
		age = new MiddleAgeFactory();
		sf = new ArmedUnitSoldier(age, "Simple", "Gogol");
		sc = new ArmedUnitSoldier(age, "Rider", "Sanchez");
	}

	@Test(expected = UnknownSoldierTypeException.class)
	public void combat() {
		int i;
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of squad " + sf.getName(), i, 4);

		sf.heal();
 		sf.addEquipmentOneEach("Defensive");
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of  " + sf.getName() + " with defensive", i, 9);

		sf.heal();
		sf.addEquipmentOneEach("Offensive");
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of " + sf.getName() + " with defensive and offensive", i, 11);
 		 
        sf.heal();
		for (i = 0; sc.parry(sf.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of " + sc.getName(), i, 3);
 
 		new ArmedUnitSoldier(age, "Poilu", "Gogol"); //exception raised : unknown soldier type
	}
}

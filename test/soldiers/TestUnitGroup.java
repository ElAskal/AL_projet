package soldiers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soldier.ArmedUnit;
import soldier.ArmedUnitGroup;
import soldier.ArmedUnitSoldier;
import util.AgeFactory;
import util.MiddleAgeFactory;

public class TestUnitGroup {
	ArmedUnitGroup squad;
	ArmedUnit sf1, sf2, sc1, sc2, s;
	AgeFactory age;

	@Before
	public void setUp() throws Exception {
		age = new MiddleAgeFactory();
		squad = new ArmedUnitGroup(age, "Lincoln");
		sf1 = new ArmedUnitSoldier(age, "Simple", "Gogol1");
		sf2 = new ArmedUnitSoldier(age, "Simple", "Gogol2");
		sc1 = new ArmedUnitSoldier(age, "Rider", "Sanchez1");
		sc2 = new ArmedUnitSoldier(age, "Rider", "Sanchez2");
		s = new ArmedUnitSoldier(age, "Rider", "St Georges");

		squad.addUnit(sf1);
		squad.addUnit(sf2);
		squad.addUnit(sc1);
		squad.addUnit(sc2);
	}

	@Test
	public void combats() {
		int i;
		s.addEquipmentOneEach("Offensive");
		for (i = 0; squad.parry(s.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of squad " + squad.getName()
				+ "with offensive", i, 12);

		squad.heal();
		squad.addEquipmentOneEach("Defensive");
		s.heal();

		for (i = 0; squad.parry(s.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of squad " + squad.getName()
				+ " with defensive", i, 102);
	}
}

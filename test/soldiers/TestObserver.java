package soldiers;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import soldier.ArmedUnitSoldier;
import soldier.ArmedUnitGroup;
import util.*;


public class TestObserver {
	@Test
	public void observers() throws Exception {
		ArmedUnitGroup squad1, squad2;
		ArmedUnitSoldier sf1, sf2, sc1, sc2, sc3;
		AgeFactory age; 

		age = new MiddleAgeFactory();

		squad1 = new ArmedUnitGroup(age, "Lincoln Squad");
		squad2 = new ArmedUnitGroup(age, "Kieffer Squad");
		sf1 = new ArmedUnitSoldier(age, "Simple", "Gogol1");
		sc1 = new ArmedUnitSoldier(age, "Rider", "Sanchez1");
		sf2 = new ArmedUnitSoldier(age, "Simple", "Gogol2");
		sc2 = new ArmedUnitSoldier(age, "Rider", "Sanchez2");
		sc3 = new ArmedUnitSoldier(age, "Rider", "St Georges");

		squad1.addUnit(sf1); squad1.addUnit(sc1);
		squad2.addUnit(sf2); squad2.addUnit(sc2); squad2.addUnit(sc3);

		ObserverDeadNames ob1 = ObserverDeadNames.getInstance();
		ObserverBodyCount ob2 = ObserverBodyCount.getInstance();

		squad1.register(ob1); squad2.register(ob1);
		sf1.register(ob1); sc1.register(ob1);
		sf2.register(ob1); sc2.register(ob1); sc3.register(ob1);

		sf1.register(ob2); sc1.register(ob2);
		sf2.register(ob2); sc2.register(ob2); sc3.register(ob2);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		int i;  
		for (i = 0; squad2.parry(squad1.strike()); i++) {
			;
		}
		assertEquals(i, 9);
		assertEquals(
				"Gogol2 is dead\r\nCurrent body count: 1\r\nKieffer Squad is dead\r\n",
				stream.toString());

	}
}


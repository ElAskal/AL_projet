package soldiers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import soldier.*; 
import util.*;

	
public class TestVisitorClassicFactory {
	@Test
	public void visitors() throws Exception {
		ArmedUnitGroup squad1, squad2;
		ArmedUnit sf1, sf2, sc1, sc2, s;
		AgeFactory age;

	    age = new MiddleAgeFactory();
	    
		squad1 = new ArmedUnitGroup(age, "Lincoln");
		squad2 = new ArmedUnitGroup(age, "Kieffer");
		sf1 = new ArmedUnitSoldier(age, "Simple", "Gogol1");
		sf2 = new ArmedUnitSoldier(age, "Simple", "Gogol2");
		sc1 = new ArmedUnitSoldier(age, "Rider", "Sanchez1");
		sc2 = new ArmedUnitSoldier(age, "Rider", "Sanchez2");
		s = new ArmedUnitSoldier(age, "Rider", "St Georges");

		squad1.addUnit(sf1);
		squad1.addUnit(sf2);
		squad2.addUnit(sc1);
		squad2.addUnit(sc2);
		squad2.addUnit(s);

		// CLASSIC VISITORS
		VisitorClassicNamer v = new VisitorClassicNamer();
		squad1.accept(v);
		assertEquals("Group Lincoln : \nSoldier Gogol1\nSoldier Gogol2\n", v.getNames());
        v.reset();		
		squad2.accept(v);
		assertEquals("Group Kieffer : \nSoldier Sanchez1\nSoldier Sanchez2\nSoldier St Georges\n", v.getNames());

		VisitorClassicCounter vi = new VisitorClassicCounter();
		squad1.accept(vi);
		assertEquals((Integer)2, vi.getCount());
		vi.reset();
		squad2.accept(vi);
		assertEquals((Integer)3, vi.getCount());

	    // FUNCTIONAL VISITORS
		VisitorFunctForArmedUnit<String> v2 = new VisitorFunctNamer();
		assertEquals("Group Lincoln : \nSoldier Gogol1\nSoldier Gogol2\n", 
				squad1.accept(v2));
		assertEquals("Group Kieffer : \nSoldier Sanchez1\nSoldier Sanchez2\nSoldier St Georges\n", 
				squad2.accept(v2));

		VisitorFunctForArmedUnit<Integer> v2i = new VisitorFunctCounter();
		assertEquals((Integer)2, squad1.accept(v2i));
		assertEquals((Integer)3, squad2.accept(v2i));
	}

}
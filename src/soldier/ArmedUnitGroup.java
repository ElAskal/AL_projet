package soldier;

import java.util.ArrayList;
import java.util.List;

import util.AgeFactory;
import util.BadAgeException;
import util.ObservableAbstract;
import util.VisitorClassicForArmedUnit;
import util.VisitorFunctForArmedUnit;


public class ArmedUnitGroup extends ObservableAbstract<ArmedUnit> 
                            implements ArmedUnit {
	protected List<ArmedUnit> armedUnitList = new ArrayList<ArmedUnit>();
	protected String name;
	protected AgeFactory age;

	public ArmedUnitGroup(AgeFactory factory, String name) {
		this.age = factory;
		this.name = name;
	}

	public void addUnit(ArmedUnit s) {
		if (s.getAge() == age) // historical coherence checked
			armedUnitList.add(s);
		else
			throw new BadAgeException();
	}

	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public float getHealthPoints() {
		float points = 0;
		for (ArmedUnit s : armedUnitList) {
			points += s.getHealthPoints();
		}
		return points;
	}
	
	@Override
	public AgeFactory getAge() {
		return age;
	}
	
	@Override
	public boolean alive() {
		boolean alive = false;
		for (ArmedUnit s : armedUnitList) {
			alive = alive || (s.alive());
		}
		return alive;
	}
	
	
	@Override
	public void heal() {
		for (ArmedUnit s : armedUnitList) {
			s.heal();
		}
	}
	
	@Override
	public void addEquipmentOneEach(String equipmentType) {
		if (alive()) {
			// Every soldier of a unit gets the same equipment
			for (ArmedUnit s : armedUnitList) {
				if (s.alive())
					s.addEquipmentOneEach(equipmentType);
			}
		}
	}

	private int getStillAliveSoldiers() {
		int stillAlive = 0;
		for (ArmedUnit s : armedUnitList) {
			if (s.alive())
				stillAlive += 1;
		}
		return stillAlive;
	}
	
	@Override
	public boolean parry(float force) {
		boolean result = false;
		if (alive()) {
			float forcePart = force / getStillAliveSoldiers();
			// Each alive soldier takes an equal part in each strike
			for (ArmedUnit s : armedUnitList) {
				result = (s.parry(forcePart)) || result;
			}
		}
		notify(this);
		return result;
	}
	
	@Override
	public float strike() {
		float result = 0;
		if (alive()) {
			for (ArmedUnit s : armedUnitList) {
				result += s.strike();
			}
		}
		return result;
	}
	
	@Override
	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
		for (ArmedUnit s : armedUnitList) {
			s.accept(v);
		}
	}
	
	@Override
	public <T> T accept(VisitorFunctForArmedUnit<T> v) {
		T result = v.visit(this);
		for (ArmedUnit s : armedUnitList) {
			result = v.compos(result, s.accept(v));
		}
		return result;
	}

}

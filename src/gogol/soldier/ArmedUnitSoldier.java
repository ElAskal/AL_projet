package gogol.soldier;

import java.util.ArrayList;
import java.util.List;

import gogol.util.*;
import gogol.except.*;

public class ArmedUnitSoldier extends ObservableAbstract<ArmedUnit> 
                              implements ArmedUnit {
	protected Soldier soldier;
	protected List<String> equipments = new ArrayList<String>();
	protected AgeFactory age;

	public ArmedUnitSoldier(AgeFactory factory, String soldierType, String name) {
		this.age = factory;
		if (soldierType == "Simple")
			soldier = age.getSimpleSoldier(name);
		else if (soldierType == "Rider")
			soldier = age.getRiderSoldier(name);
		else
			throw new UnknownSoldierTypeException("Unknown soldier type");
	}

	public void addEquipmentOneEach(String equipmentType) {
		if (equipments.contains(equipmentType)) {
			throw new BreakingRuleException(
					"too many weapon occurrences of this kind");
		} else {
			if (equipmentType == "Offensive")
				soldier = age.addOffensiveWeapon(soldier);
			else if (equipmentType == "Defensive")
				soldier = age.addDefensiveWeapon(soldier);
			else
				throw new UnknownWeaponTypeException("Unknown weapon type");
		}
	}
	
	@Override
	public String getName() {
		return soldier.getName();
	}
	
	@Override
	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}
	
	@Override
	public AgeFactory getAge() {
		return age;
	}
	
	@Override
	public boolean alive() {
		return soldier.alive();
	}
	
	@Override
	public void heal() {
		soldier.heal();
	}
	
	@Override
	public float strike() {
		return soldier.strike();
	}
	
	@Override
	public boolean parry(float force) {
		notify(this);
		return soldier.parry(force);
	}
	
	@Override
	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
	}
	
	@Override
	public <T> T accept(VisitorFunctForArmedUnit<T> v) {
		return v.visit(this);
	}

}

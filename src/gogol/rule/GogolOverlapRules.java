package gogol.rule;

import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.MoveStrategyDefaultImpl;
import gameframework.moves_rules.MoveStrategyRandom;
import gameframework.moves_rules.MoveStrategyStraightLine;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import gogol.entity.Jail;
import gogol.entity.Pacgum;
import gogol.entity.TeleportPairOfPoints;
import gogol.entity.Sanchez;
import gogol.entity.Shield;
import gogol.entity.Sword;
import gogol.entity.Cavalry;
import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;
import gogol.soldier.Horseman;
import gogol.soldier.Soldier;
import gogol.util.MiddleAgeFactory;
import gogol.util.VisitorClassicCounter;
import gogol.weapon.SoldierWithSword;
import gogol.GameLevelOne;

public class GogolOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Vector<Cavalry> vCavalries = new Vector<Cavalry>();

	protected Point sanchezStartPos;
	protected Point cavalryStartPos;
	protected boolean manageSanchezDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Boolean> endOfGame;
	private int totalNbGums = 0;
	private int nbEatenGums = 0;

	public GogolOverlapRules(Point sPos, Point cPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		sanchezStartPos = (Point) sPos.clone();
		cavalryStartPos = (Point) cPos.clone();
		this.life = life;
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void setTotalNbGums(int totalNbGums) {
		this.totalNbGums = totalNbGums;
	}

	public void addCavalry(Cavalry c) {
		vCavalries.addElement(c);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		manageSanchezDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Sanchez s, Cavalry c) {
		if(!(c.getBeaten())){
			ArmedUnitGroup aug = s.getAug();
			ArmedUnitGroup aug2 = c.getAug();
			System.out.println("PV de Sanchez avant overlap: "+aug.getHealthPoints()+"\n");
			System.out.println("PV de la cavalerie avant overlap: "+aug2.getHealthPoints()+"\n");
			while(aug.alive() && aug2.alive()){
				MoveStrategyDefaultImpl strat = new MoveStrategyDefaultImpl();
				GameMovableDriverDefaultImpl CavalryDriv = (GameMovableDriverDefaultImpl) c
						.getDriver();
				CavalryDriv.setStrategy(strat);
				aug2.parry(aug.strike());
				if(aug2.alive())
					aug.parry(aug2.strike());
			}
			if(aug.alive()){
				c.setBeaten(true);
				score.setValue(score.getValue() + 25);
				victoryByPoints();
				aug.addUnit(new ArmedUnitSoldier(s.getMaf(), "Rider", ""));
				VisitorClassicCounter vcc = new VisitorClassicCounter();
				vcc.visit(aug);
				System.out.println("Compteur du visiteur = "+vcc.getCount());
				if(vcc.getCount() >= 6)
					endOfGame.setValue(true);
			}
			else
				if(manageSanchezDeath){
					life.setValue(life.getValue() - 1);
					aug.heal();
					s.setPosition(sanchezStartPos);
					GameLevelOne.fillCavalries(vCavalries);
					for (Cavalry cav : vCavalries) {
						cav.setPosition(cavalryStartPos);
					}
					manageSanchezDeath = false;
				}
		}
		/* if (!p.isVulnerable()) {
			if (g.isActive()) {
				g.setAlive(false);
				MoveStrategyStraightLine strat = new MoveStrategyStraightLine(
						g.getPosition(), horsemanStartPos);
				GameMovableDriverDefaultImpl HorsemanDriv = (GameMovableDriverDefaultImpl) g
						.getDriver();
				HorsemanDriv.setStrategy(strat);

			}
		} else {
			if (g.isActive()) {
				if (manageSanchezDeath) {
					life.setValue(life.getValue() - 1);
					p.setPosition(sanchezStartPos);
					for (Horseman Horseman : vHorsemans) {
						Horseman.setPosition(horsemanStartPos);
					}
					manageSanchezDeath = false;
				}
			}
		}*/
	}

	public void overlapRule(Cavalry c, Sword s) {
		universe.removeGameEntity(s);
		c.getAug().addEquipmentOneEach("Offensive");
	}
	
	public void overlapRule(Cavalry c, Shield s) {
		universe.removeGameEntity(s);
		c.getAug().addEquipmentOneEach("Defensive");
	}

	public void overlapRule(Cavalry c, Pacgum pg) {
	}

	public void overlapRule(Cavalry c, TeleportPairOfPoints teleport) {
		c.setPosition(teleport.getDestination());
	}

	public void overlapRule(Sanchez s, TeleportPairOfPoints teleport) {
		s.setPosition(teleport.getDestination());
	}

	public void overlapRule(Cavalry c, Jail jail) {

	}

	public void overlapRule(Sanchez sa, Sword sw) {
		score.setValue(score.getValue() + 5);
		victoryByPoints();
		universe.removeGameEntity(sw);
		sa.getAug().addEquipmentOneEach("Offensive");
	}
	
	public void overlapRule(Sanchez sa, Shield sh) {
		score.setValue(score.getValue() + 5);
		victoryByPoints();
		universe.removeGameEntity(sh);
		sa.getAug().addEquipmentOneEach("Defensive");
	}

	public void overlapRule(Sanchez s, Pacgum pg) {
		score.setValue(score.getValue() + 1);
		victoryByPoints();
		universe.removeGameEntity(pg);
		pacgumEatenHandler();
	}

	private void pacgumEatenHandler() {
		nbEatenGums++;
	}
	
	private void victoryByPoints(){
		if(score.getValue() >= 150)
			endOfGame.setValue(true);
	}
}

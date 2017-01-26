package gogol.rule;

import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
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
import gogol.soldier.Horseman;
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
		ArmedUnitGroup aug = s.getAug();
		Horseman h = c.getHorseman();
		System.out.println("PV de Sanchez avant overlap: "+aug.getHealthPoints()+"\n");
		System.out.println("PV de la cavalerie avant overlap: "+h.getHealthPoints()+"\n");
		// TODO : Rendre s et h immobiles
		while(aug.alive() && h.alive()){
			h.parry(aug.strike());
			if(h.alive())
				aug.parry(h.strike());
		}
		if(aug.alive()){
			vCavalries.remove(c);
			if(vCavalries.isEmpty())
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
		universe.removeGameEntity(sw);
		sa.getAug().addEquipmentOneEach("Offensive");
		pacgumEatenHandler();
	}
	
	public void overlapRule(Sanchez sa, Shield sh) {
		score.setValue(score.getValue() + 5);
		universe.removeGameEntity(sh);
		sa.getAug().addEquipmentOneEach("Defensive");
		pacgumEatenHandler();
	}

	public void overlapRule(Sanchez s, Pacgum pg) {
		score.setValue(score.getValue() + 1);
		universe.removeGameEntity(pg);
		pacgumEatenHandler();
	}

	private void pacgumEatenHandler() {
		nbEatenGums++;
	}
}

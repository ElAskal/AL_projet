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

import gogol.entity.Horseman;
import gogol.entity.Jail;
import gogol.entity.Pacgum;
import gogol.entity.ArmedUnitGroup;
import gogol.entity.Sword;
import gogol.entity.TeleportPairOfPoints;

import soldier.Horseman; // TODO : Remove these when integrated
import soldier.ArmedUnitGroup;

public class GogolOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Vector<Horseman> vHorsemans = new Vector<Horseman>();

	protected Point sanchezStartPos;
	protected Point horsemanStartPos;
	protected boolean manageSanchezDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Float> health;
	private final ObservableValue<Integer> xp;
	private final ObservableValue<Boolean> endOfGame;
	private int totalNbGums = 0;
	private int nbEatenGums = 0;
	private static final int NB_HORSEMANS = 5;

	public GogolOverlapRules(Point pacPos, Point gPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame, ObservableValue<Integer> xp,
			ObservableValue<Float> health) {
		sanchezStartPos = (Point) pacPos.clone();
		horsemanStartPos = (Point) gPos.clone();
		this.life = life;
		this.score = score;
		this.endOfGame = endOfGame;
		this.xp = xp;
		this.health = health;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void setTotalNbGums(int totalNbGums) {
		this.totalNbGums = totalNbGums;
	}

	public void addHorseman(Horseman g) {
		vHorsemans.addElement(g);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		manageSanchezDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(ArmedUnitGroup aug, Horseman h) {
		// TODO : Rendre aug et h immobiles
		while(aug.alive() && h.alive()){
			h.parry(aug.strike());
			if(h.alive())
				aug.parry(h.strike());
		}
		if(aug.alive()){
			health.setValue(aug.getHealthPoints());
			xp.setValue(xp.getValue() + 25);
			vHorsemans.remove(h);
			if(vHorsemans.isEmpty())
				endOfGame.setValue(true);
		}
		else
			if(manageSanchezDeath){
				life.setValue(life.getValue() - 1);
				aug.heal();
				health.setValue(aug.getHealthPoints());
				aug.setPosition(sanchezStartPos);
				while(vHorsemans.size() < NB_HORSEMANS)
					vHorsemans.addElement(new Horseman(""));
				for (Horseman horse : vHorsemans) {
					horse.setPosition(horsemanStartPos);
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

	public void overlapRule(Horseman h, Sword s) {
	}

	public void overlapRule(Horseman h, Pacgum pg) {
	}

	public void overlapRule(Horseman h, TeleportPairOfPoints teleport) {
		h.setPosition(teleport.getDestination());
	}

	public void overlapRule(ArmedUnitGroup aug, TeleportPairOfPoints teleport) {
		aug.setPosition(teleport.getDestination());
	}

	public void overlapRule(Horseman h, Jail jail) {

	}

	public void overlapRule(ArmedUnitGroup aug, Sword s) {
		score.setValue(score.getValue() + 5);
		universe.removeGameEntity(s);
		aug.addEquipmentOneEach("Offensive");
		pacgumEatenHandler();
	}
	
	public void overlapRule(ArmedUnitGroup aug, Shield s) {
		score.setValue(score.getValue() + 5);
		universe.removeGameEntity(s);
		aug.addEquipmentOneEach("Defensive");
		pacgumEatenHandler();
	}

	public void overlapRule(ArmedUnitGroup aug, Pacgum pg) {
		score.setValue(score.getValue() + 1);
		xp.setValue(xp.getValue() + 1);
		universe.removeGameEntity(pg);
		pacgumEatenHandler();
	}

	private void pacgumEatenHandler() {
		nbEatenGums++;
	}
}

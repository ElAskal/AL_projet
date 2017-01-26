package gogol.rule;

import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.MoveStrategyRandom;
import gameframework.moves_rules.MoveStrategyStraightLine;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.awt.Canvas;
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

public class GogolOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Vector<Cavalry> vCavalries = new Vector<Cavalry>();

	protected Point sanchezStartPos;
	protected Point cavalryStartPos;
	protected boolean manageSanchezDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Float> health;
	private final ObservableValue<Integer> xp;
	private final ObservableValue<Boolean> endOfGame;
	private int totalNbGums = 0;
	private int nbEatenGums = 0;
	private static final int NB_HORSEMANS = 5;

	public GogolOverlapRules(Point sPos, Point cPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame, ObservableValue<Integer> xp,
			ObservableValue<Float> health) {
		sanchezStartPos = (Point) sPos.clone();
		cavalryStartPos = (Point) cPos.clone();
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
		// TODO : Rendre s et h immobiles
		while(aug.alive() && h.alive()){
			h.parry(aug.strike());
			if(h.alive())
				aug.parry(h.strike());
		}
		if(aug.alive()){
			health.setValue(aug.getHealthPoints());
			xp.setValue(xp.getValue() + 25);
			vCavalries.remove(c);
			if(vCavalries.isEmpty())
				endOfGame.setValue(true);
		}
		else
			if(manageSanchezDeath){
				life.setValue(life.getValue() - 1);
				aug.heal();
				health.setValue(aug.getHealthPoints());
				s.setPosition(sanchezStartPos);
				while(vCavalries.size() < NB_HORSEMANS)
					vCavalries.addElement(new Cavalry(new Canvas(), new Horseman("")));
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
		xp.setValue(xp.getValue() + 1);
		universe.removeGameEntity(pg);
		pacgumEatenHandler();
	}

	private void pacgumEatenHandler() {
		nbEatenGums++;
	}
}

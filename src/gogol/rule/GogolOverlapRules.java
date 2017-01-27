package gogol.rule;

import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.MoveStrategyDefaultImpl;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import gogol.entity.Jail;
import gogol.entity.Ruby;
import gogol.entity.TeleportPairOfPoints;
import gogol.entity.Sanchez;
import gogol.entity.Shield;
import gogol.entity.Sword;
import gogol.entity.Cavalry;
import gogol.soldier.ArmedUnitGroup;
import gogol.soldier.ArmedUnitSoldier;
import gogol.util.VisitorClassicCounter;
import gogol.GameLevelOne;

public class GogolOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Vector<Cavalry> vCavalries = new Vector<Cavalry>();
	private static final int SCORE_TO_WIN = 125;
	private static final int ARMY_SIZE_TO_WIN = 5;

	protected Point sanchezStartPos;
	protected Point cavalryStartPos;
	protected boolean manageSanchezDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Float> health;
	private final ObservableValue<Integer> army;
	private final ObservableValue<Boolean> endOfGame;

	public GogolOverlapRules(Point sPos, Point cPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Float> health, ObservableValue<Integer> army,
			ObservableValue<Boolean> endOfGame) {
		sanchezStartPos = (Point) sPos.clone();
		cavalryStartPos = (Point) cPos.clone();
		this.life = life;
		this.score = score;
		this.health = health;
		this.army = army;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
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
			System.out.println("PV de l'ennemi avant la bataille: "+aug2.getHealthPoints()+"\n");
			aug2.parry(aug.strike()); // Sanchez always strikes first, it is harder to strike on a horse.
			if(Math.random() >= 0.9 && aug2.alive()){
				aug2.parry(aug.strike());
				System.out.println("Sanchez inflige un coup critique !\n");
			}
			if(!(aug2.alive())){
				System.out.println("L'ennemi est vaincu !");
				c.setBeaten(true);
				MoveStrategyDefaultImpl strat = new MoveStrategyDefaultImpl();
				GameMovableDriverDefaultImpl CavalryDriv = (GameMovableDriverDefaultImpl) c
					.getDriver();
				CavalryDriv.setStrategy(strat);
				score.setValue(score.getValue() + 25);
				victoryByPoints();
				health.setValue(aug.getHealthPoints());
				army.setValue(army.getValue() + 1);
				if(army.getValue() >= ARMY_SIZE_TO_WIN)
					endOfGame.setValue(true);
			}
			else{
				aug.parry(aug2.strike());
				if(Math.random() >= 0.9 && aug.alive()){
					aug.parry(aug2.strike());
					System.out.println("L'ennemi inflige un coup critique !\n");
				}
			}
			if(!(aug.alive())){
				System.out.println("Sanchez est vaincu !");
				if(manageSanchezDeath){
					life.setValue(life.getValue() - 1);
					score.setValue(0);
					aug.heal();
					health.setValue(aug.getHealthPoints());
					s.setPosition(sanchezStartPos);
					GameLevelOne.fillCavalries(vCavalries);
					for (Cavalry cav : vCavalries) {
						cav.setPosition(cavalryStartPos);
					}
					manageSanchezDeath = false;
				}
			}
			else{
				System.out.println("PV de la cavalerie après la bataille: "+aug2.getHealthPoints()+"\n");
				health.setValue(aug.getHealthPoints());
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

	public void overlapRule(Cavalry c, Ruby pg) {
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

	public void overlapRule(Sanchez s, Ruby pg) {
		score.setValue(score.getValue() + 1);
		victoryByPoints();
		universe.removeGameEntity(pg);
	}
	
	private void victoryByPoints(){
		if(score.getValue() >= SCORE_TO_WIN)
			endOfGame.setValue(true);
	}
}

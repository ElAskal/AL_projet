package gogol.rule;

import gameframework.moves_rules.IllegalMoveException;
import gameframework.moves_rules.MoveBlockerRulesApplierDefaultImpl;
import pacman.entity.Ghost;
import pacman.entity.Wall;

public class GogolMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(Horseman h, Wall w) throws IllegalMoveException {
		// The default case is when a ghost is active and not able to cross a
		// wall
		if (h.isActive()) {
			throw new IllegalMoveException();
			// When a ghost is not active, it is able to cross a wall
		}
	}
}

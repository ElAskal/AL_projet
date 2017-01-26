package weapon;

public class Shield extends WeaponAbstract {
	private static final float DEFENSE = 10;
	private static final float ATTACK = 0;
	private static final float RESISTANCE = 1;

	public Shield() {
		super(DEFENSE, ATTACK, RESISTANCE);
	}

	public void fix() {
		resistance = RESISTANCE;
	}
}
package gogol.weapon;

public abstract class WeaponAbstract implements Weapon {
	protected float defense;
	protected float attack;
	protected float resistance; // multiplicative coef for weapon resistance  

	public WeaponAbstract(float defense, float attack, float resistance) {
		this.defense = defense;
		this.attack = attack;
		this.resistance = resistance;
	}

	@Override
	public float getParryValue() {
		return defense * resistance;
	}

	@Override
	public float getStrikeValue() {
		return attack * resistance;
	}

	@Override
	public float getResistanceToDamage() {
		return resistance;
	}

	@Override
	public void damageCompute(float coef) {
		resistance -= coef;
		if (resistance < 0)
			resistance = 0;
	}
}

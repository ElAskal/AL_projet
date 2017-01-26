package soldier;

public class UnknownWeaponTypeException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;

	public UnknownWeaponTypeException() {
		super();
	}

	public UnknownWeaponTypeException(String arg0) {
		super(arg0);
	}

	public UnknownWeaponTypeException(Throwable arg0) {
		super(arg0);
	}

	public UnknownWeaponTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}

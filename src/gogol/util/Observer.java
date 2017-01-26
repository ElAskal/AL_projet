package gogol.util;

public interface Observer<S> {
	void update(S s);
}

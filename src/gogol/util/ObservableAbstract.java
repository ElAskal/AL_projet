package gogol.util;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameframework.core.GameMovable;

public class ObservableAbstract<S> implements Observable<S> {
	private ArrayList<Observer<S>> observers = new ArrayList<Observer<S>>();

	@Override	
	public void register(Observer<S> ob) {
		observers.add(ob);
	}

	@Override
	public void unregister(Observer<S> ob) {
		observers.remove(ob);
	}

	@Override
	public void notify(S s) {
		for (Observer<S> ob : observers)
			ob.update(s);
	}
}

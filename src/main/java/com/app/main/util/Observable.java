package com.app.main.util;

import java.util.List;

/**
 * The Observable interface defines a contract for objects that can be
 * observed
 * by other objects (observers). It provides methods to manage observers and
 * notify
 * them of changes or events.
 *
 * 
 * Features:
 * 
 * Allows adding and removing observers.
 * Notifies all registered observers of changes or actions.
 * Prevents duplicate observers from being added.
 * 
 * Example:
 * 
 * public class MyObservableClass implements Observable {
 * 	private final List<Observer> observers = new ArrayList<>();
 * 
 * 	@Override
 * 	public List<Observer> getObservers() {
 * 		return observers;
 * 	}
 * }
 * 
 * 
 * @author Daie Elias
 * @author Mohamed Ibrir
 * @version 1.0
 */
public interface Observable {

	/**
	 * Returns the list of observers registered to this observable object.
	 * 
	 * @return A list of observers.
	 */
	List<Observer> getObservers();

	/**
	 * Adds an observer to the list of observers if it is not already present.
	 * 
	 * @param observer The observer to add.
	 */
	default void addObserver(Observer observer) {
		if (!getObservers().contains(observer)) { // Prevent duplicates
			getObservers().add(observer);
		}
	}

	/**
	 * Removes an observer from the list of observers.
	 * 
	 * @param observer The observer to remove.
	 */
	default void removeObserver(Observer observer) {
		getObservers().remove(observer);
	}

	default void removeAllObservers() {
		getObservers().clear();
	}

	/**
	 * Notifies all registered observers of a change or action.
	 * 
	 * @param observable  The observable object that triggered the notification.
	 * @param arg    An optional argument providing additional context for the
	 *               notification.
	 * @param action A string describing the action or event that occurred.
	 */
	default void notifyObservers(Observable observable, Object arg, String action) {
		for (Observer observer : getObservers()) {
			observer.update(observable, arg, action);
		}
	}
}
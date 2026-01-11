package com.app.main.util;

/**
 *
 * The Observer interface defines a contract for objects that wish to be
 * notified
 * of changes or events occurring in an Observable object. It follows the
 * Observer design pattern, where observers are updated whenever the observable
 * object triggers a notification.
 * 
 * 
 * Features:
 * 
 * Allows objects to react to changes in observable objects.
 * Supports passing additional context and action details during
 * updates.
 * 
 * 
 * Usage Example:
 *
 * public class MyObserver implements Observer {
 *     @Override
 *     public void update(Observable o, Object arg, String action) {
 *         System.out.println("Received update from: " + o);
 *         System.out.println("Action: " + action);
 *         if (arg != null) {
 *             System.out.println("Additional context: " + arg);
 *         }
 *     }
 * }
 * 
 * @see Observable
 * @author Dai Elias
 * @author Mohamed Ibrir
 * @version 1.0
 */
public interface Observer {

    /**
     * Called when the observable object notifies its observers of a change or
     * event.
     * 
     * @param o      The observable object that triggered the update.
     * @param arg    An optional argument providing additional context for the
     *               update.
     * @param action A string describing the action or event that occurred.
     */
    void update(Observable o, Object arg, String action);
}
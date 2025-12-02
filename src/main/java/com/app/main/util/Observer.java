package com.app.main.util;

/**
 * <h1>Observer</h1>
 * <p>
 * The <b>Observer</b> interface defines a contract for objects that wish to be
 * notified
 * of changes or events occurring in an <b>Observable</b> object. It follows the
 * Observer design pattern, where observers are updated whenever the observable
 * object triggers a notification.
 * </p>
 * 
 * <h2>Features:</h2>
 * <ul>
 * <li>Allows objects to react to changes in observable objects.</li>
 * <li>Supports passing additional context and action details during
 * updates.</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * 
 * <pre>
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
 * </pre>
 * 
 * @see Observable
 * @author Dai Elias
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
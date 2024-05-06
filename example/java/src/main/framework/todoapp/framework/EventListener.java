package todoapp.framework;

import java.util.concurrent.CompletionStage;

/**
 * An event listener is responsible for handling an event.
 *
 * @param <E> the type of the event
 */
public interface EventListener<E> {
  /**
   * Handles the given event.
   *
   * @param event the event to handle
   * @return a completion stage
   */
  CompletionStage<Void> on(E event);
}

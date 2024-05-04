package todoapp.framework;

import java.util.List;
import java.util.concurrent.CompletionStage;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * A command handler is responsible for handling a command and producing a result and a list of
 * events.
 *
 * @param <C> the type of the command
 * @param <R> the type of the result
 */
public interface CommandHandler<C, R> {
  /**
   * Represents the outcome of handling a command.
   *
   * @param <R> the type of the result
   */
  @Value
  @Builder(toBuilder = true)
  class Outcome<R> {

    /**
     * The result of handling the command.
     */
    R result;

    /**
     * The events produced by handling the command.
     */
    @Singular
    List<Object> events;

    /**
     * Creates an empty outcome.
     *
     * @return an empty outcome
     */
    public static Outcome<Void> empty() {
      return Outcome.<Void>builder().result(null).build();
    }

    /**
     * Creates an empty outcome.
     * <p>
     * The flavor is designed for API based on lambda expressions where the parameter is expected null.
     *
     * @param unused an unused parameter
     * @return an empty outcome
     */
    @SuppressWarnings("java:S1172")
    public static Outcome<Void> empty(Object unused) {
      return empty();
    }
  }

  /**
   * Handles the given command.
   *
   * @param command the command to handle
   * @return the outcome of handling the command
   */
  CompletionStage<Outcome<R>> handle(C command);
}

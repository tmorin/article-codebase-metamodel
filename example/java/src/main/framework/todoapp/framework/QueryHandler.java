package todoapp.framework;

import java.util.concurrent.CompletionStage;

/**
 * A query handler is responsible for handling a query and producing a result.
 *
 * @param <Q> the type of the query
 * @param <R> the type of the result
 */
public interface QueryHandler<Q, R> {
  /**
   * Handles the given query.
   *
   * @param query the query to handle
   * @return the result of handling the query
   */
  CompletionStage<R> handle(Q query);
}

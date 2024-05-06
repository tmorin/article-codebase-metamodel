package todoapp.core;

import java.util.concurrent.CompletionStage;

/**
 * Repository for {@link TodoItem}.
 */
public interface TodoItemRepository {
  /**
   * Persists a TodoItem.
   *
   * @param todoItem the TodoItem to persist
   * @return a completion stage
   */
  CompletionStage<Void> persist(TodoItem todoItem);
}

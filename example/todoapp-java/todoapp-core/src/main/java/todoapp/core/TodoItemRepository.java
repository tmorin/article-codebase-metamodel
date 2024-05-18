package todoapp.core;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;

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

  /**
   * Loads a TodoItem by its ID.
   *
   * @param todoListId the ID of the TodoList
   * @param todoItemId the ID of the TodoItem
   * @return a completion stage with the TodoItem
   */
  CompletionStage<Optional<TodoItem>> load(
    TodoListId todoListId,
    TodoItemId todoItemId
  );
}

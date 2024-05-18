package todoapp.core;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import todoapp.api.TodoListId;

/**
 * Repository for {@link TodoList}.
 */
public interface TodoListRepository {
  /**
   * Persists a TodoList.
   *
   * @param todoList the TodoList to persist
   * @return a completion stage
   */
  CompletionStage<Void> persist(TodoList todoList);

  /**
   * Loads a TodoList by its ID.
   *
   * @param todoListId the ID of the TodoList
   * @return a completion stage with the TodoList
   */
  CompletionStage<Optional<TodoList>> load(TodoListId todoListId);

  /**
   * Checks if a TodoList exists.
   *
   * @param todoListId the ID of the TodoList
   * @return a completion stage with a boolean
   */
  CompletionStage<Boolean> contain(TodoListId todoListId);

  /**
   * Lists all TodoList.
   *
   * @return a completion stage with the set of TodoLists
   */
  CompletionStage<Set<TodoList>> list();
}

package todoapp.core;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;

/**
 * The repository of the TodoItemsImporterSaga.
 */
public interface TodoItemsImporterSagaRepository {
  /**
   * Persist the TodoItemsImporterSaga.
   *
   * @param saga the saga to persist
   * @return the completion stage of the operation
   */
  CompletionStage<Void> persist(TodoItemsImporterSaga saga);

  /**
   * Load a TodoItemsImporterSaga matching the TodoList id and the candidate TodoItem id.
   *
   * @param todoListId the TodoList id
   * @param todoItemId the TodoItem id
   * @return the completion stage of the operation
   */
  CompletionStage<Optional<TodoItemsImporterSaga>> load(
    TodoListId todoListId,
    TodoItemId todoItemId
  );
}

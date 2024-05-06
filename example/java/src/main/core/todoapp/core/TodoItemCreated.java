package todoapp.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;

/**
 * The event emitted when a TodoItem is created.
 */
@Value
@Builder(toBuilder = true)
class TodoItemCreated {

  /**
   * The identifier of the TodoList.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * The identifier of the TodoItem.
   */
  @NonNull
  TodoItemId todoItemId;
}

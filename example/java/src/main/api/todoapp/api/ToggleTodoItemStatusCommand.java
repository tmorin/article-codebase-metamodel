package todoapp.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * A command to toggle the completed status of a TodoItem.
 */
@Value
@Builder(toBuilder = true)
public class ToggleTodoItemStatusCommand {

  /**
   * The ID of the TodoList.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * The ID of the TodoList.
   */
  @NonNull
  TodoItemId todoItemId;
}

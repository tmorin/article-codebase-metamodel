package todoapp.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * A command to create a new TodoItem.
 */
@Value
@Builder(toBuilder = true)
public class CreateTodoItemCommand {

  /**
   * The ID of the TodoList.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * The ID of the TodoList.
   */
  @NonNull
  @Builder.Default
  TodoItemId todoItemId = TodoItemId.builder().build();

  /**
   * The label of the TodoItem.
   */
  @NonNull
  String label;
}

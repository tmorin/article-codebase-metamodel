package todoapp.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * A command to create a new TodoList.
 */
@Value
@Builder(toBuilder = true)
public class CreateTodoListCommand {

  /**
   * The ID of the TodoList.
   */
  @NonNull
  @Builder.Default
  TodoListId todoListId = TodoListId.builder().build();

  /**
   * The title of the TodoList.
   */
  @NonNull
  String title;
}

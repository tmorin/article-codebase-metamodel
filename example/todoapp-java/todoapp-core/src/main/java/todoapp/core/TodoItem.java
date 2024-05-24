package todoapp.core;

import java.time.Instant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;

/**
 * The aggregate of a TodoItem.
 */
@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TodoItem {

  /**
   * The identifier of the TodoList.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * The identifier of the TodoItem.
   */
  @NonNull
  @Builder.Default
  @EqualsAndHashCode.Include
  TodoItemId todoItemId = TodoItemId.builder().build();

  /**
   * The creation date of the TodoItem.
   */
  @NonNull
  @Builder.Default
  Instant createdAt = Instant.now();

  /**
   * The last update date of the TodoItem.
   */
  @NonNull
  @Builder.Default
  Instant updatedAt = Instant.now();

  /**
   * The label of the TodoItem.
   */
  @NonNull
  String label;

  /**
   * Whether the TodoItem is completed.
   */
  boolean completed;
}

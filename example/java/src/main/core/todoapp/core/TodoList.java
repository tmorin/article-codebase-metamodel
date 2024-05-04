package todoapp.core;

import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import todoapp.api.TodoListId;

/**
 * The aggregate of a TodoList.
 */
@Value
@Builder
public class TodoList {

  /**
   * The identifier of the TodoList.
   */
  @NonNull
  @Builder.Default
  TodoListId todoListId = TodoListId.builder().build();

  /**
   * The creation date of the TodoList.
   */
  @NonNull
  @Builder.Default
  Instant createdAt = Instant.now();

  /**
   * The last update date of the TodoList.
   */
  @NonNull
  @Builder.Default
  Instant updatedAt = createdAt;

  /**
   * The title of the TodoList.
   */
  @NonNull
  String title;

  /**
   * The number of items in the TodoList.
   */
  @NonNull
  int itemCount;
}

package todoapp.core;

import java.time.Instant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import todoapp.api.TodoListId;

/**
 * The aggregate of a TodoList.
 */
@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TodoList {

  /**
   * The identifier of the TodoList.
   */
  @NonNull
  @Builder.Default
  @EqualsAndHashCode.Include
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
  Instant updatedAt = Instant.now();

  /**
   * The title of the TodoList.
   */
  @NonNull
  String title;

  /**
   * The number of items in the TodoList.
   */
  int itemCount;
}

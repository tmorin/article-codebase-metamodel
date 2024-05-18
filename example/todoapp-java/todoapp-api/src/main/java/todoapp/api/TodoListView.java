package todoapp.api;

import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * A view of a TodoList.
 */
@Value
@Builder(toBuilder = true)
public class TodoListView {

  /**
   * The ID of the TodoList.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * The title of the TodoList.
   */
  @NonNull
  String title;

  /**
   * The timestamp when the TodoList was last updated.
   */
  @NonNull
  Instant updatedAt;

  /**
   * The number of items in the TodoList.
   */
  int itemCount;
}

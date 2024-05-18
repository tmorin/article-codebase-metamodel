package todoapp.api;

import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The ID of a TodoList.
 */
@Value
@Builder(toBuilder = true)
public class TodoListId {

  /**
   * The value of the ID.
   */
  @NonNull
  UUID value;
}

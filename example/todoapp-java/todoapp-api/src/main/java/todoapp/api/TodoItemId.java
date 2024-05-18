package todoapp.api;

import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The ID of a TodoItem.
 */
@Value
@Builder(toBuilder = true)
public class TodoItemId {

  /**
   * The value of the ID.
   */
  @NonNull
  UUID value;
}

package todoapp.core;

import java.time.Instant;
import java.util.UUID;
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
   * The identifier of the event.
   */
  @NonNull
  @Builder.Default
  UUID eventId = UUID.randomUUID();

  /**
   * The instant when the event occurred.
   */
  @NonNull
  @Builder.Default
  Instant occurredOn = Instant.now();

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

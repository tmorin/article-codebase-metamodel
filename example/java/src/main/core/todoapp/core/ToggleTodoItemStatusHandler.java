package todoapp.core;

import java.util.concurrent.CompletionStage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.ToggleTodoItemStatusCommand;
import todoapp.framework.CommandHandler;

/**
 * The handler of the command {@link ToggleTodoItemStatusCommand}.
 * <p>
 * This handler is responsible for toggling the status of a TodoItem.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class ToggleTodoItemStatusHandler
  implements CommandHandler<ToggleTodoItemStatusCommand, Void> {

  /**
   * The repository of the TodoItem Aggregate.
   */
  @NonNull
  TodoItemRepository todoItemRepository;

  @Override
  public CompletionStage<Outcome<Void>> handle(
    @NonNull ToggleTodoItemStatusCommand command
  ) {
    return todoItemRepository
      .load(command.getTodoListId(), command.getTodoItemId())
      .thenApply(optionalTodoItem ->
        optionalTodoItem.orElseThrow(() ->
          new IllegalArgumentException(
            String.format("TodoItem %s not found", command.getTodoItemId())
          )
        )
      )
      .thenApply(todoItem ->
        todoItem.toBuilder().completed(!todoItem.isCompleted()).build()
      )
      .thenCompose(todoItem -> {
        log.info(String.format("Toggled TodoItem %s", command.getTodoItemId()));
        return todoItemRepository.persist(todoItem);
      })
      .thenApply(unused -> Outcome.<Void>builder().build());
  }
}

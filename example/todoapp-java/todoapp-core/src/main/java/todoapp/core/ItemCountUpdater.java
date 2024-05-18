package todoapp.core;

import java.util.concurrent.CompletionStage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.framework.EventListener;

/**
 * The updater of the item count of the TodoList.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class ItemCountUpdater implements EventListener<TodoItemCreated> {

  /**
   * The repository of the TodoList Aggregate.
   */
  @NonNull
  TodoListRepository todoListRepository;

  /**
   * Updates the item count of the TodoList when a TodoItem is created.
   *
   * @param todoItemCreated the event emitted when a TodoItem is created.
   * @return a completion stage that indicates when the item count is updated.
   */
  @Override
  public CompletionStage<Void> on(@NonNull TodoItemCreated todoItemCreated) {
    return todoListRepository
      .load(todoItemCreated.getTodoListId())
      .thenApply(optionalTodoList ->
        optionalTodoList.orElseThrow(() ->
          new IllegalArgumentException(
            String.format(
              "TodoList %s not found",
              todoItemCreated.getTodoListId()
            )
          )
        )
      )
      .thenApply(todoList ->
        todoList.toBuilder().itemCount(todoList.getItemCount() + 1).build()
      )
      .thenCompose(todoListRepository::persist)
      .thenAccept(v -> log.info("Item count updated"));
  }
}

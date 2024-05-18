package todoapp.adapter.mem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;
import todoapp.core.TodoItem;
import todoapp.core.TodoItemRepository;

/**
 * An in-memory implementation of {@link TodoItemRepository}.
 */
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TodoItemRepositoryInMemory implements TodoItemRepository {

  /**
   * The store of the TodoItem instances.
   */
  @NonNull
  @Builder.Default
  Map<TodoListId, Map<TodoItemId, TodoItem>> store = new HashMap<>();

  @Override
  public CompletionStage<Void> persist(TodoItem todoItem) {
    store
      .computeIfAbsent(todoItem.getTodoListId(), unused -> new HashMap<>())
      .put(todoItem.getTodoItemId(), todoItem);
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public CompletionStage<Optional<TodoItem>> load(
    TodoListId todoListId,
    TodoItemId todoItemId
  ) {
    return CompletableFuture.completedFuture(
      Optional.ofNullable(
        store.getOrDefault(todoListId, Map.of()).get(todoItemId)
      )
    );
  }
}

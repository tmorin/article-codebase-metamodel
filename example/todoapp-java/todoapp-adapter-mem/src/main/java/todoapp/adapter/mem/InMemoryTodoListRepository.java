package todoapp.adapter.mem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.TodoListId;
import todoapp.core.TodoList;
import todoapp.core.TodoListRepository;

/**
 * An in-memory implementation of {@link TodoListRepository}.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class InMemoryTodoListRepository implements TodoListRepository {

  /**
   * The store of the TodoList instances.
   */
  @NonNull
  @Builder.Default
  Map<TodoListId, TodoList> store = new HashMap<>();

  @Override
  public CompletionStage<Void> persist(@NonNull TodoList todoList) {
    log.info(String.format("Persisting TodoList: %s", todoList));
    store.put(todoList.getTodoListId(), todoList);
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public CompletionStage<Optional<TodoList>> load(
    @NonNull TodoListId todoListId
  ) {
    log.info(String.format("Loading TodoList: %s", todoListId));
    return CompletableFuture.completedFuture(
      Optional.ofNullable(store.get(todoListId))
    );
  }

  @Override
  public CompletionStage<Boolean> contain(@NonNull TodoListId todoListId) {
    log.info(String.format("Checking if TodoList exists: %s", todoListId));
    return CompletableFuture.completedFuture(store.containsKey(todoListId));
  }

  @Override
  public CompletionStage<Set<TodoList>> list() {
    log.info("Listing all TodoLists");
    return CompletableFuture.completedFuture(Set.copyOf(store.values()));
  }
}

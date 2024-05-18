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
import todoapp.api.TodoListId;
import todoapp.core.TodoList;
import todoapp.core.TodoListRepository;

/**
 * An in-memory implementation of {@link TodoListRepository}.
 */
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TodoListRepositoryInMemory implements TodoListRepository {

  /**
   * The store of the TodoList instances.
   */
  @NonNull
  @Builder.Default
  Map<TodoListId, TodoList> store = new HashMap<>();

  @Override
  public CompletionStage<Void> persist(@NonNull TodoList todoList) {
    store.put(todoList.getTodoListId(), todoList);
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public CompletionStage<Optional<TodoList>> load(TodoListId todoListId) {
    return CompletableFuture.completedFuture(
      Optional.ofNullable(store.get(todoListId))
    );
  }

  @Override
  public CompletionStage<Boolean> contain(TodoListId todoListId) {
    return CompletableFuture.completedFuture(store.containsKey(todoListId));
  }

  @Override
  public CompletionStage<Set<TodoList>> list() {
    return CompletableFuture.completedFuture(Set.copyOf(store.values()));
  }
}

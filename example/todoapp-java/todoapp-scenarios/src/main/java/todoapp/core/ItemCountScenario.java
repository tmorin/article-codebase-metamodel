package todoapp.core;

import java.util.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.TodoItemId;
import todoapp.framework.Scenario;

@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ItemCountScenario implements Scenario {

  @NonNull
  TodoListRepository todoListRepository;

  @Override
  public void validate() {
    log.info("Scenario: Update the item count of the TodoList");
    // GIVEN a TodoList
    val todoList = TodoList.builder().title("a simple title").build();
    // AND the TodoList is persisted
    todoListRepository.persist(todoList);
    // AND an event listener
    val listener = ItemCountUpdater
      .builder()
      .todoListRepository(todoListRepository)
      .build();
    // AND a TodoItemCreated event
    val event = TodoItemCreated
      .builder()
      .todoListId(todoList.getTodoListId())
      .todoItemId(TodoItemId.builder().build())
      .build();
    // WHEN the event is emitted
    listener.on(event).toCompletableFuture().join();
    // THEN the item count is updated
    val persistedAggregate = todoListRepository
      .load(event.getTodoListId())
      .toCompletableFuture()
      .join();
    assert Objects.nonNull(persistedAggregate);
    assert persistedAggregate.isPresent();
    // AND the title is persisted
    assert persistedAggregate.get().getItemCount() == 1;
  }
}

package todoapp.core;

import java.util.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.ToggleTodoItemStatusCommand;
import todoapp.framework.Scenario;

@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ToggleTodoItemStatusScenario implements Scenario {

  /**
   * The repository of the TodoList Aggregate.
   */
  @NonNull
  TodoListRepository todoListRepository;

  /**
   * The repository of the TodoItem Aggregate.
   */
  @NonNull
  TodoItemRepository todoItemRepository;

  @Override
  public void validate() {
    log.info("Scenario: Toggle TodoItem Status");
    val scenarioFixturesBuilder = ScenarioFixturesBuilder
      .builder()
      .todoListRepository(todoListRepository)
      .todoItemRepository(todoItemRepository)
      .build();
    // GIVEN a TodoList
    val todoListId = scenarioFixturesBuilder.createTodoList();
    // AND a TodoItem
    val todoIteId = scenarioFixturesBuilder.createTodoItem(todoListId);
    // AND a command
    val command = ToggleTodoItemStatusCommand
      .builder()
      .todoListId(todoListId)
      .todoItemId(todoIteId)
      .build();
    // AND an handler
    val handler = ToggleTodoItemStatusHandler
      .builder()
      .todoItemRepository(todoItemRepository)
      .build();
    // WHEN the command is handled
    val fOutcome = handler.handle(command).toCompletableFuture();
    // THEN the outcome is successful
    val outcome = fOutcome.join();
    // AND the event are empty
    assert outcome.getEvents().isEmpty();
    // AND the aggregate can be loaded
    val persistedAggregate = todoItemRepository
      .load(command.getTodoListId(), command.getTodoItemId())
      .toCompletableFuture()
      .join();
    assert Objects.nonNull(persistedAggregate);
    assert persistedAggregate.isPresent();
    // AND the title is persisted
    assert persistedAggregate.get().isCompleted();
  }
}

package todoapp.core;

import java.util.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.CreateTodoItemCommand;
import todoapp.framework.Scenario;

@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class CreateTodoItemScenario implements Scenario {

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
    log.info("Scenario: Create a TodoItem");
    val scenarioFixturesBuilder = ScenarioFixturesBuilder
      .builder()
      .todoListRepository(todoListRepository)
      .todoItemRepository(todoItemRepository)
      .build();
    // GIVEN a TodoList
    val todoListId = scenarioFixturesBuilder.createTodoList();
    // AND a command
    val command = CreateTodoItemCommand
      .builder()
      .todoListId(todoListId)
      .label("a simple label")
      .build();
    // AND an handler
    val handler = CreateTodoItemHandler
      .builder()
      .todoListRepository(todoListRepository)
      .todoItemRepository(todoItemRepository)
      .build();
    // WHEN the command is handled
    val fOutcome = handler.handle(command).toCompletableFuture();
    // THEN the outcome is successful
    val outcome = fOutcome.join();
    // AND the event are not empty
    assert outcome.getEvents().size() == 1;
    assert outcome.getEvents().getFirst() instanceof TodoItemCreated;
    assert ((TodoItemCreated) outcome.getEvents().getFirst()).getTodoListId()
      .equals(command.getTodoListId());
    assert ((TodoItemCreated) outcome.getEvents().getFirst()).getTodoItemId()
      .equals(command.getTodoItemId());
    // AND the aggregate can be loaded
    val persistedAggregate = todoItemRepository
      .load(command.getTodoListId(), command.getTodoItemId())
      .toCompletableFuture()
      .join();
    assert Objects.nonNull(persistedAggregate);
    assert persistedAggregate.isPresent();
    // AND the label is persisted
    assert Objects.equals(
      persistedAggregate.get().getLabel(),
      command.getLabel()
    );
  }
}

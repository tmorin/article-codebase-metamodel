package todoapp.core;

import java.util.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.CreateTodoListCommand;
import todoapp.framework.Scenario;

/**
 * The scenario validate the driven adapter implementations related to the {@link CreateTodoListCommand} command.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class CreateTodoListScenario implements Scenario {

  /**
   * The repository of the TodoList Aggregate.
   */
  @NonNull
  TodoListRepository todoListRepository;

  @Override
  public void validate() {
    log.info("Scenario: Create a todo list");
    // GIVEN a command
    val command = CreateTodoListCommand
      .builder()
      .title("a simple title")
      .build();
    // AND an handler
    val handler = CreateTodoListHandler
      .builder()
      .todoListRepository(todoListRepository)
      .build();
    // WHEN the command is handled
    val fOutcome = handler.handle(command).toCompletableFuture();
    // THEN the outcome is successful
    val outcome = fOutcome.join();
    // AND the event are empty
    assert outcome.getEvents().isEmpty();
    // AND the aggregate can be loaded
    val persistedAggregate = todoListRepository
      .load(command.getTodoListId())
      .toCompletableFuture()
      .join();
    assert Objects.nonNull(persistedAggregate);
    // AND the title is persisted
    assert Objects.equals(persistedAggregate.getTitle(), command.getTitle());
  }
}

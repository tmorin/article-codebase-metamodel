package todoapp.core;

import java.util.concurrent.CompletionStage;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.CreateTodoListCommand;
import todoapp.framework.CommandHandler;

/**
 * The handler of the command {@link CreateTodoListCommand}.
 * <p>
 * The handler creates and persists TodoList aggregates.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class CreateTodoListHandler
  implements CommandHandler<CreateTodoListCommand, Void> {

  /**
   * The repository of the TodoList Aggregate.
   */
  @NonNull
  TodoListRepository todoListRepository;

  @Override
  public CompletionStage<Outcome<Void>> handle(
    @NonNull CreateTodoListCommand command
  ) {
    val todoList = TodoList.builder().title(command.getTitle()).build();
    log.info(() -> String.format("Creating todo list %s", todoList));
    return todoListRepository.persist(todoList).thenApply(Outcome::empty);
  }
}

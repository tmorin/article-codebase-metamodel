package todoapp.core;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.CreateTodoItemCommand;
import todoapp.api.ImportTodoItemsCommand;
import todoapp.framework.CommandHandler;
import todoapp.framework.SagaStatus;

/**
 * The handler of the command {@link todoapp.api.ImportTodoItemsCommand}.
 * <p>
 * This handler is responsible for creating a <i>saga</i> and the commands to create the TodoItems.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportTodoItemsHandler
  implements CommandHandler<ImportTodoItemsCommand, Void> {

  /**
   * The repository of the TodoItemsImporterSaga.
   */
  @NonNull
  TodoItemsImporterSagaRepository todoItemsImporterSagaRepository;

  @Override
  public CompletionStage<Outcome<Void>> handle(ImportTodoItemsCommand command) {
    // create the saga
    val saga = TodoItemsImporterSaga
      .builder()
      .todoListId(command.getTodoListId())
      .candidateItems(
        command
          .getEntries()
          .stream()
          .map(entry ->
            TodoItemsImporterSaga.Item
              .builder()
              .label(entry.getLabel())
              .completed(entry.isCompleted())
              .build()
          )
          .collect(Collectors.toSet())
      )
      .status(SagaStatus.inProgress("importing items"))
      .build();

    log.info(() -> String.format("starting the saga %s", saga));

    // create the commands
    val commands = saga
      .getCandidateItems()
      .stream()
      .map(item ->
        CreateTodoItemCommand
          .builder()
          .label(item.getLabel())
          .completed(item.isCompleted())
          .build()
      )
      .collect(Collectors.toSet());

    // persist the saga
    return todoItemsImporterSagaRepository
      .persist(saga)
      .thenApply(unused -> Outcome.<Void>builder().commands(commands).build());
  }
}

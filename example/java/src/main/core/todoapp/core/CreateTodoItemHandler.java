package todoapp.core;

import java.util.concurrent.CompletionStage;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.CreateTodoItemCommand;
import todoapp.framework.CommandHandler;

/**
 * The handler of the command {@link todoapp.api.CreateTodoItemCommand}.
 * <p>
 * This handler is responsible for creating a new TodoItem in the TodoList.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class CreateTodoItemHandler
  implements CommandHandler<CreateTodoItemCommand, Void> {

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
  public CompletionStage<Outcome<Void>> handle(
    @NonNull CreateTodoItemCommand command
  ) {
    // create the aggregate
    val todoItem = TodoItem
      .builder()
      .todoListId(command.getTodoListId())
      .todoItemId(command.getTodoItemId())
      .label(command.getLabel())
      .build();
    // create the domain event
    val todoItemCreated = TodoItemCreated
      .builder()
      .todoListId(command.getTodoListId())
      .todoItemId(command.getTodoItemId())
      .build();
    // persist the aggregate if the TodoList exists
    return todoListRepository
      .contain(command.getTodoListId())
      .thenApply(contain -> {
        if (!contain) {
          throw new IllegalArgumentException(
            String.format("TodoList %s not found", command.getTodoListId())
          );
        }
        return null;
      })
      .thenCompose(unused -> {
        log.info(() -> String.format("Creating todo item %s", todoItem));
        return todoItemRepository.persist(todoItem);
      })
      .thenApply(unused ->
        Outcome.<Void>builder().event(todoItemCreated).build()
      );
  }
}

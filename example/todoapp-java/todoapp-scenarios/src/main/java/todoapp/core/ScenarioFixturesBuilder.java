package todoapp.core;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.CreateTodoItemCommand;
import todoapp.api.CreateTodoListCommand;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;

/**
 * A builder of fixtures for the scenarios.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ScenarioFixturesBuilder {

  TodoListRepository todoListRepository;
  TodoItemRepository todoItemRepository;

  /**
   * Create a new TodoList.
   *
   * @return the ID of the created TodoList.
   */
  TodoListId createTodoList() {
    val command = CreateTodoListCommand
      .builder()
      .title("a simple title")
      .build();
    CreateTodoListHandler
      .builder()
      .todoListRepository(todoListRepository)
      .build()
      .handle(command)
      .toCompletableFuture()
      .join();
    return command.getTodoListId();
  }

  /**
   * Create a new TodoItem.
   *
   * @param todoListId the ID of the TodoList.
   * @return the ID of the created TodoItem.
   */
  TodoItemId createTodoItem(@NonNull TodoListId todoListId) {
    val command = CreateTodoItemCommand
      .builder()
      .todoListId(todoListId)
      .label("a simple label")
      .build();
    CreateTodoItemHandler
      .builder()
      .todoListRepository(todoListRepository)
      .todoItemRepository(todoItemRepository)
      .build()
      .handle(command)
      .toCompletableFuture()
      .join();
    return command.getTodoItemId();
  }
}

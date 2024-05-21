package todoapp.core;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.ListTodoListsQuery;
import todoapp.framework.Scenario;

/**
 * The scenario validate the driven adapter implementations related to the {@link todoapp.api.ListTodoListsQuery} query.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ListTodoListsScenario implements Scenario {

  /**
   * The repository of the TodoList Aggregate.
   */
  @NonNull
  TodoListRepository todoListRepository;

  /**
   * The factory of the TodoListView.
   */
  TodoListViewFactory todoListViewFactory = TodoListViewFactory
    .builder()
    .build();

  @Override
  public void validate() {
    log.info("Scenario: List TodoLists");
    // GIVEN a query
    val query = ListTodoListsQuery.builder().build();
    // AND a persisted aggregate instance
    val todoList = TodoList.builder().title("a simple title").build();
    todoListRepository.persist(todoList).toCompletableFuture().join();
    // AND an handler
    val handler = ListTodoListsHandler
      .builder()
      .todoListRepository(todoListRepository)
      .build();
    // WHEN the query is handled
    val fResult = handler.handle(query).toCompletableFuture();
    // THEN the result is successful
    val result = fResult.join();
    // AND the aggregate can be found
    assert result
      .getTodoListViews()
      .contains(todoListViewFactory.createTodoListView(todoList));
  }
}

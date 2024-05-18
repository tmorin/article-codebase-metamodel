package todoapp.core;

import java.util.concurrent.CompletionStage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.api.ListTodoListsQuery;
import todoapp.api.ListTodoListsResult;
import todoapp.framework.QueryHandler;

/**
 * The handler of the query {@link ListTodoListsQuery}.
 * <p>
 * The handler lists the TodoList aggregates.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class ListTodoListsHandler
  implements QueryHandler<ListTodoListsQuery, ListTodoListsResult> {

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
  public CompletionStage<ListTodoListsResult> handle(
    @NonNull ListTodoListsQuery query
  ) {
    log.info(() -> "Handling query: " + query);
    return todoListRepository
      .list()
      .thenApply(todoLists ->
        todoLists.stream().map(todoListViewFactory::createTodoListView).toList()
      )
      .thenApply(todoListViews ->
        ListTodoListsResult.builder().todoListViews(todoListViews).build()
      );
  }
}

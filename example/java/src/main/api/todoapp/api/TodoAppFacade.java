package todoapp.api;

import java.util.concurrent.CompletionStage;

public interface TodoAppFacade {
  void addTodoList(CreateTodoListCommand command);

  CompletionStage<ListTodoListsResult> listTodoLists(ListTodoListsQuery query);
}

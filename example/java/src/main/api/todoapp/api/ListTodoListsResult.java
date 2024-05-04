package todoapp.api;

import java.util.Set;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * The result of the query {@link ListTodoListsQuery}.
 * <p>
 * The result contains a set of {@link TodoListView}s.
 */
@Value
@Builder(toBuilder = true)
public class ListTodoListsResult {

  /**
   * The set of TodoListViews.
   */
  @Singular
  Set<TodoListView> todoListViews;
}

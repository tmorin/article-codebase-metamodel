package todoapp.api;

import lombok.Builder;
import lombok.Value;

/**
 * The query to list all TodoLists.
 * <p>
 * The query doesn't have criteria, so the data structure is empty.
 */
@Value
@Builder(toBuilder = true)
public class ListTodoListsQuery {}

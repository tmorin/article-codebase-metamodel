package todoapp.api;

import java.util.Set;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * A command to import multiple TodoItems.
 */
@Value
@Builder(toBuilder = true)
public class ImportTodoItemsCommand {

  /**
   * The ID of the TodoList.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * The entries to import.
   */
  @NonNull
  @Singular
  Set<Entry> entries;

  /**
   * A single entry to import.
   */
  @Value
  @Builder(toBuilder = true)
  public static class Entry {

    /**
     * The label of the TodoItem.
     */
    @NonNull
    String label;

    /**
     * The status of the TodoItem.
     */
    boolean completed;
  }
}

package todoapp.core;

import java.util.Set;
import lombok.*;
import todoapp.api.TodoItemId;
import todoapp.api.TodoListId;
import todoapp.framework.SagaId;
import todoapp.framework.SagaStatus;

/**
 * TodoItemsImporterSaga is a saga that imports TodoItems from an external source to a TodoList.
 */
@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TodoItemsImporterSaga {

  /**
   * SagaId is a unique identifier for the saga.
   */
  @NonNull
  @EqualsAndHashCode.Include
  SagaId sagaId;

  /**
   * Status is the current status of the saga.
   */
  @NonNull
  SagaStatus status;

  /**
   * TodoListId is the identifier of the TodoList to import the TodoItems.
   */
  @NonNull
  TodoListId todoListId;

  /**
   * CandidateItems is the set of TodoItems to import.
   */
  @Singular
  Set<Item> candidateItems;

  /**
   * DoneItems is the set of TodoItems that have been successfully imported.
   */
  @Singular
  Set<Item> doneItems;

  @Singular
  Set<Item> failedItems;

  @Value
  @Builder(toBuilder = true)
  static class Item {

    @NonNull
    @Builder.Default
    TodoItemId todoItemId = TodoItemId.builder().build();

    @NonNull
    String label;

    boolean completed;
  }
}

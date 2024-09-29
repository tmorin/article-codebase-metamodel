package todoapp.core;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import todoapp.framework.EventListener;
import todoapp.framework.SagaStatus;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * The listener of the TodoItemsImporterSaga.
 */
@Log
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TodoItemsImporterSagaListener implements EventListener<TodoItemCreated> {

  /**
   * The repository of the TodoItemsImporterSaga.
   */
  @NonNull
  TodoItemsImporterSagaRepository todoItemsImporterSagaRepository;

  /**
   * Mutate the saga when a TodoItemCreated event is emitted.
   *
   * @param saga  the saga to mutate
   * @param event the event emitted
   * @return the completion stage of the operation
   */
  private Optional<TodoItemsImporterSaga> apply(
    @NonNull TodoItemsImporterSaga saga,
    @NonNull TodoItemCreated event
  ) {
    return saga
      .getCandidateItems()
      .stream()
      .filter(item -> item.getTodoItemId().equals(event.getTodoItemId()))
      .findFirst()
      .map(newDoneItem -> {
        // remove the item from the candidateItems
        val newCandidateItems = saga
          .getCandidateItems()
          .stream()
          .filter(candidateItem -> !candidateItem.equals(newDoneItem))
          .collect(Collectors.toSet());

        // add the item to the done candidateItems
        val mewDoneItems = new HashSet<>(saga.getDoneItems());
        mewDoneItems.add(newDoneItem);

        // if all candidate items are done, set the status to DONE
        val newStatus = newCandidateItems.isEmpty()
          ? SagaStatus.done("all item completed")
          : SagaStatus.inProgress("some item are still pending");

        // create a new saga with the updated values and return it
        return saga
          .toBuilder()
          .doneItems(mewDoneItems)
          .candidateItems(newCandidateItems)
          .status(newStatus)
          .build();
      });
  }

  /**
   * Handle the TodoItemCreated event.
   *
   * @param event the event emitted
   * @return the completion stage of the operation
   */
  @Override
  public CompletionStage<Void> on(@NonNull TodoItemCreated event) {
    // load a matching saga
    return this.todoItemsImporterSagaRepository.load(
        event.getTodoListId(),
        event.getTodoItemId()
      )
      // apply the mutation
      .thenApply(todoItemsImporterSaga ->
        todoItemsImporterSaga.flatMap(saga -> apply(saga, event))
      )
      // persist the mutation
      .thenApply(todoItemsImporterSaga ->
        todoItemsImporterSaga.map(todoItemsImporterSagaRepository::persist)
      )
      // return a completed stage when no matching saga is found
      .thenCompose(voidCompletionStage ->
        voidCompletionStage.orElse(CompletableFuture.completedStage(null))
      );
  }
}

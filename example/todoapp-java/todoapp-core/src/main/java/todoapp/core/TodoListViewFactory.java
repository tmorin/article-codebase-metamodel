package todoapp.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import todoapp.api.TodoListView;

/**
 * The factory of the TodoListView.
 */
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class TodoListViewFactory {

  /**
   * Creates a TodoListView from a TodoList.
   *
   * @param todoList the TodoList to create the view from.
   * @return the created TodoListView.
   */
  TodoListView createTodoListView(@NonNull TodoList todoList) {
    return TodoListView
      .builder()
      .todoListId(todoList.getTodoListId())
      .title(todoList.getTitle())
      .updatedAt(todoList.getUpdatedAt())
      .itemCount(todoList.getItemCount())
      .build();
  }
}

package todoapp.adapter.mem;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import todoapp.core.CreateTodoItemScenario;

class CreateTodoItemScenarioTest {

  @Test
  void shouldValidateTheScenario() {
    val scenario = CreateTodoItemScenario
      .builder()
      .todoListRepository(InMemoryTodoListRepository.builder().build())
      .todoItemRepository(InMemoryTodoItemRepository.builder().build())
      .build();
    Assertions.assertDoesNotThrow(scenario::validate);
  }
}

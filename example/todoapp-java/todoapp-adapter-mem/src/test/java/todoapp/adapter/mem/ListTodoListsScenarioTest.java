package todoapp.adapter.mem;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import todoapp.core.ListTodoListsScenario;

class ListTodoListsScenarioTest {

  @Test
  void shouldValidateTheScenario() {
    val scenario = ListTodoListsScenario
      .builder()
      .todoListRepository(InMemoryTodoListRepository.builder().build())
      .build();
    Assertions.assertDoesNotThrow(scenario::validate);
  }
}

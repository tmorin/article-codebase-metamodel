package todoapp.adapter.mem;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import todoapp.core.CreateTodoListScenario;

class CreateTodoListScenarioTest {

  @Test
  void shouldValidateTheScenario() {
    val scenario = CreateTodoListScenario
      .builder()
      .todoListRepository(InMemoryTodoListRepository.builder().build())
      .build();
    Assertions.assertDoesNotThrow(scenario::validate);
  }
}

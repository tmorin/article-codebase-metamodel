package todoapp.adapter.mem;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import todoapp.core.ItemCountScenario;

class ItemCountScenarioTest {

  @Test
  void shouldValidateTheScenario() {
    val scenario = ItemCountScenario
      .builder()
      .todoListRepository(InMemoryTodoListRepository.builder().build())
      .build();
    Assertions.assertDoesNotThrow(scenario::validate);
  }
}

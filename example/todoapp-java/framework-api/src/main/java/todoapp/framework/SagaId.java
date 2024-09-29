package todoapp.framework;

import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The ID of a Saga.
 */
@Value
@Builder(toBuilder = true)
public class SagaId {

  /**
   * The value of the ID.
   */
  @NonNull
  @Builder.Default
  UUID value = UUID.randomUUID();
}

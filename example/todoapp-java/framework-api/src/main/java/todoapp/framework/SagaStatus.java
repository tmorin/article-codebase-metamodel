package todoapp.framework;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The status of a saga.
 */
@Value
@Builder(toBuilder = true)
public class SagaStatus {

  /**
   * The type of the status.
   */
  @NonNull
  Type type;

  /**
   * The reason of the status.
   */
  @NonNull
  String reason;

  /**
   * Create a new status in progress.
   *
   * @param reason the reason of the status
   * @return the status
   */
  public static SagaStatus inProgress(@NonNull String reason) {
    return SagaStatus.builder().type(Type.IN_PROGRESS).reason(reason).build();
  }

  /**
   * Create a new status done.
   *
   * @param reason the reason of the status
   * @return the status
   */
  public static SagaStatus done(@NonNull String reason) {
    return SagaStatus.builder().type(Type.DONE).reason(reason).build();
  }

  /**
   * Create a new status failed.
   *
   * @param reason the reason of the status
   * @return the status
   */
  public static SagaStatus failed(@NonNull String reason) {
    return SagaStatus.builder().type(Type.FAILED).reason(reason).build();
  }

  /**
   * The type of the status.
   */
  public enum Type {
    /**
     * The saga is in progress.
     */
    IN_PROGRESS,
    /**
     * The saga is done.
     */
    DONE,
    /**
     * The saga is failed.
     */
    FAILED,
  }
}

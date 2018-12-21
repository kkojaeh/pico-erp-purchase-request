package pico.erp.purchase.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.LocalizedNameable;

@AllArgsConstructor
public enum PurchaseRequestStatusKind implements LocalizedNameable {

  /**
   * 작성중
   */
  DRAFT(true, true, false, false, true, false, false),

  /**
   * 제출함
   */
  COMMITTED(false, false, true, false, true, true, false),

  /**
   * 접수됨
   */
  ACCEPTED(false, false, false, true, true, false, false),

  /**
   * 진행중
   */
  IN_PROGRESS(false, false, false, true, false, false, true),

  /**
   * 취소됨
   */
  CANCELED(false, false, false, false, false, false, false),

  /**
   * 반려됨
   */
  REJECTED(false, false, false, false, false, false, false),

  /**
   * 생산완료
   */
  COMPLETED(false, false, false, false, false, false, false);

  @Getter
  private final boolean updatable;

  @Getter
  private final boolean committable;

  @Getter
  private final boolean acceptable;

  @Getter
  private final boolean progressable;

  @Getter
  private final boolean cancelable;

  @Getter
  private final boolean rejectable;

  @Getter
  private final boolean completable;

}

package pico.erp.purchase.request.item;

import java.util.Arrays;
import pico.erp.shared.data.LocalizedNameable;

public enum PurchaseRequestItemStatusKind implements LocalizedNameable {

  /**
   * 작성중
   */
  DRAFT,

  /**
   * 제출함
   */
  COMMITTED,

  /**
   * 접수됨
   */
  ACCEPTED,

  /**
   * 진행중
   */
  IN_PROGRESS,

  /**
   * 취소됨
   */
  CANCELED,

  /**
   * 반려됨
   */
  REJECTED,

  /**
   * 구매완료
   */
  COMPLETED;


  public boolean isAcceptable() {
    return this == COMMITTED;
  }

  public boolean isCancelable() {
    return Arrays.asList(DRAFT, COMMITTED, ACCEPTED).contains(this);
  }

  public boolean isCommittable() {
    return this == DRAFT;
  }

  public boolean isCompletable() {
    return this == IN_PROGRESS;
  }

  public boolean isProgressable() {
    return Arrays.asList(ACCEPTED, IN_PROGRESS).contains(this);
  }

  public boolean isRejectable() {
    return this == COMMITTED;
  }

  public boolean isUpdatable() {
    return this == DRAFT;
  }
}

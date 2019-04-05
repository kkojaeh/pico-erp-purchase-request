package pico.erp.purchase.request;

import java.util.Arrays;
import pico.erp.shared.data.LocalizedNameable;

public enum PurchaseRequestStatusKind implements LocalizedNameable {

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
   * 계획중
   */
  IN_PLANNING,

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
    return Arrays.asList(DRAFT, COMMITTED, ACCEPTED, IN_PLANNING).contains(this);
  }

  public boolean isCommittable() {
    return this == DRAFT;
  }

  public boolean isCompletable() {
    return this == IN_PROGRESS;
  }

  public boolean isPlannable() {
    return this == ACCEPTED;
  }

  public boolean isProgressCancelable() {
    return Arrays.asList(IN_PLANNING, IN_PROGRESS).contains(this);
  }

  public boolean isProgressable() {
    return Arrays.asList(IN_PLANNING, IN_PROGRESS).contains(this);
  }

  public boolean isRejectable() {
    return this == COMMITTED;
  }

  public boolean isUpdatable() {
    return this == DRAFT;
  }
}

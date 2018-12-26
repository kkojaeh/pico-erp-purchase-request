package pico.erp.purchase.request.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.item.ItemData;
import pico.erp.item.spec.ItemSpecData;
import pico.erp.purchase.request.PurchaseRequest;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "purchase-request-item")
public class PurchaseRequestItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  PurchaseRequestItemId id;

  PurchaseRequest request;

  ItemData item;

  ItemSpecData itemSpec;

  BigDecimal quantity;

  String remark;

  PurchaseRequestItemStatusKind status;


  public PurchaseRequestItem() {

  }

  public PurchaseRequestItemMessages.Create.Response apply(
    PurchaseRequestItemMessages.Create.Request request) {
    if (!request.getRequest().isUpdatable()) {
      throw new PurchaseRequestItemExceptions.CannotCreateException();
    }
    this.id = request.getId();
    this.request = request.getRequest();
    this.item = request.getItem();
    this.itemSpec = request.getItemSpec();
    this.quantity = request.getQuantity();
    this.remark = request.getRemark();
    this.status = PurchaseRequestItemStatusKind.DRAFT;

    return new PurchaseRequestItemMessages.Create.Response(
      Arrays.asList(new PurchaseRequestItemEvents.CreatedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Update.Response apply(
    PurchaseRequestItemMessages.Update.Request request) {
    if (!this.isUpdatable()) {
      throw new PurchaseRequestItemExceptions.CannotUpdateException();
    }
    this.itemSpec = request.getItemSpec();
    this.quantity = request.getQuantity();
    this.remark = request.getRemark();
    return new PurchaseRequestItemMessages.Update.Response(
      Arrays.asList(new PurchaseRequestItemEvents.UpdatedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Delete.Response apply(
    PurchaseRequestItemMessages.Delete.Request request) {
    if (!this.isUpdatable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    return new PurchaseRequestItemMessages.Delete.Response(
      Arrays.asList(new PurchaseRequestItemEvents.DeletedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Accept.Response apply(
    PurchaseRequestItemMessages.Accept.Request request) {
    if (!this.isAcceptable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.ACCEPTED;
    return new PurchaseRequestItemMessages.Accept.Response(
      Arrays.asList(new PurchaseRequestItemEvents.AcceptedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Commit.Response apply(
    PurchaseRequestItemMessages.Commit.Request request) {
    if (!this.isCommittable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.COMMITTED;
    return new PurchaseRequestItemMessages.Commit.Response(
      Arrays.asList(new PurchaseRequestItemEvents.CommittedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Complete.Response apply(
    PurchaseRequestItemMessages.Complete.Request request) {
    if (!this.isCompletable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.COMPLETED;
    return new PurchaseRequestItemMessages.Complete.Response(
      Arrays.asList(new PurchaseRequestItemEvents.CompletedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Reject.Response apply(
    PurchaseRequestItemMessages.Reject.Request request) {
    if (!this.isRejectable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.REJECTED;
    return new PurchaseRequestItemMessages.Reject.Response(
      Arrays.asList(new PurchaseRequestItemEvents.RejectedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Cancel.Response apply(
    PurchaseRequestItemMessages.Cancel.Request request) {
    if (!this.isCancelable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.CANCELED;
    return new PurchaseRequestItemMessages.Cancel.Response(
      Arrays.asList(new PurchaseRequestItemEvents.CanceledEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Progress.Response apply(
    PurchaseRequestItemMessages.Progress.Request request) {
    if (!this.isProgressable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.IN_PROGRESS;
    return new PurchaseRequestItemMessages.Progress.Response(
      Arrays.asList(new PurchaseRequestItemEvents.ProgressedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.CancelProgress.Response apply(
    PurchaseRequestItemMessages.CancelProgress.Request request) {
    if (!this.isProgressCancelable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    this.status = PurchaseRequestItemStatusKind.ACCEPTED;
    return new PurchaseRequestItemMessages.CancelProgress.Response(
      Arrays.asList(new PurchaseRequestItemEvents.ProgressCanceledEvent(this.id))
    );
  }

  public boolean isAcceptable() {
    return status.isAcceptable();
  }

  public boolean isCancelable() {
    return status.isCancelable();
  }

  public boolean isCommittable() {
    return status.isCommittable();
  }

  public boolean isCompletable() {
    return status.isCompletable();
  }

  public boolean isProgressCancelable() {
    return status.isProgressCancelable();
  }

  public boolean isProgressable() {
    return status.isProgressable();
  }

  public boolean isRejectable() {
    return status.isRejectable();
  }

  public boolean isUpdatable() {
    return status.isUpdatable();
  }


}

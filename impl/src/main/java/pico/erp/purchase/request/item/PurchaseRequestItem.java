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

    return new PurchaseRequestItemMessages.Create.Response(
      Arrays.asList(new PurchaseRequestItemEvents.CreatedEvent(this.id))
    );
  }

  public PurchaseRequestItemMessages.Update.Response apply(
    PurchaseRequestItemMessages.Update.Request request) {
    if (!this.request.isUpdatable()) {
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
    if (!this.request.isUpdatable()) {
      throw new PurchaseRequestItemExceptions.CannotDeleteException();
    }
    return new PurchaseRequestItemMessages.Delete.Response(
      Arrays.asList(new PurchaseRequestItemEvents.DeletedEvent(this.id))
    );
  }


}

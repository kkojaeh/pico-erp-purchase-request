package pico.erp.purchase.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface PurchaseRequestEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.created";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class ProgressedEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.progressed";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class AcceptedEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.accepted";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.updated";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CanceledEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.canceled";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CompletedEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.completed";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class RejectedEvent implements Event {

    public final static String CHANNEL = "event.purchase-request.rejected";

    private PurchaseRequestId purchaseRequestId;

    public String channel() {
      return CHANNEL;
    }

  }


}

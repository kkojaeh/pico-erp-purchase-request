package pico.erp.purchase.request.item;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.purchase.request.PurchaseRequestEvents;

@SuppressWarnings("unused")
@Component
public class PurchaseRequestItemEventListener {

  private static final String LISTENER_NAME = "listener.purchase-request-item-event-listener";


  @Autowired
  private PurchaseRequestItemService purchaseRequestItemService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + PurchaseRequestEvents.AcceptedEvent.CHANNEL)
  public void onRequestAccepted(PurchaseRequestEvents.AcceptedEvent event) {
    val requestId = event.getPurchaseRequestId();

    purchaseRequestItemService.getAll(requestId).forEach(item -> {
      purchaseRequestItemService.accept(
        PurchaseRequestItemRequests.AcceptRequest.builder()
          .id(item.getId())
          .build()
      );
    });
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + PurchaseRequestEvents.CanceledEvent.CHANNEL)
  public void onRequestCanceled(PurchaseRequestEvents.CanceledEvent event) {
    val requestId = event.getPurchaseRequestId();

    purchaseRequestItemService.getAll(requestId).forEach(item -> {
      purchaseRequestItemService.cancel(
        PurchaseRequestItemRequests.CancelRequest.builder()
          .id(item.getId())
          .build()
      );
    });
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + PurchaseRequestEvents.CommittedEvent.CHANNEL)
  public void onRequestCommitted(PurchaseRequestEvents.CommittedEvent event) {
    val requestId = event.getPurchaseRequestId();

    purchaseRequestItemService.getAll(requestId).forEach(item -> {
      purchaseRequestItemService.commit(
        PurchaseRequestItemRequests.CommitRequest.builder()
          .id(item.getId())
          .build()
      );
    });
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + PurchaseRequestEvents.RejectedEvent.CHANNEL)
  public void onRequestRejected(PurchaseRequestEvents.RejectedEvent event) {
    val requestId = event.getPurchaseRequestId();

    purchaseRequestItemService.getAll(requestId).forEach(item -> {
      purchaseRequestItemService.reject(
        PurchaseRequestItemRequests.RejectRequest.builder()
          .id(item.getId())
          .build()
      );
    });
  }

}

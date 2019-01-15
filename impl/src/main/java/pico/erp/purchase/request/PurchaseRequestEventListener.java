package pico.erp.purchase.request;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.purchase.request.item.PurchaseRequestItemEvents;
import pico.erp.purchase.request.item.PurchaseRequestItemService;
import pico.erp.purchase.request.item.PurchaseRequestItemStatusKind;

@SuppressWarnings("unused")
@Component
public class PurchaseRequestEventListener {

  private static final String LISTENER_NAME = "listener.purchase-request-event-listener";

  @Autowired
  private PurchaseRequestItemService purchaseRequestItemService;

  @Autowired
  private PurchaseRequestService purchaseRequestService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + PurchaseRequestItemEvents.CompletedEvent.CHANNEL)
  public void onRequestItemCompleted(PurchaseRequestItemEvents.CompletedEvent event) {
    val requestItem = purchaseRequestItemService.get(event.getPurchaseRequestItemId());
    val requestId = requestItem.getRequestId();

    val completed = purchaseRequestItemService.getAll(requestId).stream()
      .allMatch(item -> item.getStatus() == PurchaseRequestItemStatusKind.COMPLETED);

    if (completed) {
      purchaseRequestService.complete(
        PurchaseRequestRequests.CompleteRequest.builder()
          .id(requestId)
          .build()
      );
    }
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + PurchaseRequestItemEvents.CompletedEvent.CHANNEL)
  public void onRequestItemProgressed(PurchaseRequestItemEvents.ProgressedEvent event) {
    val requestItem = purchaseRequestItemService.get(event.getPurchaseRequestItemId());
    val requestId = requestItem.getRequestId();

    purchaseRequestService.progress(
      PurchaseRequestRequests.ProgressRequest.builder()
        .id(requestId)
        .build()
    );
  }

  // TODO: 생산 계획에 비율 반영

}

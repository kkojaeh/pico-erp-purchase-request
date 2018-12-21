package pico.erp.purchase.request.detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.purchase.request.PurchaseRequestEvents;
import pico.erp.purchase.request.PurchaseRequestService;

@SuppressWarnings("unused")
@Component
public class PurchaseRequestItemEventListener {

  private static final String LISTENER_NAME = "listener.purchase-request-item-event-listener";

}

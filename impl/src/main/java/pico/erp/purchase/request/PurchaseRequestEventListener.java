package pico.erp.purchase.request;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Component
@Transactional
public class PurchaseRequestEventListener {

  private static final String LISTENER_NAME = "listener.purchase-request-event-listener";

}

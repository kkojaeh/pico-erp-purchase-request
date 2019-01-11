package pico.erp.purchase.request;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.user.group.GroupRequests;
import pico.erp.user.group.GroupService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class PurchaseRequestInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  GroupService groupService;

  @Autowired
  PurchaseRequestProperties properties;

  @Override
  public void initialize() {
    val acceptGroup = properties.getAcceptGroup();
    if (!groupService.exists(acceptGroup.getId())) {
      groupService.create(
        GroupRequests.CreateRequest.builder()
          .id(acceptGroup.getId())
          .name(acceptGroup.getName())
          .build()
      );
    }
  }
}

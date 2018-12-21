package pico.erp.purchase.request;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import pico.erp.item.ItemRequests;
import pico.erp.item.ItemService;
import pico.erp.item.category.ItemCategoryRequests;
import pico.erp.item.category.ItemCategoryService;
import pico.erp.item.lot.ItemLotRequests;
import pico.erp.item.lot.ItemLotService;
import pico.erp.item.spec.ItemSpecRequests;
import pico.erp.item.spec.ItemSpecService;
import pico.erp.item.spec.variables.ItemSpecVariables;
import pico.erp.shared.ApplicationInitializer;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@Profile({"!development", "!production"})
public class TestDataInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  private PurchaseRequestService purchaseRequestService;


  @Autowired
  private DataProperties dataProperties;

  @Override
  public void initialize() {
    dataProperties.purchaseRequests.forEach(purchaseRequestService::create);
  }

  @Data
  @Configuration
  @ConfigurationProperties("data")
  public static class DataProperties {

    List<PurchaseRequestRequests.CreateRequest> purchaseRequests = new LinkedList<>();

  }

}

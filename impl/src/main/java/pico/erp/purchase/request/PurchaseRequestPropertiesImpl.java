package pico.erp.purchase.request;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pico.erp.shared.Public;
import pico.erp.user.group.GroupData;

@Public
@Data
@Configuration
@ConfigurationProperties("purchase-request")
public class PurchaseRequestPropertiesImpl implements PurchaseRequestProperties {

  GroupData acceptGroup;

}

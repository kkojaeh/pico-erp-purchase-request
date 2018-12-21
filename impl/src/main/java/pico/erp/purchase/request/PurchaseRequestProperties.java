package pico.erp.purchase.request;

import java.time.LocalTime;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("purchase-request")
public class PurchaseRequestProperties {

  DetailGenerationPolicy detailGenerationPolicy;

  @Data
  public static class DetailGenerationPolicy {

    LocalTime startTime;

    LocalTime endTime;

  }

}

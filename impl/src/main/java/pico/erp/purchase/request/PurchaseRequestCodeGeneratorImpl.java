package pico.erp.purchase.request;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PurchaseRequestCodeGeneratorImpl implements PurchaseRequestCodeGenerator {

  @Lazy
  @Autowired
  private PurchaseRequestRepository purchaseRequestRepository;

  @Override
  public PurchaseRequestCode generate(PurchaseRequest purchaseRequest) {
    val now = LocalDateTime.now();
    val begin = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    val end = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    val count = purchaseRequestRepository.countCreatedBetween(begin, end);
    val code = String
      .format("PR%03d%02d-%04d", now.getYear() % 1000, now.getMonthValue(), count + 1)
      .toUpperCase();
    return PurchaseRequestCode.from(code);
  }
}

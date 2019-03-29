package pico.erp.purchase.request;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestRepository {

  long countCreatedBetween(LocalDateTime begin, LocalDateTime end);

  PurchaseRequest create(@NotNull PurchaseRequest orderAcceptance);

  void deleteBy(@NotNull PurchaseRequestId id);

  boolean exists(@NotNull PurchaseRequestId id);

  Optional<PurchaseRequest> findBy(@NotNull PurchaseRequestId id);

  void update(@NotNull PurchaseRequest orderAcceptance);

}

package pico.erp.purchase.request.item;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.purchase.request.PurchaseRequestId;

@Repository
public interface PurchaseRequestItemRepository {

  PurchaseRequestItem create(@NotNull PurchaseRequestItem item);

  void deleteBy(@NotNull PurchaseRequestItemId id);

  boolean exists(@NotNull PurchaseRequestItemId id);

  Stream<PurchaseRequestItem> findAllBy(@NotNull PurchaseRequestId planId);

  Optional<PurchaseRequestItem> findBy(@NotNull PurchaseRequestItemId id);

  void update(@NotNull PurchaseRequestItem item);

}

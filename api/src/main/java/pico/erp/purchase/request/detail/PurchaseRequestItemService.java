package pico.erp.purchase.request.detail;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.purchase.request.PurchaseRequestId;

public interface PurchaseRequestItemService {

  PurchaseRequestItemData create(
    @Valid @NotNull PurchaseRequestItemRequests.CreateRequest request);

  void delete(@Valid @NotNull PurchaseRequestItemRequests.DeleteRequest request);

  boolean exists(@Valid @NotNull PurchaseRequestItemId id);

  PurchaseRequestItemData get(@Valid @NotNull PurchaseRequestItemId id);

  List<PurchaseRequestItemData> getAll(PurchaseRequestId planId);

  void update(@Valid @NotNull PurchaseRequestItemRequests.UpdateRequest request);


}

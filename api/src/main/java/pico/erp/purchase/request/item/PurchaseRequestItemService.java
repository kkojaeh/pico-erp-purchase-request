package pico.erp.purchase.request.item;

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

  void accept(@Valid @NotNull PurchaseRequestItemRequests.AcceptRequest request);

  void update(@Valid @NotNull PurchaseRequestItemRequests.UpdateRequest request);

  void cancel(@Valid @NotNull PurchaseRequestItemRequests.CancelRequest request);

  void cancelProgress(@Valid @NotNull PurchaseRequestItemRequests.CancelProgressRequest request);

  void commit(@Valid @NotNull PurchaseRequestItemRequests.CommitRequest request);

  void complete(@Valid @NotNull PurchaseRequestItemRequests.CompleteRequest request);

  List<PurchaseRequestItemData> getAll(@Valid @NotNull PurchaseRequestId requestId);

  void progress(@Valid @NotNull PurchaseRequestItemRequests.ProgressRequest request);

  void reject(@Valid @NotNull PurchaseRequestItemRequests.RejectRequest request);


}

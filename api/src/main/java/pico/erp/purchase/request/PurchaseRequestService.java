package pico.erp.purchase.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PurchaseRequestService {

  void cancel(@Valid @NotNull PurchaseRequestRequests.CancelRequest request);

  PurchaseRequestData create(@Valid @NotNull PurchaseRequestRequests.CreateRequest request);

  boolean exists(@Valid @NotNull PurchaseRequestId id);

  PurchaseRequestData get(@Valid @NotNull PurchaseRequestId id);

  void update(@Valid @NotNull PurchaseRequestRequests.UpdateRequest request);

  void accept(@Valid @NotNull PurchaseRequestRequests.AcceptRequest request);

  void commit(@Valid @NotNull PurchaseRequestRequests.CommitRequest request);

  void complete(@Valid @NotNull PurchaseRequestRequests.CompleteRequest request);

  void reject(@Valid @NotNull PurchaseRequestRequests.RejectRequest request);

  void progress(@Valid @NotNull PurchaseRequestRequests.ProgressRequest request);


}

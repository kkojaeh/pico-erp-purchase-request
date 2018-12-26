package pico.erp.purchase.request.item;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.item.spec.ItemSpecData;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.item.spec.ItemSpecService;
import pico.erp.purchase.request.PurchaseRequest;
import pico.erp.purchase.request.PurchaseRequestExceptions;
import pico.erp.purchase.request.PurchaseRequestId;
import pico.erp.purchase.request.PurchaseRequestMapper;
import pico.erp.shared.data.Auditor;

@Mapper
public abstract class PurchaseRequestItemMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @Lazy
  @Autowired
  protected ItemService itemService;

  @Lazy
  @Autowired
  protected ItemSpecService itemSpecService;

  @Lazy
  @Autowired
  private PurchaseRequestItemRepository purchaseRequestItemRepository;


  @Autowired
  private PurchaseRequestMapper requestMapper;

  protected PurchaseRequestItemId id(PurchaseRequestItem purchaseRequestItem) {
    return purchaseRequestItem != null ? purchaseRequestItem.getId() : null;
  }

  @Mappings({
    @Mapping(target = "requestId", source = "request.id"),
    @Mapping(target = "itemId", source = "item.id"),
    @Mapping(target = "itemSpecId", source = "itemSpec.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract PurchaseRequestItemEntity jpa(PurchaseRequestItem data);

  public PurchaseRequestItem jpa(PurchaseRequestItemEntity entity) {
    return PurchaseRequestItem.builder()
      .id(entity.getId())
      .request(map(entity.getRequestId()))
      .item(map(entity.getItemId()))
      .itemSpec(map(entity.getItemSpecId()))
      .quantity(entity.getQuantity())
      .remark(entity.getRemark())
      .status(entity.getStatus())
      .build();
  }

  public PurchaseRequestItem map(PurchaseRequestItemId purchaseRequestItemId) {
    return Optional.ofNullable(purchaseRequestItemId)
      .map(id -> purchaseRequestItemRepository.findBy(id)
        .orElseThrow(PurchaseRequestExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected ItemSpecData map(ItemSpecId itemSpecId) {
    return Optional.ofNullable(itemSpecId)
      .map(itemSpecService::get)
      .orElse(null);
  }

  protected PurchaseRequest map(PurchaseRequestId purchaseRequestId) {
    return requestMapper.map(purchaseRequestId);
  }

  @Mappings({
    @Mapping(target = "requestId", source = "request.id"),
    @Mapping(target = "itemId", source = "item.id"),
    @Mapping(target = "itemSpecId", source = "itemSpec.id")
  })
  public abstract PurchaseRequestItemData map(PurchaseRequestItem item);

  @Mappings({
    @Mapping(target = "request", source = "requestId"),
    @Mapping(target = "item", source = "itemId"),
    @Mapping(target = "itemSpec", source = "itemSpecId")
  })
  public abstract PurchaseRequestItemMessages.Create.Request map(
    PurchaseRequestItemRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "itemSpec", source = "itemSpecId")
  })
  public abstract PurchaseRequestItemMessages.Update.Request map(
    PurchaseRequestItemRequests.UpdateRequest request);

  public abstract PurchaseRequestItemMessages.Delete.Request map(
    PurchaseRequestItemRequests.DeleteRequest request);

  public abstract PurchaseRequestItemMessages.Accept.Request map(
    PurchaseRequestItemRequests.AcceptRequest request);

  public abstract PurchaseRequestItemMessages.Commit.Request map(
    PurchaseRequestItemRequests.CommitRequest request);

  public abstract PurchaseRequestItemMessages.Complete.Request map(
    PurchaseRequestItemRequests.CompleteRequest request);

  public abstract PurchaseRequestItemMessages.Reject.Request map(
    PurchaseRequestItemRequests.RejectRequest request);

  public abstract PurchaseRequestItemMessages.Cancel.Request map(
    PurchaseRequestItemRequests.CancelRequest request);

  public abstract PurchaseRequestItemMessages.Progress.Request map(
    PurchaseRequestItemRequests.ProgressRequest request);

  public abstract PurchaseRequestItemMessages.CancelProgress.Request map(
    PurchaseRequestItemRequests.CancelProgressRequest request);

  public abstract PurchaseRequestItemMessages.Plan.Request map(
    PurchaseRequestItemRequests.PlanRequest request);


  public abstract void pass(
    PurchaseRequestItemEntity from, @MappingTarget PurchaseRequestItemEntity to);


}




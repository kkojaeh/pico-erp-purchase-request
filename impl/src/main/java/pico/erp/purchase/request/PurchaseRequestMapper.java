package pico.erp.purchase.request;

import java.util.Optional;
import kkojaeh.spring.boot.component.ComponentAutowired;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.company.CompanyData;
import pico.erp.company.CompanyId;
import pico.erp.company.CompanyService;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.item.spec.ItemSpecData;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.item.spec.ItemSpecService;
import pico.erp.project.ProjectData;
import pico.erp.project.ProjectId;
import pico.erp.project.ProjectService;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;
import pico.erp.warehouse.location.site.SiteData;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.site.SiteService;
import pico.erp.warehouse.location.station.StationData;
import pico.erp.warehouse.location.station.StationId;
import pico.erp.warehouse.location.station.StationService;

@Mapper
public abstract class PurchaseRequestMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @ComponentAutowired
  protected ItemService itemService;

  @ComponentAutowired
  protected ItemSpecService itemSpecService;

  @Autowired
  protected PurchaseRequestCodeGenerator purchaseRequestCodeGenerator;

  @ComponentAutowired
  private CompanyService companyService;

  @ComponentAutowired
  private UserService userService;

  @Lazy
  @Autowired
  private PurchaseRequestRepository purchaseRequestRepository;

  @ComponentAutowired
  private ProjectService projectService;

  @ComponentAutowired
  private SiteService siteService;

  @ComponentAutowired
  private StationService stationService;

  protected Auditor auditor(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::getAuditor)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract PurchaseRequestEntity jpa(PurchaseRequest data);

  public PurchaseRequest jpa(PurchaseRequestEntity entity) {
    return PurchaseRequest.builder()
      .id(entity.getId())
      .code(entity.getCode())
      .itemId(entity.getItemId())
      .itemSpecId(entity.getItemSpecId())
      .itemSpecCode(entity.getItemSpecCode())
      .quantity(entity.getQuantity())
      .progressedQuantity(entity.getProgressedQuantity())
      .unit(entity.getUnit())
      .projectId(entity.getProjectId())
      .dueDate(entity.getDueDate())
      .supplierId(entity.getSupplierId())
      .receiverId(entity.getReceiverId())
      .receiveSiteId(entity.getReceiveSiteId())
      .receiveStationId(entity.getReceiveStationId())
      .remark(entity.getRemark())
      .requesterId(entity.getRequesterId())
      .accepterId(entity.getAccepterId())
      .committedDate(entity.getCommittedDate())
      .acceptedDate(entity.getAcceptedDate())
      .completedDate(entity.getCompletedDate())
      .rejectedDate(entity.getRejectedDate())
      .canceledDate(entity.getCanceledDate())
      .status(entity.getStatus())
      .rejectedReason(entity.getRejectedReason())
      .build();
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected CompanyData map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(companyService::get)
      .orElse(null);
  }

  protected ProjectData map(ProjectId projectId) {
    return Optional.ofNullable(projectId)
      .map(projectService::get)
      .orElse(null);
  }

  public PurchaseRequest map(PurchaseRequestId purchaseRequestId) {
    return Optional.ofNullable(purchaseRequestId)
      .map(id -> purchaseRequestRepository.findBy(id)
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

  protected StationData map(StationId stationId) {
    return Optional.ofNullable(stationId)
      .map(stationService::get)
      .orElse(null);
  }

  protected SiteData map(SiteId siteId) {
    return Optional.ofNullable(siteId)
      .map(siteService::get)
      .orElse(null);
  }

  @Mappings({
  })
  public abstract PurchaseRequestData map(PurchaseRequest purchaseRequest);

  @Mappings({
    @Mapping(target = "codeGenerator", expression = "java(purchaseRequestCodeGenerator)")
  })
  public abstract PurchaseRequestMessages.Create.Request map(
    PurchaseRequestRequests.CreateRequest request);

  public abstract PurchaseRequestMessages.Update.Request map(
    PurchaseRequestRequests.UpdateRequest request);


  public abstract PurchaseRequestMessages.Accept.Request map(
    PurchaseRequestRequests.AcceptRequest request);

  public abstract PurchaseRequestMessages.Commit.Request map(
    PurchaseRequestRequests.CommitRequest request);

  public abstract PurchaseRequestMessages.Complete.Request map(
    PurchaseRequestRequests.CompleteRequest request);

  public abstract PurchaseRequestMessages.Cancel.Request map(
    PurchaseRequestRequests.CancelRequest request);

  public abstract PurchaseRequestMessages.Reject.Request map(
    PurchaseRequestRequests.RejectRequest request);

  public abstract PurchaseRequestMessages.Progress.Request map(
    PurchaseRequestRequests.ProgressRequest request);

  public abstract PurchaseRequestMessages.Plan.Request map(
    PurchaseRequestRequests.PlanRequest request);

  public abstract PurchaseRequestMessages.CancelProgress.Request map(
    PurchaseRequestRequests.CancelProgressRequest request);

  public abstract void pass(PurchaseRequestEntity from, @MappingTarget PurchaseRequestEntity to);


}



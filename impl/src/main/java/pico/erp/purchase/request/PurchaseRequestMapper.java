package pico.erp.purchase.request;

import java.util.Optional;
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

  @Lazy
  @Autowired
  protected ItemService itemService;

  @Lazy
  @Autowired
  protected ItemSpecService itemSpecService;

  @Autowired
  protected PurchaseRequestCodeGenerator purchaseRequestCodeGenerator;

  @Lazy
  @Autowired
  private CompanyService companyService;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private PurchaseRequestRepository purchaseRequestRepository;

  @Lazy
  @Autowired
  private ProjectService projectService;

  @Lazy
  @Autowired
  private SiteService siteService;

  @Lazy
  @Autowired
  private StationService stationService;

  @Mappings({
    @Mapping(target = "receiverId", source = "receiver.id"),
    @Mapping(target = "receiveSiteId", source = "receiveSite.id"),
    @Mapping(target = "receiveStationId", source = "receiveStation.id"),
    @Mapping(target = "projectId", source = "project.id"),
    @Mapping(target = "requesterId", source = "requester.id"),
    @Mapping(target = "accepterId", source = "accepter.id"),
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
      .project(map(entity.getProjectId()))
      .dueDate(entity.getDueDate())
      .receiver(map(entity.getReceiverId()))
      .receiveSite(map(entity.getReceiveSiteId()))
      .receiveStation(map(entity.getReceiveStationId()))
      .remark(entity.getRemark())
      .requester(map(entity.getRequesterId()))
      .accepter(map(entity.getAccepterId()))
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

  protected Auditor auditor(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::getAuditor)
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
    @Mapping(target = "receiverId", source = "receiver.id"),
    @Mapping(target = "receiveSiteId", source = "receiveSite.id"),
    @Mapping(target = "receiveStationId", source = "receiveStation.id"),
    @Mapping(target = "projectId", source = "project.id"),
    @Mapping(target = "requesterId", source = "requester.id"),
    @Mapping(target = "accepterId", source = "accepter.id")

  })
  public abstract PurchaseRequestData map(PurchaseRequest purchaseRequest);

  @Mappings({
    @Mapping(target = "receiver", source = "receiverId"),
    @Mapping(target = "receiveSite", source = "receiveSiteId"),
    @Mapping(target = "receiveStation", source = "receiveStationId"),
    @Mapping(target = "project", source = "projectId"),
    @Mapping(target = "requester", source = "requesterId"),
    @Mapping(target = "codeGenerator", expression = "java(purchaseRequestCodeGenerator)")
  })
  public abstract PurchaseRequestMessages.Create.Request map(
    PurchaseRequestRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "receiver", source = "receiverId"),
    @Mapping(target = "receiveSite", source = "receiveSiteId"),
    @Mapping(target = "receiveStation", source = "receiveStationId"),
    @Mapping(target = "project", source = "projectId")
  })
  public abstract PurchaseRequestMessages.Update.Request map(
    PurchaseRequestRequests.UpdateRequest request);


  @Mappings({
    @Mapping(target = "accepter", source = "accepterId")
  })
  public abstract PurchaseRequestMessages.Accept.Request map(
    PurchaseRequestRequests.AcceptRequest request);

  @Mappings({
    @Mapping(target = "committer", source = "committerId")
  })
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

  public abstract void pass(PurchaseRequestEntity from, @MappingTarget PurchaseRequestEntity to);


}



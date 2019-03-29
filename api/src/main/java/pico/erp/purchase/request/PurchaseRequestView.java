package pico.erp.purchase.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.project.ProjectId;
import pico.erp.shared.data.UnitKind;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

@Data
public class PurchaseRequestView {

  PurchaseRequestId id;

  PurchaseRequestCode code;

  ItemId itemId;

  ItemSpecId itemSpecId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  BigDecimal progressedQuantity;

  UnitKind unit;

  UserId requesterId;

  UserId accepterId;

  ProjectId projectId;

  CompanyId supplierId;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  LocalDateTime dueDate;

  LocalDateTime committedDate;

  LocalDateTime completedDate;

  LocalDateTime acceptedDate;

  LocalDateTime rejectedDate;

  LocalDateTime canceledDate;

  PurchaseRequestStatusKind status;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String code;

    CompanyId receiverId;

    UserId requesterId;

    UserId accepterId;

    ProjectId projectId;

    ItemId itemId;

    Set<PurchaseRequestStatusKind> statuses;

    LocalDateTime startDueDate;

    LocalDateTime endDueDate;

  }

}

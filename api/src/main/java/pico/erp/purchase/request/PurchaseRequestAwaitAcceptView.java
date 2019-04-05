package pico.erp.purchase.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
public class PurchaseRequestAwaitAcceptView {

  PurchaseRequestId id;

  PurchaseRequestCode code;

  ItemId itemId;

  ItemSpecId itemSpecId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  UnitKind unit;

  UserId requesterId;

  ProjectId projectId;

  CompanyId supplierId;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  OffsetDateTime committedDate;

  OffsetDateTime dueDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String code;

    CompanyId receiverId;

    UserId requesterId;

    ProjectId projectId;

    ItemId itemId;

    OffsetDateTime startDueDate;

    OffsetDateTime endDueDate;

  }

}

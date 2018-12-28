package pico.erp.purchase.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.project.ProjectId;
import pico.erp.purchase.request.item.PurchaseRequestItemId;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

@Data
public class PurchaseRequestAwaitOrderView {

  PurchaseRequestId requestId;

  PurchaseRequestItemId requestItemId;

  ItemId itemId;

  ItemSpecId itemSpecId;

  BigDecimal quantity;

  UserId requesterId;

  ProjectId projectId;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  OffsetDateTime committedDate;

  OffsetDateTime acceptedDate;

  OffsetDateTime dueDate;


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    CompanyId receiverId;

    UserId requesterId;

    ProjectId projectId;

    ItemId itemId;

    OffsetDateTime startDueDate;

    OffsetDateTime endDueDate;

  }

}

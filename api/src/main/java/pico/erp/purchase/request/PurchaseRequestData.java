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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PurchaseRequestData {

  PurchaseRequestId id;

  PurchaseRequestCode code;

  ItemId itemId;

  ItemSpecId itemSpecId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  UnitKind unit;

  UserId requesterId;

  UserId accepterId;

  ProjectId projectId;

  String rejectedReason;

  CompanyId supplierId;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  OffsetDateTime dueDate;

  OffsetDateTime committedDate;

  OffsetDateTime completedDate;

  OffsetDateTime acceptedDate;

  OffsetDateTime rejectedDate;

  OffsetDateTime canceledDate;

  PurchaseRequestStatusKind status;

  String remark;

  boolean cancelable;

  boolean completable;

  boolean acceptable;

  boolean progressable;

  boolean rejectable;

  boolean committable;

  boolean updatable;

  boolean plannable;

  boolean progressCancelable;

}

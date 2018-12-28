package pico.erp.purchase.request;

import java.time.OffsetDateTime;
import lombok.Data;
import pico.erp.company.CompanyId;
import pico.erp.project.ProjectId;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

@Data
public class PurchaseRequestData {

  PurchaseRequestId id;

  PurchaseRequestCode code;

  String name;

  UserId requesterId;

  UserId accepterId;

  ProjectId projectId;

  String rejectedReason;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  OffsetDateTime dueDate;

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

}

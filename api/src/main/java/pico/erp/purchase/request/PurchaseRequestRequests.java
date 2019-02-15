package pico.erp.purchase.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.project.ProjectId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.UnitKind;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

public interface PurchaseRequestRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

    @Valid
    @NotNull
    ItemId itemId;

    @Valid
    ItemSpecId itemSpecId;

    @Valid
    @NotNull
    ItemSpecCode itemSpecCode;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @NotNull
    UnitKind unit;

    @Valid
    @NotNull
    ProjectId projectId;

    @Future
    @NotNull
    OffsetDateTime dueDate;

    @Valid
    CompanyId supplierId;

    @Valid
    @NotNull
    CompanyId receiverId;

    @Valid
    SiteId receiveSiteId;

    @Valid
    StationId receiveStationId;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

    @Valid
    @NotNull
    UserId requesterId;


  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

    @Valid
    @NotNull
    ItemId itemId;

    @Valid
    ItemSpecId itemSpecId;

    @Valid
    @NotNull
    ItemSpecCode itemSpecCode;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @NotNull
    UnitKind unit;

    @Valid
    @NotNull
    ProjectId projectId;

    @Future
    @NotNull
    OffsetDateTime dueDate;

    @Valid
    CompanyId supplierId;

    @Valid
    @NotNull
    CompanyId receiverId;

    @Valid
    SiteId receiveSiteId;

    @Valid
    StationId receiveStationId;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class AcceptRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

    @Valid
    @NotNull
    UserId accepterId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CommitRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

    @Valid
    @NotNull
    UserId committerId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CompleteRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class RejectRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String rejectedReason;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CancelRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class ProgressRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class PlanRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CancelProgressRequest {

    @Valid
    @NotNull
    PurchaseRequestId id;

  }

}

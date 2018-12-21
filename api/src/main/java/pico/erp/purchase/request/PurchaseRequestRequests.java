package pico.erp.purchase.request;

import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.project.ProjectId;
import pico.erp.shared.TypeDefinitions;
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
    ProjectId projectId;

    @Future
    @NotNull
    OffsetDateTime dueDate;

    @Valid
    @NotNull
    CompanyId receiveCompanyId;

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
    ProjectId projectId;

    @Future
    @NotNull
    OffsetDateTime dueDate;

    @Valid
    @NotNull
    CompanyId receiveCompanyId;

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

}

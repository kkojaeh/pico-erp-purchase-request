package pico.erp.purchase.request.detail;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.purchase.request.PurchaseRequestId;
import pico.erp.shared.TypeDefinitions;

public interface PurchaseRequestItemRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    PurchaseRequestItemId id;

    @Valid
    PurchaseRequestId requestId;

    @Valid
    @NotNull
    ItemId itemId;

    @Valid
    ItemSpecId itemSpecId;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    PurchaseRequestItemId id;

    @Valid
    ItemSpecId itemSpecId;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    PurchaseRequestItemId id;

  }
}

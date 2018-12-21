package pico.erp.purchase.request.detail;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.purchase.request.PurchaseRequestId;
import pico.erp.shared.data.UnitKind;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseRequestItemData {

  PurchaseRequestItemId id;

  PurchaseRequestId requestId;

  ItemId itemId;

  ItemSpecId itemSpecId;

  BigDecimal quantity;

  String remark;

}

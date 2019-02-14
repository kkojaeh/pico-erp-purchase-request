package pico.erp.purchase.request;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseRequestQuery {

  Page<PurchaseRequestView> retrieve(@NotNull PurchaseRequestView.Filter filter,
    @NotNull Pageable pageable);

  Page<PurchaseRequestAwaitOrderView> retrieve(@NotNull PurchaseRequestAwaitOrderView.Filter filter,
    @NotNull Pageable pageable);

  Page<PurchaseRequestAwaitAcceptView> retrieve(
    @NotNull PurchaseRequestAwaitAcceptView.Filter filter, @NotNull Pageable pageable);


}

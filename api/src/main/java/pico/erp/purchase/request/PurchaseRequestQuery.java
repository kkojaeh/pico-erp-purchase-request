package pico.erp.purchase.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.project.ProjectView;
import pico.erp.shared.data.LabeledValuable;

public interface PurchaseRequestQuery {

  Page<PurchaseRequestView> retrieve(@NotNull PurchaseRequestView.Filter filter, @NotNull Pageable pageable);

}

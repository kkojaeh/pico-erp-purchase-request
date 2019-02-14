package pico.erp.purchase.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.project.ProjectId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

public interface PurchaseRequestMessages {

  interface Create {

    @Data
    class Request {

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

      @Valid
      @NotNull
      ProjectId projectId;

      @Future
      @NotNull
      OffsetDateTime dueDate;

      CompanyId supplierId;

      @NotNull
      CompanyId receiverId;

      @Valid
      SiteId receiveSiteId;

      @Valid
      StationId receiveStationId;

      @Size(max = TypeDefinitions.REMARK_LENGTH)
      String remark;

      @NotNull
      UserId requesterId;

      @NotNull
      PurchaseRequestCodeGenerator codeGenerator;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface Update {

    @Data
    class Request {

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

      @Valid
      @NotNull
      ProjectId projectId;

      @Future
      @NotNull
      OffsetDateTime dueDate;

      CompanyId supplierId;

      @NotNull
      CompanyId receiverId;

      @Valid
      SiteId receiveSiteId;

      @Valid
      StationId receiveStationId;

      @Size(max = TypeDefinitions.REMARK_LENGTH)
      String remark;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface Accept {

    @Data
    class Request {

      @NotNull
      UserId accepterId;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface Commit {

    @Data
    class Request {

      @NotNull
      UserId committerId;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }

  interface Complete {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Reject {

    @Data
    class Request {

      @Size(max = TypeDefinitions.REMARK_LENGTH)
      String rejectedReason;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Cancel {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Progress {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Plan {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }


}

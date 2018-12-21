package pico.erp.purchase.request;

import java.time.OffsetDateTime;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.company.CompanyData;
import pico.erp.project.ProjectData;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;
import pico.erp.shared.event.Event;
import pico.erp.warehouse.location.site.SiteData;
import pico.erp.warehouse.location.station.StationData;

public interface PurchaseRequestMessages {

  interface Create {

    @Data
    class Request {

      @Valid
      @NotNull
      PurchaseRequestId id;

      @Valid
      @NotNull
      ProjectData project;

      @Future
      @NotNull
      OffsetDateTime dueDate;

      @NotNull
      CompanyData receiver;

      @Valid
      SiteData receiveSite;

      @Valid
      StationData receiveStation;

      @Size(max = TypeDefinitions.REMARK_LENGTH)
      String remark;

      @NotNull
      Auditor requester;

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
      ProjectData project;

      @Future
      @NotNull
      OffsetDateTime dueDate;

      @NotNull
      CompanyData receiver;

      @Valid
      SiteData receiveSite;

      @Valid
      StationData receiveStation;

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
      Auditor accepter;

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
      Auditor committer;

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


}

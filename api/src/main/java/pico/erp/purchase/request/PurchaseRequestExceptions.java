package pico.erp.purchase.request;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface PurchaseRequestExceptions {

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.already.exists.exception")
  class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.update.exception")
  class CannotUpdateException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.accept.exception")
  class CannotAcceptException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.progress.exception")
  class CannotProgressException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.commit.exception")
  class CannotCommitException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.cancel.exception")
  class CannotCancelException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.complete.exception")
  class CannotCompleteException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.reject.exception")
  class CannotRejectException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "purchase-request.cannot.plan.exception")
  class CannotPlanException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }


  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "purchase-request.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}

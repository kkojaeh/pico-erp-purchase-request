package pico.erp.purchase.request;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.company.CompanyData;
import pico.erp.project.ProjectData;
import pico.erp.shared.data.Auditor;
import pico.erp.warehouse.location.site.SiteData;
import pico.erp.warehouse.location.station.StationData;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "purchase-request")
public class PurchaseRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  PurchaseRequestId id;

  PurchaseRequestCode code;

  ProjectData project;

  OffsetDateTime dueDate;

  CompanyData receiveCompany;

  SiteData receiveSite;

  StationData receiveStation;

  String remark;

  Auditor requester;

  Auditor accepter;

  OffsetDateTime committedDate;

  OffsetDateTime acceptedDate;

  OffsetDateTime completedDate;

  OffsetDateTime rejectedDate;

  OffsetDateTime canceledDate;

  PurchaseRequestStatusKind status;

  String rejectedReason;


  public PurchaseRequest() {

  }

  public PurchaseRequestMessages.Create.Response apply(
    PurchaseRequestMessages.Create.Request request) {
    this.id = request.getId();
    this.project = request.getProject();
    this.dueDate = request.getDueDate();
    this.receiveCompany = request.getReceiveCompany();
    this.receiveSite = request.getReceiveSite();
    this.receiveStation = request.getReceiveStation();
    this.remark = request.getRemark();
    this.status = PurchaseRequestStatusKind.DRAFT;
    this.requester = request.getRequester();
    this.code = request.getCodeGenerator().generate(this);
    return new PurchaseRequestMessages.Create.Response(
      Arrays.asList(new PurchaseRequestEvents.CreatedEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Update.Response apply(
    PurchaseRequestMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new PurchaseRequestExceptions.CannotUpdateException();
    }
    this.project = request.getProject();
    this.dueDate = request.getDueDate();
    this.receiveCompany = request.getReceiveCompany();
    this.receiveSite = request.getReceiveSite();
    this.receiveStation = request.getReceiveStation();
    this.remark = request.getRemark();
    return new PurchaseRequestMessages.Update.Response(
      Arrays.asList(new PurchaseRequestEvents.UpdatedEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Accept.Response apply(
    PurchaseRequestMessages.Accept.Request request) {
    if (!isAcceptable()) {
      throw new PurchaseRequestExceptions.CannotAcceptException();
    }
    this.status = PurchaseRequestStatusKind.ACCEPTED;
    this.accepter = request.getAccepter();
    this.acceptedDate = OffsetDateTime.now();
    return new PurchaseRequestMessages.Accept.Response(
      Arrays.asList(new PurchaseRequestEvents.AcceptedEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Cancel.Response apply(
    PurchaseRequestMessages.Cancel.Request request) {
    if (!isCancelable()) {
      throw new PurchaseRequestExceptions.CannotCancelException();
    }
    this.status = PurchaseRequestStatusKind.CANCELED;
    this.canceledDate = OffsetDateTime.now();
    return new PurchaseRequestMessages.Cancel.Response(
      Arrays.asList(new PurchaseRequestEvents.CanceledEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Complete.Response apply(
    PurchaseRequestMessages.Complete.Request request) {
    if (!isCompletable()) {
      throw new PurchaseRequestExceptions.CannotCompleteException();
    }
    this.status = PurchaseRequestStatusKind.COMPLETED;
    this.completedDate = OffsetDateTime.now();
    return new PurchaseRequestMessages.Complete.Response(
      Arrays.asList(new PurchaseRequestEvents.CompletedEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Commit.Response apply(
    PurchaseRequestMessages.Commit.Request request) {
    if (!isCommittable() || !requester.equals(request.getCommitter())) {
      throw new PurchaseRequestExceptions.CannotCommitException();
    }
    this.status = PurchaseRequestStatusKind.COMMITTED;
    this.committedDate = OffsetDateTime.now();
    return new PurchaseRequestMessages.Commit.Response(
      Arrays.asList(new PurchaseRequestEvents.CompletedEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Progress.Response apply(
    PurchaseRequestMessages.Progress.Request request) {
    if (!isProgressable()) {
      throw new PurchaseRequestExceptions.CannotProgressException();
    }
    this.status = PurchaseRequestStatusKind.IN_PROGRESS;
    return new PurchaseRequestMessages.Progress.Response(
      Arrays.asList(new PurchaseRequestEvents.ProgressedEvent(this.id))
    );
  }

  public PurchaseRequestMessages.Reject.Response apply(
    PurchaseRequestMessages.Reject.Request request) {
    if (!isRejectable()) {
      throw new PurchaseRequestExceptions.CannotRejectException();
    }
    this.status = PurchaseRequestStatusKind.REJECTED;
    this.rejectedDate = OffsetDateTime.now();
    this.rejectedReason = request.getRejectedReason();
    return new PurchaseRequestMessages.Reject.Response(
      Arrays.asList(new PurchaseRequestEvents.RejectedEvent(this.id))
    );
  }


  public boolean isCancelable() {
    return status.isCancelable();
  }

  public boolean isCompletable() {
    return status.isCompletable();
  }

  public boolean isAcceptable() {
    return status.isAcceptable();
  }

  public boolean isProgressable() {
    return status.isProgressable();
  }

  public boolean isRejectable() {
    return status.isRejectable();
  }

  public boolean isCommittable() {
    return status.isCommittable();
  }

  public boolean isUpdatable() {
    return status.isUpdatable();
  }


}

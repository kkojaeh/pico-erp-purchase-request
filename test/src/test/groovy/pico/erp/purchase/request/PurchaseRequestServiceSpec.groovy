package pico.erp.purchase.request

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyId
import pico.erp.item.ItemId
import pico.erp.item.spec.ItemSpecCode
import pico.erp.project.ProjectId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.UnitKind
import pico.erp.user.UserId
import pico.erp.warehouse.location.site.SiteId
import pico.erp.warehouse.location.station.StationId
import spock.lang.Specification

import java.time.OffsetDateTime

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class PurchaseRequestServiceSpec extends Specification {

  @Autowired
  PurchaseRequestService requestService

  def id = PurchaseRequestId.from("request-1")

  def unknownId = PurchaseRequestId.from("unknown")

  def projectId = ProjectId.from("sample-project1")

  def dueDate = OffsetDateTime.now().plusDays(7)

  def remark = "요청 비고"

  def receiverId = CompanyId.from("CUST1")

  def supplierId = CompanyId.from("SUPP1")

  def requesterId = UserId.from("kjh")

  def accepterId = UserId.from("kjh")

  def receiverId2 = CompanyId.from("CUST2")

  def projectId2 = ProjectId.from("sample-project2")

  def dueDate2 = OffsetDateTime.now().plusDays(8)

  def remark2 = "요청 비고2"

  def receiveSiteId = SiteId.from("A1")

  def receiveStationId = StationId.from("S1")

  def receiveStationId2 = StationId.from("S2")

  def itemId = ItemId.from("item-1")

  def itemSpecCode = ItemSpecCode.NOT_APPLICABLE

  def quantity = 100

  def unit = UnitKind.EA

  def setup() {
    requestService.create(
      new PurchaseRequestRequests.CreateRequest(
        id: id,
        itemId: itemId,
        itemSpecCode: itemSpecCode,
        quantity: quantity,
        unit: unit,
        projectId: projectId,
        supplierId: supplierId,
        receiverId: receiverId,
        receiveSiteId: receiveSiteId,
        receiveStationId: receiveStationId,
        requesterId: requesterId,
        dueDate: dueDate,
        remark: remark,
      )
    )
  }

  def cancelRequest() {
    requestService.cancel(
      new PurchaseRequestRequests.CancelRequest(
        id: id
      )
    )
  }

  def commitRequest() {
    requestService.commit(
      new PurchaseRequestRequests.CommitRequest(
        id: id,
        committerId: requesterId
      )
    )
  }

  def rejectRequest() {
    requestService.reject(
      new PurchaseRequestRequests.RejectRequest(
        id: id,
        rejectedReason: "필요 없는것 같음"
      )
    )
  }

  def planRequest() {
    requestService.plan(
      new PurchaseRequestRequests.PlanRequest(
        id: id
      )
    )
  }

  def progressRequest() {
    requestService.progress(
      new PurchaseRequestRequests.ProgressRequest(
        id: id,
        progressedQuantity: 50
      )
    )
  }

  def completeRequest() {
    requestService.complete(
      new PurchaseRequestRequests.CompleteRequest(
        id: id
      )
    )
  }

  def acceptRequest() {
    requestService.accept(
      new PurchaseRequestRequests.AcceptRequest(
        id: id,
        accepterId: accepterId
      )
    )
  }

  def updateRequest() {
    requestService.update(
      new PurchaseRequestRequests.UpdateRequest(
        id: id,
        itemId: itemId,
        itemSpecCode: itemSpecCode,
        quantity: quantity,
        unit: unit,
        projectId: projectId2,
        supplierId: supplierId,
        receiverId: receiverId2,
        receiveStationId: receiveStationId2,
        dueDate: dueDate2,
        remark: remark2,
      )
    )
  }


  def "존재 - 아이디로 존재 확인"() {
    when:
    def exists = requestService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = requestService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def request = requestService.get(id)

    then:
    request.id == id
    request.itemId == itemId
    request.itemSpecCode == itemSpecCode
    request.quantity == quantity
    request.unit == unit
    request.supplierId == supplierId
    request.receiverId == receiverId
    request.receiveSiteId == receiveSiteId
    request.receiveStationId == receiveStationId
    request.remark == remark
    request.projectId == projectId
    request.requesterId == requesterId
    request.dueDate == dueDate
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    requestService.get(unknownId)

    then:
    thrown(PurchaseRequestExceptions.NotFoundException)
  }


  def "수정 - 취소 후 수정"() {
    when:
    cancelRequest()
    updateRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotUpdateException)
  }

  def "수정 - 접수 후 수정"() {
    when:
    commitRequest()
    acceptRequest()
    updateRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotUpdateException)
  }

  def "수정 - 제출 후 수정"() {
    when:
    commitRequest()
    updateRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotUpdateException)
  }

  def "수정 - 완료 후 수정"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    updateRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotUpdateException)
  }

  def "수정 - 반려 후 수정"() {
    when:
    commitRequest()
    rejectRequest()
    updateRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotUpdateException)
  }

  def "수정 - 진행 후 수정"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    updateRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotUpdateException)
  }


  def "수정 - 작성 후 수정"() {
    when:
    updateRequest()
    def request = requestService.get(id)

    then:
    request.id == id
    request.itemId == itemId
    request.itemSpecCode == itemSpecCode
    request.quantity == quantity
    request.unit == unit
    request.projectId == projectId2
    request.receiverId == receiverId2
    request.receiveStationId == receiveStationId2
    request.dueDate == dueDate2
    request.remark == remark2
  }

  def "제출 - 작성 후 제출"() {
    when:
    commitRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.COMMITTED
    request.committedDate != null
  }

  def "제출 - 제출 후 제출"() {
    when:
    commitRequest()
    commitRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCommitException)
  }

  def "제출 - 진행 중 제출"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    commitRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCommitException)
  }

  def "제출 - 취소 후 제출"() {
    when:
    cancelRequest()
    commitRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCommitException)
  }

  def "제출 - 반려 후 제출"() {
    when:
    commitRequest()
    rejectRequest()
    commitRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCommitException)
  }

  def "제출 - 완료 후 제출"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    commitRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCommitException)
  }

  def "접수 - 작성 후 접수"() {
    when:
    acceptRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotAcceptException)
  }

  def "접수 - 접수 후 접수"() {
    when:
    commitRequest()
    acceptRequest()
    acceptRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotAcceptException)
  }

  def "접수 - 제출 후 접수"() {
    when:
    commitRequest()
    acceptRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.ACCEPTED
  }

  def "접수 - 진행 중 접수"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    acceptRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotAcceptException)
  }

  def "접수 - 취소 후 접수"() {
    when:
    cancelRequest()
    acceptRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotAcceptException)
  }

  def "접수 - 반려 후 접수"() {
    when:
    commitRequest()
    rejectRequest()
    acceptRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotAcceptException)
  }

  def "접수 - 완료 후 접수"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    acceptRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotAcceptException)
  }

  def "진행 - 작성 후 진행"() {
    when:
    progressRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotProgressException)
  }

  def "진행 - 제출 후 진행"() {
    when:
    commitRequest()
    progressRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotProgressException)
  }

  def "진행 - 접수 후 진행"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.IN_PROGRESS
  }

  def "진행 - 진행 중 진행"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    progressRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.IN_PROGRESS
  }

  def "진행 - 취소 후 진행"() {
    when:
    cancelRequest()
    progressRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotProgressException)
  }

  def "진행 - 반려 후 진행"() {
    when:
    commitRequest()
    rejectRequest()
    progressRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotProgressException)
  }

  def "진행 - 완료 후 진행"() {
    when:
    commitRequest()
    acceptRequest()
    progressRequest()
    completeRequest()
    progressRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotProgressException)
  }

  def "취소 - 취소 후에는 취소"() {
    when:
    cancelRequest()
    cancelRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCancelException)
  }

  def "취소 - 제출 후 취소"() {
    when:
    commitRequest()
    cancelRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.CANCELED
  }

  def "취소 - 접수 후 취소"() {
    when:
    commitRequest()
    acceptRequest()
    cancelRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.CANCELED
  }

  def "취소 - 진행 후 취소"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    cancelRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCancelException)
  }

  def "취소 - 반려 후 취소"() {
    when:
    commitRequest()
    rejectRequest()
    cancelRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCancelException)
  }

  def "취소 - 완료 후 취소"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    cancelRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCancelException)
  }

  def "반려 - 작성 후 반려"() {
    when:
    rejectRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotRejectException)
  }

  def "반려 - 제출 후 반려"() {
    when:
    commitRequest()
    rejectRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.REJECTED

  }

  def "반려 - 접수 후 반려"() {
    when:
    commitRequest()
    acceptRequest()
    rejectRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotRejectException)
  }

  def "반려 - 진행 중 반려"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    rejectRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotRejectException)
  }

  def "반려 - 취소 후 반려"() {
    when:
    cancelRequest()
    rejectRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotRejectException)
  }

  def "반려 - 반려 후 반려"() {
    when:
    commitRequest()
    rejectRequest()
    rejectRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotRejectException)
  }

  def "반려 - 완료 후 반려"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    rejectRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotRejectException)
  }

  def "완료 - 작성 후 완료"() {
    when:
    completeRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCompleteException)
  }

  def "완료 - 제출 후 완료"() {
    when:
    commitRequest()
    completeRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCompleteException)

  }

  def "완료 - 접수 후 완료"() {
    when:
    commitRequest()
    acceptRequest()
    completeRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCompleteException)
  }

  def "완료 - 진행 중 완료"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    def request = requestService.get(id)
    then:
    request.status == PurchaseRequestStatusKind.COMPLETED

  }

  def "완료 - 취소 후 완료"() {
    when:
    cancelRequest()
    completeRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCompleteException)
  }

  def "완료 - 반려 후 완료"() {
    when:
    commitRequest()
    rejectRequest()
    completeRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCompleteException)
  }

  def "완료 - 완료 후 완료"() {
    when:
    commitRequest()
    acceptRequest()
    planRequest()
    progressRequest()
    completeRequest()
    completeRequest()
    then:
    thrown(PurchaseRequestExceptions.CannotCompleteException)
  }


}

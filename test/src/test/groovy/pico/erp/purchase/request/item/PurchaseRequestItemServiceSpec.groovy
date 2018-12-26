package pico.erp.purchase.request.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.item.ItemId
import pico.erp.purchase.request.PurchaseRequestId
import pico.erp.purchase.request.PurchaseRequestRequests
import pico.erp.purchase.request.PurchaseRequestService
import pico.erp.shared.IntegrationConfiguration
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class PurchaseRequestItemServiceSpec extends Specification {

  @Autowired
  PurchaseRequestService requestService

  @Autowired
  PurchaseRequestItemService requestItemService

  def requestId = PurchaseRequestId.from("purchase-request-1")

  def id = PurchaseRequestItemId.from("purchase-request-item-1")

  def unknownId = PurchaseRequestItemId.from("unknown")

  def itemId = ItemId.from("item-1")

  def requesterId = UserId.from("kjh")

  def accepterId = UserId.from("kjh")

  def setup() {

  }

  def cancelRequest() {
    requestService.cancel(
      new PurchaseRequestRequests.CancelRequest(
        id: requestId
      )
    )
  }

  def commitRequest() {
    requestService.commit(
      new PurchaseRequestRequests.CommitRequest(
        id: requestId,
        committerId: requesterId
      )
    )
  }

  def rejectRequest() {
    requestService.reject(
      new PurchaseRequestRequests.RejectRequest(
        id: requestId,
        rejectedReason: "필요 없는것 같음"
      )
    )
  }

  def progressRequest() {
    requestService.progress(
      new PurchaseRequestRequests.ProgressRequest(
        id: requestId
      )
    )
  }

  def completeRequest() {
    requestService.complete(
      new PurchaseRequestRequests.CompleteRequest(
        id: requestId
      )
    )
  }

  def acceptRequest() {
    requestService.accept(
      new PurchaseRequestRequests.AcceptRequest(
        id: requestId,
        accepterId: accepterId
      )
    )
  }

  def createItem() {
    requestItemService.create(
      new PurchaseRequestItemRequests.CreateRequest(
        id: id,
        requestId: requestId,
        itemId: itemId,
        quantity: 100,
        remark: "품목 비고"
      )
    )
  }

  def createItem2() {
    requestItemService.create(
      new PurchaseRequestItemRequests.CreateRequest(
        id: PurchaseRequestItemId.from("purchase-request-item-2"),
        requestId: requestId,
        itemId: itemId,
        quantity: 100,
        remark: "품목 비고"
      )
    )
  }

  def updateItem() {
    requestItemService.update(
      new PurchaseRequestItemRequests.UpdateRequest(
        id: id,
        quantity: 200,
        remark: "품목 비고2"
      )
    )
  }

  def deleteItem() {
    requestItemService.delete(
      new PurchaseRequestItemRequests.DeleteRequest(
        id: id
      )
    )
  }


  def "존재 - 아이디로 확인"() {
    when:
    createItem()
    def exists = requestItemService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = requestItemService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    createItem()
    def item = requestItemService.get(id)
    then:
    item.id == id
    item.itemId == itemId
    item.requestId == requestId
    item.quantity == 100
    item.remark == "품목 비고"
    item.status == PurchaseRequestItemStatusKind.DRAFT

  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    requestItemService.get(unknownId)

    then:
    thrown(PurchaseRequestItemExceptions.NotFoundException)
  }

  def "생성 - 작성 후 생성"() {
    when:
    createItem()
    def items = requestItemService.getAll(requestId)
    then:
    items.size() > 0
  }

  def "생성 - 제출 후 생성"() {
    when:
    createItem()
    commitRequest()
    createItem2()
    then:
    thrown(PurchaseRequestItemExceptions.CannotCreateException)

  }

  def "생성 - 접수 후 생성"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    createItem2()
    then:
    thrown(PurchaseRequestItemExceptions.CannotCreateException)
  }

  def "생성 - 진행 중 생성"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    progressRequest()
    createItem2()
    then:
    thrown(PurchaseRequestItemExceptions.CannotCreateException)

  }

  def "생성 - 취소 후 생성"() {
    when:
    createItem()
    cancelRequest()
    createItem2()
    then:
    thrown(PurchaseRequestItemExceptions.CannotCreateException)
  }

  def "생성 - 반려 후 생성"() {
    when:
    createItem()
    commitRequest()
    rejectRequest()
    createItem2()
    then:
    thrown(PurchaseRequestItemExceptions.CannotCreateException)
  }

  def "생성 - 완료 후 생성"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    progressRequest()
    completeRequest()
    createItem2()
    then:
    thrown(PurchaseRequestItemExceptions.CannotCreateException)
  }

  def "수정 - 작성 후 수정"() {
    when:
    createItem()
    updateItem()
    def items = requestItemService.getAll(requestId)
    then:
    items.size() > 0
  }

  def "수정 - 제출 후 수정"() {
    when:
    createItem()
    commitRequest()
    updateItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotUpdateException)

  }

  def "수정 - 접수 후 수정"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    updateItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotUpdateException)
  }

  def "수정 - 진행 중 수정"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    progressRequest()
    updateItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotUpdateException)

  }

  def "수정 - 취소 후 수정"() {
    when:
    createItem()
    cancelRequest()
    updateItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotUpdateException)
  }

  def "수정 - 반려 후 수정"() {
    when:
    createItem()
    commitRequest()
    rejectRequest()
    updateItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotUpdateException)
  }

  def "수정 - 완료 후 수정"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    progressRequest()
    completeRequest()
    updateItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotUpdateException)
  }


  def "삭제 - 작성 후 삭제"() {
    when:
    createItem()
    deleteItem()
    def items = requestItemService.getAll(requestId)
    then:
    items.size() == 0
  }

  def "삭제 - 제출 후 삭제"() {
    when:
    createItem()
    commitRequest()
    deleteItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotDeleteException)

  }

  def "삭제 - 접수 후 삭제"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    deleteItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotDeleteException)
  }

  def "삭제 - 진행 중 삭제"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    progressRequest()
    deleteItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotDeleteException)

  }

  def "삭제 - 취소 후 삭제"() {
    when:
    createItem()
    cancelRequest()
    deleteItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotDeleteException)
  }

  def "삭제 - 반려 후 삭제"() {
    when:
    createItem()
    commitRequest()
    rejectRequest()
    deleteItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotDeleteException)
  }

  def "삭제 - 완료 후 삭제"() {
    when:
    createItem()
    commitRequest()
    acceptRequest()
    progressRequest()
    completeRequest()
    deleteItem()
    then:
    thrown(PurchaseRequestItemExceptions.CannotDeleteException)
  }


}

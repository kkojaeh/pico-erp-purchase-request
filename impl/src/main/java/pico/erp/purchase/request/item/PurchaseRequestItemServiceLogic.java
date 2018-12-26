package pico.erp.purchase.request.item;

import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.purchase.request.PurchaseRequestId;
import pico.erp.purchase.request.PurchaseRequestProperties;
import pico.erp.purchase.request.PurchaseRequestService;
import pico.erp.purchase.request.item.PurchaseRequestItemRequests.DeleteRequest;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class PurchaseRequestItemServiceLogic implements PurchaseRequestItemService {

  @Autowired
  private PurchaseRequestItemRepository planDetailRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private PurchaseRequestItemMapper mapper;

  @Autowired
  private PurchaseRequestProperties properties;

  @Autowired
  private PurchaseRequestService purchaseRequestService;

  @Lazy
  @Autowired
  private AuditService auditService;


  @Override
  public PurchaseRequestItemData create(PurchaseRequestItemRequests.CreateRequest request) {
    val item = new PurchaseRequestItem();
    val response = item.apply(mapper.map(request));
    if (planDetailRepository.exists(item.getId())) {
      throw new PurchaseRequestItemExceptions.AlreadyExistsException();
    }
    val created = planDetailRepository.create(item);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.deleteBy(item.getId());
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }



  @Override
  public boolean exists(PurchaseRequestItemId id) {
    return planDetailRepository.exists(id);
  }


  @Override
  public PurchaseRequestItemData get(PurchaseRequestItemId id) {
    return planDetailRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
  }

  @Override
  public void accept(PurchaseRequestItemRequests.AcceptRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void update(PurchaseRequestItemRequests.UpdateRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void cancel(PurchaseRequestItemRequests.CancelRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void cancelProgress(PurchaseRequestItemRequests.CancelProgressRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void commit(PurchaseRequestItemRequests.CommitRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void complete(PurchaseRequestItemRequests.CompleteRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public List<PurchaseRequestItemData> getAll(PurchaseRequestId requestId) {
    return planDetailRepository.findAllBy(requestId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void progress(PurchaseRequestItemRequests.ProgressRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void reject(PurchaseRequestItemRequests.RejectRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void plan(PurchaseRequestItemRequests.PlanRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

}

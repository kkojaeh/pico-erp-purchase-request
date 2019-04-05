package pico.erp.purchase.request;

import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.purchase.request.PurchaseRequestRequests.AcceptRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.CancelProgressRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.CancelRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.CommitRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.CompleteRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.PlanRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.ProgressRequest;
import pico.erp.purchase.request.PurchaseRequestRequests.RejectRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional
@Validated
public class PurchaseRequestServiceLogic implements PurchaseRequestService {

  @Autowired
  private PurchaseRequestRepository purchaseRequestRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private PurchaseRequestMapper mapper;

  @Override
  public void accept(AcceptRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void cancel(CancelRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void cancelProgress(CancelProgressRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void commit(CommitRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void complete(CompleteRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public PurchaseRequestData create(PurchaseRequestRequests.CreateRequest request) {
    val purchaseRequest = new PurchaseRequest();
    val response = purchaseRequest.apply(mapper.map(request));
    if (purchaseRequestRepository.exists(purchaseRequest.getId())) {
      throw new PurchaseRequestExceptions.AlreadyExistsException();
    }
    val created = purchaseRequestRepository.create(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public boolean exists(PurchaseRequestId id) {
    return purchaseRequestRepository.exists(id);
  }

  @Override
  public PurchaseRequestData get(PurchaseRequestId id) {
    return purchaseRequestRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
  }

  @Override
  public void plan(PlanRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void progress(ProgressRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void reject(RejectRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void update(PurchaseRequestRequests.UpdateRequest request) {
    val purchaseRequest = purchaseRequestRepository.findBy(request.getId())
      .orElseThrow(PurchaseRequestExceptions.NotFoundException::new);
    val response = purchaseRequest.apply(mapper.map(request));
    purchaseRequestRepository.update(purchaseRequest);
    eventPublisher.publishEvents(response.getEvents());
  }
}

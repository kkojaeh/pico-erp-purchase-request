package pico.erp.purchase.request.item;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.purchase.request.PurchaseRequestId;

@Repository
interface PurchaseRequestItemEntityRepository extends
  CrudRepository<PurchaseRequestItemEntity, PurchaseRequestItemId> {

  @Query("SELECT i FROM PurchaseRequestItem i WHERE i.requestId = :requestId ORDER BY i.createdDate")
  Stream<PurchaseRequestItemEntity> findAllBy(@Param("requestId") PurchaseRequestId planId);

}

@Repository
@Transactional
public class PurchaseRequestItemRepositoryJpa implements PurchaseRequestItemRepository {

  @Autowired
  private PurchaseRequestItemEntityRepository repository;

  @Autowired
  private PurchaseRequestItemMapper mapper;

  @Override
  public PurchaseRequestItem create(PurchaseRequestItem planItem) {
    val entity = mapper.jpa(planItem);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(PurchaseRequestItemId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(PurchaseRequestItemId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<PurchaseRequestItem> findAllBy(PurchaseRequestId planId) {
    return repository.findAllBy(planId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<PurchaseRequestItem> findBy(PurchaseRequestItemId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(PurchaseRequestItem planItem) {
    val entity = repository.findOne(planItem.getId());
    mapper.pass(mapper.jpa(planItem), entity);
    repository.save(entity);
  }
}

package pico.erp.purchase.request;

import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface PurchaseRequestEntityRepository extends
  CrudRepository<PurchaseRequestEntity, PurchaseRequestId> {

  @Query("SELECT COUNT(pr) FROM PurchaseRequest pr WHERE pr.createdDate >= :begin AND pr.createdDate <= :end")
  long countCreatedBetween(@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end);

}

@Repository
@Transactional
public class PurchaseRequestRepositoryJpa implements PurchaseRequestRepository {

  @Autowired
  private PurchaseRequestEntityRepository repository;

  @Autowired
  private PurchaseRequestMapper mapper;

  @Override
  public long countCreatedBetween(OffsetDateTime begin, OffsetDateTime end) {
    return repository.countCreatedBetween(begin, end);
  }

  @Override
  public PurchaseRequest create(PurchaseRequest plan) {
    val entity = mapper.jpa(plan);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(PurchaseRequestId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(PurchaseRequestId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<PurchaseRequest> findBy(PurchaseRequestId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(PurchaseRequest plan) {
    val entity = repository.findOne(plan.getId());
    mapper.pass(mapper.jpa(plan), entity);
    repository.save(entity);
  }
}

package pico.erp.purchase.request;

import java.time.LocalDateTime;
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
  long countCreatedBetween(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

}

@Repository
@Transactional
public class PurchaseRequestRepositoryJpa implements PurchaseRequestRepository {

  @Autowired
  private PurchaseRequestEntityRepository repository;

  @Autowired
  private PurchaseRequestMapper mapper;

  @Override
  public long countCreatedBetween(LocalDateTime begin, LocalDateTime end) {
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
    repository.deleteById(id);
  }

  @Override
  public boolean exists(PurchaseRequestId id) {
    return repository.existsById(id);
  }

  @Override
  public Optional<PurchaseRequest> findBy(PurchaseRequestId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(PurchaseRequest plan) {
    val entity = repository.findById(plan.getId()).get();
    mapper.pass(mapper.jpa(plan), entity);
    repository.save(entity);
  }
}

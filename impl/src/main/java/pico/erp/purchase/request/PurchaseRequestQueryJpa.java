package pico.erp.purchase.request;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.purchase.request.PurchaseRequestView.Filter;
import pico.erp.purchase.request.item.QPurchaseRequestItemEntity;
import pico.erp.shared.jpa.QueryDslJpaSupport;

public class PurchaseRequestQueryJpa implements PurchaseRequestQuery {


  private final QPurchaseRequestEntity request = QPurchaseRequestEntity.purchaseRequestEntity;

  private final QPurchaseRequestItemEntity requestItem = QPurchaseRequestItemEntity.purchaseRequestItemEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public Page<PurchaseRequestView> retrieve(Filter filter, Pageable pageable) {
    val query = new JPAQuery<PurchaseRequestView>(entityManager);
    val select = Projections.bean(PurchaseRequestView.class,
      request.id,
      request.code,
      request.requester,
      request.accepter,
      request.projectId,
      request.receiveCompanyId,
      request.receiveSiteId,
      request.receiveStationId,
      request.dueDate,
      request.completedDate,
      request.acceptedDate,
      request.rejectedDate,
      request.canceledDate,
      request.status,
      request.createdBy,
      request.createdDate
    );
    query.select(select);
    query.from(request);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getCode())) {
      builder.and(request.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiveCompanyId() != null) {
      builder.and(request.receiveCompanyId.eq(filter.getReceiveCompanyId()));
    }

    if (filter.getRequesterId() != null) {
      builder.and(request.requester.id.eq(filter.getRequesterId().getValue()));
    }

    if (filter.getAccepterId() != null) {
      builder.and(request.accepter.id.eq(filter.getAccepterId().getValue()));
    }

    if (filter.getProjectId() != null) {
      builder.and(request.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(
        request.id.in(
          JPAExpressions.select(requestItem.requestId)
            .from(requestItem)
            .where(requestItem.itemId.eq(filter.getItemId()))
        )
      );
    }

    if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
      builder.and(request.status.in(filter.getStatuses()));
    }

    if (filter.getStartDueDate() != null) {
      builder.and(request.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(request.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}

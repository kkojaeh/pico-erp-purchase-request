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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.purchase.request.PurchaseRequestView.Filter;
import pico.erp.purchase.request.item.PurchaseRequestItemStatusKind;
import pico.erp.purchase.request.item.QPurchaseRequestItemEntity;
import pico.erp.shared.Public;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
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
      request.requestedBy,
      request.acceptedBy,
      request.projectId,
      request.receiverId,
      request.receiveSiteId,
      request.receiveStationId,
      request.dueDate,
      request.committedDate,
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

    if (filter.getReceiverId() != null) {
      builder.and(request.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getRequesterId() != null) {
      builder.and(request.requestedBy.id.eq(filter.getRequesterId().getValue()));
    }

    if (filter.getAccepterId() != null) {
      builder.and(request.acceptedBy.id.eq(filter.getAccepterId().getValue()));
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

  @Override
  public Page<PurchaseRequestAwaitOrderView> retrieve(PurchaseRequestAwaitOrderView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<PurchaseRequestAwaitOrderView>(entityManager);
    val select = Projections.bean(PurchaseRequestAwaitOrderView.class,
      requestItem.requestId,
      requestItem.id.as("requestItemId"),
      requestItem.itemId,
      requestItem.itemSpecId,
      requestItem.quantity,
      request.requestedBy,
      request.projectId,
      request.receiverId,
      request.receiveSiteId,
      request.receiveStationId,
      request.committedDate,
      request.acceptedDate,
      request.dueDate,
      request.createdDate
    );
    query.select(select);
    query.from(request);
    query.join(requestItem).on(request.id.eq(requestItem.requestId));

    val builder = new BooleanBuilder();

    builder.and(requestItem.status.eq(PurchaseRequestItemStatusKind.ACCEPTED));

    if (filter.getReceiverId() != null) {
      builder.and(request.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getRequesterId() != null) {
      builder.and(request.requestedBy.id.eq(filter.getRequesterId().getValue()));
    }

    if (filter.getProjectId() != null) {
      builder.and(request.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(
        requestItem.itemId.eq(filter.getItemId())
      );
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

  @Override
  public Page<PurchaseRequestAwaitAcceptView> retrieve(PurchaseRequestAwaitAcceptView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<PurchaseRequestAwaitAcceptView>(entityManager);
    val select = Projections.bean(PurchaseRequestAwaitAcceptView.class,
      request.id,
      request.code,
      request.requestedBy,
      request.projectId,
      request.receiverId,
      request.receiveSiteId,
      request.receiveStationId,
      request.committedDate,
      request.dueDate
    );
    query.select(select);
    query.from(request);

    val builder = new BooleanBuilder();

    builder.and(requestItem.status.eq(PurchaseRequestItemStatusKind.COMMITTED));

    if (!isEmpty(filter.getCode())) {
      builder.and(request.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiverId() != null) {
      builder.and(request.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getRequesterId() != null) {
      builder.and(request.requestedBy.id.eq(filter.getRequesterId().getValue()));
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

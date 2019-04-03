package pico.erp.purchase.request;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.project.ProjectId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;
import pico.erp.shared.data.UnitKind;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

@Entity(name = "PurchaseRequest")
@Table(name = "PCR_PURCHASE_REQUEST", indexes = {
  @Index(columnList = "createdDate")
})
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequestEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  PurchaseRequestId id;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "CODE", length = TypeDefinitions.CODE_LENGTH))
  })
  PurchaseRequestCode code;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ITEM_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ItemId itemId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ITEM_SPEC_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ItemSpecId itemSpecId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ITEM_SPEC_CODE", length = TypeDefinitions.CODE_LENGTH))
  })
  ItemSpecCode itemSpecCode;

  @Column(precision = 19, scale = 2)
  BigDecimal quantity;

  @Column(precision = 19, scale = 2)
  BigDecimal progressedQuantity;

  @Column(length = TypeDefinitions.ENUM_LENGTH)
  @Enumerated(EnumType.STRING)
  UnitKind unit;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "PROJECT_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProjectId projectId;

  LocalDateTime dueDate;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "SUPPLIER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CompanyId supplierId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "RECEIVER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CompanyId receiverId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "RECEIVE_SITE_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  SiteId receiveSiteId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "RECEIVE_STATION_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  StationId receiveStationId;

  @Column(length = TypeDefinitions.REMARK_LENGTH)
  String remark;

  @Column(length = TypeDefinitions.REMARK_LENGTH)
  String rejectedReason;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "REQUESTER_ID", length = TypeDefinitions.ID_LENGTH)),
  })
  UserId requesterId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ACCEPTER_ID", length = TypeDefinitions.ID_LENGTH)),
  })
  UserId accepterId;

  LocalDateTime committedDate;

  LocalDateTime acceptedDate;

  LocalDateTime completedDate;

  LocalDateTime rejectedDate;

  LocalDateTime canceledDate;

  @Column(length = TypeDefinitions.ENUM_LENGTH)
  @Enumerated(EnumType.STRING)
  PurchaseRequestStatusKind status;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @CreatedDate
  @Column(updatable = false)
  LocalDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  @LastModifiedDate
  LocalDateTime lastModifiedDate;

}

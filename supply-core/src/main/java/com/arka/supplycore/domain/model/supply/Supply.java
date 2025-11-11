package com.arka.supplycore.domain.model.supply;

import com.arka.supplycore.application.exception.BusinessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Supply {
  private static final String CANT_ALTER_SUPPLY = "The supply is in a state that can't be altered";
  private final String supplyId;
  private final Set<SupplyDetail> details = new HashSet<>();
  private BigDecimal total;
  private SupplyStatus status;

  public Supply(String supplyId) {
    this.supplyId = supplyId.toLowerCase();
    total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    status = SupplyStatus.CREATED;
  }

  public Supply(String supplyId, SupplyStatus status, BigDecimal total, Collection<SupplyDetailView> details) {
    this.supplyId = supplyId.toLowerCase();
    this.status = status;
    this.total = total;

    List<SupplyDetail> detailsList = details.stream()
      .map(detail -> new SupplyDetail(detail.getProductId(), detail.getQuantity(), detail.getPrice()))
      .toList();

    this.details.addAll(detailsList);
  }

  private SupplyDetail findDetail(String productId) {
    return details.stream()
      .filter(detail -> detail.getProductId().equals(productId))
      .findFirst()
      .orElse(null);
  }

  public void addDetail(SupplyDetailView supplyDetail) throws IllegalStateException, BusinessException {
    if (!canAlterSupply()) {
      throw new IllegalStateException(CANT_ALTER_SUPPLY);
    }

    if (findDetail(supplyDetail.getProductId()) != null) {
      throw new BusinessException("The product is already registered in the supply order");
    }

    details.add(new SupplyDetail(supplyDetail.getProductId(), supplyDetail.getQuantity(), supplyDetail.getPrice()));

    summarizeDetails();
  }

  public void updateDetail(SupplyDetailView supplyDetail) throws IllegalStateException {
    if (!canAlterSupply()) {
      throw new IllegalStateException(CANT_ALTER_SUPPLY);
    }

    SupplyDetail detail = findDetail(supplyDetail.getProductId());

    if (detail == null) {
      throw new IllegalStateException("The product is not registered in the supply order");
    }

    details.remove(detail);
    details.add(new SupplyDetail(supplyDetail.getProductId(), detail.getQuantity(), detail.getPrice()));
    summarizeDetails();
  }

  public void removeDetail(SupplyDetailView supplyDetail) throws BusinessException, IllegalStateException {
    if (!canAlterSupply()) {
      throw new BusinessException(CANT_ALTER_SUPPLY);
    }

    SupplyDetail detail = findDetail(supplyDetail.getProductId());

    if (detail == null) {
      throw new IllegalStateException("The product is not registered in the supply order");
    }

    details.remove(detail);
    summarizeDetails();
  }

  public void cancelSupply() {
    switch (status) {
      case CANCELLED:
        throw new IllegalStateException("The supply has been cancelled");
      case RECEIVED:
        throw new IllegalStateException("The supply has been processed");
      case CREATED:
        status = SupplyStatus.CANCELLED;
        details.clear();
        summarizeDetails();
        break;
      default:
        throw new IllegalStateException("The supply is in an unknown status");
    }
  }

  public void processSupply() throws BusinessException {
    switch (status) {
      case CANCELLED:
        throw new IllegalStateException("The supply has been cancelled");
      case RECEIVED:
        throw new IllegalStateException("The supply has been processed");
      case CREATED:
        if (total.compareTo(BigDecimal.ZERO) == 0 || details.isEmpty()) {
          throw new BusinessException("The supply order is empty");
        }
        status = SupplyStatus.RECEIVED;
        break;
      default:
        throw new IllegalStateException("The supply is in an unknown status");
    }
  }

  /**
   * Summarize all subtotals from details
   */
  protected void summarizeDetails() {
    total = details.stream()
      .map(SupplyDetail::subtotal)
      .reduce(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
  }

  public String getSupplyId() {
    return supplyId;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public List<SupplyDetailView> getDetails() {
    return List.copyOf(details);
  }

  public SupplyStatus getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }

    if (o instanceof Supply s) {
      return supplyId.equals(s.supplyId);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return supplyId.hashCode();
  }

  /**
   * shows if this Supply it's in a state that accept any modification or deletion
   * @return true if and only if status is {@link SupplyStatus#CREATED}
   */
  public boolean canAlterSupply() {
    return status == SupplyStatus.CREATED;
  }
}

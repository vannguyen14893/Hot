package com.shopping.vn.repository.customer.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.Product;
import com.shopping.vn.repository.customer.ProductRepositoryCustomer;

public class ProductRepositoryCustomerImpl implements ProductRepositoryCustomer {
  @PersistenceContext
  EntityManager entityManager;
  public static final String LIST = "select p from Product p join p.category ct ";
  public static final String SORT = " ORDER BY ";

  @SuppressWarnings("unchecked")
  @Override
  public List<Product> readAll(SortFilterDto filter) {
    StringBuilder builder = new StringBuilder(LIST);

    builder.append(" WHERE 1=1 ");

    if (filter.getStatus() != null) {
      builder.append(" AND CAST(p.status as char(5)) = '" + filter.getStatus() + "'");
    }
    if (CollectionUtils.isEmpty(filter.getPrice())) {
      builder.append(" AND p.price in '(:" + filter.getPrice() + ")'");
    }
//    if (CollectionUtils.isEmpty(filter.getPriceSale())) {
//      builder.append(" AND p.priceSale in ('" + filter.getPriceSale() + "')");
//    }
//    if (CollectionUtils.isEmpty(filter.getCategoryIds())) {
//      builder.append(" AND ct.id in ('" + filter.getCategoryIds() + "')");
//    }
    // if(filter.getColorIds().isEmpty()) {
    // builder.append(" AND cl.id in ('" + filter.getColorIds() + "')");
    // }
    // if(filter.getSizeIds().isEmpty()) {
    // builder.append(" AND s.id in ('" + filter.getSizeIds() + "')");
    // }
    // if (StringUtils.isNotBlank(filter.getTitle())) {
    // builder.append(" AND LOWER(p.title) like '%" + filter.getTitle().toLowerCase()
    // + "%' or LOWER(p.description) like '%" + filter.getTitle().toLowerCase() + "%' ");
    // }
    if (!StringUtils.isNotBlank(filter.getSortName())) {
      builder.append(SORT + "p.id");
    } else {
      builder.append(SORT + "p." + filter.getSortName() + "");
      builder.append(filter.getSort() ? " ASC" : " DESC");
    }
    Query query = entityManager.createQuery(builder.toString(), Product.class);
    query.setFirstResult((filter.getPage() - 1) * filter.getPageSize());
    query.setMaxResults(filter.getPageSize());
    return query.getResultList();

  }
}



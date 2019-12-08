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
import com.shopping.vn.utils.Utils;

public class ProductRepositoryCustomerImpl implements ProductRepositoryCustomer {
  @PersistenceContext
  EntityManager entityManager;
  public static final String LIST = "select p from Product p join p.category ct ";
  public static final String COUNT = "select count(*) from Product p join p.category ct ";
  public static final String SORT = " ORDER BY ";

  @SuppressWarnings("unchecked")
  @Override
  public List<Product> readAll(SortFilterDto filter) {
    StringBuilder builder = new StringBuilder(LIST);
    if (!CollectionUtils.isEmpty(filter.getColorIds())) {
      builder.append(" join s.sizeColors pc join pc.color c ");
    }
    if (!CollectionUtils.isEmpty(filter.getSizeIds())) {
      builder.append(" join p.productSizes ps join ps.size s ");
    }
    builder.append(" WHERE 1=1 ");

    if (filter.getStatus() != null) {
      builder.append(" AND CAST(p.status as char(5)) = '" + filter.getStatus() + "'");
    }
    if (!CollectionUtils.isEmpty(filter.getPrice())) {
      builder.append(" AND p.price in " + Utils.generateCollection(filter.getPrice()));
    }
    if (!CollectionUtils.isEmpty(filter.getPriceSales())) {
      builder.append(" AND p.priceSale in " + Utils.generateCollection(filter.getPriceSales()));
    }

    if (!CollectionUtils.isEmpty(filter.getCategoryIds())) {
      builder.append(" AND ct.id in " + Utils.generateCollection(filter.getCategoryIds()));

    }
    if (!CollectionUtils.isEmpty(filter.getColorIds())) {
      builder.append(" AND c.id in " + Utils.generateCollection(filter.getColorIds()));
    }
    if (!CollectionUtils.isEmpty(filter.getSizeIds())) {
      builder.append(" AND s.id in " + Utils.generateCollection(filter.getSizeIds()));
    }
    if (StringUtils.isNotBlank(filter.getTitle())) {
      builder.append(" AND LOWER(p.title) like '%" + filter.getTitle().toLowerCase()
          + "%' or LOWER(p.description) like '%" + filter.getTitle().toLowerCase() + "%' ");
    }
    builder.append(" group by p.id ");
    if (!StringUtils.isNotBlank(filter.getSortName())) {
      builder.append(SORT + "p.id");
    } else {
      builder.append(SORT + "p." + filter.getSortName() + "");
      boolean check = filter.getSort();
      builder.append(check ? " ASC" : " DESC");
    }

    Query query = entityManager.createQuery(builder.toString(), Product.class);
    query.setFirstResult((filter.getPage()));
    query.setMaxResults(filter.getPageSize());
    return query.getResultList();


  }

  @Override
  public Long countProduct(SortFilterDto filter) {

    StringBuilder builder = new StringBuilder(COUNT);
    if (!CollectionUtils.isEmpty(filter.getColorIds())) {
      builder.append(" join s.sizeColors pc join pc.color c ");
    }
    if (!CollectionUtils.isEmpty(filter.getSizeIds())) {
      builder.append(" join p.productSizes ps join ps.size s ");
    }
    builder.append(" WHERE 1=1 ");

    if (filter.getStatus() != null) {
      builder.append(" AND CAST(p.status as char(5)) = '" + filter.getStatus() + "'");
    }
    if (!CollectionUtils.isEmpty(filter.getPrice())) {
      builder.append(" AND p.price in " + Utils.generateCollection(filter.getPrice()));
    }
    if (!CollectionUtils.isEmpty(filter.getPriceSales())) {
      builder.append(" AND p.priceSale in " + Utils.generateCollection(filter.getPriceSales()));
    }

    if (!CollectionUtils.isEmpty(filter.getCategoryIds())) {
      builder.append(" AND ct.id in " + Utils.generateCollection(filter.getCategoryIds()));

    }
    if (!CollectionUtils.isEmpty(filter.getColorIds())) {
      builder.append(" AND c.id in " + Utils.generateCollection(filter.getColorIds()));
    }
    if (!CollectionUtils.isEmpty(filter.getSizeIds())) {
      builder.append(" AND s.id in " + Utils.generateCollection(filter.getSizeIds()));
    }
    if (StringUtils.isNotBlank(filter.getTitle())) {
      builder.append(" AND LOWER(p.title) like '%" + filter.getTitle().toLowerCase()
          + "%' or LOWER(p.description) like '%" + filter.getTitle().toLowerCase() + "%' ");
    }
    Query query = entityManager.createQuery(builder.toString(), Long.class);
    return (Long) query.getSingleResult();

  }
}

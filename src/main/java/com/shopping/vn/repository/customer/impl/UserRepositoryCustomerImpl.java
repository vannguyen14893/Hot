package com.shopping.vn.repository.customer.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.customer.UserRepositoryCustomer;
import com.shopping.vn.utils.Utils;

public class UserRepositoryCustomerImpl implements UserRepositoryCustomer {
	@PersistenceContext
	EntityManager entityManager;
	public static final String LIST = "select u from User u join u.roles r ";
	public static final String COUNT = "select count(u.id) from User u join u.roles r ";
	public static final String SORT = " ORDER BY ";

	@SuppressWarnings("unchecked")
	@Override
	public List<User> readAll(SortFilterDto filter) {
		StringBuilder builder = new StringBuilder(LIST);

		builder.append(" WHERE 1=1 ");

		if (!CollectionUtils.isEmpty(filter.getRoleIds())) {
			builder.append(" AND r.id in " + Utils.generateCollection(filter.getRoleIds()));
		}

		if (filter.getStatus() != null) {
			builder.append(" AND u.status = '" + filter.getStatus() + "'");
		}
		if (StringUtils.isNotBlank(filter.getStartDate()) && StringUtils.isNotBlank(filter.getEndDate())) {
			builder.append(
					" AND date(u.birthDay) between '" + filter.getStartDate() + " 'and'" + filter.getEndDate() + "'");
		}
		if (StringUtils.isNotBlank(filter.getFullName())) {
			builder.append(" AND LOWER(u.email) like '%" + filter.getFullName().toLowerCase()
					+ "%' or LOWER(u.fullName)  like '%" + filter.getFullName().toLowerCase() + "%' ");
		}
		if (!StringUtils.isNotBlank(filter.getSortName())) {
			builder.append(SORT + "u.userId");
		} else {
			builder.append(SORT + "u." + filter.getSortName() + "");
			boolean check=filter.getSort();
			builder.append(check ? " ASC" : " DESC");
		}
		Query query = entityManager.createQuery(builder.toString(), User.class);
		query.setFirstResult((filter.getPage() - 1) * filter.getPageSize());
		query.setMaxResults(filter.getPageSize());
		return query.getResultList();

	}

	@Override
	public Long countUser(SortFilterDto filter) {
		StringBuilder builder = new StringBuilder(COUNT);

		builder.append(" WHERE 1=1 ");

		if (!CollectionUtils.isEmpty(filter.getRoleIds())) {
			builder.append(" AND r.id in " + Utils.generateCollection(filter.getRoleIds()));
		}

		if (filter.getStatus() != null) {
			builder.append(" AND u.status = '" + filter.getStatus() + "'");
		}
		if (StringUtils.isNotBlank(filter.getStartDate()) && StringUtils.isNotBlank(filter.getEndDate())) {
			builder.append(
					" AND date(u.birthDay) between '" + filter.getStartDate() + " 'and'" + filter.getEndDate() + "'");
		}
		if (StringUtils.isNotBlank(filter.getFullName())) {
			builder.append(" AND LOWER(u.email) like '%" + filter.getFullName().toLowerCase()
					+ "%' or LOWER(u.fullName)  like '%" + filter.getFullName().toLowerCase() + "%' ");
		}
		Query query = entityManager.createQuery(builder.toString(), Long.class);
		return (Long) query.getSingleResult();
	}

}

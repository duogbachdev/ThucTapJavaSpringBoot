package com.example.learnspringboot.repository;

import com.example.learnspringboot.dto.response.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public PageResponse<?> getAllUsersWithSortByComlumnAndSearch(int pageNo, int pageSize, String search, String sortBy){
        StringBuilder sql = new StringBuilder("select new com.example.learnspringboot.dto.response.UserDetailResponse(u.id, u.firstName, u.lastName) from User u where 1=1");
        if (StringUtils.hasLength(search)) {
            sql.append(" and lower(u.firstName) like lower(:firstName)");
            sql.append(" or lower(u.lastName) like lower(:lastName)");
            sql.append(" or lower(u.email) like lower(:email)");
        }

        Query setQuery = entityManager.createQuery(sql.toString());
        setQuery.setFirstResult(pageNo);
        setQuery.setMaxResults(pageSize);
        if (StringUtils.hasLength(search)) {
            setQuery.setParameter("firstName", String.format("%%%s%%", search));
            setQuery.setParameter("lastName", "%" + search + "%");
            setQuery.setParameter("email", "%" + search + "%");
        }

        List users = setQuery.getResultList();
        System.out.println(users);
        // Query ra list user

        // Query ra so record
        StringBuilder sqlCountQuery = new StringBuilder("select count(*) from User u where 1=1");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" and lower(u.firstName) like lower(?1)");
            sqlCountQuery.append(" or lower(u.lastName) like lower(?2)");
            sqlCountQuery.append(" or lower(u.email) like lower(?3)");
        }

        Query setCountQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            setCountQuery.setParameter(1, String.format("%%%s%%", search));
            setCountQuery.setParameter(2, String.format("%%%s%%", search));
            setCountQuery.setParameter(3, String.format("%%%s%%", search));
        }
        Long totalElements = (Long) setCountQuery.getSingleResult();
        System.out.println(totalElements);

        Page<?> page = new PageImpl<Object>(users, PageRequest.of(pageNo, pageSize), totalElements);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(page.getTotalPages())
                .items(page.stream().toList())
                .build();
    }
}

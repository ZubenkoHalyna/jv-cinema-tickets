package com.dev.cinema.dao.impl;

import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import java.util.function.BiFunction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseDaoImpl<T> {
    protected void fetchTables(Root<T> root) {
    }

    protected T add(T item) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            return item;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save entity " + item, e);
        } finally {
            session.close();
        }
    }

    protected List<T> getAll(Class<T> clazz) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(clazz);
            fetchTables(criteriaQuery.from(clazz));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all items for class "
                    + clazz.getSimpleName(), e);
        }
    }

    protected List<T> getWithParams(Class<T> clazz,
            BiFunction<Root<T>, CriteriaBuilder, Predicate> getPredicate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            fetchTables(root);
            criteriaQuery.select(root)
                         .where(getPredicate.apply(root, builder));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get items by params for class "
                    + clazz.getSimpleName(), e);
        }
    }
}

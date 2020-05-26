package com.dev.cinema.dao.impl;

import com.dev.cinema.exceptions.HibernateQueryException;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class BaseDaoImpl<T> {
    /**
     * Provides immediate selecting of data from specified tables by one query to DB
     * Use root.fetch to specify the tables.
     *
     * @param root - root element of CriteriaQuery
     */
    protected void fetchTables(Root<T> root) {
    }

    protected T addItem(T item) {
        return sessionFunc(item, Session::save);
    }

    protected T updateItem(T item) {
        return sessionFunc(item, Session::merge);
    }

    protected List<T> getAll(Class<T> clazz) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(clazz);
            fetchTables(criteriaQuery.from(clazz));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new HibernateQueryException("Can't get all items for class "
                    + clazz.getSimpleName(), e);
        }
    }

    protected List<T> getListWithParams(Class<T> clazz,
                              BiFunction<Root<T>, CriteriaBuilder, Predicate> getPredicate) {
        return getWithParams(clazz, getPredicate, Query::getResultList,
                "Can't get items by params for class " + clazz.getSimpleName());
    }

    protected Optional<T> getWithParams(Class<T> clazz,
                              BiFunction<Root<T>, CriteriaBuilder, Predicate> getPredicate) {
        return getWithParams(clazz, getPredicate, Query::uniqueResultOptional,
                "Can't get " + clazz.getSimpleName() + " by params");
    }

    private <R> R getWithParams(Class<T> clazz,
                                BiFunction<Root<T>, CriteriaBuilder, Predicate> getPredicate,
                                Function<Query<T>, R> getResult,
                                String errorMsg) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            fetchTables(root);
            criteriaQuery.select(root)
                    .where(getPredicate.apply(root, builder));
            return getResult.apply(session.createQuery(criteriaQuery));
        } catch (Exception e) {
            throw new HibernateQueryException(errorMsg, e);
        }
    }

    private T sessionFunc(T item, BiFunction<Session, T, ?> sessionFunc) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            sessionFunc.apply(session, item);
            transaction.commit();
            return item;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateQueryException("Can't save entity " + item, e);
        } finally {
            session.close();
        }
    }
}

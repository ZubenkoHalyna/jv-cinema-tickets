package com.dev.cinema.dao.impl;

import com.dev.cinema.exceptions.HibernateQueryException;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;

public abstract class BaseDaoImpl<T> {
    /**
     * Provides immediate selecting of data for specified fields by one query to DB
     * Use root.fetch to specify the fields.
     *
     * @param root - root element of CriteriaQuery
     */
    protected void fetchFields(Root<T> root) {
    }

    protected T addItem(T item) {
        executeTransactional(session -> session.save(item), "Can't save entity " + item);
        return item;
    }

    protected void updateItem(T item) {
        executeTransactional(session -> session.update(item), "Can't update entity " + item);
    }

    protected List<T> getAll(Class<T> clazz) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(clazz).distinct(true);
            fetchFields(criteriaQuery.from(clazz));
            return session.createQuery(criteriaQuery)
                    .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                    .getResultList();
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
            CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz).distinct(true);
            Root<T> root = criteriaQuery.from(clazz);
            fetchFields(root);
            criteriaQuery.select(root)
                    .where(getPredicate.apply(root, builder));
            return getResult.apply(session.createQuery(criteriaQuery)
                    .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false));
        } catch (Exception e) {
            throw new HibernateQueryException(errorMsg, e);
        }
    }

    protected void deleteWithParams(Class<T> clazz,
                                    BiFunction<Root<T>, CriteriaBuilder, Predicate> getPredicate) {
        executeTransactional(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<T> query = builder.createCriteriaDelete(clazz);
            query.where(getPredicate.apply(query.from(clazz), builder));
            session.createQuery(query).executeUpdate();
        }, "Can't delete items by params for class " + clazz.getSimpleName());
    }

    protected void updateWithParams(Class<T> clazz,
                                    BiFunction<Root<T>, CriteriaBuilder, Predicate> getPredicate,
                                    Map<String, Object> setMap) {
        executeTransactional(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<T> query = builder.createCriteriaUpdate(clazz);
            query.where(getPredicate.apply(query.from(clazz), builder));
            setMap.forEach(query::set);
            session.createQuery(query).executeUpdate();
        }, "Can't update items by params for class " + clazz.getSimpleName());
    }

    private void executeTransactional(Consumer<Session> executeWithTransaction,
                                      String errorMsg) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            executeWithTransaction.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateQueryException(errorMsg, e);
        } finally {
            session.close();
        }
    }
}

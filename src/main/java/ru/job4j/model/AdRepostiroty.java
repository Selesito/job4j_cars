package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepostiroty implements AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Ads> findAllBrand(String brand) {
        return tx(session -> session.createQuery(
                "select distinct ad from Ads ad "
                        + "join fetch ad.car c "
                        + "join fetch ad.author a "
                        + "join fetch ad.bodyType b "
                        + "where c.name = :brand"
        ).setParameter("brand", brand).list());
    }

    public List<Ads> findAllPhoto() {
        return tx(session -> session.createQuery(
                "select distinct ad from Ads ad "
                        + "join fetch ad.car c "
                        + "join fetch ad.author a "
                        + "join fetch ad.bodyType b "
                        + "where ad.photo is not null ").list());
    }

    public List<Ads> findAllData() {
        Date dat = new Date(System.currentTimeMillis());
        return tx(session -> session.createQuery(
                "select distinct ad from Ads ad "
                        + "join fetch ad.car c "
                        + "join fetch ad.author a "
                        + "join fetch ad.bodyType b "
                        + "where ad.created <= :date"
        ).setParameter("date", dat).list());
    }
}

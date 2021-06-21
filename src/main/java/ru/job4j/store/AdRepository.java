package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepository implements AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static AdRepository instOf() {
        return Lazy.INST;
    }

    private static final class Lazy {
        private final static AdRepository INST = new AdRepository();
    }

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

    public List<Ads> findAllBrand(int brand) {
        return tx(session -> session.createQuery(
                "select distinct a from Ads a "
                        + "left join fetch a.car c "
                        + "left join fetch a.photos p "
                        + "left join fetch a.author at "
                        + "left join fetch c.engine "
                        + "left join fetch c.color "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.transmission "
                        + "left join fetch c.model m "
                        + "left join fetch m.brand br "
                        + "where br.id = :brand"
        ).setParameter("brand", brand).list());
    }

    public List<Ads> findAllPhoto() {
        return tx(session -> session.createQuery(
                "select distinct a from Ads a "
                        + "left join fetch a.car c "
                        + "left join fetch a.photos p "
                        + "left join fetch a.author at "
                        + "left join fetch c.engine "
                        + "left join fetch c.color "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.transmission "
                        + "left join fetch c.model m "
                        + "left join fetch m.brand br "
                        + "where p.path is not null ").list());
    }

    public List<Ads> findAllData() {
        Date dat = new Date(System.currentTimeMillis());
        return tx(session -> session.createQuery(
                "select distinct a from Ads a "
                        + "left join fetch a.car c "
                        + "left join fetch a.photos p "
                        + "left join fetch a.author at "
                        + "left join fetch c.engine "
                        + "left join fetch c.color "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.transmission "
                        + "left join fetch c.model m "
                        + "left join fetch m.brand br "
                        + "where a.created <= :date"
        ).setParameter("date", dat).list());
    }

    public Author findByEmail(String email) {
        Author author = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Author where email = :email");
            query.setParameter("email", email);
            if (!query.getResultList().isEmpty())  {
                author = (Author) query.getResultList().get(0);
            }
            session.getTransaction().commit();
        }
        return author;
    }

    public Author addUser(Author author) {
        return this.tx(session -> {
            session.save(author);
            return author;
        });
    }

    public Ads addAds(Ads ads) {
        return this.tx(session -> {
            session.save(ads);
            return ads;
        });
    }

    public Ads updateAds(Ads ads) {
        return this.tx(session -> {
            session.update(ads);
            return ads;
        });
    }

    public Ads deleteAds(Ads ads) {
        return this.tx(session -> {
            session.delete(ads);
            return ads;
        });
    }

    public Car deleteCar(Car car) {
        return this.tx(session -> {
            session.delete(car);
            return car;
        });
    }

    public Car addCar(Car car) {
        return this.tx(session -> {
            session.save(car);
            return car;
        });
    }

    public List<Brand> findBrand() {
        return this.tx(session -> session.createQuery(
                "from Brand b order by b.name", Brand.class).list()
        );
    }

    public List<Engine> findEngine() {
        return this.tx(session -> session.createQuery(
                "from Engine e order by e.name", Engine.class).list()
        );
    }

    public List<Transmission> findTransmission() {
        return this.tx(session -> session.createQuery(
                "from Transmission t order by t.name", Transmission.class).list()
        );
    }

    public List<Color> findColor() {
        return this.tx(session -> session.createQuery(
                "from Color c order by c.name", Color.class).list()
        );
    }

    public List<BodyType> findBodyType() {
        return this.tx(session -> session.createQuery(
                "from BodyType b order by b.name", BodyType.class).list()
        );
    }

    public List<Model> findByModels(int id) {
        return this.tx(session -> session.createQuery(
                "select distinct m from Model m "
                        + "join fetch m.brand b "
                        + "where b.id = :brandId"
        ).setParameter("brandId", id).list());
    }

    public Car findByCarVin(String vin) {
        Car car = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Car where vinNumber = :paramvin");
            query.setParameter("paramvin", vin);
            if (!query.getResultList().isEmpty())  {
                car = (Car) query.getResultList().get(0);
            }
            session.getTransaction().commit();
        }
        return car;
    }

    public List<Ads> findAds() {
        return this.tx(session -> session.createQuery(
                "select distinct a from Ads a "
                        + "left join fetch a.car c "
                        + "left join fetch a.photos p "
                        + "left join fetch a.author at "
                        + "left join fetch c.engine "
                        + "left join fetch c.color "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.transmission "
                        + "left join fetch c.model m "
                        + "left join fetch m.brand ", Ads.class)
                .list()
        );
    }

    public List<Ads> findAdsUser(int id) {
        return this.tx(session -> session.createQuery(
                "select distinct a from Ads a "
                        + "left join fetch a.author at "
                        + "left join fetch a.car c "
                        + "left join fetch a.photos p "
                        + "left join fetch c.engine "
                        + "left join fetch c.color "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.transmission "
                        + "left join fetch c.model m "
                        + "left join fetch m.brand "
                        + "where at.id = :paramId", Ads.class)
                .setParameter("paramId", id)
                .list()
        );
    }

    public Ads findByAds(int id) {
        Ads ads = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Ads where id = :paramId");
            query.setParameter("paramId", id);
            if (!query.getResultList().isEmpty()) {
                ads = (Ads) query.getResultList().get(0);
            }
            session.getTransaction().commit();
        }
        return ads;
    }
}

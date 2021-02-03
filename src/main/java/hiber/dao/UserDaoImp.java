package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User get(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User get(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
                "from User user join fetch user.car car " +
                        "where car.model = :model and car.series = :series"
        );
        query.setParameter("model", model);
        query.setParameter("series", series);

        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? null : query.getResultList().get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

}

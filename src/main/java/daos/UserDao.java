package daos;


import entities.User;
import org.apache.log4j.Logger;
import resources.ConnectionUtil;
import resources.EntityManagerUtils;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CRUD<User> {
    private static final Logger LOG = Logger.getLogger(UserDao.class);

    private Connection connection;

    public UserDao() {
        this.connection = ConnectionUtil.getConnection();
    }

    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String DELETE = "DELETE FROM users where id = ?";
    public static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ?, role = ?, password = ? where id = ?";
    public static final String SELECT_BY_ID = "SELECT * FROM users where id = ?";
    public static final String SELECT_BY_EMAIL = "SELECT u FROM User u where u.email = :email";

    @Override
    public User create(User user) {
        LOG.info("Creating new user....");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        LOG.info("New user was created");

        return user;
    }

    private void setParametersForUser(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getRole());
        preparedStatement.setString(5, user.getPassword());

    }

    @Override
    public Optional<User> read(int id) {
        LOG.trace("Reading user by id...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return Optional.of(User.of(resultSet));
            }
        } catch (SQLException e) {
            String errorReadById = String.format("Can`t read user with id = %s", id);
            LOG.error(errorReadById, e);
        }
        return Optional.empty();
    }

    public Optional<User> readByEmail(String email) {
        LOG.info("Reading user by email...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        User user = (User) entityManager.createQuery(SELECT_BY_EMAIL)
                .setParameter("email", email)
                .getSingleResult();

        entityManager.getTransaction().commit();
        return Optional.ofNullable(user);
    }

    @Override
    public void update(User user) {
        LOG.trace("Updating user...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            setParametersForUser(preparedStatement, user);
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();

            String infoUpdate = String.format("User with id = %d was updated to user with email = %d", user.getId(), user.getEmail());
            LOG.info(infoUpdate);

        } catch (SQLException e) {
            LOG.error("Can`t update user", e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.trace("Deleting user...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Can`t delete user by id", e);
        }
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        LOG.trace("Reading all users from DB...");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                users.add(User.of(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Can`t read all users", e);
        }
        return users;
    }
}

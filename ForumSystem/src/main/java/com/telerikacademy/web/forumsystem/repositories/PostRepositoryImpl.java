package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository{
    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long getPostCount() {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT COUNT(*) FROM Post";

            Query<Long> query = session.createQuery(hql, Long.class);

            return query.getSingleResult();
        }
    }

    @Override
    public List<Post> getAll(FilterPostOptions filterPostOptions) {
        try (
                Session session = sessionFactory.openSession()) {

            StringBuilder queryString = new StringBuilder("from Post ");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterPostOptions.getTitle().ifPresent(value -> {
                filters.add(" title like :title ");
                params.put("title", String.format("%%%s%%", value));
            });

            filterPostOptions.getContent().ifPresent(value -> {
                filters.add(" content like :content ");
                params.put("content", String.format("%%%s%%", value));
            });

            filterPostOptions.getCreatedBy().ifPresent(value -> {
                filters.add(" createdBy.username like :createdBy ");
                params.put("createdBy", String.format("%%%s%%", value));
            });


            if (!filters.isEmpty()) {
                queryString.append(" where ")
                        .append(String.join(" and ", filters));
            } else {
                queryString.append(" order by timestamp desc");
            }


            queryString.append(createOrderBy(filterPostOptions));
            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public List<Post> getMostCommentedPosts() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT p, COUNT(c.id) AS comment_count " +
                    "FROM Post p " +
                    "LEFT JOIN Comment c ON p.id = c.post.id " +
                    "GROUP BY p " +
                    "ORDER BY comment_count DESC";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setMaxResults(10);


            return query.list().stream()
                    .map(objArr -> (Post) objArr[0])
                    .toList();
        }
    }

    @Override
    public List<Post> getMostRecentPosts() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT p " +
                    "FROM Post p " +
                    "ORDER BY p.timestamp DESC";

            Query<Post> query = session.createQuery(hql, Post.class);
            query.setMaxResults(10);

            return query.list();
        }
    }


    public List<Post> getPaginatedPosts(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("FROM Post ORDER BY timestamp DESC", Post.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.list();
        }
    }


    @Override
    public Post getById(int id) {
        try(Session session = sessionFactory.openSession()){
            Post post = session.get(Post.class, id);

            if (post == null){
                throw new EntityNotFoundException("Post", id);
            }

            return post;
        }
    }

    @Override
    public void create(Post post) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Post post) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Post post = getById(id);
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void alterPostLikes(Post post) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }

    private String createOrderBy(FilterPostOptions filterOptions) {
        if(filterOptions.getSortBy().isEmpty()){
            return "";
        }

        String orderBy = switch (filterOptions.getSortBy().get()) {
            case "title" -> "title";
            case "content" -> "content";
            case "createdBy" -> "createdBy.username";
            default -> "";
        };

        orderBy = String.format(" order by %s", orderBy);
        if(filterOptions.getSortOrder().isPresent() &&
                filterOptions.getSortOrder().get().equalsIgnoreCase("desc")){
            orderBy = String.format("%s desc", orderBy);
        }

        return orderBy;
    }
}

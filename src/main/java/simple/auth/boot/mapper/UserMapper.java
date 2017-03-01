package simple.auth.boot.mapper;

import java.util.List;

import simple.auth.boot.dmo.User;


public interface UserMapper {

    public int insert(User user);

    public void update(User user);

    public void delete(User user);

    public List<User> find(User user);

    public User findByUserName(String userName);
}

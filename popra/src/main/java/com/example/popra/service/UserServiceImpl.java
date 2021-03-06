package com.example.popra.service;

import com.example.popra.model.Authority;
import com.example.popra.model.User;
import com.example.popra.repository.AuthoritiesRepository;
import com.example.popra.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    //ранее было использование синхронайзе блока, но есть ведь у спринга такая замечательная транзакция
    //установлено именно REPEATABLE_READ, поскольку по сравнению с Serializable, но у второй выше нагрузка, а первая
    //хоть и подвержена фантомному чтению, но поскольку практически нет выборки - фантомное чтение фактически невозможно
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean createNewUser(String name, String pass) {
        logger.info(String.format("Создания нового пользователя [логин: %s пароль: %s] (начало)", name, pass));
        try {
            if (!validate(name)) {
                logger.info(String.format("Создания нового пользователя [логин: %s пароль: %s] (неудачно, " +
                        "пользователь с таким именем уже существует)", name, pass));
                return false;
            }
            User user = new User();
            user.setUsername(name);
            user.setPassword(pass);
            user.setEnabled(true);
            Authority authority = new Authority();
            authority.setUsername(name);
            authority.setAuthority("ROLE_USER");
            authoritiesRepository.save(authority);
            userRepository.save(user);

            logger.info(String.format("Создания нового пользователя [логин: %s пароль: %s] (успешно, " +
                    "пользователь создан)", name, pass));

        } catch (Exception e) {
            logger.error(String.format("Создания нового пользователя [логин: %s пароль: %s] (ошибка: " +
                    "[%s] %s)", name, pass, e.getMessage(), e));
            return false;
        }

        return true;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    @Override
    public boolean validate(String name) {
        User user = userRepository.findByUsername(name);
        return user == null;
    }
}

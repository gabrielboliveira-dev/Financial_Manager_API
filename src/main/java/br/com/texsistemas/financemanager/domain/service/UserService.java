package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.model.user.User;

public interface UserService {

    User addUser(User user);

    User getUser(String email);
}

package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.model.Account;
import br.com.texsistemas.financemanager.domain.repository.AccountRepository;
import br.com.texsistemas.financemanager.dto.AccountDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account.getId(), account.getName(), account.getType(), account.getOpeningBalance(), account.getCoin()))
                .toList();
    }

    public Optional<AccountDTO> findById(UUID id) {
        return accountRepository.findById(id)
                .map(account -> new AccountDTO(account.getId(), account.getName(), account.getType(), account.getOpeningBalance(), account.getCoin()));
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(UUID id) {
        accountRepository.deleteById(id);
    }
}

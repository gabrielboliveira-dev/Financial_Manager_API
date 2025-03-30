package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Account;
import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.domain.repository.AccountRepository;
import br.com.texsistemas.financemanager.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account createAccount(Account account, UUID userId) {
        Optional<User> userOptional = userRepository.findByIdAndAtivoTrue(userId);
        if (userOptional.isEmpty()) {
            throw new BusinessException("Usuário não encontrado ou inativo.", "USER_NOT_FOUND");
        }
        account.setUser(userOptional.get());
        account.setCreationDate(LocalDateTime.now());
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getAccountsByUser(UUID userId) {
        Optional<User> userOptional = userRepository.findByIdAndAtivoTrue(userId);
        if (userOptional.isEmpty()) {
            throw new BusinessException("Usuário não encontrado ou inativo.", "USER_NOT_FOUND");
        }
        return accountRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public Account updateAccount(UUID accountId, Account accountDetails) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new BusinessException("Conta não encontrada.", "ACCOUNT_NOT_FOUND");
        }
        Account existingAccount = accountOptional.get();

        // Atualiza os campos permitidos
        existingAccount.setAccountNumber(accountDetails.getAccountNumber());
        existingAccount.setAgencyNumber(accountDetails.getAgencyNumber());
        existingAccount.setAccountName(accountDetails.getAccountName());
        existingAccount.setAccountType(accountDetails.getAccountType());
        existingAccount.setBalance(accountDetails.getBalance());

        return accountRepository.save(existingAccount);
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new BusinessException("Conta não encontrada.", "ACCOUNT_NOT_FOUND");
        }
        accountRepository.deleteById(accountId);
    }

    @Transactional(readOnly = true)
    public List<Account> getAccountsByType(UUID userId, String accountType) {
        Optional<User> userOptional = userRepository.findByIdAndAtivoTrue(userId);
        if (userOptional.isEmpty()) {
            throw new BusinessException("Usuário não encontrado ou inativo.", "USER_NOT_FOUND");
        }
        return accountRepository.findByUser_IdAndAccountType(userId, accountType);
    }
}
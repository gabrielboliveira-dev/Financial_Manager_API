package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Account;
import br.com.texsistemas.financemanager.domain.model.Category;
import br.com.texsistemas.financemanager.domain.model.Transaction;
import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.domain.repository.AccountRepository;
import br.com.texsistemas.financemanager.domain.repository.CategoryRepository;
import br.com.texsistemas.financemanager.domain.repository.TransactionRepository;
import br.com.texsistemas.financemanager.domain.repository.UserRepository;
import br.com.texsistemas.financemanager.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
    }

    // Busco todas as transações. Converto cada transação para TransactionDTO e coleto numa lista.
    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busco uma transação pelo seu ‘ID’. Se encontro, converto para TransactionDTO.
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findById(UUID id) {
        return transactionRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Busco o usuário ativo pelo ‘ID’, verifico se  existe e está ativo. Se não, lanço uma exceção.
    @Transactional // Transação para operações de leitura e escrita.
    public TransactionDTO save(Transaction transaction, UUID userId, UUID accountId, String categoryName) {
        Optional<User> userOptional = userRepository.findByIdAndAtivoTrue(userId);
        if (userOptional.isEmpty()) {
            throw new BusinessException("Usuário não encontrado ou inativo.");
        }
        User user = userOptional.get();

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new BusinessException("Conta não encontrada.");
        }
        Account account = accountOptional.get();

        // Busco uma categoria pelo nome. Se não existir, crio e salvo uma nova. Se existir, obtenho a categoria.
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);
        Category category;
        if (categoryOptional.isEmpty()) { //
            category = new Category();
            category.setName(categoryName);
            category = categoryRepository.save(category);
        } else {
            category = categoryOptional.get();
        }

        //Faço associações
        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setCategory(category);

        // Salvo a transação no banco de dados e converto a transação salva para TransactionDTO.
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }

    // Busco a transação pelo ‘ID’. Se não existir, lanço uma exceção.
    @Transactional
    public TransactionDTO updateStatus(UUID id, String newStatus) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new BusinessException("Transação não encontrada.");
        }
        //Pego a transação, atualizo o seu status, após salvar, converto em TransactionDTO.
        Transaction transaction = transactionOptional.get();
        transaction.setStatus(newStatus);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return convertToDTO(updatedTransaction);
    }

    // Busco a transação pelo ‘ID’. Se não existir, lanço uma exceção.
    @Transactional // Transação para operações de leitura e escrita.
    public void delete(UUID id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new BusinessException("Transação não encontrada.");
        }
        // Pego ela, verifico o seu status e se permite excluir, se não permitir lanço em exceção.
        Transaction transaction = transactionOptional.get();
        String status = transaction.getStatus();
        if (!status.equalsIgnoreCase("PENDENTE") && !status.equalsIgnoreCase("ABERTO")) {
            throw new BusinessException("A transação só pode ser excluída se estiver com status PENDENTE ou ABERTO.");
        }
        // Se o status for permitido, excluo a transação.
        transactionRepository.deleteById(id);
    }

    //Converto a entidade Transaction para um TransactionDTO.
    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getCategory().getId(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getStatus()
        );
    }
}
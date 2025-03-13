package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.model.Transaction;
import br.com.texsistemas.financemanager.domain.repository.TransactionRepository;
import br.com.texsistemas.financemanager.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getAccount().getId(),
                        transaction.getCategory().getId(),
                        transaction.getDescription(),
                        transaction.getValue(),
                        transaction.getDate(),
                        transaction.getType(),
                        transaction.getStatus()
                )).toList();
    }

    public Optional<TransactionDTO> findById(UUID id) {
        return transactionRepository.findById(id)
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getAccount().getId(),
                        transaction.getCategory().getId(),
                        transaction.getDescription(),
                        transaction.getValue(),
                        transaction.getDate(),
                        transaction.getType(),
                        transaction.getStatus()
                ));
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void delete(UUID id) {
        transactionRepository.deleteById(id);
    }
}

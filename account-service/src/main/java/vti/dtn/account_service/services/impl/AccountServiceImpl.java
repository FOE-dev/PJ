package vti.dtn.account_service.services.impl;

import org.springframework.stereotype.Service;
import vti.dtn.account_service.dto.AccountDTO;
import vti.dtn.account_service.entity.AccountEntity;
import vti.dtn.account_service.repository.AccountRepository;
import vti.dtn.account_service.services.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDTO> getListAccounts() {
        List<AccountEntity> accountEntities = accountRepository.findAll();

        return accountEntities.stream()
                .map(a -> new AccountDTO(
                        a.getId(),
                        a.getUsername(),
                        a.getEmail()
                ))
                .toList();
    }
}

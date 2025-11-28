package vti.dtn.account_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import vti.dtn.account_service.dto.AccountDTO;
import vti.dtn.account_service.services.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        log.info("GET /api/v1/accounts called");
        return accountService.getListAccounts();
    }
}

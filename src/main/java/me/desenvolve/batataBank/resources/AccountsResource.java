package me.desenvolve.batataBank.resources;

import me.desenvolve.batataBank.Exceptions.InvalidTransactionException;
import me.desenvolve.batataBank.resources.dto.CreateAccountDTO;
import me.desenvolve.batataBank.resources.dto.TransactionDTO;
import me.desenvolve.batataBank.resources.dto.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contas")
public class AccountsResource {

    private Map<Integer, CreateAccountDTO> accounts = new HashMap<>();

    private Map<Integer, List<TransactionDTO>> transactions = new HashMap<>();


    public AccountsResource(){

        CreateAccountDTO conta1 = new CreateAccountDTO(001, 00001, "760.136.190-00");
        conta1.setId(1);
        accounts.put(conta1.getId(), conta1);

        CreateAccountDTO conta2 = new CreateAccountDTO(001, 00002, "277.756.350-02");
        conta2.setId(2);
        accounts.put(conta2.getId(), conta2);

        CreateAccountDTO conta3 = new CreateAccountDTO(001, 00003, "760.136.190-00");
        conta3.setId(3);
        accounts.put(conta3.getId(), conta3);

        CreateAccountDTO conta4 = new CreateAccountDTO(001, 00004, "563.652.930-97");
        conta4.setId(4);
        accounts.put(conta4.getId(), conta4);

        CreateAccountDTO conta5 = new CreateAccountDTO(001, 00005, "615.946.890-16");
        conta5.setId(5);
        accounts.put(conta5.getId(), conta5);
    }


    @PostMapping
    public ResponseEntity createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) throws URISyntaxException {
        createAccountDTO.setId(accounts.size());

        accounts.put(createAccountDTO.getId(), createAccountDTO);

        return ResponseEntity.created(new URI("http://localhost:8080/contas/"+createAccountDTO.getId())).build();
    }

    @GetMapping()
    public ResponseEntity getAccounts(@RequestParam(required = false) String cpf){

        return ResponseEntity.ok(accounts
                .values()
                .stream()
                .filter( account -> {
                    if (cpf != null){
                        return account.getCpf().equalsIgnoreCase(cpf);
                    }
                    return true;
                })
                .collect(Collectors.toList()));
    }

    @GetMapping("/{account_id}")
    public ResponseEntity getAccount(@PathVariable("account_id") Integer accountId){

        return Optional.ofNullable(accounts.get(accountId))
                .map( (account) -> ResponseEntity.ok(account))
                .orElseGet( () -> ResponseEntity.status(204).build() );
    }

    @PostMapping("/{account_id}/transacoes")
    public ResponseEntity addTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
                                         @PathVariable("account_id") Integer accountId){


        CreateAccountDTO account = accounts.get(accountId);

        if(account == null){
            return ResponseEntity.notFound().build();
        }

        try{
            account.realizaTrasacao(transactionDTO);
            List<TransactionDTO> transactionsOfAccount = transactions.getOrDefault(accountId, new ArrayList<>());
            transactionsOfAccount.add(transactionDTO);
            transactions.put(accountId, transactionsOfAccount);
            return ResponseEntity.status(201).build();
        }catch (InvalidTransactionException ex){
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{account_id}/transacoes")
    public ResponseEntity getTransactions(@PathVariable("account_id") Integer accountId){
        return ResponseEntity.ok(transactions.getOrDefault(accountId, new ArrayList<>()));
    }
}

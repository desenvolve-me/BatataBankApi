package me.desenvolve.batataBank.resources;

import me.desenvolve.batataBank.Exceptions.InvalidTransactionException;
import me.desenvolve.batataBank.model.Account;
import me.desenvolve.batataBank.model.BillingAddress;
import me.desenvolve.batataBank.model.Transaction;
import me.desenvolve.batataBank.model.TransactionType;
import me.desenvolve.batataBank.resources.dto.*;
import org.springframework.http.ResponseEntity;
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

    private Map<Integer, Account> accounts = new HashMap<>();


    public AccountsResource(){

        String [] cpfs = {"760.136.190-00", "277.756.350-02", "760.136.190-00", "563.652.930-97", "615.946.890-16"};

        for(int index = 0 ; index < cpfs.length ; index++) {
            BillingAddress billingAddress = new BillingAddress("09835000",
                    "SP",
                    "São Paulo",
                    "Osasco",
                    "Rua "+index+1,
                    Integer.valueOf(1000+index).toString(),
                    null);

            Account account = new Account(index, 1, accounts.size() + 1, cpfs[index], billingAddress);
            accounts.put(account.getId(), account);
        }
    }


    @PostMapping
    public ResponseEntity createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) throws URISyntaxException {


        BillingAddress billingAddress = new BillingAddress(
                createAccountDTO.getEnderecoDeCobranca().getCep(),
                createAccountDTO.getEnderecoDeCobranca().getEstado(),
                createAccountDTO.getEnderecoDeCobranca().getCidade(),
                createAccountDTO.getEnderecoDeCobranca().getBairro(),
                createAccountDTO.getEnderecoDeCobranca().getEndereco(),
                createAccountDTO.getEnderecoDeCobranca().getNumero(),
                createAccountDTO.getEnderecoDeCobranca().getComplemento());

        Account newAccount = new Account(accounts.size(), createAccountDTO.getAgencia(),
                            createAccountDTO.getConta(), createAccountDTO.getCpf(), billingAddress);

        accounts.put(newAccount.getId(), newAccount);

        return ResponseEntity.created(new URI("http://localhost:8080/contas/"+newAccount.getId())).build();
    }

    @GetMapping()
    public ResponseEntity getAccounts(@RequestParam(required = false) String cpf){

        return ResponseEntity.ok(accounts
                .values()
                .stream()
                .map( account ->
                     new AccountResumeDTO(account.getId(), account.getAgencia(), account.getConta(), account.getCpf())
                )
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
                .map(account -> {
                    BillingAddressDTO billingAddressDTO = new BillingAddressDTO(account.getEnderecoDeCobranca().getCep(),
                            account.getEnderecoDeCobranca().getEstado(),
                            account.getEnderecoDeCobranca().getCidade(),
                            account.getEnderecoDeCobranca().getBairro(),
                            account.getEnderecoDeCobranca().getEndereco(),
                            account.getEnderecoDeCobranca().getNumero(),
                            account.getEnderecoDeCobranca().getComplemento());

                    return new AccountDTO(account.getId(), account.getAgencia(), account.getConta(), account.getCpf(),
                            account.getSaldo(), billingAddressDTO);
                })
                .map( (accountDTO) -> ResponseEntity.ok(accountDTO))
                .orElseGet( () -> ResponseEntity.status(204).build() );
    }

    @PostMapping("/{account_id}/transacoes")
    public ResponseEntity addTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
                                         @PathVariable("account_id") Integer accountId){

        Account account = accounts.get(accountId);

        if(account == null){
            return ResponseEntity.notFound().build();
        }

        try{
            Transaction transaction = new Transaction(TransactionType.valueOf(transactionDTO.getTipo()), transactionDTO.getValor());
            account.realizaTrasacao(transaction);
            return ResponseEntity.status(201).build();
        }catch (InvalidTransactionException ex){
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{account_id}/transacoes")
    public ResponseEntity getTransactions(@PathVariable("account_id") Integer accountId){

        List<TransactionDTO> transactionDTOS = Optional.ofNullable(accounts.get(accountId))
                .map(account -> account.getTransactions())
                .orElseGet(() -> new ArrayList<>())
                .stream()
                .map(transaction -> new TransactionDTO(transaction.getTipo().name(), transaction.getValor()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDTOS);
    }


    @PutMapping("/{account_id}")
    public ResponseEntity updateAccount(@PathVariable("account_id") Integer accountId,
                                        @Valid @RequestBody BillingAddressDTO billingAddressDTO) {

        Account account = accounts.get(accountId);

        if(account == null){
            return ResponseEntity.notFound().build();
        }

        BillingAddress billingAddress = new BillingAddress(
                billingAddressDTO.getCep(),
                billingAddressDTO.getEstado(),
                billingAddressDTO.getCidade(),
                billingAddressDTO.getBairro(),
                billingAddressDTO.getEndereco(),
                billingAddressDTO.getNumero(),
                billingAddressDTO.getComplemento());

        account.setEnderecoDeCobranca(billingAddress);

        return ResponseEntity.ok().build();
    }

}

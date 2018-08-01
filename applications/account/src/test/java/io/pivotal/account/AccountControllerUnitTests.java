package io.pivotal.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.account.controller.AccountController;
import io.pivotal.account.domain.Account;
import io.pivotal.account.repository.AccountRepository;
import io.pivotal.account.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerUnitTests {

  private Account account = new Account("Ryan", "Reynolds", "rreynolds@deadpool.com");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountRepository accountRepository;

  @MockBean
  private AddressRepository addressRepository;

  @MockBean
  private org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig instanceConfig;

  @MockBean
  private org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration eurekaDiscoveryClientConfiguration;

  @InjectMocks
  private AccountController accountController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAccountCreation() throws Exception {
    //Account account = new Account("Ryan", "Reynolds", "rreynolds@deadpool.com");
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    mockMvc
        .perform(post("/accounts")
            .param("firstName", account.getFirstName())
            .param("lastName", account.getLastName())
            .param("email", account.getEmail()))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  public void testAccountCreationWithBadParameters() throws Exception {
    //Account account = new Account("Ryan", "Reynolds", "rreynolds@deadpool.com");
    when(accountRepository.save(any(Account.class))).thenReturn(null);

    mockMvc
        .perform(post("/accounts")
            .param("firstName", account.getFirstName())
            .param("lastName", account.getLastName())
            .param("email", account.getEmail()))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  public void testUpdatingAccount() throws Exception {
    when(accountRepository.findOne(any(Long.class))).thenReturn(account);
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    mockMvc
        .perform(put("/accounts")
            .param("id", "1")
            .param("firstName", account.getFirstName())
            .param("lastName", account.getLastName())
            .param("email", account.getEmail()))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void testUpdatingAccountWithBadID() throws Exception {
    when(accountRepository.findOne(any(Long.class))).thenReturn(null);

    mockMvc
        .perform(put("/accounts")
            .param("id", "1")
            .param("firstName", account.getFirstName())
            .param("lastName", account.getLastName())
            .param("email", account.getEmail()))
        .andExpect(status().isBadRequest())
        .andReturn();
  }



  public static String convertObjectToJson(Object object) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }
}

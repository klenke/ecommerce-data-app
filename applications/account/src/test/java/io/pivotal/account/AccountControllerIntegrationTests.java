package io.pivotal.account;

import io.pivotal.account.controller.AccountController;
import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Address;
import io.pivotal.account.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTests {

  @Autowired
  private AccountController accountController;

  @Autowired
  private AddressRepository addressRepository;    //needed to add addresses to account for tests

  private Account account;

  @Before
  public void setup(){
    account = accountController.create("Ryan", "Reynolds", "dead@pool.com").getBody();
  }

  @Test
  public void testCreatingAccount(){
    Account resp = accountController
        .create("Collin", "Klenke", "cklenke@solstice.com")
        .getBody();

    assertNotNull(resp);
    assertEquals("Collin", resp.getFirstName());
    assertEquals("Klenke", resp.getLastName());
    assertEquals("cklenke@solstice.com", resp.getEmail());
  }

  @Test
  public void testUpdatingAccount(){
    Account resp = accountController
        .update(account.getId(), "Ralph", "Emerson", "wheresWaldo@org.com")
        .getBody();

    assertEquals("Ralph", resp.getFirstName());
    assertEquals("Emerson", resp.getLastName());
    assertEquals("wheresWaldo@org.com", resp.getEmail());
    assertEquals(resp.getId(), account.getId());
  }

  @Test
  public void testGetById(){
    Account resp = accountController.account(account.getId());

    assertEquals(resp.getId(), account.getId());
    assertEquals(resp.getEmail(), account.getEmail());
    assertEquals(resp.getFirstName(), account.getFirstName());
    assertEquals(resp.getLastName(), account.getLastName());
  }

  @Test
  public void testDelete(){
    accountController.deleteAccount(account.getId().toString());
    Account resp = accountController.account(account.getId());
    assertNull(resp);
  }

  @Test
  public void testGetAddressesForAccount(){
    //each will be tied to account
    List<Address> testList = Arrays.asList(
        new Address("1232 W Eddy st", "Unit G", "Chicago", "IL", "60657", "US", account),
        new Address("333 Old Woodlands Road", "", "Columbia", "SC", "29209", "US", account),
        new Address("9238 Elizabeth Road", "", "Houston", "TX", "77055", "US", account)
    );

    addressRepository.save(testList);

    List<Address> addresses = accountController.getAddresses(account.getId()).getBody();

    assertNotNull(addresses);
    assertEquals(3, addresses.size());

    for (Address a:addresses) {
      assertEquals(account.getId(), a.getAccount().getId());
    }
  }
}

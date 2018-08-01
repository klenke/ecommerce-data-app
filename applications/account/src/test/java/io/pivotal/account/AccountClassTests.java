package io.pivotal.account;

import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountClassTests {

  private Account account;

  @Before
  public void setup(){
    this.account = new Account("John", "Doe", "jd@mia.org");
  }

  @Test
  public void testAccountCreation(){
    assertNotNull(account);
  }

  @Test
  public void testGetName(){
    assertEquals("John", account.getFirstName());
  }

  @Test
  public void testGetLastName(){
    assertEquals("Doe", account.getLastName());
  }

  @Test
  public void testGetEmail(){
    assertEquals("jd@mia.org", account.getEmail());
  }


}

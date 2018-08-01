package io.pivotal.account;

import io.pivotal.account.controller.AddressController;
import io.pivotal.account.domain.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerIntegrationTests {

  @Autowired
  private AddressController addressController;

  private Address address;

  @Before
  public void setup(){
    address = addressController
        .create("1232 W Eddy st",
            "Unit G",
            "Chicago",
            "IL",
            "60657",
            "USA",
            1L);

  }

  @Test
  public void testCreatingAddress(){
    assertNotNull(address);
    assertEquals("1232 W Eddy st", address.getStreet());
    assertEquals("Unit G", address.getApt());
    assertEquals("Chicago", address.getCity());
    assertEquals("IL", address.getState());
    assertEquals("60657", address.getZip());
    assertEquals("USA", address.getCountry());

  }

  @Test
  public void testUpdatingAddress(){
    Address resp = addressController.update(
        address.getId(),
        "111 N Canal",
        "Floor 7",
        "Chi",
        "Illinois",
        "60606",
        "United States",
        2L);

    assertEquals("111 N Canal", resp.getStreet());
    assertEquals("Floor 7", resp.getApt());
    assertEquals("Chi", resp.getCity());
    assertEquals("Illinois", resp.getState());
    assertEquals("60606", resp.getZip());
    assertEquals("United States", resp.getCountry());

    assertEquals(resp.getId(), address.getId());
  }

  @Test
  public void testGetById(){
    Address resp = addressController.address(address.getId());
    assertEquals(resp.getId(), address.getId());
    assertEquals(resp.getApt(), address.getApt());
    assertEquals(resp.getCity(), address.getCity());
    assertEquals(resp.getCountry(), address.getCountry());
    assertEquals(resp.getState(), address.getState());
    assertEquals(resp.getZip(), address.getZip());
  }

  @Test
  public void testDelete(){
    //delete it
    addressController.deleteAddress(address.getId());

    //try to find it in repo
    Address resp = addressController.address(address.getId());
    assertNull(resp);
  }
}

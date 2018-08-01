package io.pivotal.order;

import io.pivotal.order.service.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import javax.management.ServiceNotFoundException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ShipmentServiceUnitTests {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private ShipmentService shipmentService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetDateFallback(){
    Date date = shipmentService.getDateFallback(1L);
    assertNull(date);
  }

  @Test
  public void testGetShippedDate(){
    Date date = new Date(1234);
    when(restTemplate.getForObject(anyString(), eq(Date.class))).thenReturn(date);
    Date resp = shipmentService.getShippedDate(1L);
    assertNotNull(resp);
    assertEquals(date, resp);
  }

  @Test
  public void testGetDeliveredDate(){
    Date date = new Date(1234);
    when(restTemplate.getForObject(anyString(), eq(Date.class))).thenReturn(date);
    Date resp = shipmentService.getDeliveredDate(1L);
    assertNotNull(resp);
    assertEquals(date, resp);
  }
}

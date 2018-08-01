package io.pivotal.order.domain;

import java.util.List;

public class OrderLineItemList {
  private List<OrderLineItem> items;

  public OrderLineItemList(){}

  public OrderLineItemList(List<OrderLineItem> items) {
    this.items = items;
  }

  public List<OrderLineItem> getItems() {
    return items;
  }

  public void setItems(List<OrderLineItem> items) {
    this.items = items;
  }
}

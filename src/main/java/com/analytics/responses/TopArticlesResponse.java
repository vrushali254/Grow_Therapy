package com.analytics.responses;


/*
"project": "en.wikipedia",
      "access": "all-access",
      "year": "2021",
      "month": "10",
      "day": "all-days",
 */


import com.analytics.models.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopArticlesResponse {

    @SerializedName("items")
    @Expose
    public List<Item> items;

    public List<Item> getItems()  { return items; }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
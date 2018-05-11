package com.xuie.android.ui.recycler.discrete;

import android.content.Context;
import android.content.SharedPreferences;

import com.xuie.android.App;
import com.xuie.android.R;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author yarolegovich
 * @date 07.03.2017
 */

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get() {
        return new Shop();
    }

    private SharedPreferences storage;

    private Shop() {
        storage = App.getContext().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "Everyday Candle", "$12.00 USD", R.mipmap.one),
                new Item(2, "Small Porcelain Bowl", "$50.00 USD", R.mipmap.two),
                new Item(3, "Favourite Board", "$265.00 USD", R.mipmap.three),
                new Item(4, "Earthenware Bowl", "$18.00 USD", R.mipmap.four),
                new Item(5, "Porcelain Dessert Plate", "$36.00 USD", R.mipmap.five)
        );
    }

    public boolean isRated(int itemId) {
        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}

package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.util.Locale;

import static com.udacity.stockhawk.R.id.change;
import static com.udacity.stockhawk.R.id.price;

/**
 * Created by rsteller on 12/19/2016.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private  int CurrentPrice;

    Context mContext = null;
    Cursor data = null;
    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        data.moveToPosition(position);

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_list_item);
        String price=String.valueOf(data.getFloat(Contract.Quote.POSITION_PRICE));
        String symbol= data.getString(Contract.Quote.POSITION_SYMBOL);

        view.setTextViewText(R.id.stock_name, symbol);
        view.setTextViewText(R.id.stock_price, price);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        data = mContext.getContentResolver().query(
                Contract.Quote.uri,
                Contract.Quote.QUOTE_COLUMNS,
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL);
    }
}
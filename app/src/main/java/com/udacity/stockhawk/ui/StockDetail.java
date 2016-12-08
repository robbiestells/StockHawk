package com.udacity.stockhawk.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.udacity.stockhawk.R;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import static com.udacity.stockhawk.R.id.price;

/**
 * Created by rsteller on 12/8/2016.
 */

public class StockDetail extends AppCompatActivity {
    @BindView(R.id.stockNameTV)
    TextView stockNameTV;
    @BindView(R.id.stockPriceTV)
    TextView stockPriceTV;
    BigDecimal stockPrice;
    Calendar calendar;
    YahooFinance yahooFinance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_detail);

        ButterKnife.bind(this);

        getHistory();


    }

    private class getHistory extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar from = calendar.getInstance();
            Calendar to = calendar.getInstance();
            from.add(Calendar.YEAR, -5); // from 5 years ago

            try {
                Stock stock = yahooFinance.get("GOOG", from, to, Interval.WEEKLY);
                price = stock.getQuote().getPrice();

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            stockNameTV.setText("GOOG");
            stockPriceTV.setText(price.toString());
        }

    }

}

package com.udacity.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by rsteller on 12/19/2016.
 */

public class CollectionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider_layout);

        views.setRemoteAdapter(R.id.widget_list, new Intent(context, WidgetService.class));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        if(MainActivity.ACTION_DATA_UPDATED.equals(intent.getAction())){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName myappWidget = new ComponentName(context.getPackageName(), CollectionWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(myappWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
}

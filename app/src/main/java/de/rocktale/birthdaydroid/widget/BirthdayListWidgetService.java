package de.rocktale.birthdaydroid.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BirthdayListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new BirthdayListWidgetViewsFactory(this.getApplicationContext(), intent));
    }
}

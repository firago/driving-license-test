package com.dfirago.dlt.screen.dashboard;

import com.dfirago.dlt.common.model.Category;

/**
 * Created by Dmytro Firago on 16/07/2017.
 */
public interface DashboardView {

    void showCategoryScreen(Category category);

    void showRateApplicationScreen();
}

package com.labexam3.dilmith

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val prefs = context.getSharedPreferences("habit_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("habits_list", null)
        val type = object : TypeToken<MutableList<Habit>>() {}.type
        val habits: List<Habit> = if (json != null) gson.fromJson(json, type) else emptyList()

        val completedCount = habits.count { it.isCompleted }
        val totalCount = habits.size
        val progress = if (totalCount > 0) (completedCount * 100 / totalCount) else 0

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.habit_widget)
        views.setTextViewText(R.id.widget_percentage, "$progress%")
        views.setProgressBar(R.id.widget_progress_bar, 100, progress, false)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
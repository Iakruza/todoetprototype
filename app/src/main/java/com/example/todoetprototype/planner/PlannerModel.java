package com.example.todoetprototype.planner;

import java.util.LinkedList;
import java.util.List;

public class PlannerModel {

    private static PlannerModel instance = null;

    public static PlannerModel getInstance() {
        if (instance == null)
            instance = new PlannerModel();
        return instance;
    }

    private List<PlannerItem> plannerItems;

    private PlannerModel() {
        this.plannerItems = new LinkedList<>();
    }

    public void loadData(PlannerActivity context) {
        throw new UnsupportedOperationException("PlannerModel.loadData is unsupported");
//        try (DatabaseHandler databaseHandler = new DatabaseHandler(context)) {
//            databaseHandler.loadAllTasks();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public List<PlannerItem> getPlannerItems() {
        return plannerItems;
    }

    public void setPlannerItems(List<PlannerItem> plannerItems) {
        this.plannerItems = plannerItems;
    }

    public void addPlannerItemToList(PlannerItem item) {
        plannerItems.add(item);
    }

    public void removePlannerItemFromList(PlannerItem item) {
        plannerItems.remove(item);
    }
}

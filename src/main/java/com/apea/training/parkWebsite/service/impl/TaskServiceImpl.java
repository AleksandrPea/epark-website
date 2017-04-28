package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.ServiceException;
import com.apea.training.parkWebsite.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    TaskServiceImpl() {}

    @Override
    public void createNewAndAssociate(Task task, List<Plant> plants) {
        MySqlTransactionHelper.getInstance().beginTransaction();
        try {
            task.setState(Task.State.NEW);
            TaskDao taskDao = MySqlDaoFactory.getInstance().getTaskDao();
            taskDao.create(task);
            plants.forEach(plant -> taskDao.associate(task, plant.getId()));
            MySqlTransactionHelper.getInstance().commitTransaction();
        } catch (DaoException e) {
            MySqlTransactionHelper.getInstance().rollbackTransaction();
            throw new ServiceException("Transaction failed.", e);
        }
    }

    @Override
    public Task getById(Integer id) {
        return MySqlDaoFactory.getInstance().getTaskDao().getById(id);
    }

    @Override
    public List<Task> getUserTasks(User user) {
        return MySqlDaoFactory.getInstance().getTaskDao().getUserTasks(user.getId());
    }

    @Override
    public void delete(Task task) {
        MySqlDaoFactory.getInstance().getTaskDao().delete(task);
    }

    @Override
    public List<Plant> getAssociatedPlants(Task task) {
        PlantDao plantDao = MySqlDaoFactory.getInstance().getPlantDao();
        return MySqlDaoFactory.getInstance().getTaskDao().getAssociatedPlantIds(task)
                .stream()
                .map(plantDao::getById)
                .collect(Collectors.toList());
    }

    @Override
    public void finishAndUpdatePlantStates(Task task, List<Plant> plants) {
        MySqlTransactionHelper.getInstance().beginTransaction();
        try {
            task.setState(Task.State.FINISHED);
            MySqlDaoFactory.getInstance().getTaskDao().updateState(task);
            PlantDao plantDao = MySqlDaoFactory.getInstance().getPlantDao();
            plants.forEach(plantDao::update);
            MySqlTransactionHelper.getInstance().commitTransaction();
        } catch (DaoException e) {
            MySqlTransactionHelper.getInstance().rollbackTransaction();
            throw new ServiceException("Transaction failed.", e);
        }
    }

    @Override
    public void abort(Task task) {
        task.setState(Task.State.INCOMPLETED);
        MySqlDaoFactory.getInstance().getTaskDao().updateState(task);
    }

    @Override
    public void confirmRecieving(Task task) {
        task.setState(Task.State.RUNNING);
        MySqlDaoFactory.getInstance().getTaskDao().updateState(task);
    }
}
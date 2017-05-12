package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MySqlDaoFactory.class, MySqlTransactionHelper.class})
public class TaskServiceImplTest {

    @Mock
    private MySqlDaoFactory factory;

    @Mock
    private MySqlTransactionHelper transactionHelper;

    @Mock
    private TaskDao taskDao;

    @Before
    public void setUpMocks() {
        mockStatic(MySqlDaoFactory.class);
        mockStatic(MySqlTransactionHelper.class);
        when(MySqlDaoFactory.getInstance()).thenReturn(factory);
        when(MySqlTransactionHelper.getInstance()).thenReturn(transactionHelper);
        when(factory.getTaskDao()).thenReturn(taskDao);
    }

    @Test
    public void testTransactionSuccessForCreateNewAndAssociate() {
        Task task = new Task();
        Plant[] plants = new Plant[10];
        Arrays.fill(plants, new Plant());
        InOrder inOrder = inOrder(transactionHelper, taskDao);

        ServiceFactoryImpl.getInstance().getTaskService().createNewAndAssociate(task, Arrays.asList(plants));

        inOrder.verify(transactionHelper).beginTransaction();
        Assert.assertEquals(Task.State.NEW, task.getState());
        inOrder.verify(taskDao).create(eq(task));
        inOrder.verify(taskDao, times(10)).associate(eq(task), anyInt());
        inOrder.verify(transactionHelper).commitTransaction();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testTransactionFailForCreateNewAndAssociate() {
        Task task = new Task();
        doThrow(DaoException.class).when(taskDao).create(eq(task));

        InOrder inOrder = inOrder(transactionHelper, taskDao);
        try {
            ServiceFactoryImpl.getInstance().getTaskService().createNewAndAssociate(task, Arrays.asList(new Plant()));
        } catch (ServiceException e) {
            Assert.assertTrue(e.getCause() instanceof DaoException);
        }

        inOrder.verify(transactionHelper).beginTransaction();
        inOrder.verify(taskDao).create(eq(task));
        inOrder.verify(transactionHelper).rollbackTransaction();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testGetAssociatedPlants() {
        PlantDao plantDao = mock(PlantDao.class);
        when(factory.getPlantDao()).thenReturn(plantDao);
        when(taskDao.getAssociatedPlantIds(any())).thenReturn(Arrays.asList(1, 2, 3));

        ServiceFactoryImpl.getInstance().getTaskService().getAssociatedPlants(new Task());

        verify(plantDao).getById(eq(1));
        verify(plantDao).getById(eq(2));
        verify(plantDao).getById(eq(3));

        verifyNoMoreInteractions(plantDao);
    }

    @Test
    public void testUpdateState() {
        Task task = new Task();
        when(taskDao.getById(anyInt())).thenReturn(task);

        ServiceFactoryImpl.getInstance().getTaskService().setState(1, Task.State.RUNNING);

        verify(taskDao).getById(eq(1));
        Assert.assertEquals(Task.State.RUNNING, task.getState());
        verify(taskDao).updateState(eq(task));

        verifyNoMoreInteractions(taskDao);
    }
}
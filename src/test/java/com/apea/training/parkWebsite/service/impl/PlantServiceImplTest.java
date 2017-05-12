package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Plant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MySqlDaoFactory.class)
public class PlantServiceImplTest {

    @Test
    public void testGetAssociatedTasks() {
        MySqlDaoFactory factory = mock(MySqlDaoFactory.class);
        PlantDao plantDao = mock(PlantDao.class);
        TaskDao taskDao = mock(TaskDao.class);
        mockStatic(MySqlDaoFactory.class);

        when(MySqlDaoFactory.getInstance()).thenReturn(factory);
        when(factory.getTaskDao()).thenReturn(taskDao);
        when(factory.getPlantDao()).thenReturn(plantDao);
        when(plantDao.getAssociatedTaskIds(any())).thenReturn(Arrays.asList(1, 2, 3));

        ServiceFactoryImpl.getInstance().getPlantService().getAssociatedTasks(new Plant());

        verify(taskDao).getById(eq(1));
        verify(taskDao).getById(eq(2));
        verify(taskDao).getById(eq(3));

        verifyNoMoreInteractions(taskDao);
    }
}
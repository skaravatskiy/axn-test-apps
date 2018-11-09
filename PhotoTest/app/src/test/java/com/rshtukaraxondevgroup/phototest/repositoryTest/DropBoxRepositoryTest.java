package com.rshtukaraxondevgroup.phototest.repositoryTest;

import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.DropBoxPresenter;
import com.rshtukaraxondevgroup.phototest.repository.DropBoxRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class DropBoxRepositoryTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    DropBoxPresenter dropBoxPresenter;

    private DropBoxRepository dropBoxRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dropBoxRepository = new DropBoxRepository();
    }

    @Test
    public void getBooksList() {
        dropBoxRepository.uploadDownloadFile(TestHelper.uriString, dropBoxPresenter);
//        verify(dropBoxPresenter).downloadSuccessful(TestHelper.file);
    }
}

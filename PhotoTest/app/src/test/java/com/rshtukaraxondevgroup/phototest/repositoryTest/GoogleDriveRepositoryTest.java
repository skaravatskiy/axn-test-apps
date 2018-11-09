package com.rshtukaraxondevgroup.phototest.repositoryTest;

import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.GoogleDrivePresenter;
import com.rshtukaraxondevgroup.phototest.repository.GoogleDriveRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class GoogleDriveRepositoryTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    GoogleDrivePresenter googleDrivePresenter;

    private GoogleDriveRepository googleDriveRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        googleDriveRepository = new GoogleDriveRepository();
    }

    @Test
    public void uploadFileInGoogleDrive() {
        googleDriveRepository.uploadFileInGoogleDrive(TestHelper.uriString, TestHelper.credentials, googleDrivePresenter);
//        verify(authPresenter).downloadSuccessful(TestHelper.file);
    }
}

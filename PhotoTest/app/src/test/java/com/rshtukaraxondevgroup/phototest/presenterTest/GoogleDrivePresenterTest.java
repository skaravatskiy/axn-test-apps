package com.rshtukaraxondevgroup.phototest.presenterTest;

import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.GoogleDrivePresenter;
import com.rshtukaraxondevgroup.phototest.repository.GoogleDriveRepository;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

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
public class GoogleDrivePresenterTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    UploadScreen uploadScreen;
    @Mock
    GoogleDriveRepository googleDriveRepository;
    private GoogleDrivePresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new GoogleDrivePresenter(uploadScreen, googleDriveRepository);
    }

    @Test
    public void uploadDownloadFileFromDropBox() {
        presenter.uploadDownloadFileToGoogleDrive(TestHelper.uriString, TestHelper.credentials);
        verify(googleDriveRepository).uploadFileInGoogleDrive(TestHelper.uriString, TestHelper.credentials, presenter);
        verify(uploadScreen).showProgressBar();
    }

    @Test
    public void downloadSuccessful() {
        presenter.downloadSuccessful(TestHelper.file);
        verify(uploadScreen).hideProgressBar();
        verify(uploadScreen).showImage(TestHelper.file);
    }

    @Test
    public void downloadError() {
        presenter.downloadError(TestHelper.throwable);
        verify(uploadScreen).hideProgressBar();
        verify(uploadScreen).showError(TestHelper.throwable);
    }
}

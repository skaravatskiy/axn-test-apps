package com.rshtukaraxondevgroup.bookstest.view;

import android.os.Bundle;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rshtukaraxondevgroup.bookstest.R;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.presenter.BookPresenter;
import com.rshtukaraxondevgroup.bookstest.presenter.DetailsPresenter;
import com.rshtukaraxondevgroup.bookstest.repository.RepositoryProvider;

public class DetailsActivity extends MvpAppCompatActivity implements DetailsView {
    @InjectPresenter(type = PresenterType.GLOBAL)
    DetailsPresenter detailsPresenter;
    private TextView mTextViewUrl;
    private TextView mTextViewName;
    private TextView mTextViewIsbn;
    private TextView mTextViewAuthors;
    private TextView mTextViewNumberOfPages;
    private TextView mTextViewPublisher;
    private TextView mTextViewCountry;
    private TextView mTextViewMediaType;
    private TextView mTextViewReleased;
    private TextView mTextViewCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTextViewUrl = findViewById(R.id.text_view_url);
        mTextViewName = findViewById(R.id.text_view_name);
        mTextViewIsbn = findViewById(R.id.text_view_isbn);
        mTextViewAuthors = findViewById(R.id.text_view_authors);
        mTextViewNumberOfPages = findViewById(R.id.text_view_number_of_pages);
        mTextViewPublisher = findViewById(R.id.text_view_publisher);
        mTextViewCountry = findViewById(R.id.text_view_country);
        mTextViewMediaType = findViewById(R.id.text_view_mediaType);
        mTextViewReleased = findViewById(R.id.text_view_released);
        mTextViewCharacters = findViewById(R.id.text_view_characters);

        String bookUrl = getIntent().getStringExtra("EXTRA_URL");

        detailsPresenter.init(bookUrl);
    }

    @Override
    public void initView(BookModel bookModel) {
        mTextViewUrl.setText("URL: " + bookModel.getUrl());
        mTextViewName.setText("NAME: " + bookModel.getName());
        mTextViewIsbn.setText("ISBN: " + bookModel.getIsbn());
        mTextViewAuthors.setText("AUTHORS: " + bookModel.getAuthors().toString());
        mTextViewNumberOfPages.setText("PAGES: " + bookModel.getNumberOfPages().toString());
        mTextViewPublisher.setText("PUBLISHER: " + bookModel.getPublisher());
        mTextViewCountry.setText("COUNTRY: " + bookModel.getCountry());
        mTextViewMediaType.setText("MEDIA TYPE: " + bookModel.getMediaType());
        mTextViewReleased.setText("RELEASED: " + bookModel.getReleased());
//        mTextViewCharacters.setText(bookModel.getCharacters().toString());
    }

    @ProvidePresenter(type = PresenterType.GLOBAL)
    DetailsPresenter provideRepositoryPresenter() {
        DetailsPresenter repositoryPresenter = new DetailsPresenter();
        repositoryPresenter.setDataStoreFactory(RepositoryProvider.getInstance(this));
        return repositoryPresenter;
    }
}

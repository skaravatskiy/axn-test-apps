package com.rshtukaraxondevgroup.bookstest.view;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.R;
import com.rshtukaraxondevgroup.bookstest.presenter.BookPresenter;
import com.rshtukaraxondevgroup.bookstest.repository.RepositoryProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements MainScreen, SwipeRefreshLayout.OnRefreshListener {
    @InjectPresenter(type = PresenterType.GLOBAL)
    BookPresenter bookPresenter;

    private RecyclerView mRecyclerView;
    private BookListAdapter bookListAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<BookModel> bookModelList = new ArrayList<>();
    private TextView textViewEmpty;
    private int page = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewEmpty = findViewById(R.id.empty);
        if (bookModelList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
        progressBar = findViewById(R.id.progressBar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        bookListAdapter = new BookListAdapter(this, this);
        mRecyclerView.setAdapter(bookListAdapter);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        bookModelList = new ArrayList<>();
        bookListAdapter.deleteList();
        setUpLoadMoreListener();
        bookPresenter.getBooksList(page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onRefresh() {
        bookModelList = new ArrayList<>();
        bookListAdapter.deleteList();
        page = 1;
        if (bookModelList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
        }
        bookPresenter.getBooksList(page);
        swipeRefreshLayout.setRefreshing(false);
    }

    @ProvidePresenter(type = PresenterType.GLOBAL)
    BookPresenter provideRepositoryPresenter() {
        BookPresenter repositoryPresenter = new BookPresenter();
        repositoryPresenter.setBookRepository(RepositoryProvider.getInstance(this));
        return repositoryPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sortByNumberOfPage:
                sortByNumberOfPage();
                break;
            case R.id.sortByPublisher:
                sortByPublisher();
                break;
            case R.id.sortByCountry:
                sortByCountry();
                break;
        }
        return false;
    }

    @Override
    public void addBooksList(List<BookModel> list) {
        bookModelList.addAll(list);
        bookListAdapter.addItems(list);
        loading = false;
        progressBar.setVisibility(View.INVISIBLE);
        if (!bookModelList.isEmpty()) {
            textViewEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void setBookList(List<BookModel> list) {
        bookModelList = list;
        bookListAdapter.setList(list);
        if (bookModelList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(BookModel linkModel) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("EXTRA_URL", linkModel.getUrl());
        startActivity(intent);
    }

    @Override
    public void onLongClick(BookModel bookModel) {
        bookListAdapter.deleteList();
        bookPresenter.deleteItem(bookModel);
    }

    private void setUpLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = mLayoutManager.getItemCount();
                lastVisibleItem = mLayoutManager
                        .findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + 1)) {
                    page++;
                    bookPresenter.getBooksList(page);
                    progressBar.setVisibility(View.VISIBLE);
                    loading = true;
                }
            }
        });
    }

    public void sortByNumberOfPage() {
        if (!bookModelList.isEmpty()) {
            Collections.sort(bookModelList, BookModel.COMPARE_BY_NUMBER_OF_PAGE);
            bookListAdapter.setList(bookModelList);
        }
    }

    public void sortByPublisher() {
        if (!bookModelList.isEmpty()) {
            Collections.sort(bookModelList, BookModel.COMPARE_BY_PUBLISHER);
            bookListAdapter.setList(bookModelList);
        }
    }

    public void sortByCountry() {
        if (!bookModelList.isEmpty()) {
            Collections.sort(bookModelList, BookModel.COMPARE_BY_COUNTRY);
            bookListAdapter.setList(bookModelList);
        }
    }

}

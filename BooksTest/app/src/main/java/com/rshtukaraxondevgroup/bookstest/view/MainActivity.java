package com.rshtukaraxondevgroup.bookstest.view;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.R;
import com.rshtukaraxondevgroup.bookstest.presenter.BookPresenter;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends MvpAppCompatActivity implements MainScreen, SwipeRefreshLayout.OnRefreshListener {
    private final int VISIBLE_THRESHOLD = 1;
    @InjectPresenter
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
    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
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
        bookListAdapter = new BookListAdapter(this, getApplicationContext());
        mRecyclerView.setAdapter(bookListAdapter);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        setUpLoadMoreListener();
        subscribeForData();
    }

    @Override
    public void onRefresh() {
        compositeDisposable.clear();
        bookListAdapter.deleteList();
        page = 1;
        if (bookModelList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
        }
        subscribeForData();
        swipeRefreshLayout.setRefreshing(false);
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
    public void showBooksList(List<BookModel> list) {
        bookModelList = list;
        bookListAdapter.setList(list);
        if (bookModelList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
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

    private Flowable<List<BookModel>> dataFromNetwork(final int page) {
        if (isNetworkAvailable()) {
            return bookPresenter.getBooksList(page)
                    .toFlowable()
                    .doOnError(this::showError)
                    .doAfterNext(list -> bookPresenter.insertToDB(list));
        } else {
            bookListAdapter.deleteList();
            Toast.makeText(getApplicationContext(), "Connection not available.", Toast.LENGTH_LONG).show();
            return bookPresenter.getBooksListFromDB();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                if (!loading
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    page++;
                    paginator.onNext(page);
                    loading = true;
                }
            }
        });
    }

    private void subscribeForData() {

        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap(new Function<Integer, Publisher<List<BookModel>>>() {
                    @Override
                    public Publisher<List<BookModel>> apply(@NonNull Integer page) {
                        loading = true;
                        progressBar.setVisibility(View.VISIBLE);
                        return dataFromNetwork(page);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> items) {
                        bookListAdapter.addItems(items);
                        loading = false;
                        bookModelList.addAll(items);
                        if (bookModelList.isEmpty()) {
                            textViewEmpty.setVisibility(View.VISIBLE);
                        } else {
                            textViewEmpty.setVisibility(View.GONE);
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        compositeDisposable.add(disposable);

        paginator.onNext(page);

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

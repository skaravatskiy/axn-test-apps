package com.rshtukaraxondevgroup.bookstest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.rshtukaraxondevgroup.bookstest.database.StringConverter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@TypeConverters({StringConverter.class})
public class BookModel {
    @PrimaryKey
    @NonNull
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("authors")
    private List<String> authors;
    @SerializedName("numberOfPages")
    private int numberOfPages;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("country")
    private String country;
    @SerializedName("mediaType")
    private String mediaType;
    @SerializedName("released")
    private String released;
    @SerializedName("characters")
    private List<String> characters = null;
    @SerializedName("povCharacters")
    private List<String> povCharacters = null;

    public BookModel(@NonNull String url, String name, String isbn, List<String> authors,
                     Integer numberOfPages, String publisher, String country, String mediaType,
                     String released, List<String> characters, List<String> povCharacters) {
        this.url = url;
        this.name = name;
        this.isbn = isbn;
        this.authors = authors;
        this.numberOfPages = numberOfPages;
        this.publisher = publisher;
        this.country = country;
        this.mediaType = mediaType;
        this.released = released;
        this.characters = characters;
        this.povCharacters = povCharacters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return numberOfPages == bookModel.numberOfPages &&
                Objects.equals(url, bookModel.url) &&
                Objects.equals(name, bookModel.name) &&
                Objects.equals(isbn, bookModel.isbn) &&
                Objects.equals(authors, bookModel.authors) &&
                Objects.equals(publisher, bookModel.publisher) &&
                Objects.equals(country, bookModel.country) &&
                Objects.equals(mediaType, bookModel.mediaType) &&
                Objects.equals(released, bookModel.released) &&
                Objects.equals(characters, bookModel.characters) &&
                Objects.equals(povCharacters, bookModel.povCharacters);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, name, isbn, authors, numberOfPages, publisher, country, mediaType, released, characters, povCharacters);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public List<String> getPovCharacters() {
        return povCharacters;
    }

    public void setPovCharacters(List<String> povCharacters) {
        this.povCharacters = povCharacters;
    }

    public static final Comparator<BookModel> COMPARE_BY_NUMBER_OF_PAGE = (o1, o2) -> o1.getNumberOfPages() - o2.getNumberOfPages();

    public static final Comparator<BookModel> COMPARE_BY_PUBLISHER = (o1, o2) -> o1.getPublisher().compareTo(o2.getPublisher());

    public static final Comparator<BookModel> COMPARE_BY_COUNTRY = (o1, o2) -> o1.getCountry().compareTo(o2.getCountry());

}

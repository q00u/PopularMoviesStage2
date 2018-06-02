package com.example.phoen.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

//based on https://github.com/udacity/android-custom-arrayadapter/blob/parcelable/app/src/main/java/demo/example/com/customarrayadapter/AndroidFlavor.java
public class Movie implements Parcelable {
    //from https://developers.themoviedb.org/3/movies/get-popular-movies
    private int mVoteCount;
    private int mID;
    private double mVoteAverage;
    private String mTitle;
    private double mPopularity;
    private String mPosterPath;
    private String mOriginalTitle;
    private String mBackdropPath;
    private String mOverview;
    private String mReleaseDate;

    public Movie(int VoteCount, int ID, double VoteAverage, String Title, double Popularity,
                 String PosterPath, String OriginalTitle, String BackdropPath, String Overview,
                 String ReleaseDate) {
        this.mVoteCount=VoteCount;
        this.mID=ID;
        this.mVoteAverage=VoteAverage;
        this.mTitle=Title;
        this.mPopularity=Popularity;
        this.mPosterPath=PosterPath;
        this.mOriginalTitle=OriginalTitle;
        this.mBackdropPath=BackdropPath;
        this.mOverview=Overview;
        this.mReleaseDate=ReleaseDate;
    }

    //for just-poster placeholders
    public Movie(int ID, String PosterPath, String Title) {
        this.mID=ID;
        this.mPosterPath=PosterPath;
        this.mTitle=Title;
    }

    private Movie(Parcel in){
        mVoteCount=in.readInt();
        mID=in.readInt();
        mVoteAverage=in.readDouble();
        mTitle=in.readString();
        mPopularity=in.readDouble();
        mPosterPath=in.readString();
        mOriginalTitle=in.readString();
        mBackdropPath=in.readString();
        mOverview=in.readString();
        mReleaseDate=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mVoteCount);
        dest.writeInt(mID);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mTitle);
        dest.writeDouble(mPopularity);
        dest.writeString(mPosterPath);
        dest.writeString(mOriginalTitle);
        dest.writeString(mBackdropPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //generate getters and setters
    public int getVoteCount() { return mVoteCount; }

    public void setVoteCount(int mVoteCount) { this.mVoteCount = mVoteCount; }

    public int getID() { return mID; }

    public void setID(int mID) { this.mID = mID; }

    public double getVoteAverage() { return mVoteAverage; }

    public void setVoteAverage(double mVoteAverage) { this.mVoteAverage = mVoteAverage; }

    public String getTitle() { return mTitle; }

    public void setTitle(String mTitle) { this.mTitle = mTitle; }

    public double getPopularity() { return mPopularity; }

    public void setPopularity(double mPopularity) { this.mPopularity = mPopularity; }

    public String getPosterPath() { return mPosterPath; }

    public void setPosterPath(String mPosterPath) { this.mPosterPath = mPosterPath; }

    public String getOriginalTitle() { return mOriginalTitle; }

    public void setOriginalTitle(String mOriginalTitle) { this.mOriginalTitle = mOriginalTitle; }

    public String getBackdropPath() { return mBackdropPath; }

    public void setBackdropPath(String mBackdropPath) { this.mBackdropPath = mBackdropPath; }

    public String getOverview() { return mOverview; }

    public void setOverview(String mOverview) { this.mOverview = mOverview; }

    public String getReleaseDate() { return mReleaseDate; }

    public void setReleaseDate(String mReleaseDate) { this.mReleaseDate = mReleaseDate; }
}

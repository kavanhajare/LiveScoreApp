package com.example.karanc.testapp.Db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by karanc on 02-11-2017.
 */
@Table(name = "Photos")
public class Photos extends Model{

@Column(name = "albumId")
@SerializedName("albumId")
public int albumId;

@Column(name = "title")
@SerializedName("title")
public String title;

@Column(name = "url")
@SerializedName("url")
public String url;

@Column(name = "thumbnailUrl")
@SerializedName("thumnbailUrl")
public String thumbnailUrl;

    public Photos() {
    }

    public Photos(int albumId, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

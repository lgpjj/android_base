package com.lgpgit.open.androidbase.httpcontant;

import com.lgpgit.open.toolutils.entiy.HttpResquest;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author lugp
 * @date 2019/2/18
 */
public interface Crash_dialogService {

    @Multipart
    @POST("uploadFile")
    Observable<HttpResquest<Object>> postEx(@Part("fileKey\"; filename=\"ex.txt") RequestBody fileBody);
}

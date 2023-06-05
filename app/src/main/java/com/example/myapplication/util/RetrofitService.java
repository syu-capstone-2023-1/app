package com.example.myapplication.util;

import com.example.myapplication.dto.BarcodeInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("api/{key}/{serviceId}/{dataType}/{startIdx}/{endIdx}/{BAR_CD}")
    Call<String> getBarcodeInfo(
            @Path("key") String key,
            @Path("serviceId") String serviceId,
            @Path("dataType") String dataType,
            @Path("startIdx") int startIdx,
            @Path("endIdx") int endIdx,
            @Path("BAR_CD") String BAR_CD
    );
}

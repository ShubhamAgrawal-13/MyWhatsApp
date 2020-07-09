package com.example.mywhatsapp.Fragments;

import com.example.mywhatsapp.Notifications.MyResponse;
import com.example.mywhatsapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAP34pfoI:APA91bEN9C7GcY1rERgFVWTldvMYHlegr5wcELFPX8AvOaONWXM-o4M43zjRcph40GnB_CwujaPzBC0lVoYTdpbjBPmPt2OZoHVVGvIjaIthdcWcVWD-0YA0DeGCgu-R0ifN9lW-nqRH"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

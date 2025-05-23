package com.example.branchapp.di

import android.content.Context
import com.example.branchapp.network.BranchApi
import com.example.branchapp.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext // Provides the Application context for injection
    }

//    //checks Network Connectivity
//    fun isInternetAvailable(context: Context): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        if (capabilities != null) {
//            if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
//                return true
//            }
//        }
//        return false
//    }
//
//    //CacheInterceptor by implementing Interceptor
//    // and we have a CacheControl builder that is used to provide the header for the Cache-Control
//    class CacheInterceptor : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val response: Response = chain.proceed(chain.request())
//            val cacheControl = CacheControl.Builder()
//                .maxAge(1, TimeUnit.MINUTES)  //Caches network calls for 1 day
//                .build()
//            return response.newBuilder()
//                .header("Cache-Control", cacheControl.toString())
//                .build()
//        }
//    }
//
//    //create a ForceCacheInterceptor in addition to the above one
//    //(CacheInterceptor, only if Cache-Control header is not enabled from the server).
//    class ForceCacheInterceptor(private val context: Context) : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val builder: Request.Builder = chain.request().newBuilder()
//            if (!isInternetAvailable(context)) {
//                builder.cacheControl(CacheControl.FORCE_CACHE);
//            }
//            return chain.proceed(builder.build());
//        }
//    }

    //Add this Interceptors to the OkHttpClient
    @Provides
    @Singleton
    fun provideHttpClient(context: Context): OkHttpClient { // Inject Context
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize) // Use injected context

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(cache)
//            .addNetworkInterceptor(CacheInterceptor())
//            .addInterceptor(ForceCacheInterceptor(context)) // Pass context to the interceptor
            .addNetworkInterceptor(logging)
            .build()
    }

    //create a moshi instance
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun providesBranchApi(okHttpClient: OkHttpClient): BranchApi {
        // inject Okhttp client instead of creating in here
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(BranchApi::class.java)


    }

}
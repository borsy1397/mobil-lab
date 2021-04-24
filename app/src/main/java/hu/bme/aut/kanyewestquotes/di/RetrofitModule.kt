package hu.bme.aut.kanyewestquotes.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.kanyewestquotes.network.MockInterceptor
import hu.bme.aut.kanyewestquotes.network.QuotesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    @Singleton
    @Named("MockInterceptor")
    fun provideMockInterceptor(): Interceptor = MockInterceptor()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("MockInterceptor") mockInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MockInterceptor())
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.kanye.rest/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideQuotesApi(retrofit: Retrofit.Builder): QuotesApi {
        return retrofit
            .build()
            .create(QuotesApi::class.java)
    }

}

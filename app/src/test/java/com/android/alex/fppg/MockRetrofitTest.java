package com.android.alex.fppg;

import com.android.alex.fppg.model.Players;
import com.android.alex.fppg.network.ApiManager;
import com.android.alex.fppg.network.FppgService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * I never used Mockito before and I know it's a very good way to test HTTP calls with Android.
 * So it's not working as I wanted but I've started to have a look on some articles and
 * JakeWharton's GitHub to try to understand how Mockiot with Retrofit 2 is working and how
 * create a Mock of the Api call interface and and the retrofit service.
 */

public final class MockRetrofitTest {
  private final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://cdn.rawgit.com/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/raw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json/").build();
  private final NetworkBehavior behavior = NetworkBehavior.create();
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Mock
  private ApiManager mockApi;
  @Mock
  private FppgService mockSerivce;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockApi = mockSerivce.getClient().create(ApiManager.class);
  }

//  @Test
//  public void getPlayersList_call() throws Exception {
//    Players test = (Players) Mockito.verify(mockApi).getPlayersList();
//  }

  @Test public void retrofitNullThrows() {
    try {
      new MockRetrofit.Builder(null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("retrofit == null");
    }
  }

  @Test public void retrofitPropagated() {
    MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
    assertThat(mockRetrofit.retrofit()).isSameAs(retrofit);
  }

  @Test public void networkBehaviorNullThrows() {
    MockRetrofit.Builder builder = new MockRetrofit.Builder(retrofit);
    try {
      builder.networkBehavior(null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("behavior == null");
    }
  }

  @Test public void networkBehaviorDefault() {
    MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
    assertThat(mockRetrofit.networkBehavior()).isNotNull();
  }

  @Test public void networkBehaviorPropagated() {
    MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
        .networkBehavior(behavior)
        .build();
    assertThat(mockRetrofit.networkBehavior()).isSameAs(behavior);
  }

  @Test public void backgroundExecutorNullThrows() {
    MockRetrofit.Builder builder = new MockRetrofit.Builder(retrofit);
    try {
      builder.backgroundExecutor(null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("executor == null");
    }
  }

  @Test public void backgroundExecutorDefault() {
    MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
    assertThat(mockRetrofit.backgroundExecutor()).isNotNull();
  }

  @Test public void backgroundExecutorPropagated() {
    MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
        .backgroundExecutor(executor)
        .build();
    assertThat(mockRetrofit.backgroundExecutor()).isSameAs(executor);
  }
}
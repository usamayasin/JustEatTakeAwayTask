package com.example.consumerapptask.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.consumerapptask.rule.MainCoroutinesRule
import com.example.consumerapptask.data.service.ApiService
import com.example.consumerapptask.testUtil.MockDataUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryImplTest {

    // Subject under test
    private lateinit var repository: RepositoryImpl

    @MockK
    private lateinit var apiService: ApiService

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getRestaurants() gives list of Restaurants`() = runBlocking {
        // Given
        repository = RepositoryImpl(apiService)
        val mockRestaurantsList = MockDataUtil.getMockRestaurants()

        // When
        coEvery { apiService.getAllRestaurants() }
            .returns(mockRestaurantsList)

        // Invoke
        val repositoryResponse = repository.getRestaurants()

        // Then
        MatcherAssert.assertThat(repositoryResponse, CoreMatchers.notNullValue())

        val restaurantsFlow = repositoryResponse.first()
        MatcherAssert.assertThat(restaurantsFlow, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            restaurantsFlow.restaurants.size,
            CoreMatchers.`is`(mockRestaurantsList.restaurants.size)
        )

        coVerify(exactly = 1) { apiService.getAllRestaurants() }
        confirmVerified(apiService)
    }
}
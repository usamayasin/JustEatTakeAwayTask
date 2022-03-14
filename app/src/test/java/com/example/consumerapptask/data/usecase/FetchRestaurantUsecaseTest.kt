package com.example.consumerapptask.data.usecase

import com.example.consumerapptask.testUtil.MockDataUtil
import com.example.consumerapptask.data.model.SortingOption
import com.example.consumerapptask.data.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FetchRestaurantUsecaseTest {

    @MockK
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchRestaurantsUseCase to give Restaurant's`() = runBlocking {
        // Given
        val fetchRestaurantUseCase = FetchRestaurantUsecase(repository)
        val mockRestaurantsData = MockDataUtil.getMockRestaurants()

        // When
        coEvery { repository.getRestaurants() }
            .returns(flowOf(mockRestaurantsData))

        // Invoke
        val restaurants =
            fetchRestaurantUseCase(fetchRestaurantUseCase.getComparatorBy(SortingOption.BEST_MATCH))

        // Then
        MatcherAssert.assertThat(restaurants, CoreMatchers.notNullValue())
    }

    @Test
    fun `test search restaurant by name`() = runBlocking {

        // Given
        val fetchRestaurantUseCase = FetchRestaurantUsecase(repository)
        val mockRestaurantsData = MockDataUtil.getMockRestaurants()

        // Invoke
        val searchResult =
            fetchRestaurantUseCase.searchByName("Pakistani Kitchen",mockRestaurantsData.restaurants)

        // Then
        MatcherAssert.assertThat(searchResult, CoreMatchers.notNullValue())
        Assert.assertEquals(searchResult[0].name, "Pakistani Kitchen")
    }

}
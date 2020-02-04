/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.repository

import com.olehka.datakick.repository.local.LocalRepository
import com.olehka.datakick.repository.remote.RemoteRepository
import com.olehka.datakick.utilities.ConnectivityAgent
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import java.util.concurrent.Executor


class ProductsRepositoryShould {

    private val connectivityAgent = mock(ConnectivityAgent::class.java)
    private val localRepository = mock(LocalRepository::class.java)
    private val remoteRepository = mock(RemoteRepository::class.java)
    private val executor = mock(Executor::class.java)

    private val productsRepository = ProductsRepository(
            connectivityAgent,
            localRepository,
            remoteRepository,
            executor
    )

    @Test
    fun returnLocalRepositoryProducts_whenInternetIsUnavailable() {
        given(connectivityAgent.isDeviceConnectedToInternet()).willReturn(false)

        productsRepository.getProducts()

        verify(localRepository).getProducts()
        verify(remoteRepository, never()).getProducts()
    }
}